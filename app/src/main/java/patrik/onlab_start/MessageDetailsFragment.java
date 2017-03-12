package patrik.onlab_start;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import patrik.onlab_start.Model.Packet;

/**
 * Created by Patrik on 2017.03.08..
 * Show the clicked Packet details
 */
public class MessageDetailsFragment extends Fragment {

    TextView tvDetTime;
    TextView tvType;
    TextView tvActionID;
    TextView tvCauseCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);
        tvDetTime = (TextView) v.findViewById(R.id.details_tvDetTime);
        tvType = (TextView) v.findViewById(R.id.detials_tvType);
        tvActionID = (TextView) v.findViewById(R.id.detials_tvActionID);
        tvCauseCode = (TextView) v.findViewById(R.id.detials_tvCauseCode);
        return v;
    }

    //Update fields
    public void changeData(Packet data) {
        tvDetTime.setText(data.getDetectionTime());
        tvType.setText(data.getType());
        tvActionID.setText(data.getActionID());
        tvCauseCode.setText(data.getCauseCode());
    }
}
