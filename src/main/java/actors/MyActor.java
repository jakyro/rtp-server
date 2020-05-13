package actors;

import common.ProcessCreator;
import model.ActorProcess;
import model.Packet;

import java.io.*;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static common.Utils.safeSleep;

abstract public class MyActor {
    public final String name;
    private final HashMap<String, ActorProcess> actors = new HashMap<>();
    private static final Queue<Packet> messageQueue = new ConcurrentLinkedQueue<>();

    public MyActor(String name, long pid) {
        this.name = name;
        listenSelfInputStream();
        parseMessageQueue();
        listenParentPid(pid);
    }

    protected long queueSize() {
        return messageQueue.size();
    }

    protected void sendMessage(String to, String message) {
        if (actors.containsKey(to)) {
            sendMessage(actors.get(to), createPacket(to, message));
        } else {
            sendSelfMessage(createPacket(to, message));
        }
    }

    protected boolean isActorAlive(String name) {
        return actors.containsKey(name) && actors.get(name).getProcess().isAlive();
    }

    protected void sendSelfMessage(Packet packet) {
        sendMessage(System.out, packet);
    }

    private void sendMessage(ActorProcess actor, Packet packet) {
        OutputStream os = actor.getProcess().getOutputStream();
        sendMessage(os, packet);
    }

    private void sendMessage(OutputStream os, Packet packet) {
        try {
            new ObjectOutputStream(os).writeObject(packet);
            os.flush();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    protected Packet createPacket(String destination, String message) {
        return new Packet(this.name, destination, message);
    }

    public void spawn(String identifier, Class<? extends MyActor> clazz) {
        actors.remove(identifier);
        try {
            Process p = ProcessCreator.createActor(clazz, identifier);
            actors.put(identifier, new ActorProcess(p, identifier));
            parseInputStream(p.getInputStream());
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public abstract void parseMessage(Packet packet);

    private void parseMessageQueue() {
        new Thread(() -> {
            while (true) {
                safeSleep(1);
                if (messageQueue.size() > 0) {
                    Packet packet = messageQueue.remove();
                    if (packet.getDestination().equals(this.name)) {
                        parseMessage(packet);
                    } else {
                        sendMessage(packet.getDestination(), packet.getData());
                    }
                }
            }
        }).start();
    }

    private void listenSelfInputStream() {
        parseInputStream(System.in);
    }

    private void parseInputStream(InputStream stream) {
        new Thread(() -> {
            while (true) {
                safeSleep(1);
                try {
                    ObjectInputStream ois = new ObjectInputStream(stream);
                    Packet packet = (Packet) ois.readObject();
                    messageQueue.add(packet);
                } catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void listenParentPid(long pid) {
        new Thread(() -> {
            while (true) {
                safeSleep(10);
                if (shouldSelfDestroy() || ProcessHandle.of(pid).isEmpty()) {
//                    "HARAKIRI";
                    System.exit(0);
                }
            }
        }).start();
    }

    public boolean shouldSelfDestroy() {
        return false;
    }
}
