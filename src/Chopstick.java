import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MohamedOsman on 2016-11-22.
 */
public class Chopstick  extends  ReentrantLock{


    public Chopstick(int chopstickID) {
        this.chopstickID = chopstickID;
    }

    private int  chopstickID;



    public int getChopstickID() {
        return chopstickID;
    }

    public void setChopstickID(int chopstickID) {
        this.chopstickID = chopstickID;
    }
}
