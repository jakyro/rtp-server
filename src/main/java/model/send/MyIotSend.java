package model.send;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyIotSend {
    @JsonProperty("wind")
    double wind;
    @JsonProperty("pressure")
    double pressure;
    @JsonProperty("timestamp")
    long timestamp;

    public MyIotSend(double wind, double pressure, long timestamp) {
        this.wind = wind;
        this.pressure = pressure;
        this.timestamp = timestamp;
    }
}
