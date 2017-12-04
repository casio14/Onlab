package patrik.onlab_start;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.commsignia.v2x.client.model.DENM;
import com.commsignia.v2x.client.model.DENMActionID;
import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.FacilityNotificationType;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.client.model.LdmObjectType;
import com.commsignia.v2x.client.model.PrimaryCause;
import com.commsignia.v2x.client.model.RoadworksSubCauseCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;

/**
 * Created by Patrik on 2017.03.07..
 * Show the captured packets
 */
public class MessageListFragment extends ListFragment{

    MessageAdapter adapter;
    PacketCommunicator communicator; /* The interface object to call the MainActivity update() method */

    int selectedItemPosition=-1;
    View selectedItemView=null;


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {
            //Set the communicator reference to MainActivity
            communicator = (PacketCommunicator) activity;

        } catch (ClassCastException e ) { /* If the MainClass not implement the interface */
            throw new ClassCastException(activity.toString() + "must implement updateDataDetails(String data)");
        }
    }

    public void sendData(PacketAncestor data) {
        communicator.showPacketDetails(data);
    }

    //Send the clicked packet infos to the DetailsFragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        if(selectedItemPosition>-1) {
            if(selectedItemPosition%2==1)
                selectedItemView.setBackgroundColor(Color.parseColor("#dcedc8"));
            else
                selectedItemView.setBackgroundColor(Color.WHITE);

            adapter.list.get(position).setSelected(1);
        }

        v.setBackgroundColor(Color.LTGRAY);
        selectedItemPosition=position;
        selectedItemView = v;

        adapter.list.get(position).setSelected(1);

        sendData(adapter.getItem(position));
    }

    public void startPacketCapturing(Map<String,String> selectedValues) {

        //Create and set an adapter
        List<PacketAncestor> values1 = new ArrayList<PacketAncestor>();
        List<PacketAncestor> values2 = Collections.synchronizedList(values1); // Thread safe List

        adapter = new MessageAdapter(getActivity(),android.R.layout.simple_list_item_1,values2,selectedValues);
        setListAdapter(adapter);
    }

}
