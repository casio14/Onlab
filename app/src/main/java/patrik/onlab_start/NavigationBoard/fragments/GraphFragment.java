package patrik.onlab_start.NavigationBoard.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Patrik on 2017.03.08..
 * Show the datas in real time from Packets
 */
public class GraphFragment extends Fragment implements Serializable {

    GraphView graph;

    Spinner dataTypeSpinner;

    CheckBox autoScrollChechBox;

    Button saveDatasButton;

    private int lastX=0;
    private boolean enableAutoScroll = true;

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSeries1 = new LineGraphSeries<>();
        mSeries2 = new LineGraphSeries<>();

        mSeries1.setDrawDataPoints(true);
        mSeries1.setDataPointsRadius(10);

        mSeries2.setDrawDataPoints(true);
        mSeries2.setDataPointsRadius(10);
        mSeries2.setColor(Color.parseColor("#00FF00"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);

        graph.getViewport().setScalable(false); // enables horizontal zooming and scrolling

        graph.getViewport().setScrollable(false);

        graph.getViewport().setXAxisBoundsStatus(Viewport.AxisBoundsStatus.FIX);
        graph.getViewport().setYAxisBoundsStatus(Viewport.AxisBoundsStatus.AUTO_ADJUSTED);

        graph.addSeries(mSeries1);

        dataTypeSpinner = (Spinner) view.findViewById(R.id.dataTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dataTypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataTypeSpinner.setAdapter(adapter);

        dataTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (dataTypeSpinner.getSelectedItem().toString().equals("Incoming packets")) {
                    graph.removeAllSeries();
                    graph.addSeries(mSeries1);
                }

                else if (dataTypeSpinner.getSelectedItem().toString().equals("SNR")) {
                    graph.removeAllSeries();
                    graph.addSeries(mSeries2);
                    System.out.println("SNR-re valt");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        autoScrollChechBox = (CheckBox) view.findViewById(R.id.cbAutoScroll);
        autoScrollChechBox.setChecked(true);
        autoScrollChechBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autoScrollChechBox.isChecked()) {
                    enableAutoScroll = true;
                    graph.getViewport().setScalable(false);
                    graph.getViewport().setScrollable(false);
                    graph.getViewport().setXAxisBoundsStatus(Viewport.AxisBoundsStatus.FIX);
                    graph.getViewport().setYAxisBoundsStatus(Viewport.AxisBoundsStatus.AUTO_ADJUSTED);
                }
                else {
                    enableAutoScroll = false;
                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScrollable(true);
                    graph.getViewport().setYAxisBoundsStatus(Viewport.AxisBoundsStatus.AUTO_ADJUSTED);
                }
            }
        });

        saveDatasButton = (Button) view.findViewById(R.id.buttonSaveDatas);
        saveDatasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showSaveAlertDialog();
            }
        });

        saveDatasButton.setEnabled(false);
        return view;
    }

    //Resume the graph
    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                graph.removeAllSeries();

                if (dataTypeSpinner.getSelectedItem().toString().equals("Incoming packets")) {
                    graph.addSeries(mSeries1);
                } else if(dataTypeSpinner.getSelectedItem().toString().equals("SNR")) {
                    graph.addSeries(mSeries2);
                }

                if (enableAutoScroll) {
                    if(lastX<10) {
                        graph.getViewport().setMaxX(lastX);
                        graph.getViewport().setMinX(0);
                    }
                    else {
                        graph.getViewport().setMaxX(lastX);
                        graph.getViewport().setMinX(lastX-10);
                    }
                }
            }
        };
        mHandler.postDelayed(mTimer1, 300);
    }

    //Stop the graph
    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    } //GOOD

    public void updateDataFromActivity(double messageCount, double snrAvarageValue) {
        if(enableAutoScroll) {
            mSeries1.appendData(new DataPoint(lastX, messageCount), true, 100000, false);
            mSeries2.appendData(new DataPoint(lastX, snrAvarageValue), true, 100000, false);
            mHandler.postDelayed(mTimer1, 0);
        } else {
            mSeries1.appendData(new DataPoint(lastX, messageCount), false, 100000, false);
            mSeries2.appendData(new DataPoint(lastX, snrAvarageValue), false,100000, false);
        }
        lastX++;
    } //GOOD

    public boolean isNull() {
        return graph == null ? true : false;
    } //GOOD

    private void saveDatas(String fileName) throws IOException {
        FileOutputStream packetFos = null;
        ObjectOutputStream packetOos = null;
        FileOutputStream snrFos = null;
        ObjectOutputStream snrOos = null;

        try {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/V2XPacketAnalyzer";
            File directory = new File(root);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File packetFile = new File(directory, fileName+"_packets.ser"); //New file to the specified folder
            packetFos = new FileOutputStream(packetFile);
            packetOos = new ObjectOutputStream(packetFos);
            Iterator<DataPoint> iteratorM1 = mSeries1.getValues(0,lastX);
            while(iteratorM1.hasNext()) {
                packetOos.writeObject(iteratorM1.next());
            }

            File snrFile = new File(directory, fileName+"_snr.ser"); //New file to the specified folder
            snrFos = new FileOutputStream(snrFile);
            snrOos = new ObjectOutputStream(snrFos);
            Iterator<DataPoint> iteratorM2 = mSeries2.getValues(0,lastX);
            while(iteratorM2.hasNext()) {
                snrOos.writeObject(iteratorM2.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Saving failed.",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Saving failed.",Toast.LENGTH_LONG).show();
        } finally {
            if(packetFos!=null)
                packetFos.close();
            if(packetOos!=null)
                packetOos.close();
            if(snrFos!=null)
                snrFos.close();
            if(snrOos!=null)
                snrOos.close();
        }
    }

    public void loadDatas(String fileName) throws Exception {
        FileInputStream packetFis = null;
        ObjectInputStream packetOis = null;
        FileInputStream snrFis = null;
        ObjectInputStream snrOis = null;

        try {
            onPause();

            String name = fileName.replace("_packets.ser","").replace("_snr.ser","");

            File packetFile = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/V2XPacketAnalyzer/"+name+"_packets.ser");
            if(!packetFile.exists())
                throw new Exception();
            packetFis = new FileInputStream(packetFile);
            packetOis = new ObjectInputStream(packetFis);
            LineGraphSeries<DataPoint> packetDataPoints = new LineGraphSeries<>();
            while(packetFis.available()>0) {
                DataPoint dataPoint = (DataPoint) packetOis.readObject();
                packetDataPoints.appendData(dataPoint,true,10000,false);
            }
            mSeries1 = packetDataPoints;


            File snrFile = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/V2XPacketAnalyzer/"+name+"_snr.ser");
            snrFis = new FileInputStream(snrFile);
            snrOis = new ObjectInputStream(snrFis);
            LineGraphSeries<DataPoint> snrDataPoints = new LineGraphSeries<>();
            while(snrFis.available()>0) {
                DataPoint data1Point = (DataPoint) snrOis.readObject();
                snrDataPoints.appendData(data1Point,true,10000,false);
            }
            mSeries2 = snrDataPoints;

            mSeries1.setDrawDataPoints(true);
            mSeries1.setDataPointsRadius(10);

            mSeries2.setDrawDataPoints(true);
            mSeries2.setDataPointsRadius(10);
            mSeries2.setColor(Color.parseColor("#00FF00"));

            graph.removeAllSeries();
            graph.addSeries(mSeries1);

            autoScrollChechBox.setChecked(false);
            autoScrollChechBox.callOnClick();

            autoScrollChechBox.setVisibility(View.INVISIBLE);
            saveDatasButton.setVisibility(View.INVISIBLE);

            graph.onDataChanged(true,true);

        }
        finally {
            if(packetFis!=null)
                packetFis.close();
            if(packetOis!=null)
                packetOis.close();
            if(snrFis!=null)
                snrFis.close();
            if(snrOis!=null)
                snrOis.close();
        }
    }

    public void showSaveAlertDialog() {

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.save_window_layout, null);

        final EditText et = (EditText) view.findViewById(R.id.save_etFileName);
        alertbox.setView(view);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    saveDatas(et.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertbox.show();
    }

    public void enableSaving() {
        saveDatasButton.setEnabled(true);
    }
}
