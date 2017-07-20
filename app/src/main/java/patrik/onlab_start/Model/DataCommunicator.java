package patrik.onlab_start.Model;

/**
 * The MainActivity can sendDatasForShowing incoming packet statistics info to the GraphFragment to show them, through this.
 *
 * Created by Patrik on 2017.04.13..
 */
public interface DataCommunicator {

    public void sendDatasForShowing(double count, double avarageSNR);

}
