package model;

import model.receive.MyIotReceive;
import model.receive.MyLegacySensorReceive;
import model.receive.MySensorReceive;
import model.send.MyIotSend;
import model.send.MyLegacySensorSend;
import model.send.MySensorSend;

public class ModelAdapter {

    public static MySensorSend convert(MySensorReceive model) {
        return new MySensorSend(model.getLight(), model.getTimestamp());
    }

    public static MyIotSend convert(MyIotReceive model) {
        return new MyIotSend(model.getWind(), model.getPressure(), model.getTimestamp());
    }

    public static MyLegacySensorSend convert(MyLegacySensorReceive model) {
        return new MyLegacySensorSend(model.getTemperature(), model.getHumidity(), model.getTimestamp());
    }
}
