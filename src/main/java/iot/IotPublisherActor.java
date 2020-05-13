package iot;

import actors.MyActor;
import common.Publisher;
import model.Packet;

public class IotPublisherActor extends MyActor {
    Publisher publisher;

    public static void main(String[] args) {
        new IotPublisherActor(args[0], Long.parseLong(args[1]));
    }

    IotPublisherActor(String name, long pid) {
        super(name, pid);
        publisher = new Publisher();
    }

    @Override
    public void parseMessage(Packet packet) {
        publisher.publish("iot_stream", packet.getData());
    }
}
