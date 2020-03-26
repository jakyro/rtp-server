package model;

public class Packet extends SerializablePacket {
    String data;
    String source;
    String destination;

    public Packet(String source, String destination, String message) {
        this.data = message;
        this.source = source;
        this.destination = destination;
    }

    public String getData() {
        return data;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
