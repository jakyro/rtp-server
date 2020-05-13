package main;

import actors.*;
import iot.IotParserActor;
import iot.IotPublisherActor;
import iot.IotRequestActor;
import legacy.LegacyParserActor;
import legacy.LegacyPublisherActor;
import legacy.LegacyRequestActor;
import sensors.SensorsParserActor;
import sensors.SensorsPublisherActor;
import sensors.SensorsRequestActor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MyActor supervisorActor = new SupervisorActor(ProcessHandle.current().pid());
        supervisorActor.spawn("iot_source", IotRequestActor.class);
        supervisorActor.spawn("sensors_source", SensorsRequestActor.class);
        supervisorActor.spawn("legacy_source", LegacyRequestActor.class);
        supervisorActor.spawn(IotParserActor.host_name, IotParserActor.class);
        supervisorActor.spawn(SensorsParserActor.host_name, SensorsParserActor.class);
        supervisorActor.spawn(LegacyParserActor.host_name, LegacyParserActor.class);
        supervisorActor.spawn("iot_publisher", IotPublisherActor.class);
        supervisorActor.spawn("sensors_publisher", SensorsPublisherActor.class);
        supervisorActor.spawn("legacy_publisher", LegacyPublisherActor.class);
        while (true) ;
    }
}

