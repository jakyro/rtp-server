package model.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

//sensors {"light_sensor_1": 175.0,"light_sensor_2": 163.0,"unix_timestamp_100us": 15891280085386}
@JsonRootName(value = "message")
public class MySensorReceive {
    @JsonProperty("light_sensor_1")
    double light1;
    @JsonProperty("light_sensor_2")
    double light2;
    @JsonProperty("unix_timestamp_100us")
    long timestamp;

    public double getLight() {
        return (light1 + light2) / 2;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
