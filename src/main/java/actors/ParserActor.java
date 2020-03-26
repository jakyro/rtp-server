package actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Utils;
import model.MyEvent;
import model.Packet;
import model.ParsedData;

import java.io.IOException;

import static common.Utils.safeSleep;

public class ParserActor extends MyActor {

    int workersCount = 0;
    int currentWorker = 0;
    long previousQueueSize = 0;

    public static void main(String[] args) {
        new ParserActor(args[0], Long.parseLong(args[1]));
    }

    ParserActor(String name, long pid) {
        super(name, pid);
    }

    @Override
    void parseMessage(Packet packet) {
        spawnOrRemoveWorker();
        deliverPacketToParser(packet);
    }

    private void spawnOrRemoveWorker() {
        if (this.name.equals("parser")) {
            if (isQueueGrowing() && workersCount < 5) {
                spawnWorker();
            }
        }
    }

    private void deliverPacketToParser(Packet packet) {
        if (currentWorker > workersCount) {
            currentWorker = 0;
        }
        if (workersCount > 10 && currentWorker == 0) {
            currentWorker = 1;
        }
        if (currentWorker == 0) {
            parseInternal(packet);
        } else {
            String workerName = "internal" + currentWorker;
            if (!isActorAlive(workerName)) {
                spawn(workerName, ParserActor.class);
            }
            sendMessage(workerName, packet.getData());
        }
        currentWorker++;
    }

    private void parseInternal(Packet packet) {
        safeSleep(1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        try {
            MyEvent e = mapper.readValue(packet.getData(), MyEvent.class);
            sendToAggregator(formatString(e));
        } catch (IOException e) {
//            e.printStackTrace();
            if (!this.name.equals("parser"))
                System.exit(0);

        }
    }

    private String formatString(MyEvent e) {
        String forecast = Utils.getForecastByEvent(e);
        ParsedData p = new ParsedData(e.getTemp(), e.getHumidity(), e.getWind(), e.getPressure(), e.getLight(), e.getTimestamp(), forecast);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(p);
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
        String workerName = "internal" + (workersCount + 1);
        spawn(workerName, ParserActor.class);
        workersCount++;
    }

    private void sendToAggregator(String msg) {
        sendMessage("aggregator", msg);
    }

    private void sendToSupervisor(String msg) {
        sendMessage("supervisor", msg);
    }

    int a = 250;

    @Override
    synchronized boolean shouldSelfDestroy() {
        if (this.name.equals("parser")) {
            return false;
        }
        long qs = queueSize();
        if (qs == 0) {
            a--;
        } else {
            a = 100;
        }
        return a == 0;
    }
}
