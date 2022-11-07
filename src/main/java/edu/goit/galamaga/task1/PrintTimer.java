package edu.goit.galamaga.task1;

import java.time.*;
import java.util.Timer;
import java.util.TimerTask;

public class PrintTimer {

    public static void main(String[] args) {

        long startTime = Clock.systemUTC().millis();

        long programLifeTime;

        if (args.length > 0 && Long.parseLong(args[0]) > 0) {
            programLifeTime = Long.parseLong(args[0]) * 1000;
        } else {
            programLifeTime = 20000;
        }

        TimerTask printTimeDuration = new TimerTask() {
            @Override
            public void run() {
                Duration duration = Duration.ofMillis(Clock.systemUTC().millis() - startTime);
                System.out.printf("%d:%d:%d have passed since the start of the program%n",
                        duration.toHours(), duration.toMinutes(), duration.toSeconds());
            }
        };

        TimerTask print5SecMsg = new TimerTask() {
            @Override
            public void run() {
                System.out.println("5 seconds have passed");
            }
        };

        Timer timer1Sec = new Timer();
        Timer timer5Sec = new Timer();

        timer1Sec.schedule(printTimeDuration, 1, 1000);
        timer5Sec.schedule(print5SecMsg, 5000, 5000);

        try {
            Thread.sleep(programLifeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer1Sec.cancel();
        timer5Sec.cancel();

    }
}
