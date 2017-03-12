package patrik.onlab_start;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.IOException;

import patrik.onlab_start.Model.Packet;

public class MainActivity extends FragmentActivity implements PacketCommunicator {

    MessageDetailsFragment messageDetailsFragment;
    GraphFragment graphFragment;

    Button startButton;
    Button stopButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Components initialization
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        //Start the graph thread
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(graphFragment==null)
                    graphFragment = (GraphFragment)getFragmentManager().findFragmentById(R.id.graphFragment);
                try {
                    graphFragment.loadDatas(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                graphFragment.onResume();
            }
        });

        //Stop the graph thread
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(graphFragment==null)
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                graphFragment.onPause();
                //graphFragment.saveDatas(getApplicationContext());
                showSaveAlertDialog();
            }
        });

    }

    @Override
    public void updateData(Packet data) {

        //Set the appropriate fragment
        if(messageDetailsFragment==null) {
            messageDetailsFragment = (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }
        //Call the MessageDetailsFragment appropriate method to update values
        messageDetailsFragment.changeData(data);
    }

    public void showSaveAlertDialog() {

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.save_window_layout,null);

        final EditText et = (EditText) view.findViewById(R.id.save_etFileName);

        alertbox.setView(view);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    graphFragment.saveDatas(getApplicationContext(),et.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertbox.show();
    }
}
