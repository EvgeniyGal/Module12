package edu.goit.galamaga.task2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class GameFizzBuzz {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        int gameCapacity;

        if (args.length != 0 && Integer.parseInt(args[0]) > 0) {
            gameCapacity = Integer.parseInt(args[0]);
        } else {
            gameCapacity = 15;
        }

        IntStream stream = IntStream.rangeClosed(1, gameCapacity);

        stream.forEach((curNumber) -> {

            new Thread(new FizzBuzzThread(curNumber, "Fizz", cyclicBarrier,
                    (number) -> number % 3 == 0 && number % 5 != 0)).start();

            new Thread(new FizzBuzzThread(curNumber, "Buzz", cyclicBarrier,
                    (number) -> number % 3 != 0 && number % 5 == 0)).start();

            new Thread(new FizzBuzzThread(curNumber, "FizzBuzz", cyclicBarrier,
                    (number) -> number % 3 == 0 && number % 5 == 0)).start();

            new Thread(new FizzBuzzThread(curNumber, String.valueOf(curNumber), cyclicBarrier,
                    (number) -> number % 3 != 0 && number % 5 != 0)).start();

            try {
                Thread.sleep(400);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
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
    CyclicBarrier cyclicBarrier;
    FizzBuzzCondition FBCondition;

    public FizzBuzzThread(int number, String printValue, CyclicBarrier cyclicBarrier,
                          FizzBuzzCondition FBCondition) {
        this.number = number;
        this.cyclicBarrier = cyclicBarrier;
        this.FBCondition = FBCondition;
        this.printValue = printValue;
    }

    @Override
    public void run() {

        if (FBCondition.checkNumber(number)) {
            System.out.print(printValue);
        }

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

}

@FunctionalInterface
interface FizzBuzzCondition {
    boolean checkNumber(int number);
}