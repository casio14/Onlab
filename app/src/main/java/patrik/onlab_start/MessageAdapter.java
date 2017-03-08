package patrik.onlab_start;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Patrik on 2017.03.07..
 */
public class MessageAdapter extends ArrayAdapter {

    List<String> list;
    Context mContext;
    MessageListFragment homeFragment; // Through this we can update the MessageDetailsFragment ( call sendData() )

    //Constructor
    public MessageAdapter(Context context, int resource,MessageListFragment fragment, List<String> input_data) {
        super(context, resource, input_data);
        mContext=context;
        list=input_data;
        homeFragment=fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.row_item,parent,false);
            ViewHolder holder = new ViewHolder();
            holder.tv = (TextView) v.findViewById(R.id.messageTextView);
            holder.button = (Button) v.findViewById(R.id.messageButton);
            v.setTag(holder);
        }

        final String element = list.get(position);
        if (element!=null) {
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.tv.setText(element);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Send data to MessageDetailsFragment
                    homeFragment.sendData(element);
                }
            });
        }

        return v;
    }

    public static class ViewHolder {
        TextView tv;
        Button button;
    }
}
