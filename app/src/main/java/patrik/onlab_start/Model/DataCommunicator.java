package patrik.onlab_start.Model;

/**
 * The MainActivity can send incoming packet statistics info to the GraphFragment to show them, through this.
 *
 * Created by Patrik on 2017.04.13..
 */
public interface DataCommunicator {

    public void send(double count, double avarageSNR);

}
