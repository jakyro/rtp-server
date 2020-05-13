package model.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

//iot {"wind_speed_sensor_1": 1.1381093232323947,"wind_speed_sensor_2": 20.807904527826814,"atmo_pressure_sensor_1": 765.6639530020232,"atmo_pressure_sensor_2": 710.1660695349171,"unix_timestamp_100us": 15891277589516}
@JsonRootName(value = "message")
public class MyIotReceive {
    @JsonProperty("wind_speed_sensor_1")
    double wind1;
    @JsonProperty("wind_speed_sensor_2")
    double wind2;
    @JsonProperty("atmo_pressure_sensor_1")
    double pressure1;
    @JsonProperty("atmo_pressure_sensor_2")
    double pressure2;
    @JsonProperty("unix_timestamp_100us")
    long timestamp;

    public double getWind() {
        return (wind1 + wind2) / 2;
    }

    public double getPressure() {
        return (pressure1 + pressure2) / 2;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
