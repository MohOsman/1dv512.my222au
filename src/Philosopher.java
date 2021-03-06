import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher implements Runnable {

    // time

    private int countEatingTime = 0;
    private int countThinkingTime = 0;
    private int countHungryTime = 0;
    private long hungryTotalTime;
    private int thinkingTotalTime;
    private int eatingTotalTime;


    // ID , chopsticks, state, random
    private int ID;
    private Random randomTime;
    private Chopstick leftChopStick;
    private State state;
    private Chopstick rightChopstick;


    // boolan to stop and start the while loop
    AtomicBoolean arePhilosphersDone = new AtomicBoolean(false);


    public Philosopher(int ID, Chopstick leftChopStick, Chopstick rightChopstick) {
        this.ID = ID;
        this.leftChopStick = leftChopStick;
        this.rightChopstick = rightChopstick;
        randomTime = new Random();


    }


    private enum State {
        THINKING,
        HUNGRY,
        EATING
    }


    int getAverageThinkingTime() {
        return thinkingTotalTime / countThinkingTime;
    }


    int getAverageEatingTime() {
        return eatingTotalTime / countEatingTime;
    }


    long getAverageHungryTime() {
        return (hungryTotalTime/countHungryTime)/100;
    }

    int getNumberOfEatingTurns() {
        return countEatingTime;
    }


    int getID() {
        return ID;
    }


    @Override
    public void run() {
        long startHungryTime = System.currentTimeMillis();


        while (!arePhilosphersDone.get()) try {
            think();


            if (leftChopStick.getQueueLength() > 0) {
                System.out.println(leftChopStick.getQueueLength());
                System.out.println("Because of DeadLock has been detected  the program execution  stopped. ");
                System.exit(0);

            }

            else if (leftChopStick.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println("philosopher " + getID() + " is " + state);
                System.out.println("philosopher " + getID() + " ----picked up left chopstick---- [" + leftChopStick.getChopstickID() + "]");
                if (rightChopstick.tryLock(2, TimeUnit.SECONDS)) { // waits two seconds if other philosopher is eating
                    System.out.println("philosopher " + getID() + " ----picked up right chopstick---- [" + rightChopstick.getChopstickID() + "]");
                    long endTime = System.currentTimeMillis();
                    hungryTotalTime += (endTime - startHungryTime);
                    eat();
                    PutDownRightChopStick();
                }
                putDownLeftChopStick();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
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
        System.out.println("Philosopher " + getID() + " is " + state);
        int randomthink = randomTime.nextInt(10 + 1) * 100;
        Thread.sleep(randomthink);
        thinkingTotalTime = randomthink + countThinkingTime;
        countThinkingTime++;
        hungry();


    }

    private void eat() throws InterruptedException {
        state = State.EATING;
        System.out.println("Philosopher " + getID() + " is " + state);
        int randomEatTime = randomTime.nextInt(10 + 1) * 100;
        Thread.sleep(randomEatTime);
        eatingTotalTime = randomEatTime + countEatingTime;
        countEatingTime++;


    }


    private void hungry() {

        state = State.HUNGRY;
        countHungryTime++;

    }
}


