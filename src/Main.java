import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {



    public static void main(String args[]) {

        // this sends the console output for to the file
//		PrintStream outToFile;
//			try {
//				outToFile = new PrintStream(new FileOutputStream("traceLog.txt"));
//				System.setOut(outToFile);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

        DiningPhilosopher dp = new DiningPhilosopher();
        dp.setSimulationtime(10000);
        dp.initialize();
        dp.start();

        ArrayList<Philosopher> philosophers = dp.getPhilosophers();
        for (Philosopher p : philosophers) {
            System.out.println("Philosopher {"+p.getID()  + "} Average thinking time - " + p.getAverageThinkingTime()+ " Average eating time - " + p.getAverageEatingTime()+  " Average hungryTime  time- " + p.getAverageHungryTime());

        }



    }


}