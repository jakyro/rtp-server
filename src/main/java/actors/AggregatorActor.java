package actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Packet;
import model.ParsedData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MILLIS;

public class AggregatorActor extends MyActor {
    public static void main(String[] args) {
        new AggregatorActor(args[0], Long.parseLong(args[1]));
    }

    AggregatorActor(String name, long pid) {
        super(name, pid);
    }

    List<ParsedData> list = new ArrayList<>();
    LocalDateTime start = LocalDateTime.now();

    @Override
    void parseMessage(Packet packet) {
        if (isInterval()) {
            forecast();
            reset();
        }
        handlePacket(packet);
    }

    private void handlePacket(Packet packet) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ParsedData data = mapper.readValue(packet.getData(), ParsedData.class);
            list.add(data);
        } catch (IOException ignored) {
        }
    }

    private void reset() {
        list = new ArrayList<>();
        start = LocalDateTime.now();
    }

    private void forecast() {
        if (list == null || list.size() == 0) {
            return;
        }
        double t = list.stream().mapToDouble(ParsedData::getTemp).average().orElse(0);
        double h = list.stream().mapToDouble(ParsedData::getHumidity).average().orElse(0);
        double w = list.stream().mapToDouble(ParsedData::getWind).average().orElse(0);
        double p = list.stream().mapToDouble(ParsedData::getPressure).average().orElse(0);
        double l = list.stream().mapToDouble(ParsedData::getLight).average().orElse(0);
        String cast = mostFrequentStream(list.stream().map(ParsedData::getForecast).collect(Collectors.toList()));
        ParsedData aggr = new ParsedData(t, h, w, p, l, list.get(0).getTimestamp(), cast);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(aggr);
            publish(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void publish(String json) {
        sendMessage("printer", json);
        sendMessage("logger", json);
    }

    private boolean isInterval() {
        return start.until(LocalDateTime.now(), MILLIS) >= 500;
    }

    private static String mostFrequentStream(List<String> elements) {
        return elements.stream() // part 1
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream() // part 2
                .collect(Collectors.groupingBy(Map.Entry::getValue))
                .entrySet().stream() // part 3
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(v -> v.get(0).getKey())
                .orElse(null);
    }
}
