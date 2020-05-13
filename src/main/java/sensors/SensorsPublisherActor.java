package sensors;

import actors.MyActor;
import common.Publisher;
import model.Packet;

public class SensorsPublisherActor extends MyActor {
    Publisher publisher;

    public static void main(String[] args) {
        new SensorsPublisherActor(args[0], Long.parseLong(args[1]));
    }

    SensorsPublisherActor(String name, long pid) {
        super(name, pid);
        publisher = new Publisher();
    }

    @Override
    public void parseMessage(Packet packet) {
        publisher.publish("sensors_stream", packet.getData());
    }
}
