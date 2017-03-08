package patrik.onlab_start;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements PacketCommunicator {

    MessageDetailsFragment messageDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void updateData(String data) {

        //Set the appropriate fragment
        if(messageDetailsFragment==null) {
            messageDetailsFragment = (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }
        //Call the MessageDetailsFragment appropriate method to update values
        messageDetailsFragment.changeData(data);
    }
}
