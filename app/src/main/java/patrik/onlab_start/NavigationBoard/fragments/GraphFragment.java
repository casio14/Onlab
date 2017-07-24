package patrik.onlab_start.NavigationBoard.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.Serializable;

import patrik.onlab_start.R;

/**
 * Created by Patrik on 2017.03.08..
 * Show the datas in real time from Packets
 */
public class GraphFragment extends Fragment implements Serializable {

    GraphView graph;

    private int lastX=0;

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<>();
        mSeries2 = new LineGraphSeries<>();
        mSeries2.setColor(Color.parseColor("#00FF00"));

        graph.setY(-5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-60.0);
        graph.getViewport().setMaxY(30.0);

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);

        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling


        graph.addSeries(mSeries1);
        graph.addSeries(mSeries2);

        return view;
    }

    //Resume the graph
    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {

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

    public void clearDatas() {
        mSeries1.resetData(new DataPoint[]{});
        mSeries2.resetData(new DataPoint[]{});
        lastX=0;
    }



    public void updateDataFromActivity(double messageCount, double snrAvarageValue) {
        mSeries1.appendData(new DataPoint(lastX,messageCount),true,10000,false);
        mSeries2.appendData(new DataPoint(lastX,snrAvarageValue),true,100000,false);
        lastX++;
    }

}
