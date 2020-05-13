package model.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

//legacy_sensors  "<SensorReadings unix_timestamp_100us='15891281172654'>    <humidity_percent>        <value>53.33022667388752</value>        <value>22.13450251502518</value>    </humidity_percent>    <temperature_celsius>        <value>35.401031722216246</value>        <value>10.978602431978342</value>    </temperature_celsius></SensorReadings>"
@JacksonXmlRootElement(localName = "SensorReadings")
public class MyLegacySensorReceive {
    @JsonProperty("humidity_percent")
    List<Double> humidity;

    @JsonProperty("temperature_celsius")
    List<Double> temperature;

    @JacksonXmlProperty(isAttribute = true, localName = "unix_timestamp_100us")
    long timestamp;

    public Double getHumidity() {
        return (humidity.get(0) + humidity.get(1)) / 2;
    }

    public Double getTemperature() {
        return (temperature.get(0) + temperature.get(1)) / 2;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
