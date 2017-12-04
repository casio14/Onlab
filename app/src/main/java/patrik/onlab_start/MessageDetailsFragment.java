package patrik.onlab_start;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import patrik.onlab_start.Model.FacilityNotificationFieldValue;
import patrik.onlab_start.Model.LdmNotificationFieldValue;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;

/**
 * Show the clicked Packet details
 *
 * Created by Patrik on 2017.03.08..
 */
public class MessageDetailsFragment extends DialogFragment {

    private static PacketAncestor data;

    List<TextView> propertiesTextViews = new ArrayList<TextView>();
    List<TextView> valuesTextViews = new ArrayList<TextView>();

    String[] denmProperties;
    String[] camProperties;
    String[] mapProperties;
    String[] spatProperties;
    String[] bsmProperties;

    View view;

    private static final long LEAP_SECONDS_UNTIL_2004 = 32;

    private static final long UTC_MILLISECONDS_UNTIL_2004 = 1072915200000L;

    public MessageDetailsFragment() {
    }

    public MessageDetailsFragment(PacketAncestor data) {
            this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);
        this.view = v;

        initializeComponents(v);

        changeData(data);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow()
                    .setLayout((int) (getScreenWidth(getActivity()) * .6), ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
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

                case "BSM":
                    setBsmDetails(lo);
                    break;
            }
        }

    }

    public void initializeComponents(View v) {

        deleteTextViews();

        denmProperties = getResources().getStringArray(R.array.denmProperties);
        camProperties = getResources().getStringArray(R.array.camProperties);
        mapProperties = getResources().getStringArray(R.array.mapProperties);
        spatProperties = getResources().getStringArray(R.array.spatProperties);
        bsmProperties = getResources().getStringArray(R.array.bsmProperties);
    }

    private void deleteTextViews() {

        LinearLayout detailsLinearLayout = (LinearLayout) view.findViewById(R.id.detailsLinearLayout);
        detailsLinearLayout.removeAllViews();

        propertiesTextViews.clear();
        valuesTextViews.clear();
    }

    public void setDenmDetails(FacilityNotification fc) {

        //Set property textviews
        createTextViews(denmProperties.length);
        for(int i=0;i<denmProperties.length;i++) {
            propertiesTextViews.get(i).setText(denmProperties[i]);
            valuesTextViews.get(i).setText(
                    new FacilityNotificationFieldValue(denmProperties[i],fc).getDENMValue()
            );
        }
    }

    public void setCamDetails(FacilityNotification fc) {
        //Set property textviews
        createTextViews(camProperties.length);
        for(int i=0;i<camProperties.length;i++) {
            propertiesTextViews.get(i).setText(camProperties[i]);
            valuesTextViews.get(i).setText(
                    new FacilityNotificationFieldValue(camProperties[i],fc).getCAMValue()
            );
        }
    }

    public void setMapDetails(LdmObject lo) {
        //Set property textviews
        createTextViews(mapProperties.length);
        for(int i=0;i<mapProperties.length;i++) {
            propertiesTextViews.get(i).setText(mapProperties[i]);
            valuesTextViews.get(i).setText(
                    new LdmNotificationFieldValue(mapProperties[i],lo).getMAPValue()
            );
        }
    }

    public void setSpatDetails(LdmObject lo) {
        //Set property textviews
        createTextViews(spatProperties.length);
        for(int i=0;i<spatProperties.length;i++) {
            propertiesTextViews.get(i).setText(spatProperties[i]);
            valuesTextViews.get(i).setText(
                    new LdmNotificationFieldValue(spatProperties[i],lo).getSPATValue()
            );
        }
    }

    public void setBsmDetails(LdmObject lo) {
        //Set property textviews
        createTextViews(bsmProperties.length);
        for(int i=0;i<bsmProperties.length;i++) {
            propertiesTextViews.get(i).setText(bsmProperties[i]);
            valuesTextViews.get(i).setText(
                    new LdmNotificationFieldValue(bsmProperties[i],lo).getBSMValue()
            );
        }
    }

    private String timeStampRepresentationConverter(Long timeStamp) {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(timeStamp + UTC_MILLISECONDS_UNTIL_2004 - (37 - LEAP_SECONDS_UNTIL_2004) * 1000),
                ZoneId.of("UTC")
        );
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX").format(utcDateTime.withZoneSameInstant(ZoneId.systemDefault()));
    }

    public static MessageDetailsFragment newInstance(String title,PacketAncestor data) {
        MessageDetailsFragment frag = new MessageDetailsFragment(data);
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    private void createTextViews(int count) {

        deleteTextViews();

        LinearLayout detailsLinearLayout = (LinearLayout) view.findViewById(R.id.detailsLinearLayout);

        for(int i=0;i<count;i++) {
            LinearLayout elementLinearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,0);
            params.setMargins(0,10,0,0);
            elementLinearLayout.setLayoutParams(params);
            elementLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            elementLinearLayout.setWeightSum(2);

            TextView label = new TextView(getContext());
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
            label.setLayoutParams(labelParams);
            label.setTextSize(12);
            label.setTypeface(null, Typeface.BOLD);

            TextView value = new TextView(getContext());
            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
            value.setLayoutParams(valueParams);
            value.setTextSize(12);

            propertiesTextViews.add(label);
            valuesTextViews.add(value);

            elementLinearLayout.addView(label);
            elementLinearLayout.addView(value);

            detailsLinearLayout.addView(elementLinearLayout);
        }
    }
}
