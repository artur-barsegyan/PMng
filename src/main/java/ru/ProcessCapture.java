package ru;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ProcessCapture {
    static private ProcessCapture instance;
    static public ProcessCapture getInstance() {
        if (instance == null) {
            instance = new ProcessCapture();
        }

        return instance;
    }

    private ProcessCapture() {}

    public Collection<UsedApplication> capture() {
        Map<String, UsedApplication> processes = new HashMap<>();

        Instant now = Instant.now();
        ProcessHandle.allProcesses().forEach(process -> {
            String user = process.info().user().orElse("other");

            if (user.equals("a.barsegyan")) {
                String program = process.info().command().orElse("unknown");
                program = Path.of(program).getFileName().toString();
                Instant startTime = process.info().startInstant().get();

                if (processes.containsKey(program)) {
                    processes.get(program).usedTime += Duration.between(startTime, now).toSeconds();
                } else {
                    processes.put(program, new UsedApplication(program,
                            Duration.between(startTime, now).toSeconds()));
                }
            }
        });

        return processes.values();
    }
}
