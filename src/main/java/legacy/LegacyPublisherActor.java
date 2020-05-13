package legacy;

import actors.MyActor;
import common.Publisher;
import model.Packet;

public class LegacyPublisherActor extends MyActor {
    Publisher publisher;

    public static void main(String[] args) {
        new LegacyPublisherActor(args[0], Long.parseLong(args[1]));
    }

    LegacyPublisherActor(String name, long pid) {
        super(name, pid);
        publisher = new Publisher();
    }

    @Override
    public void parseMessage(Packet packet) {
        publisher.publish("legacy_stream", packet.getData());
    }
}
