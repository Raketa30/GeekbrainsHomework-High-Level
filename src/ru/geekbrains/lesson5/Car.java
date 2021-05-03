package ru.geekbrains.lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static volatile boolean win = false;
    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier racePreparation;
    private CountDownLatch raceFinish;


    public Car(Race race, int speed, CyclicBarrier racePreparation, CountDownLatch raceFinish) {
        this.race = race;
        this.speed = speed;
        this.racePreparation = racePreparation;
        this.raceFinish = raceFinish;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            racePreparation.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if(i == race.getStages().size() - 1 && !win) {
                win = true;
                System.out.println(this.name + " WIN!!");
            }
        }
        raceFinish.countDown();
    }
}
