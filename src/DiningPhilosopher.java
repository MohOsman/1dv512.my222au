import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.SendingContext.RunTime;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MohamedOsman on 2016-11-22.
 */
public class DiningPhilosopher {
    private ArrayList<Philosopher> philosophers = new ArrayList<>();
    private ArrayList<Chopstick> chopstickses = new ArrayList<>();
    private static final int NUMOFPHILOSPHERS = 5;
    private long simulationTime;
    private Thread thread = null;
    private ExecutorService executorService = null;

    public void setSimulationtime(long simulationTime) {
        this.simulationTime = simulationTime;
    }

    public ArrayList<Philosopher> getPhilosophers() {
        return philosophers;
    }

    public void initialize() {
        for (int j = 0; j < NUMOFPHILOSPHERS; j++) {
            chopstickses.add(new Chopstick(j));

        }
        for (int i = 0; i < NUMOFPHILOSPHERS; i++) {
            philosophers.add(new Philosopher(i, chopstickses.get(i), chopstickses.get((i + 1) % NUMOFPHILOSPHERS)));

        }

    }

    public void start() {
       executorService = Executors.newFixedThreadPool(NUMOFPHILOSPHERS);

        for(Philosopher ps: philosophers){
            executorService.execute(ps);
        }
        try {
            Thread.sleep(simulationTime);
            for(Philosopher p: philosophers){
               p.arePhilosphersDone.set(true);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }





        }


}




