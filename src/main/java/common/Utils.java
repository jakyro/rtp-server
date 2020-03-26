package common;

import model.MyEvent;

public class Utils {
    public static void safeSleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }

    public static String getForecastByEvent(MyEvent event) {
        if (event.getTemp() < -2 && event.getLight() < 128 && event.getPressure() < 720) {
            return "SNOW";
        }
        if (event.getTemp() < -2 && event.getLight() > 128 && event.getPressure() < 680) {
            return "WET_SNOW";
        }
        if (event.getTemp() < -8) {
            return "SNOW";
        }
        if (event.getTemp() < -15 && event.getWind() > 45) {
            return "BLIZZARD";
        }
        if (event.getTemp() > 0 && event.getPressure() < 710 && event.getHumidity() > 70 && event.getWind() < 20) {
            return "SLIGHT_RAIN";
        }
        if (event.getTemp() > 0 && event.getPressure() < 690 && event.getHumidity() > 70 && event.getWind() < 20) {
            return "HEAVY_RAIN";
        }
        if (event.getTemp() > 30 && event.getPressure() < 770 && event.getHumidity() > 80 && event.getLight() > 192) {
            return "HOT";
        }
        if (event.getTemp() > 30 && event.getPressure() < 770 && event.getHumidity() > 50 && event.getLight() > 192) {
            return "CONVECTION_OVEN";
        }
        if (event.getTemp() > 25 && event.getPressure() < 750 && event.getHumidity() > 70 && event.getLight() > 192 && event.getWind() < 10) {
            return "WARM";
        }
        if (event.getTemp() > 25 && event.getPressure() < 750 && event.getHumidity() > 70 && event.getLight() > 192 && event.getWind() > 10) {
            return "SLIGHT_BREEZE";
        }
        if (event.getLight() < 128) {
            return "CLOUDY";
        }
        if (event.getTemp() > 30 && event.getPressure() < 660 && event.getHumidity() > 85 && event.getWind() > 45) {
            return "MONSOON";
        }
        return "JUST_A_NORMAL_DAY";
    }
}


//{
//    "the_weather_forecast_rules":
//        [
    //          "if temperature < -2 and light < 128 and athm_pressure < 720 then SNOW",
    //          "if temperature < -2 and light > 128 and athm_pressure < 680 then WET_SNOW",
    //          "if temperature < -8 then SNOW",
    //          "if temperature < -15 and wind_speed > 45 then BLIZZARD",
    //          "if temperature > 0 and athm_pressure < 710 and humidity > 70 and wind_speed < 20 then SLIGHT_RAIN",
    //          "if temperature > 0 and athm_pressure < 690 and humidity > 70 and wind_speed > 20 then HEAVY_RAIN",
    //          "if temperature > 30 and athm_pressure < 770 and humidity > 80 and light > 192 then HOT",
    //          "if temperature > 30 and athm_pressure < 770 and humidity > 50 and light > 192 and wind_speed > 35 then CONVECTION_OVEN",
    //          "if temperature > 25 and athm_pressure < 750 and humidity > 70 and light < 192 and wind_speed < 10 then WARM",
    //          "if temperature > 25 and athm_pressure < 750 and humidity > 70 and light < 192 and wind_speed > 10 then SLIGHT_BREEZE",
    //          "if light < 128 then CLOUDY",
    //          "if temperature > 30 and athm_pressure < 660 and humidity > 85 and wind_speed > 45 then MONSOON",
    //          "if nothing matches then JUST_A_NORMAL_DAY"
//        ],
//        "parameters":
//        [
//            "temperature_sensors_celsius",
//            "humidity_sensors_percent",
//            "wind_speed_sensors_kmh",
//            "athm_pressure_sensor_mmhg",
//            "light_sensor_analog",
//            "unix_timestamp_us"
//        ],
//        "general_description":"To start streaming data, access the /iot route. Data is in SSE/EventSource format."
//}
