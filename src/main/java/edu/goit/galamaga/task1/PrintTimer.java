package edu.goit.galamaga.task1;

import java.time.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrintTimer {

    private static final long startTime = Clock.systemUTC().millis();

    public static void main(String[] args) {

        long programLifeTime;

        if (args.length > 0 && Long.parseLong(args[0]) > 0) {
            programLifeTime = Long.parseLong(args[0]) * 1000;
        } else {
            programLifeTime = 20000;
        }

        var executor1sec = Executors.newSingleThreadScheduledExecutor();
        var executor5sec = Executors.newSingleThreadScheduledExecutor();

        executor1sec.scheduleAtFixedRate(() -> {
            Duration duration = Duration.ofMillis(Clock.systemUTC().millis() - startTime);
            System.out.printf("%d:%d:%d have passed since the start of the program%n",
                    duration.toHours(), duration.toMinutes(), duration.toSeconds());
        }, 0, 1, TimeUnit.SECONDS);

        executor5sec.scheduleAtFixedRate(() -> System.out.println("5 seconds have passed"),
                5, 5, TimeUnit.SECONDS);

        try {
            Thread.currentThread().join(programLifeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor1sec.shutdown();
        executor5sec.shutdown();

    }

}
