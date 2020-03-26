package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "message")
public class MyEvent {
    @JsonProperty("temperature_sensor_1")
    double temp1;
    @JsonProperty("temperature_sensor_2")
    double temp2;
    @JsonProperty("humidity_sensor_1")
    double humidity1;
    @JsonProperty("humidity_sensor_2")
    double humidity2;
    @JsonProperty("wind_speed_sensor_1")
    double wind1;
    @JsonProperty("wind_speed_sensor_2")
    double wind2;
    @JsonProperty("atmo_pressure_sensor_1")
    double pressure1;
    @JsonProperty("atmo_pressure_sensor_2")
    double pressure2;
    @JsonProperty("light_sensor_1")
    double light1;
    @JsonProperty("light_sensor_2")
    double light2;
    @JsonProperty("unix_timestamp_us")
    long timestamp;

    public double getTemp() {
        return (temp1 + temp2) / 2;
    }

    public double getHumidity() {
        return (humidity1 + humidity2) / 2;
    }

    public double getWind() {
        return (wind1 + wind2) / 2;
    }

    public double getPressure() {
        return (pressure1 + pressure2) / 2;
    }

    public double getLight() {
        return (light1 + light2) / 2;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
