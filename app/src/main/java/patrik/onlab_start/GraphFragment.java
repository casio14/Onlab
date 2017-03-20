package patrik.onlab_start;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Patrik on 2017.03.08..
 * Show the datas in real time from Packets
 */
public class GraphFragment extends Fragment implements Serializable {

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

    public void saveDatas(Context context, String fileName) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            File directory = new File(context.getFilesDir().getPath(),"SavedDatas"); //New folder
            if(!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory,fileName); //New file to the specified folder
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            List<DataPoint> list = new ArrayList<DataPoint>(); //Save as list because that's serializable
            Iterator<DataPoint> iterator = mSeries1.getValues(mSeries1.getLowestValueX(),mSeries1.getHighestValueX());
            while(iterator.hasNext()) {
                list.add(iterator.next());
            }
            oos.writeObject(list);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            oos.close();
            fos.close();
        }
    }

    public void loadDatas(Context context,String fileName) throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File dir = new File(context.getFilesDir().getPath(),"SavedDatas"); //get folder
            File file = new File(dir,fileName); //New file to the specified folder
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            List<DataPoint> list = (List<DataPoint>)ois.readObject();
            DataPoint[] points = new DataPoint[list.size()];
            for(int i=list.size()-1;i>=0;i--) {
                points[i]=list.get(i);
            }
            mSeries1.resetData(points);
            graph.addSeries(mSeries1);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            ois.close();
            fis.close();
        }


    }


}
