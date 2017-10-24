package patrik.onlab_start;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

import patrik.onlab_start.Model.DataCommunicator;

/**
 * Created by kisspatrik on 2017.08.03..
 */

public class CounterTimer {

    private Counter counter;
    private CounterTimerTask counterTimerTask;

    private int period;
    private int delay;

    private DataCommunicator dataCommunicator;

    Timer timer;

    public CounterTimer(Counter counter, DataCommunicator dataCommunicator, int delay, int period) {
        this.counter = counter;
        counterTimerTask = new CounterTimerTask();

        this.delay = delay;
        this.period = period;

        this.dataCommunicator = dataCommunicator;

        timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(counterTimerTask,delay,period);
    }

    public void stop() {
        timer.cancel();
    }


    private class CounterTimerTask extends TimerTask {

        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("SNR: " + counter.getSnrSum() + " Count: " + counter.getMessageCounter());
                    dataCommunicator.sendDatasForShowing(counter.getMessageCounter(),new Double(counter.getSnrSum() / counter.getMessageCounter()).intValue());
                    counter.resetMessageCounter();
                    counter.resetSnrSum();
                }
            }).run();
        }
    }
}
