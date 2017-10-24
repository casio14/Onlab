package patrik.onlab_start;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.utils.units.AccelerationUnit;
import com.commsignia.v2x.utils.units.DegreeUnit;
import com.commsignia.v2x.utils.units.LengthUnit;
import com.commsignia.v2x.utils.units.SpeedUnit;
import com.commsignia.v2x.utils.units.TemperatureUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import patrik.onlab_start.Model.FacilityNotificationFieldValue;
import patrik.onlab_start.Model.LdmNotificationFieldValue;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;

/**
 * Created by kisspatrik on 2017.10.20..
 */

public class CarListAdapter extends ArrayAdapter {
    List<String> list;

    Context mContext;

    //Adjusted spinner values
    Map<String, String> selectedValues;

    //Constructor
    public CarListAdapter(Context context, int resource, List<String> input_data) {
        super(context, resource, input_data);
        this.mContext = context;
        this.list = input_data;

        this.selectedValues = selectedValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.carrow, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.tvID = (TextView) v.findViewById(R.id.my_text_view);
            v.setTag(holder);
        }

        final String element = list.get(position);
        if (element != null) {
            CarListAdapter.ViewHolder holder = (CarListAdapter.ViewHolder) v.getTag();

            holder.tvID.setText(element);
        }
        return v;
    }

    //Get an Item
    public String getItem(int position) {
        return list.get(position);
    }

    //Add packet to list
    public void addCarID(String packet) {
        list.add(packet);
        notifyDataSetChanged();
    }

    //ViewHolder class
    public static class ViewHolder {
        TextView tvID;
    }
}
