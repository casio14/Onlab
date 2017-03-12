package patrik.onlab_start;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import patrik.onlab_start.Model.Packet;

/**
 * Created by Patrik on 2017.03.07..
 * Adapter class to MessageListFragment
 */
public class MessageAdapter extends ArrayAdapter {

    List<Packet> list;
    Context mContext;
    MessageListFragment homeFragment; // Through this we can update the MessageDetailsFragment ( call sendData() )

    //Constructor
    public MessageAdapter(Context context, int resource, List<Packet> input_data) {
        super(context, resource, input_data);
        mContext=context;
        list=input_data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.row_item,parent,false);

            if(position%2==1)
                v.setBackgroundColor(Color.parseColor("#dcedc8"));

            ViewHolder holder = new ViewHolder();
            holder.tvTime = (TextView) v.findViewById(R.id.tvDetTime);
            holder.tvType = (TextView) v.findViewById(R.id.tvType);
            holder.tvActionID = (TextView) v.findViewById(R.id.tvActionID);
            holder.tvCauseCode = (TextView) v.findViewById(R.id.tvCauseCode);
            v.setTag(holder);
        }

        final Packet element = list.get(position);
        if (element!=null) {
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.tvTime.setText(element.getDetectionTime());
            holder.tvType.setText(element.getType());
            holder.tvActionID.setText(element.getActionID());
            holder.tvCauseCode.setText(element.getCauseCode());
        }

        return v;
    }

    public Packet getItem(int position) {
        return list.get(position);
    }

    public static class ViewHolder {
        TextView tvTime;
        TextView tvType;
        TextView tvActionID;
        TextView tvCauseCode;
    }
}
