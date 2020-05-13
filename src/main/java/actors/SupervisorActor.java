package actors;

import model.Packet;

public class SupervisorActor extends MyActor {

    public SupervisorActor(long pid) {
        super("supervisor", pid);
    }

    @Override
    public void parseMessage(Packet packet) {
//        if (packet.getSource().equals("logger")) {
//            System.out.println(packet.getData());
//        }
        System.out.println("New message in queue for supervisor");
        System.out.println(packet.getData());
    }
}
