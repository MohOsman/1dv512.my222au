import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {
   static PrintStream outToFile;


    public static void main(String args[]) {

        // this sends the console output for to the file

//
//			try {
//				outToFile = new PrintStream(new FileOutputStream("traceLog.txt"));
//				System.setOut(outToFile);
//                System.out.println
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

        DiningPhilosopher dp = new DiningPhilosopher();
        dp.setSimulationTime(10000);

        dp.initialize();
        dp.start();


        ArrayList<Philosopher> philosophers = dp.getPhilosophers();
        System.out.println("--------------------------------");
        for (Philosopher p : philosophers) {
            System.out.println("Philosopher {"+p.getID()  + "} Average thinking time - " + p.getAverageThinkingTime()+ " Average eating time - " + p.getAverageEatingTime()+  " Average hungryTime  time- " + p.getAverageHungryTime());

        }
        System.out.println("------------------------------");
        for (Philosopher p : philosophers) {
            System.out.println("Philosopher {"+p.getID()  + "} number of eating turns - " + p.getNumberOfEatingTurns());

        }


        System.out.println("Simulation Ended");
        System.exit(0);




    }


}