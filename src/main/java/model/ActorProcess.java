package model;

public class ActorProcess {
    Process process;
    String name;

    public ActorProcess(Process process, String name) {
        this.name = name;
        this.process = process;
    }

    public Process getProcess() {
        return process;
    }
}
