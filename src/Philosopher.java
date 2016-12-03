import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by MohamedOsman on 2016-11-22.
 */
public class Philosopher implements Runnable {

    // time

    private int countEatinTime = 0;
    private int countThinkingTime = 0;
    private int countHungryTime = 0;
    private long starthungryTime;
    private long hungryTotalTime;
    private int thinkingTotalTime;
    private int eatingTotalTime;
    private int randomOperationTime; // random time to eat or thinik


    // ID , chopsticks, state, radanntiem
    private int ID;
    private Random randomTime;
    private Chopstick leftChopStick;
    private State state;
    private Chopstick rightChopstick;


    // boolan to stop and start the while loop
    public AtomicBoolean arePhilosphersDone = new AtomicBoolean(false);


    public Philosopher(int ID, Chopstick leftChopStick, Chopstick rightChopstick) {
        this.ID = ID;
        this.leftChopStick = leftChopStick;
        this.rightChopstick = rightChopstick;
        randomTime = new Random();


    }


    public enum State {
        THINKING,
        HUNGRY,
        EATING
    }


    public int getAverageThinkingTime() {
        return thinkingTotalTime / countThinkingTime;
    }


    public int getAverageEatingTime() {
        return eatingTotalTime / countEatinTime;
    }


    public long getAverageHungryTime() {
        return (hungryTotalTime/1000)*100/countHungryTime;
    }


    public int getID() {
        return ID;
    }


    @Override
    public void run() {
        starthungryTime = System.currentTimeMillis();


        while (!arePhilosphersDone.get()) {

            try {
                think();
                Thread.sleep(500);

                if (leftChopStick.getQueueLength() > 0) {
                    System.out.println(leftChopStick.getQueueLength());
                    System.out.println("Because of DeadLock has been detected  the program execution  stopped. ");

                }



               if (leftChopStick.tryLock(1,TimeUnit.SECONDS)) {
                    System.out.println("philosopher " + getID() + " is " + state);
                    System.out.println("philosopher " + getID() + " ----picked up left chopstick---- [" + leftChopStick.getChopstickID()+"]");
                    if (rightChopstick.tryLock(2,TimeUnit.SECONDS)) {
                        System.out.println("philosopher " + getID() + " ----picked up right chopstick---- [" +rightChopstick.getChopstickID()+"]");
                        long endtime = System.currentTimeMillis();
                        hungryTotalTime = (endtime - starthungryTime) + countHungryTime;

                        try {
                            eat();
                            PutDownRightChopStick();
                        } finally {
                            putDownLeftChopStick();
                        }
                    }

                }





            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void putDownLeftChopStick() {
        System.out.println("philosopher " + getID() + " ----put the left chopstick---- [" + leftChopStick.getChopstickID() + "] down");
        leftChopStick.unlock();
    }

    private void PutDownRightChopStick() {
        System.out.println("philosopher " + getID() + " ----put the right chopstick---- [" + rightChopstick.getChopstickID() + "] down");

        rightChopstick.unlock();

    }


    private void think() throws InterruptedException {
        state = State.THINKING;
        System.out.println("Philosopher " + getID() + "is " + state);
        int randomthink = randomTime.nextInt(10 + 1) * 100;
        Thread.sleep(randomthink);
        thinkingTotalTime = randomthink + countThinkingTime;
        countThinkingTime++;
        hungry();


    }

    private void eat() throws InterruptedException {
        state = State.EATING;
        System.out.println("Philosopher " + getID() + " is " + state);
        randomOperationTime = randomTime.nextInt(10 + 1) * 100;
        Thread.sleep(randomOperationTime);
        eatingTotalTime = randomOperationTime + countEatinTime;
        countEatinTime++;


    }


    private void hungry() {
            state = State.HUNGRY;
        countHungryTime++;

    }
}


