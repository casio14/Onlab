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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_details_fragment, container, false);

        initializeComponents(v);

        return v;
    }

    public void changeData(PacketAncestor data) {

        if(data.getNotificationType() == NotificationType.FAC_NOTIFICATION) {
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

        if(data.getNotificationType() == NotificationType.LDM_NOTIFICATION) {
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
            fcSetDENMParamValue(denmProperties[i],valuesTextViews.get(i),fc);
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
            fcSetCAMParamValue(camProperties[i],valuesTextViews.get(i),fc);
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
            loSetMAPParamValue(mapProperties[i],valuesTextViews.get(i),lo);
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
            loSetSPATParamValue(spatProperties[i],valuesTextViews.get(i),lo);
        }
        if(spatProperties.length<propertiesTextViews.size()) {
            int from = propertiesTextViews.size()-spatProperties.length;
            for(int i=propertiesTextViews.size()-from;i<propertiesTextViews.size();i++) {
                propertiesTextViews.get(i).setText("");
                valuesTextViews.get(i).setText("");
            }
        }
    }

    public void fcSetDENMParamValue(String value,TextView tv,FacilityNotification fc) {
        switch (value) {
            case "ActionID":
                if(fc.getDENM().getActionID()!=null)
                    tv.setText(String.valueOf(fc.getDENM().getActionID().getStationID()));
                else
                    tv.setText("null");
                break;

            case "DetectionTime":
                if(fc.getDENM().getDetectionTime()!=null)
                    tv.setText(fc.getDENM().getDetectionTime().toString());
                else
                    tv.setText("null");
                break;

            case "ReferenceTime":
                if(fc.getDENM().getReferenceTime()!=null)
                    tv.setText(fc.getDENM().getReferenceTime().toString());
                else
                    tv.setText("null");
                break;

            case "StationType":
                if(fc.getDENM().getStationType()!=null)
                    tv.setText(fc.getDENM().getStationType().toString());
                else
                    tv.setText("null");
                break;

            case "ValidityDuration":
                if(fc.getDENM().getValidityDuration(TimeUnit.MINUTES)!=null)
                    tv.setText(fc.getDENM().getValidityDuration(TimeUnit.MINUTES).toString());
                else
                    tv.setText("null");
                break;
            //
            case "Altitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getAltitude(LengthUnit.M)!=null)
                    tv.setText(fc.getDENM().getEventPosition().getAltitude(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "Latitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLatitude()!=null)
                    tv.setText(fc.getDENM().getEventPosition().getLatitude().toString());
                else
                    tv.setText("null");
                break;

            case "Longitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLongitude()!=null)
                    tv.setText(fc.getDENM().getEventPosition().getLongitude().toString());
                else
                    tv.setText("null");
                break;
            //
            case "DangerousGoods":
                if(fc.getDENM().getStationaryVehicle()!=null && fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods()!=null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods().toString());
                else
                    tv.setText("null");
                break;

            case "LaneClosed":
                if(fc.getDENM().getRoadWorks()!=null && fc.getDENM().getRoadWorks().getDrivingLaneStatus()!=null)
                    tv.setText(String.valueOf(fc.getDENM().getRoadWorks().getDrivingLaneStatus().isLaneClosed(((fc.getDENM().getLaneNumber().intValue())))));
                else
                    tv.setText("null");
                break;

            case "Heading":
                if(fc.getDENM().getEventPosition()!=null && fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG)!=null)
                    tv.setText(fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if(fc.getDENM().getSpeedValue(SpeedUnit.KMperH)!=null)
                    tv.setText(fc.getDENM().getSpeedValue(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "EventType":
                if(fc.getDENM().getEventType()!=null)
                    tv.setText(fc.getDENM().getEventType().toString());
                else
                    tv.setText("null");
                break;

            case "Ext.Temperature":
                if(fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS)!=null)
                    tv.setText(fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS).toString());
                else
                    tv.setText("null");
                break;

            case "InformationQuality":
                if(fc.getDENM().getInformationQuality()!=null)
                    tv.setText(fc.getDENM().getInformationQuality().toString());
                else
                    tv.setText("null");
                break;

            case "LaneNumber":
                if(fc.getDENM().getLaneNumber()!=null)
                    tv.setText(fc.getDENM().getLaneNumber().toString());
                else
                    tv.setText("null");
                break;

            case "RelevanceDistance":
                if(fc.getDENM().getRelevanceDistance()!=null)
                    tv.setText(fc.getDENM().getRelevanceDistance().toString());
                else
                    tv.setText("null");
                break;

            case "RelevanceTrafficDirection":
                if(fc.getDENM().getRelevanceTrafficDirection()!=null)
                    tv.setText(fc.getDENM().getRelevanceTrafficDirection().toString());
                else
                    tv.setText("null");
                break;

            case "RoadType":
                if(fc.getDENM().getRoadType()!=null)
                    tv.setText(fc.getDENM().getRoadType().toString());
                else
                    tv.setText("null");
                break;

            case "SpeedLimit":
                if(fc.getDENM().getRoadWorks()!=null && fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH)!=null)
                    tv.setText(fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "StartingPointSpeedLimit":
                if(fc.getDENM().getRoadWorks()!=null && fc.getDENM().getRoadWorks().getStartingPointSpeedLimit()!=null)
                    tv.setText(fc.getDENM().getRoadWorks().getStartingPointSpeedLimit().toString());
                else
                    tv.setText("null");
                break;

            case "StationaryCause":
                if(fc.getDENM().getStationaryVehicle()!=null && fc.getDENM().getStationaryVehicle().getStationaryCause()!=null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getStationaryCause().toString());
                else
                    tv.setText("null");
                break;

            case "StationarySince":
                if(fc.getDENM().getStationaryVehicle()!=null && fc.getDENM().getStationaryVehicle().getStationarySince()!=null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getStationarySince().toString());
                else
                    tv.setText("null");
                break;

            case "TransmissionInterval":
                if(fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES)!=null)
                    tv.setText(fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES).toString());
                else
                    tv.setText("null");
                break;

            default:
                tv.setText("null");
        }
    }

    public void fcSetCAMParamValue(String value,TextView tv,FacilityNotification fc) {

        if(fc.getStationObject()==null) {
            tv.setText("null");
            return;
        }

        switch (value) {
            case "Heading":
                if( fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "StationType":
                if(fc.getStationObject().getBasicInfo()!=null && fc.getStationObject().getBasicInfo().getStationType()!=null)
                    tv.setText(fc.getStationObject().getBasicInfo().getStationType().toString());
                else
                    tv.setText("null");
                break;
            //
            case "Altitude":
                if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null &&
                        fc.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M)!=null)
                    tv.setText(fc.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "Latitude":
                if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null)
                    tv.setText(fc.getStationObject().getBasicInfo().getReferencePosition().getLatitude().toString());
                else
                    tv.setText("null");
                break;

            case "Longitude":
                if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null)
                    tv.setText(fc.getStationObject().getBasicInfo().getReferencePosition().getLongitude().toString());
                else
                    tv.setText("null");
                break;
            //
            case "VehicleRole":
                if(fc.getStationObject().getCommonInfo()!=null && fc.getStationObject().getCommonInfo().getVehicleRole()!=null)
                    tv.setText(fc.getStationObject().getCommonInfo().getVehicleRole().toString());
                else
                    tv.setText("null");
                break;

            case "PerformanceClass":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getPerformanceClass()!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getPerformanceClass().toString());
                else
                    tv.setText("null");
                break;

            case "LaneNumber":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getLaneNumber()!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLaneNumber().toString());
                else
                    tv.setText("null");
                break;

            case "DriveDirection":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getDriveDirection()!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getDriveDirection().toString());
                else
                    tv.setText("null");
                break;

            case "LongitudinalAcceleration":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "LateralAcceleration":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "VerticalAcceleration":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "EmbarkationStatus":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().isEmbarkationStatus()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isEmbarkationStatus().toString());
                else
                    tv.setText("null");
                break;

            case "CurvaureValue":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getCurvatureValue()!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getCurvatureValue().toString());
                else
                    tv.setText("null");
                break;

            case "CurvatureCalculationMode":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getCurvatureCalculationMode()!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getCurvatureCalculationMode().toString());
                else
                    tv.setText("null");
                break;

            case "YawRate":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "SteeringWheelAngle":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "VehicleLength":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "VehhicleWidth":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M)!=null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "PtActivationType":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getPtActivationType()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getPtActivationType().toString());
                else
                    tv.setText("null");
                break;

            case "SpecialTransportType":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getSpecialTransportType()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getSpecialTransportType().toString());
                else
                    tv.setText("null");
                break;

            case "DangerousGoodsBasic":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic().toString());
                else
                    tv.setText("null");
                break;

            case "RoadworksSubCauseCode":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode().toString());
                else
                    tv.setText("null");
                break;

            case "TrafficRule":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getTrafficRule()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getTrafficRule().toString());
                else
                    tv.setText("null");
                break;

            case "SpeedLimit":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH)!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "SirenActivated":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().isSirenActivated()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isSirenActivated().toString());
                else
                    tv.setText("null");
                break;

            case "IndicationCauseCode":
                if(fc.getStationObject().getSpecialInfo()!=null &&  fc.getStationObject().getSpecialInfo().getIndicationCauseCode()!=null)
                    tv.setText( fc.getStationObject().getSpecialInfo().getIndicationCauseCode().toString());
                else
                    tv.setText("null");
                break;

            case "EmergencyPriorityRequestForFreeCrossingAtATrafficLight":
                if(fc.getStationObject().getSpecialInfo()!=null && fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight()!=null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight().toString());
                else
                    tv.setText("null");
                break;

            case "ProtectedZonneLatitude":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone()!=null)
                    tv.setText(String.valueOf(fc.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLatitude()));
                else
                    tv.setText("null");
                break;

            case "ProtectedZoneLongitude":
                if(fc.getStationObject().getPositionInfo()!=null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone()!=null)
                    tv.setText(String.valueOf(fc.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLongitude()));
                else
                    tv.setText("null");
                break;

            default:
                tv.setText("null");
        }
    }

    public void loSetMAPParamValue(String value, TextView tv, LdmObject lo) {

        switch (value) {
            case "Longitude":
                if (lo.getLongitude() != null)
                    tv.setText(lo.getLongitude().toString());
                else
                    tv.setText("null");
                break;

            case "Latitude":
                if (lo.getLatitude() != null)
                    tv.setText(lo.getLatitude().toString());
                else
                    tv.setText("null");
                break;

            case "TimeStamp":
                if (lo.getTimestamp() != null)
                    tv.setText(String.valueOf(lo.getTimestamp().getTime()));
                else
                    tv.setText("null");
                break;

            case "ObjectID":
                if (lo.getObjectID() != null)
                    tv.setText(lo.getObjectID().toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if (lo.getSpeed(SpeedUnit.KMperH) != null)
                    tv.setText(lo.getSpeed(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "Security":
                if (lo.getSecurity() != null)
                    tv.setText(lo.getSecurity().toString());
                else
                    tv.setText("null");
                break;

            case "1.Intersection Longitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(0).getRefPoint().getLongitude()));
                else
                    tv.setText("null");
                break;

            case "1.Intersection Latitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(0).getRefPoint().getLatitude()));
                else
                    tv.setText("null");
                break;

            case "1.Intersection ID":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1)
                    tv.setText(String.valueOf(lo.getIntersections().get(0).getId()));
                else
                    tv.setText("null");
                break;

            case "1.Intersection laneWidth":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getLaneWidth(LengthUnit.M) != null)
                    tv.setText(lo.getIntersections().get(0).getLaneWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "1.Intersection revision":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1)
                    tv.setText(String.valueOf(lo.getIntersections().get(0).getRevision()));
                else
                    tv.setText("null");
                break;

            case "1.Intersection name":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getName() != null)
                    tv.setText(lo.getIntersections().get(0).getName().toString());
                else
                    tv.setText("null");
                break;

            case "1.Intersection laneCount":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getIntersectionLaneSet() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(0).getIntersectionLaneSet().size()));
                else
                    tv.setText("null");
                break;

            case "2.Intersection Longitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(1).getRefPoint().getLongitude()));
                else
                    tv.setText("null");
                break;

            case "2.Intersection Latitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(1).getRefPoint().getLatitude()));
                else
                    tv.setText("null");
                break;

            case "2.Intersection ID":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2)
                    tv.setText(String.valueOf(lo.getIntersections().get(1).getId()));
                else
                    tv.setText("null");
                break;

            case "2.Intersection laneWidth":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getLaneWidth(LengthUnit.M) != null)
                    tv.setText(lo.getIntersections().get(1).getLaneWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "2.Intersection revision":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2)
                    tv.setText(String.valueOf(lo.getIntersections().get(1).getRevision()));
                else
                    tv.setText("null");
                break;

            case "2.Intersection name":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getName() != null)
                    tv.setText(lo.getIntersections().get(1).getName().toString());
                else
                    tv.setText("null");
                break;

            case "2.Intersection laneCount":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getIntersectionLaneSet() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(1).getIntersectionLaneSet().size()));
                else
                    tv.setText("null");
                break;

            case "3.Intersection Longitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(2).getRefPoint().getLongitude()));
                else
                    tv.setText("null");
                break;

            case "3.Intersection Latitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(2).getRefPoint().getLatitude()));
                else
                    tv.setText("null");
                break;

            case "3.Intersection ID":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3)
                    tv.setText(String.valueOf(lo.getIntersections().get(2).getId()));
                else
                    tv.setText("null");
                break;

            case "3.Intersection laneWidth":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getLaneWidth(LengthUnit.M) != null)
                    tv.setText(lo.getIntersections().get(2).getLaneWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "3.Intersection revision":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3)
                    tv.setText(String.valueOf(lo.getIntersections().get(2).getRevision()));
                else
                    tv.setText("null");
                break;

            case "3.Intersection name":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getName() != null)
                    tv.setText(lo.getIntersections().get(2).getName().toString());
                else
                    tv.setText("null");
                break;

            case "3.Intersection laneCount":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getIntersectionLaneSet() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(2).getIntersectionLaneSet().size()));
                else
                    tv.setText("null");
                break;

            case "4.Intersection Longitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(3).getRefPoint().getLongitude()));
                else
                    tv.setText("null");
                break;

            case "4.Intersection Latitude":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getRefPoint() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(3).getRefPoint().getLatitude()));
                else
                    tv.setText("null");
                break;

            case "4.Intersection ID":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4)
                    tv.setText(String.valueOf(lo.getIntersections().get(3).getId()));
                else
                    tv.setText("null");
                break;

            case "4.Intersection laneWidth":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getLaneWidth(LengthUnit.M) != null)
                    tv.setText(lo.getIntersections().get(3).getLaneWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "4.Intersection revision":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4)
                    tv.setText(String.valueOf(lo.getIntersections().get(3).getRevision()));
                else
                    tv.setText("null");
                break;

            case "4.Intersection name":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getName() != null)
                    tv.setText(lo.getIntersections().get(3).getName().toString());
                else
                    tv.setText("null");
                break;

            case "4.Intersection laneCount":
                if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getIntersectionLaneSet() != null)
                    tv.setText(String.valueOf(lo.getIntersections().get(3).getIntersectionLaneSet().size()));
                else
                    tv.setText("null");
                break;

            default:
                tv.setText("null");
        }
    }

    public void loSetSPATParamValue(String value, TextView tv, LdmObject lo) {

        switch (value) {
            case "Longitude":
                if (lo.getLongitude() != null)
                    tv.setText(lo.getLongitude().toString());
                else
                    tv.setText("null");
                break;

            case "Latitude":
                if (lo.getLatitude() != null)
                    tv.setText(lo.getLatitude().toString());
                else
                    tv.setText("null");
                break;

            case "TimeStamp":
                if (lo.getTimestamp() != null)
                    tv.setText(String.valueOf(lo.getTimestamp().getTime()));
                else
                    tv.setText("null");
                break;

            case "ObjectID":
                if (lo.getObjectID() != null)
                    tv.setText(lo.getObjectID().toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if (lo.getSpeed(SpeedUnit.KMperH) != null)
                    tv.setText(lo.getSpeed(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "Security":
                if (lo.getSecurity() != null)
                    tv.setText(lo.getSecurity().toString());
                else
                    tv.setText("null");
                break;

            case "Count of intersectionStates":
                if (lo.getIntersectionStates() != null)
                    tv.setText(String.valueOf(lo.getIntersectionStates().size()));
                else
                    tv.setText("null");
                break;

            case "1.IntersectionState TimeStamp":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getTimestamp() != null)
                    tv.setText(lo.getIntersectionStates().get(0).getTimestamp().toString());
                else
                    tv.setText("null");
                break;

            case "1.IntersectionState ID":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getId() != null)
                    tv.setText(lo.getIntersectionStates().get(0).getId().toString());
                else
                    tv.setText("null");
                break;

            case "1.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getLaneCount() != null)
                    tv.setText(lo.getIntersectionStates().get(0).getLaneCount().toString());
                else
                    tv.setText("null");
                break;

            case "1.IntersectionState Revision":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1)
                    tv.setText(String.valueOf(lo.getIntersectionStates().get(0).getRevision()));
                else
                    tv.setText("null");
                break;

            case "2.IntersectionState TimeStamp":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getTimestamp() != null)
                    tv.setText(lo.getIntersectionStates().get(1).getTimestamp().toString());
                else
                    tv.setText("null");
                break;

            case "2.IntersectionState ID":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getId() != null)
                    tv.setText(lo.getIntersectionStates().get(1).getId().toString());
                else
                    tv.setText("null");
                break;

            case "2.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getLaneCount() != null)
                    tv.setText(lo.getIntersectionStates().get(1).getLaneCount().toString());
                else
                    tv.setText("null");
                break;

            case "2.IntersectionState Revision":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2)
                    tv.setText(String.valueOf(lo.getIntersectionStates().get(1).getRevision()));
                else
                    tv.setText("null");
                break;

            case "3.IntersectionState TimeStamp":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getTimestamp() != null)
                    tv.setText(lo.getIntersectionStates().get(2).getTimestamp().toString());
                else
                    tv.setText("null");
                break;

            case "3.IntersectionState ID":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getId() != null)
                    tv.setText(lo.getIntersectionStates().get(2).getId().toString());
                else
                    tv.setText("null");
                break;

            case "3.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getLaneCount() != null)
                    tv.setText(lo.getIntersectionStates().get(2).getLaneCount().toString());
                else
                    tv.setText("null");
                break;

            case "3.IntersectionState Revision":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3)
                    tv.setText(String.valueOf(lo.getIntersectionStates().get(2).getRevision()));
                else
                    tv.setText("null");
                break;

            case "4.IntersectionState TimeStamp":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getTimestamp() != null)
                    tv.setText(lo.getIntersectionStates().get(3).getTimestamp().toString());
                else
                    tv.setText("null");
                break;

            case "4.IntersectionState ID":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getId() != null)
                    tv.setText(lo.getIntersectionStates().get(3).getId().toString());
                else
                    tv.setText("null");
                break;

            case "4.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getLaneCount() != null)
                    tv.setText(lo.getIntersectionStates().get(3).getLaneCount().toString());
                else
                    tv.setText("null");
                break;

            case "4.IntersectionState Revision":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4)
                    tv.setText(String.valueOf(lo.getIntersectionStates().get(3).getRevision()));
                else
                    tv.setText("null");
                break;

            default:
                tv.setText("null");
        }
    }
}
