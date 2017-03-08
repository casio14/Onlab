package patrik.onlab_start;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrik on 2017.03.07..
 */
public class MessageListFragment extends ListFragment {

    ListView lv;
    PacketCommunicator communicator; /* The interface object to call the MainActivity update() method */

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {
            //Set the communicator reference to MainActivity
            communicator = (PacketCommunicator) activity;

        } catch (ClassCastException e ) { /* If the MainClass not implement the interface */
            throw new ClassCastException(activity.toString() + "must implement updateData(String data)");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Creat the datas
        List<String> values = new ArrayList<String>();
        values.add("MAP");values.add("CAM");values.add("DENM");values.add("CAM");values.add("CAM");values.add("DENM");
        values.add("MAP");values.add("MAP");values.add("DENM");

        //Create and set an adapter
        ArrayAdapter adapter = new MessageAdapter(getActivity(),android.R.layout.simple_list_item_1,MessageListFragment.this,values);
        setListAdapter(adapter);
    }

    public void sendData(String data) {
        communicator.updateData(data);
    }

}
