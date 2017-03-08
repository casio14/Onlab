package patrik.onlab_start;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Patrik on 2017.03.08..
 */
public class MessageDetailsFragment extends Fragment {

    TextView protocol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);
        protocol = (TextView) v.findViewById(R.id.tvProtocol);
        return v;
    }

    //Update fields
    public void changeData(String data) {
        protocol.setText(data);
    }
}
