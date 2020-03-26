package actors;

import model.Packet;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerActor extends MyActor {
    public static void main(String[] args) {
        new LoggerActor(args[0], Long.parseLong(args[1]));
    }

    Logger logger;

    LoggerActor(String name, long pid) {
        super(name, pid);
        logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("My first log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void parseMessage(Packet packet) {
        logger.info(packet.getData());
    }
}
