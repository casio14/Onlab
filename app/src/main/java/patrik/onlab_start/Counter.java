package patrik.onlab_start;

/**
 * Created by kisspatrik on 2017.08.03..
 */

public class Counter {

    private int messageCounter;
    private float snrSum;


    public Counter() {
        this.messageCounter = 0;
        this.snrSum = 0;
    }

    public int getMessageCounter() {
        return messageCounter;
    }

    public float getSnrSum() {
        return snrSum;
    }

    public void resetMessageCounter() {
        this.messageCounter = 0;
    }

    public void resetSnrSum() {
        this.snrSum = 0;
    }

    public void incrementMessageCounter() {
        messageCounter++;
    }

    public void incrementSnrSum(float sum) {
        snrSum += sum;
    }
}
