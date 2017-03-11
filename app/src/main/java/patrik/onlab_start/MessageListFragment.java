package patrik.onlab_start;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import patrik.onlab_start.Model.Packet;

/**
 * Created by Patrik on 2017.03.07..
 */
public class MessageListFragment extends ListFragment {

    ListView lv;
    MessageAdapter adapter;
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
        List<Packet> values = new ArrayList<Packet>();
        values.add(new Packet("1.2141","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("1.6321","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("1.9235","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.5235","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));
        values.add(new Packet("3.7326","MAP","XXXXX.Y","94.2"));
        values.add(new Packet("4.5474","CAM","XXXXX.Y","94.2"));
        values.add(new Packet("5.8734","DENM","XXXXX.Y","94.2"));



        //Create and set an adapter
        adapter = new MessageAdapter(getActivity(),android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
    }

    public void sendData(Packet data) {
        communicator.updateData(data);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        sendData(adapter.getItem(position));
    }
}
