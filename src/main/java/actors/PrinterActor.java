package actors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Packet;

public class PrinterActor extends MyActor {
    public static void main(String[] args) {
        new PrinterActor(args[0], Long.parseLong(args[1]));
    }

    PrinterActor(String name, long pid) {
        super(name, pid);
    }

    @Override
    public void parseMessage(Packet packet) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(packet.getData());
        String prettyJsonString = gson.toJson(je);
        sendMessage("supervisor", prettyJsonString);
    }
}
