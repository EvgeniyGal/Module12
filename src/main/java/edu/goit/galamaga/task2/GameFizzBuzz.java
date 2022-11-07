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

        stream.forEach((v) -> {

            new Thread(new Fizz(v, cyclicBarrier)).start();
            new Thread(new Buzz(v, cyclicBarrier)).start();
            new Thread(new FizzBuzz(v, cyclicBarrier)).start();
            new Thread(new SimpleNumber(v, cyclicBarrier)).start();

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.print(", ");

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

    }

}

class Fizz implements Runnable {

    int checkNumber;
    CyclicBarrier cyclicBarrier;

    public Fizz(int checkNumber, CyclicBarrier cyclicBarrier) {
        this.checkNumber = checkNumber;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        if (checkNumber % 3 == 0 && checkNumber % 5 != 0) {
            System.out.print("fizz");
        }

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

}

class Buzz implements Runnable {

    int checkNumber;
    CyclicBarrier cyclicBarrier;


    public Buzz(int checkNumber, CyclicBarrier cyclicBarrier) {
        this.checkNumber = checkNumber;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        if (checkNumber % 3 != 0 && checkNumber % 5 == 0) {
            System.out.print("buzz");
        }


        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

}

class FizzBuzz implements Runnable {

    int checkNumber;
    CyclicBarrier cyclicBarrier;

    public FizzBuzz(int checkNumber, CyclicBarrier cyclicBarrier) {
        this.checkNumber = checkNumber;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        if (checkNumber % 3 == 0 && checkNumber % 5 == 0) {
            System.out.print("fizzbuzz");
        }

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

}


class SimpleNumber implements Runnable {

    int checkNumber;
    CyclicBarrier cyclicBarrier;

    public SimpleNumber(int checkNumber, CyclicBarrier cyclicBarrier) {
        this.checkNumber = checkNumber;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {

        if (checkNumber % 3 != 0 && checkNumber % 5 != 0) {
            System.out.print(checkNumber);
        }

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}