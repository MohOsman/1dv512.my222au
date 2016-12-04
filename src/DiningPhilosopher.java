import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DiningPhilosopher {
    private ArrayList<Philosopher> philosophers = new ArrayList<>();
    private ArrayList<Chopstick> chopstickses = new ArrayList<>();
    private static final int NUM_OF_PHILOSPHERS = 5;
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
        for (int j = 0; j < NUM_OF_PHILOSPHERS; j++) {
            chopstickses.add(new Chopstick(j));

        }
        for (int i = 0; i < NUM_OF_PHILOSPHERS; i++) {
            philosophers.add(new Philosopher(i, chopstickses.get(i), chopstickses.get((i + 1) % NUM_OF_PHILOSPHERS)));

        }

    }

    public void start() {
       executorService = Executors.newFixedThreadPool(NUM_OF_PHILOSPHERS);

        for(Philosopher ps: philosophers){
            executorService.execute(ps);
        }
        try {
            Thread.sleep(simulationTime);
            for(Philosopher p: philosophers){
               p.arePhilosphersDone.set(true);
                Thread.sleep(2000);  // makes sure the that the simulation ended before printing out average time
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }





        }


}




