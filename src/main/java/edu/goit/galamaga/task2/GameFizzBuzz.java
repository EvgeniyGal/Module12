package edu.goit.galamaga.task2;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class GameFizzBuzz {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(4);

        int gameCapacity;

        if (args.length != 0 && Integer.parseInt(args[0]) > 0) {
            gameCapacity = Integer.parseInt(args[0]);
        } else {
            gameCapacity = 15;
        }

        IntStream stream = IntStream.rangeClosed(1, gameCapacity);

        stream.forEach((curNumber) -> {

            new Thread(new FizzBuzzThread(curNumber, "Fizz", countDownLatch,
                    (number) -> number % 3 == 0 && number % 5 != 0)).start();

            new Thread(new FizzBuzzThread(curNumber, "Buzz", countDownLatch,
                    (number) -> number % 3 != 0 && number % 5 == 0)).start();

            new Thread(new FizzBuzzThread(curNumber, "FizzBuzz", countDownLatch,
                    (number) -> number % 3 == 0 && number % 5 == 0)).start();

            new Thread(new FizzBuzzThread(curNumber, String.valueOf(curNumber), countDownLatch,
                    (number) -> number % 3 != 0 && number % 5 != 0)).start();

            try {
                Thread.sleep(400);
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (curNumber < gameCapacity) {
                System.out.print(", ");
            }

        });

    }

}

class FizzBuzzThread implements Runnable {

    int number;
    String printValue;
    CountDownLatch countDownLatch;
    FizzBuzzCondition FBCondition;

    public FizzBuzzThread(int number, String printValue, CountDownLatch countDownLatch,
                          FizzBuzzCondition FBCondition) {
        this.number = number;
        this.countDownLatch = countDownLatch;
        this.FBCondition = FBCondition;
        this.printValue = printValue;
    }

    @Override
    public void run() {

        if (FBCondition.checkNumber(number)) {
            System.out.print(printValue);
        }

        countDownLatch.countDown();

    }

}

@FunctionalInterface
interface FizzBuzzCondition {
    boolean checkNumber(int number);
}