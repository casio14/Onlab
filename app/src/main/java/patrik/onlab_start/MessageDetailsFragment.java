package patrik.onlab_start;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.utils.units.AccelerationUnit;
import com.commsignia.v2x.utils.units.DegreeUnit;
import com.commsignia.v2x.utils.units.LengthUnit;
import com.commsignia.v2x.utils.units.SpeedUnit;
import com.commsignia.v2x.utils.units.TemperatureUnit;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import patrik.onlab_start.Model.FacilityNotificationFieldValue;
import patrik.onlab_start.Model.LdmNotificationFieldValue;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;

/**
 * Show the clicked Packet details
 *
 * Created by Patrik on 2017.03.08..
 */
public class MessageDetailsFragment extends Fragment {

    List<TextView> propertiesTextViews = new ArrayList<TextView>();
    List<TextView> valuesTextViews = new ArrayList<TextView>();

    String[] denmProperties;
    String[] camProperties;
    String[] mapProperties;
    String[] spatProperties;

    private static final long LEAP_SECONDS_UNTIL_2004 = 32;

    private static final long UTC_MILLISECONDS_UNTIL_2004 = 1072915200000L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);

        initializeComponents(v);

        return v;
    }

    public void changeData(PacketAncestor data) {

        if(data.getNotificationType().equals(NotificationType.FAC_NOTIFICATION)) {
            FacilityNotification fc = (FacilityNotification) data.getObject();
            switch (fc.getType().toString()) {
                case "DENM":
                    setDenmDetails(fc);
                    break;

                case "CAM":
                    setCamDetails(fc);
                    break;
            }
        }

        if(data.getNotificationType().equals(NotificationType.LDM_NOTIFICATION)) {
            LdmObject lo = (LdmObject) data.getObject();
            switch (lo.getObjectType().toString()) {
                case "MAP":
                    setMapDetails(lo);
                    break;

                case "SPAT":
                    setSpatDetails(lo);
                    break;
            }
        }

    }

    public void initializeComponents(View v) {

        denmProperties = getResources().getStringArray(R.array.denmProperties);
        camProperties = getResources().getStringArray(R.array.camProperties);
        mapProperties = getResources().getStringArray(R.array.mapProperties);
        spatProperties = getResources().getStringArray(R.array.spatProperties);

        TextView tvProp1; TextView tvProp2; TextView tvProp3; TextView tvProp4; TextView tvProp5; TextView tvProp6; TextView tvProp7;
        TextView tvProp8; TextView tvProp9; TextView tvProp10; TextView tvProp11; TextView tvProp12; TextView tvProp13; TextView tvProp14;
        TextView tvProp15; TextView tvProp16; TextView tvProp17; TextView tvProp18; TextView tvProp19; TextView tvProp20; TextView tvProp21;
        TextView tvProp22; TextView tvProp23; TextView tvProp24; TextView tvProp25; TextView tvProp26; TextView tvProp27; TextView tvProp28;
        TextView tvProp29; TextView tvProp30; TextView tvProp31; TextView tvProp32; TextView tvProp33; TextView tvProp34;

        TextView tvProp1Value; TextView tvProp2Value; TextView tvProp3Value; TextView tvProp4Value; TextView tvProp5Value; TextView tvProp6Value;
        TextView tvProp7Value; TextView tvProp8Value; TextView tvProp9Value; TextView tvProp10Value; TextView tvProp11Value; TextView tvProp12Value;
        TextView tvProp13Value; TextView tvProp14Value; TextView tvProp15Value; TextView tvProp16Value; TextView tvProp17Value; TextView tvProp18Value;
        TextView tvProp19Value; TextView tvProp20Value; TextView tvProp21Value; TextView tvProp22Value; TextView tvProp23Value; TextView tvProp24Value;
        TextView tvProp25Value; TextView tvProp26Value; TextView tvProp27Value; TextView tvProp28Value; TextView tvProp29Value; TextView tvProp30Value;
        TextView tvProp31Value; TextView tvProp32Value; TextView tvProp33Value; TextView tvProp34Value;

        tvProp1 = (TextView) v.findViewById(R.id.tvDetProp1); tvProp2 = (TextView) v.findViewById(R.id.tvDetProp2);
        tvProp3 = (TextView) v.findViewById(R.id.tvDetProp3); tvProp4 = (TextView) v.findViewById(R.id.tvDetProp4);
        tvProp5 = (TextView) v.findViewById(R.id.tvDetProp5); tvProp6 = (TextView) v.findViewById(R.id.tvDetProp6);
        tvProp7 = (TextView) v.findViewById(R.id.tvDetProp7); tvProp8 = (TextView) v.findViewById(R.id.tvDetProp8);
        tvProp9 = (TextView) v.findViewById(R.id.tvDetProp9); tvProp10 = (TextView) v.findViewById(R.id.tvDetProp10);
        tvProp11 = (TextView) v.findViewById(R.id.tvDetProp11); tvProp12 = (TextView) v.findViewById(R.id.tvDetProp12);
        tvProp13 = (TextView) v.findViewById(R.id.tvDetProp13); tvProp14 = (TextView) v.findViewById(R.id.tvDetProp14);
        tvProp15 = (TextView) v.findViewById(R.id.tvDetProp15); tvProp16 = (TextView) v.findViewById(R.id.tvDetProp16);
        tvProp17 = (TextView) v.findViewById(R.id.tvDetProp17); tvProp18 = (TextView) v.findViewById(R.id.tvDetProp18);
        tvProp19 = (TextView) v.findViewById(R.id.tvDetProp19); tvProp20 = (TextView) v.findViewById(R.id.tvDetProp20);
        tvProp21 = (TextView) v.findViewById(R.id.tvDetProp21); tvProp22 = (TextView) v.findViewById(R.id.tvDetProp22);
        tvProp23 = (TextView) v.findViewById(R.id.tvDetProp23); tvProp24 = (TextView) v.findViewById(R.id.tvDetProp24);
        tvProp25 = (TextView) v.findViewById(R.id.tvDetProp25); tvProp26 = (TextView) v.findViewById(R.id.tvDetProp26);
        tvProp27 = (TextView) v.findViewById(R.id.tvDetProp27); tvProp28 = (TextView) v.findViewById(R.id.tvDetProp28);
        tvProp29 = (TextView) v.findViewById(R.id.tvDetProp29); tvProp30 = (TextView) v.findViewById(R.id.tvDetProp30);
        tvProp31 = (TextView) v.findViewById(R.id.tvDetProp31); tvProp32 = (TextView) v.findViewById(R.id.tvDetProp32);
        tvProp33 = (TextView) v.findViewById(R.id.tvDetProp33); tvProp34 = (TextView) v.findViewById(R.id.tvDetProp34);

        tvProp1Value = (TextView) v.findViewById(R.id.tvDetPropValue1); tvProp2Value = (TextView) v.findViewById(R.id.tvDetPropValue2);
        tvProp3Value = (TextView) v.findViewById(R.id.tvDetPropValue3); tvProp4Value = (TextView) v.findViewById(R.id.tvDetPropValue4);
        tvProp5Value = (TextView) v.findViewById(R.id.tvDetPropValue5); tvProp6Value = (TextView) v.findViewById(R.id.tvDetPropValue6);
        tvProp7Value = (TextView) v.findViewById(R.id.tvDetPropValue7); tvProp8Value = (TextView) v.findViewById(R.id.tvDetPropValue8);
        tvProp9Value = (TextView) v.findViewById(R.id.tvDetPropValue9); tvProp10Value = (TextView) v.findViewById(R.id.tvDetPropValue10);
        tvProp11Value = (TextView) v.findViewById(R.id.tvDetPropValue11); tvProp12Value = (TextView) v.findViewById(R.id.tvDetPropValue12);
        tvProp13Value = (TextView) v.findViewById(R.id.tvDetPropValue13); tvProp14Value = (TextView) v.findViewById(R.id.tvDetPropValue14);
        tvProp15Value = (TextView) v.findViewById(R.id.tvDetPropValue15); tvProp16Value = (TextView) v.findViewById(R.id.tvDetPropValue16);
        tvProp17Value = (TextView) v.findViewById(R.id.tvDetPropValue17); tvProp18Value = (TextView) v.findViewById(R.id.tvDetPropValue18);
        tvProp19Value = (TextView) v.findViewById(R.id.tvDetPropValue19); tvProp20Value = (TextView) v.findViewById(R.id.tvDetPropValue20);
        tvProp21Value = (TextView) v.findViewById(R.id.tvDetPropValue21); tvProp22Value = (TextView) v.findViewById(R.id.tvDetPropValue22);
        tvProp23Value = (TextView) v.findViewById(R.id.tvDetPropValue23); tvProp24Value = (TextView) v.findViewById(R.id.tvDetPropValue24);
        tvProp25Value = (TextView) v.findViewById(R.id.tvDetPropValue25); tvProp26Value = (TextView) v.findViewById(R.id.tvDetPropValue26);
        tvProp27Value = (TextView) v.findViewById(R.id.tvDetPropValue27); tvProp28Value = (TextView) v.findViewById(R.id.tvDetPropValue28);
        tvProp29Value = (TextView) v.findViewById(R.id.tvDetPropValue29); tvProp30Value = (TextView) v.findViewById(R.id.tvDetPropValue30);
        tvProp31Value = (TextView) v.findViewById(R.id.tvDetPropValue31); tvProp32Value = (TextView) v.findViewById(R.id.tvDetPropValue32);
        tvProp33Value = (TextView) v.findViewById(R.id.tvDetPropValue33); tvProp34Value = (TextView) v.findViewById(R.id.tvDetPropValue34);


        propertiesTextViews.add(tvProp1); propertiesTextViews.add(tvProp2); propertiesTextViews.add(tvProp3); propertiesTextViews.add(tvProp4);
        propertiesTextViews.add(tvProp5); propertiesTextViews.add(tvProp6); propertiesTextViews.add(tvProp7); propertiesTextViews.add(tvProp8);
        propertiesTextViews.add(tvProp9); propertiesTextViews.add(tvProp10); propertiesTextViews.add(tvProp11); propertiesTextViews.add(tvProp12);
        propertiesTextViews.add(tvProp13); propertiesTextViews.add(tvProp14); propertiesTextViews.add(tvProp15); propertiesTextViews.add(tvProp16);
        propertiesTextViews.add(tvProp17); propertiesTextViews.add(tvProp18); propertiesTextViews.add(tvProp19); propertiesTextViews.add(tvProp20);
        propertiesTextViews.add(tvProp21); propertiesTextViews.add(tvProp22); propertiesTextViews.add(tvProp23); propertiesTextViews.add(tvProp24);
        propertiesTextViews.add(tvProp25); propertiesTextViews.add(tvProp26); propertiesTextViews.add(tvProp27); propertiesTextViews.add(tvProp28);
        propertiesTextViews.add(tvProp29); propertiesTextViews.add(tvProp30); propertiesTextViews.add(tvProp31); propertiesTextViews.add(tvProp32);
        propertiesTextViews.add(tvProp33); propertiesTextViews.add(tvProp34);

        valuesTextViews.add(tvProp1Value); valuesTextViews.add(tvProp2Value); valuesTextViews.add(tvProp3Value); valuesTextViews.add(tvProp4Value);
        valuesTextViews.add(tvProp5Value); valuesTextViews.add(tvProp6Value); valuesTextViews.add(tvProp7Value); valuesTextViews.add(tvProp8Value);
        valuesTextViews.add(tvProp9Value); valuesTextViews.add(tvProp10Value); valuesTextViews.add(tvProp11Value); valuesTextViews.add(tvProp12Value);
        valuesTextViews.add(tvProp13Value); valuesTextViews.add(tvProp14Value); valuesTextViews.add(tvProp15Value); valuesTextViews.add(tvProp16Value);
        valuesTextViews.add(tvProp17Value); valuesTextViews.add(tvProp18Value); valuesTextViews.add(tvProp19Value); valuesTextViews.add(tvProp20Value);
        valuesTextViews.add(tvProp21Value); valuesTextViews.add(tvProp22Value); valuesTextViews.add(tvProp23Value); valuesTextViews.add(tvProp24Value);
        valuesTextViews.add(tvProp25Value); valuesTextViews.add(tvProp26Value); valuesTextViews.add(tvProp27Value); valuesTextViews.add(tvProp28Value);
        valuesTextViews.add(tvProp29Value); valuesTextViews.add(tvProp30Value); valuesTextViews.add(tvProp31Value); valuesTextViews.add(tvProp32Value);
        valuesTextViews.add(tvProp33Value); valuesTextViews.add(tvProp34Value);

        clearDetails();
    }

    public void clearDetails() {
        for (TextView tv: propertiesTextViews
                ) {
            tv.setText("");
        }

        for (TextView tv : valuesTextViews) {
            tv.setText("");
        }
    }

    public void setDenmDetails(FacilityNotification fc) {

        //Set property textviews
        for(int i=0;i<denmProperties.length;i++) {
            propertiesTextViews.get(i).setText(denmProperties[i]);
            valuesTextViews.get(i).setText(
                    new FacilityNotificationFieldValue(denmProperties[i],fc).getDENMValue()
            );
        }
        if(denmProperties.length<propertiesTextViews.size()) {
            int from = propertiesTextViews.size()-denmProperties.length;
            for(int i=propertiesTextViews.size()-from;i<propertiesTextViews.size();i++) {
                propertiesTextViews.get(i).setText("");
                valuesTextViews.get(i).setText("");
            }
        }

        //Set values
        //TODO: set values
    }

    public void setCamDetails(FacilityNotification fc) {
        //Set property textviews
        for(int i=0;i<camProperties.length;i++) {
            propertiesTextViews.get(i).setText(camProperties[i]);
            valuesTextViews.get(i).setText(
                    new FacilityNotificationFieldValue(camProperties[i],fc).getCAMValue()
            );
        }
        if(camProperties.length<propertiesTextViews.size()) {
            int from = propertiesTextViews.size()-camProperties.length;
            for(int i=propertiesTextViews.size()-from;i<propertiesTextViews.size();i++) {
                propertiesTextViews.get(i).setText("");
                valuesTextViews.get(i).setText("");
            }
        }
    }

    public void setMapDetails(LdmObject lo) {
        //Set property textviews
        for(int i=0;i<mapProperties.length;i++) {
            propertiesTextViews.get(i).setText(mapProperties[i]);
            valuesTextViews.get(i).setText(
                    new LdmNotificationFieldValue(mapProperties[i],lo).getMAPValue()
            );
        }
        if(mapProperties.length<propertiesTextViews.size()) {
            int from = propertiesTextViews.size()-mapProperties.length;
            for(int i=propertiesTextViews.size()-from;i<propertiesTextViews.size();i++) {
                propertiesTextViews.get(i).setText("");
                valuesTextViews.get(i).setText("");
            }
        }
    }

    public void setSpatDetails(LdmObject lo) {
        //Set property textviews
        for(int i=0;i<spatProperties.length;i++) {
            propertiesTextViews.get(i).setText(spatProperties[i]);
            valuesTextViews.get(i).setText(
                    new LdmNotificationFieldValue(spatProperties[i],lo).getSPATValue()
            );
        }
        if(spatProperties.length<propertiesTextViews.size()) {
            int from = propertiesTextViews.size()-spatProperties.length;
            for(int i=propertiesTextViews.size()-from;i<propertiesTextViews.size();i++) {
                propertiesTextViews.get(i).setText("");
                valuesTextViews.get(i).setText("");
            }
        }
    }

    private String timeStampRepresentationConverter(Long timeStamp) {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(timeStamp + UTC_MILLISECONDS_UNTIL_2004 - (37 - LEAP_SECONDS_UNTIL_2004) * 1000),
                ZoneId.of("UTC")
        );
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX").format(utcDateTime.withZoneSameInstant(ZoneId.systemDefault()));
    }
}
