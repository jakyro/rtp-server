package common;

import actors.MyActor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessCreator {
    public static Process createActor(Class<? extends MyActor> clazz, String name) throws IOException {
        long currentProcess = ProcessHandle.current().pid();
        return exec(clazz, List.of(name, String.valueOf(currentProcess)));
    }

    private static Process exec(Class<? extends MyActor> clazz, List<String> args) throws IOException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = clazz.getName();
        List<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-cp");
        command.add(classpath);
        command.add(className);
        if (args != null) {
            command.addAll(args);
        }
        ProcessBuilder builder = new ProcessBuilder(command);
        return builder.start();
    }
}
