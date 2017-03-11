package patrik.onlab_start;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

/**
 * Created by Patrik on 2017.03.08..
 */
public class GraphFragment extends Fragment {

    GraphView graph;

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private LineGraphSeries<DataPoint> mSeries1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<>(generateData());
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.addSeries(mSeries1);

        return view;
    }

    //Resume the graph
    @Override
    public void onResume() {
        super.onResume();
        final double[] x = {10};
        mTimer1 = new Runnable() {
            @Override
            public void run() {

                x[0]++;
                //mSeries1.resetData(generateData());
                mSeries1.appendData(generate(x[0]),true,100,false);
                mHandler.postDelayed(this, 600);
            }
        };
        mHandler.postDelayed(mTimer1, 300);
    }

    //Stop the graph
    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    }

    //Generate graph points
    private DataPoint[] generateData() {
        int count = 10;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    private DataPoint generate(double x) {
        double i= 10;
        double f = mRand.nextDouble()*0.15+0.3;
        double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
        DataPoint v = new DataPoint(x, y);
        return v;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }

    //Pause the graph thread from outside
    public void threadPause() throws InterruptedException {
        if(mTimer1!= null)
            mTimer1.wait();
    }

    //Resume the graph thread from outside
    public void threadResume() throws InterruptedException {
        if(mTimer1!= null)
            mTimer1.run();
    }


}
