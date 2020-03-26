package main;

import actors.*;

public class Main {


    public static void main(String[] args) {
        MyActor supervisorActor = new SupervisorActor(ProcessHandle.current().pid());
        supervisorActor.spawn("request", RequestHandlerActor.class);
        supervisorActor.spawn("aggregator", AggregatorActor.class);
        supervisorActor.spawn("parser", ParserActor.class);
        supervisorActor.spawn("logger", LoggerActor.class);
        supervisorActor.spawn("printer", PrinterActor.class);
        while (true) ;
    }
}

