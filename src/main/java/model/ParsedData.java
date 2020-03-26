package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParsedData {
    @JsonProperty("temp")
    double temp;
    @JsonProperty("humidity")
    double humidity;
    @JsonProperty("wind")
    double wind;
    @JsonProperty("pressure")
    double pressure;
    @JsonProperty("light")
    double light;
    @JsonProperty("timestamp")
    long timestamp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    @JsonProperty("forecast")
    String forecast;

    public ParsedData() {
    }

    public ParsedData(double temp, double humidity, double wind, double pressure, double light, long timestamp, String forecast) {
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.pressure = pressure;
        this.light = light;
        this.timestamp = timestamp;
        this.forecast = forecast;
    }
}
