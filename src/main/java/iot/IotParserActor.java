package iot;

import actors.MyActor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ModelAdapter;
import model.Packet;
import model.receive.MyIotReceive;

import java.io.IOException;

import static common.Utils.safeSleep;

public class IotParserActor extends MyActor {
    public static final String host_name = "iot_parser";
    public static final String publish_name = "iot_publisher";
    int workersCount = 0;
    int currentWorker = 0;
    long previousQueueSize = 0;
    int maxWorkers = 3;

    public static void main(String[] args) {
        new IotParserActor(args[0], Long.parseLong(args[1]));
    }

    IotParserActor(String name, long pid) {
        super(name, pid);
    }

    @Override
    public void parseMessage(Packet packet) {
        spawnOrRemoveWorker();
        deliverPacketToParser(packet);
    }

    private void spawnOrRemoveWorker() {
        if (this.name.equals(host_name)) {
            if (isQueueGrowing() && workersCount < maxWorkers) {
                spawnWorker();
            }
        }
    }

    private void deliverPacketToParser(Packet packet) {
        if (currentWorker > workersCount) {
            currentWorker = 0;
        }
        if (currentWorker == 0) {
            parseInternal(packet);
        } else {
            String workerName = prefix() + currentWorker;
            if (!isActorAlive(workerName)) {
                spawn(workerName, IotParserActor.class);
            }
            sendMessage(workerName, packet.getData());
        }
        currentWorker++;
    }

    private String prefix() {
        return host_name + "_internal";
    }

    private void parseInternal(Packet packet) {
        safeSleep(1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        try {
            MyIotReceive e = mapper.readValue(packet.getData(), MyIotReceive.class);
            sendToPublisher(formatString(e));
        } catch (IOException e) {
        }
    }

    private String formatString(MyIotReceive e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ModelAdapter.convert(e));
        } catch (JsonProcessingException ignored) {
        }
        return "";
    }

    private boolean isQueueGrowing() {
        long qs = queueSize();
        boolean isGrowing = qs > previousQueueSize;
        previousQueueSize = qs;
        return isGrowing;
    }

    private void spawnWorker() {
        String workerName = prefix() + (workersCount + 1);
        spawn(workerName, IotParserActor.class);
        workersCount++;
    }

    private void sendToSupervisor(String msg) {
        sendMessage("supervisor", msg);
    }

    private void sendToPublisher(String msg) {
        sendMessage(publish_name, msg);
    }

    int a = 250;

    @Override
    public synchronized boolean shouldSelfDestroy() {
        if (this.name.equals(host_name)) {
            return false;
        }
        long qs = queueSize();
        if (qs == 0) {
            a--;
        } else {
            a = 250;
        }
        return a == 0;
    }
}
