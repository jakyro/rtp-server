package model.send;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyLegacySensorSend {
    @JsonProperty("temp")
    double temp;
    @JsonProperty("humidity")
    double humidity;
    @JsonProperty("timestamp")
    long timestamp;

    public MyLegacySensorSend(double temp, double humidity, long timestamp) {
        this.temp = temp;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }
}
