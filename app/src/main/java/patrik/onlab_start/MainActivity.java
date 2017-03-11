package patrik.onlab_start;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

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
}
