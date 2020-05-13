package model.send;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MySensorSend {
    @JsonProperty("light")
    double light;
    @JsonProperty("timestamp")
    long timestamp;

    public MySensorSend(double light, long timestamp) {
        this.light = light;
        this.timestamp = timestamp;
    }
}
