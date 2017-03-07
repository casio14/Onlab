package patrik.onlab_start;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrik on 2017.03.07..
 */
public class MessageListFragment extends ListFragment {

    ListView lv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Creat the datas
        List<String> values = new ArrayList<String>();
        values.add("szia");values.add("szia");values.add("szia");

        //Create and set an adapter
        ArrayAdapter adapter = new MessageAdapter(getActivity(),android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);
    }

}
