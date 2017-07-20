package patrik.onlab_start;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
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
import java.util.concurrent.TimeUnit;

import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;

/**
 * Adapter class to MessageListFragment
 * <p>
 * Created by Patrik on 2017.03.07..
 */
public class MessageAdapter extends ArrayAdapter {

    List<PacketAncestor> list;

    Context mContext;

    //Adjusted spinner values
    String DENMType1Value;
    String DENMType2Value;
    String DENMType3Value;
    String CAMType1Value;
    String CAMType2Value;
    String CAMType3Value;
    String MAPType1Value;
    String MAPType2Value;
    String MAPType3Value;
    String SPATType1Value;
    String SPATType2Value;
    String SPATType3Value;

    //Constructor
    public MessageAdapter(Context context, int resource, List<PacketAncestor> input_data, String DENMType1Value, String DENMType2Value,
                          String DENMType3Value, String CAMType1Value, String CAMType2Value, String CAMType3Value,
                          String MAPType1Value, String MAPType2Value, String MAPType3Value,
                          String SPATType1Value, String SPATType2Value, String SPATType3Value) {
        super(context, resource, input_data);
        mContext = context;
        list = input_data;

        this.DENMType1Value = DENMType1Value;
        this.DENMType2Value = DENMType2Value;
        this.DENMType3Value = DENMType3Value;
        this.CAMType1Value = CAMType1Value;
        this.CAMType2Value = CAMType2Value;
        this.CAMType3Value = CAMType3Value;
        this.MAPType1Value = MAPType1Value;
        this.MAPType2Value = MAPType2Value;
        this.MAPType3Value = MAPType3Value;
        this.SPATType1Value = SPATType1Value;
        this.SPATType2Value = SPATType2Value;
        this.SPATType3Value = SPATType3Value;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.row_item, parent, false);

            if (position % 2 == 1)
                v.setBackgroundColor(Color.parseColor("#dcedc8"));

            ViewHolder holder = new ViewHolder();
            holder.tvType = (TextView) v.findViewById(R.id.tvType);
            holder.tvParamType1 = (TextView) v.findViewById(R.id.tvParamType1);
            holder.tvParamType2 = (TextView) v.findViewById(R.id.tvParamType2);
            holder.tvParamType3 = (TextView) v.findViewById(R.id.tvParamType3);
            holder.tvParam1 = (TextView) v.findViewById(R.id.tvParam1);
            holder.tvParam2 = (TextView) v.findViewById(R.id.tvParam2);
            holder.tvParam3 = (TextView) v.findViewById(R.id.tvParam3);
            v.setTag(holder);
        }

        if (position % 2 == 1 && list.get(position).getSelected() == 0)
            v.setBackgroundColor(Color.parseColor("#dcedc8"));
        if (position % 2 == 0 && list.get(position).getSelected() == 0)
            v.setBackgroundColor(Color.parseColor("#FFFFFF"));

        final PacketAncestor element = list.get(position);
        if (element != null) {
            ViewHolder holder = (ViewHolder) v.getTag();

            if (element.getNotificationType() == NotificationType.FAC_NOTIFICATION) {
                FacilityNotification fc = (FacilityNotification) element.getObject();

                if (fc.getType().toString().equals("DENM")) {
                    holder.tvType.setText(fc.getType().toString());
                    holder.tvParamType1.setText(DENMType1Value);
                    holder.tvParamType2.setText(DENMType2Value);
                    holder.tvParamType3.setText(DENMType3Value);

                    fcSetDENMParamValue(DENMType1Value, holder.tvParam1, fc);
                    fcSetDENMParamValue(DENMType2Value, holder.tvParam2, fc);
                    fcSetDENMParamValue(DENMType3Value, holder.tvParam3, fc);
                }

                if (fc.getType().toString().equals("CAM")) {
                    holder.tvType.setText(fc.getType().toString());
                    holder.tvParamType1.setText(CAMType1Value);
                    holder.tvParamType2.setText(CAMType2Value);
                    holder.tvParamType3.setText(CAMType3Value);

                    fcSetCAMParamValue(CAMType1Value, holder.tvParam1, fc);
                    fcSetCAMParamValue(CAMType2Value, holder.tvParam2, fc);
                    fcSetCAMParamValue(CAMType3Value, holder.tvParam3, fc);
                }
            }


            if (element.getNotificationType() == NotificationType.LDM_NOTIFICATION) {
                LdmObject lo = (LdmObject) element.getObject();

                if (lo.getObjectType().toString().equals("MAP")) {
                    holder.tvType.setText(lo.getObjectType().toString());
                    holder.tvParamType1.setText(MAPType1Value);
                    holder.tvParamType2.setText(MAPType2Value);
                    holder.tvParamType3.setText(MAPType3Value);
                    loSetMAPParamValue(MAPType1Value, holder.tvParam1, lo);
                    loSetMAPParamValue(MAPType2Value, holder.tvParam2, lo);
                    loSetMAPParamValue(MAPType3Value, holder.tvParam3, lo);
                }

                if (lo.getObjectType().toString().equals("SPAT")) {
                    holder.tvType.setText(lo.getObjectType().toString());
                    holder.tvParamType1.setText(SPATType1Value);
                    holder.tvParamType2.setText(SPATType2Value);
                    holder.tvParamType3.setText(SPATType3Value);
                    loSetSPATParamValue(SPATType1Value, holder.tvParam1, lo);
                    loSetSPATParamValue(SPATType2Value, holder.tvParam2, lo);
                    loSetSPATParamValue(SPATType3Value, holder.tvParam3, lo);
                }
            }

        }
        return v;
    }

    //Get an Item
    public PacketAncestor getItem(int position) {
        return list.get(position);
    }

    //Add packet to list
    public void addPacket(PacketAncestor packet) {
        list.add(packet);
        notifyDataSetChanged();
    }

    public void fcSetDENMParamValue(String value, TextView tv, FacilityNotification fc) {
        switch (value) {
            case "ActionID":
                if (fc.getDENM().getActionID() != null)
                    tv.setText(String.valueOf(fc.getDENM().getActionID().getStationID()));
                else
                    tv.setText("null");
                break;

            case "DetectionTime":
                if (fc.getDENM().getDetectionTime() != null)
                    tv.setText(fc.getDENM().getDetectionTime().toString());
                else
                    tv.setText("null");
                break;

            case "ReferenceTime":
                if (fc.getDENM().getReferenceTime() != null)
                    tv.setText(fc.getDENM().getReferenceTime().toString());
                else
                    tv.setText("null");
                break;

            case "StationType":
                if (fc.getDENM().getStationType() != null)
                    tv.setText(fc.getDENM().getStationType().toString());
                else
                    tv.setText("null");
                break;

            case "ValidityDuration":
                if (fc.getDENM().getValidityDuration(TimeUnit.MINUTES) != null)
                    tv.setText(fc.getDENM().getValidityDuration(TimeUnit.MINUTES).toString());
                else
                    tv.setText("null");
                break;
            //
            case "Altitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getAltitude(LengthUnit.M) != null)
                    tv.setText(fc.getDENM().getEventPosition().getAltitude(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "Latitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLatitude() != null)
                    tv.setText(fc.getDENM().getEventPosition().getLatitude().toString());
                else
                    tv.setText("null");
                break;

            case "Longitude":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLongitude() != null)
                    tv.setText(fc.getDENM().getEventPosition().getLongitude().toString());
                else
                    tv.setText("null");
                break;
            //

            case "DangerousGoods":
                if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods() != null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods().toString());
                else
                    tv.setText("null");
                break;

            case "LaneClosed":
                if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getDrivingLaneStatus() != null)
                    tv.setText(String.valueOf(fc.getDENM().getRoadWorks().getDrivingLaneStatus().isLaneClosed(((fc.getDENM().getLaneNumber().intValue())))));
                else
                    tv.setText("null");
                break;

            case "Heading":
                if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG) != null)
                    tv.setText(fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if (fc.getDENM().getSpeedValue(SpeedUnit.KMperH) != null)
                    tv.setText(fc.getDENM().getSpeedValue(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "EventType":
                if (fc.getDENM().getEventType() != null)
                    tv.setText(fc.getDENM().getEventType().toString());
                else
                    tv.setText("null");
                break;

            case "Ext.Temperature":
                if (fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS) != null)
                    tv.setText(fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS).toString());
                else
                    tv.setText("null");
                break;

            case "InformationQuality":
                if (fc.getDENM().getInformationQuality() != null)
                    tv.setText(fc.getDENM().getInformationQuality().toString());
                else
                    tv.setText("null");
                break;

            case "LaneNumber":
                if (fc.getDENM().getLaneNumber() != null)
                    tv.setText(fc.getDENM().getLaneNumber().toString());
                else
                    tv.setText("null");
                break;

            case "RelevanceDistance":
                if (fc.getDENM().getRelevanceDistance() != null)
                    tv.setText(fc.getDENM().getRelevanceDistance().toString());
                else
                    tv.setText("null");
                break;

            case "RelevanceTrafficDirection":
                if (fc.getDENM().getRelevanceTrafficDirection() != null)
                    tv.setText(fc.getDENM().getRelevanceTrafficDirection().toString());
                else
                    tv.setText("null");
                break;

            case "RoadType":
                if (fc.getDENM().getRoadType() != null)
                    tv.setText(fc.getDENM().getRoadType().toString());
                else
                    tv.setText("null");
                break;

            case "SpeedLimit":
                if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH) != null)
                    tv.setText(fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "StartingPointSpeedLimit":
                if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getStartingPointSpeedLimit() != null)
                    tv.setText(fc.getDENM().getRoadWorks().getStartingPointSpeedLimit().toString());
                else
                    tv.setText("null");
                break;

            case "StationaryCause":
                if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getStationaryCause() != null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getStationaryCause().toString());
                else
                    tv.setText("null");
                break;

            case "StationarySince":
                if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getStationarySince() != null)
                    tv.setText(fc.getDENM().getStationaryVehicle().getStationarySince().toString());
                else
                    tv.setText("null");
                break;

            case "TransmissionInterval":
                if (fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES) != null)
                    tv.setText(fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES).toString());
                else
                    tv.setText("null");
                break;

            default:
                tv.setText("null");
        }
    }

    public void fcSetCAMParamValue(String value, TextView tv, FacilityNotification fc) {

        if (fc.getStationObject() == null) {
            tv.setText("null");
            return;
        }

        switch (value) {
            case "Heading":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "Speed":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "StationType":
                if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getStationType() != null)
                    tv.setText(fc.getStationObject().getBasicInfo().getStationType().toString());
                else
                    tv.setText("null");
                break;
            //
            case "Altitude":
                if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null &&
                        fc.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M) != null)
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
                if (fc.getStationObject().getCommonInfo() != null && fc.getStationObject().getCommonInfo().getVehicleRole() != null)
                    tv.setText(fc.getStationObject().getCommonInfo().getVehicleRole().toString());
                else
                    tv.setText("null");
                break;

            case "PerformanceClass":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getPerformanceClass() != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getPerformanceClass().toString());
                else
                    tv.setText("null");
                break;

            case "LaneNumber":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLaneNumber() != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLaneNumber().toString());
                else
                    tv.setText("null");
                break;

            case "DriveDirection":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getDriveDirection() != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getDriveDirection().toString());
                else
                    tv.setText("null");
                break;

            case "LongitudinalAcceleration":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "LateralAcceleration":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "VerticalAcceleration":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2).toString());
                else
                    tv.setText("null");
                break;

            case "EmbarkationStatus":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isEmbarkationStatus() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isEmbarkationStatus().toString());
                else
                    tv.setText("null");
                break;

            case "CurvaureValue":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCurvatureValue() != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getCurvatureValue().toString());
                else
                    tv.setText("null");
                break;

            case "CurvatureCalculationMode":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCurvatureCalculationMode() != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getCurvatureCalculationMode().toString());
                else
                    tv.setText("null");
                break;

            case "YawRate":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "SteeringWheelAngle":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG).toString());
                else
                    tv.setText("null");
                break;

            case "VehicleLength":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "VehhicleWidth":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M) != null)
                    tv.setText(fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M).toString());
                else
                    tv.setText("null");
                break;

            case "PtActivationType":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getPtActivationType() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getPtActivationType().toString());
                else
                    tv.setText("null");
                break;

            case "SpecialTransportType":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getSpecialTransportType() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getSpecialTransportType().toString());
                else
                    tv.setText("null");
                break;

            case "DangerousGoodsBasic":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic().toString());
                else
                    tv.setText("null");
                break;

            case "RoadworksSubCauseCode":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode().toString());
                else
                    tv.setText("null");
                break;

            case "TrafficRule":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getTrafficRule() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getTrafficRule().toString());
                else
                    tv.setText("null");
                break;

            case "SpeedLimit":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH) != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH).toString());
                else
                    tv.setText("null");
                break;

            case "SirenActivated":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isSirenActivated() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isSirenActivated().toString());
                else
                    tv.setText("null");
                break;

            case "IndicationCauseCode":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getIndicationCauseCode() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().getIndicationCauseCode().toString());
                else
                    tv.setText("null");
                break;

            case "EmergencyPriorityRequestForFreeCrossingAtATrafficLight":
                if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight() != null)
                    tv.setText(fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight().toString());
                else
                    tv.setText("null");
                break;

            case "ProtectedZonneLatitude":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
                    tv.setText(String.valueOf(fc.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLatitude()));
                else
                    tv.setText("null");
                break;

            case "ProtectedZoneLongitude":
                if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
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

    public void savePackets(String fileName) throws IOException {
        FileWriter fw = null;

        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File directory = new File(root, "V2XPacketAnalyzerCSV's"); //New folder
            if (!directory.exists()) {
                directory.mkdir();
            }

            Log.d(directory.getAbsolutePath().toString(), "    " + directory.getAbsolutePath());

            File file = new File(directory, fileName + ".csv"); //New file to the specified folder
            fw = new FileWriter(file);

            String firstLineDENM = "ActionID,DetectionTime,ReferenceTime,StationType,ValidityDuration,Altitude,Latitude,Longitude,DangerousGoods,LaneClosed,Heading," +
                    "Speed,EventType,Ext.Temperature,InformationQuality,LaneNumber,RelevanceDistance,RelevanceTrafficDirection,RoadType,SpeedLimit," +
                    "StartingPointSpeedLimit,StationaryCause,StationarySince,TransmissionInterval" + "\n";

            String firstLineCAM = "Heading,Speed,StationType,Altitude,Latitude,Longitude,VehicleRole,PerformanceClass,LaneNumber,DriveDirection," +
                    "LongitudinalAcceleration,LateralAcceleration,VerticalAcceleration,EmbarkationStatus,CurvatureValue,CurvatureCalculationMode," +
                    "YawRate,SteeringWheelAngle,VehicleLength,VehicleWidth,PtActivationType,SpecialTransportType,DangerousGoodsBasic," +
                    "RoadworksSubCauseCode,TrafficRule,SpeedLimit,SirenActivated,IndicationCauseCode,EmergencyPriorityRequestForFreeCrossingAtATrafficLight," +
                    "ProtectedZoneLatitude,ProtectedZoneLongitude" + "\n";

            String firstLineMAP = "Longitude,Latitude,TimeStamp,ObjectID,Speed,Security,1.Intersection Longitude,1.Intersection Latitude," +
                    "1.Intersection ID,1.Intersection laneWidth,1.Intersection revision,1.Intersection name,1.Intersection laneCount," +
                    "2.Intersection Longitude,2.Intersection Latitude,2.Intersection ID,2.Intersection laneWidth,2.Intersection revision," +
                    "2.Intersection name,2.Intersection laneCount,3.Intersection Longitude,3.Intersection Latitude,3.Intersection ID," +
                    "3.Intersection laneWidth,3.Intersection revision,3.Intersection name,3.Intersection laneCount,4.Intersection Longitude," +
                    "4.Intersection Latitude,4.Intersection ID,4.Intersection laneWidth,4.Intersection revision,4.Intersection name," +
                    "4.Intersection laneCount" + "\n";

            String firstLineSPAT = "Count of intersectionStates,Longitude,Latitude,TimeStamp,ObjectID,Speed,Security," +
                    "1.IntersectionState TimeStamp,1.IntersectionState ID,1.IntersectionState LaneCount,1.IntersectionState Revision" +
                    "2.IntersectionState TimeStamp,2.IntersectionState ID,2.IntersectionState LaneCount,2.IntersectionState Revision" +
                    "3.IntersectionState TimeStamp,3.IntersectionState ID,3.IntersectionState LaneCount,3.IntersectionState Revision" +
                    "4.IntersectionState TimeStamp,4.IntersectionState ID,4.IntersectionState LaneCount,4.IntersectionState Revision" + "\n";
            ;

            fw.write(firstLineDENM);
            fw.write(firstLineCAM);
            fw.write(firstLineMAP);
            fw.write(firstLineSPAT);

            for (PacketAncestor p :
                    list) {
                NotificationType notificationType = p.getNotificationType();
                if (notificationType == NotificationType.FAC_NOTIFICATION) {
                    FacilityNotification fn = (FacilityNotification) p.getObject();
                    switch (fn.getType().toString()) {
                        case "DENM":
                            String lineDENM = fillDENMString(fn);
                            lineDENM += "\n";
                            fw.write(lineDENM);
                            break;
                        case "CAM":
                            String lineCAM = fillCAMString(fn);
                            lineCAM += "\n";
                            fw.write(lineCAM);
                            break;
                    }
                }

                if (notificationType == NotificationType.LDM_NOTIFICATION) {
                    LdmObject lo = (LdmObject) p.getObject();
                    switch (lo.getObjectType().toString()) {
                        case "MAP":
                            String lineMAP = fillMAPString(lo);
                            lineMAP += "\n";
                            fw.write(lineMAP);
                            break;
                        case "SPAT":
                            String lineSPAT = fillSPATString(lo);
                            lineSPAT += "\n";
                            fw.write(lineSPAT);
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw.close();
        }
    }


    private String fillDENMString(FacilityNotification fc) {
        String line = "DENM,";
        if (fc.getDENM().getActionID() != null)
            line += String.valueOf(fc.getDENM().getActionID().getStationID()) + ",";
        else
            line += "null,";

        if (fc.getDENM().getDetectionTime() != null)
            line += fc.getDENM().getDetectionTime().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getReferenceTime() != null)
            line += fc.getDENM().getReferenceTime().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getStationType() != null)
            line += fc.getDENM().getStationType().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getValidityDuration(TimeUnit.MINUTES) != null)
            line += fc.getDENM().getValidityDuration(TimeUnit.MINUTES).toString() + ",";
        else
            line += "null,";
        //
        if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getAltitude(LengthUnit.M) != null)
            line += fc.getDENM().getEventPosition().getAltitude(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLatitude() != null)
            line += fc.getDENM().getEventPosition().getLatitude().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getLongitude() != null)
            line += fc.getDENM().getEventPosition().getLongitude().toString() + ",";
        else
            line += "null,";
        //
        if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods() != null)
            line += fc.getDENM().getStationaryVehicle().getCarryingDangerousGoods().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getDrivingLaneStatus() != null)
            line += String.valueOf(fc.getDENM().getRoadWorks().getDrivingLaneStatus().isLaneClosed(((fc.getDENM().getLaneNumber().intValue())))) + ",";
        else
            line += "null,";

        if (fc.getDENM().getEventPosition() != null && fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG) != null)
            line += fc.getDENM().getEventPosition().getHeading(DegreeUnit.DEG).toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getSpeedValue(SpeedUnit.KMperH) != null)
            line += fc.getDENM().getSpeedValue(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getEventType() != null)
            line += fc.getDENM().getEventType().getPrimaryCause().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS) != null)
            line += fc.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS).toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getInformationQuality() != null)
            line += fc.getDENM().getInformationQuality().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getLaneNumber() != null)
            line += fc.getDENM().getLaneNumber().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRelevanceDistance() != null)
            line += fc.getDENM().getRelevanceDistance().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRelevanceTrafficDirection() != null)
            line += fc.getDENM().getRelevanceTrafficDirection().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRoadType() != null)
            line += fc.getDENM().getRoadType().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH) != null)
            line += fc.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getRoadWorks() != null && fc.getDENM().getRoadWorks().getStartingPointSpeedLimit() != null)
            line += fc.getDENM().getRoadWorks().getStartingPointSpeedLimit().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getStationaryCause() != null)
            line += fc.getDENM().getStationaryVehicle().getStationaryCause().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getStationaryVehicle() != null && fc.getDENM().getStationaryVehicle().getStationarySince() != null)
            line += fc.getDENM().getStationaryVehicle().getStationarySince().toString() + ",";
        else
            line += "null,";

        if (fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES) != null)
            line += fc.getDENM().getTransmissionInterval(TimeUnit.MINUTES).toString();
        else
            line += "null";

        return line;
    }

    private String fillCAMString(FacilityNotification fc) {
        String line = "CAM,";

        if (fc.getStationObject() == null) {
            line += "null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null," +
                    "null,null,null,null,null,null,null,null,null";
            return line;
        }

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG) != null)
            line += fc.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH) != null)
            line += fc.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getStationType() != null)
            line += fc.getStationObject().getBasicInfo().getStationType().toString() + ",";
        else
            line += "null,";
        //
        if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null &&
                fc.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M) != null)
            line += fc.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null &&
                fc.getStationObject().getBasicInfo().getReferencePosition().getLatitude() != null)
            line += fc.getStationObject().getBasicInfo().getReferencePosition().getLatitude().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getBasicInfo() != null && fc.getStationObject().getBasicInfo().getReferencePosition() != null &&
                fc.getStationObject().getBasicInfo().getReferencePosition().getLongitude() != null)
            line += fc.getStationObject().getBasicInfo().getReferencePosition().getLongitude().toString() + ",";
        else
            line += "null,";
        //
        if (fc.getStationObject().getCommonInfo() != null && fc.getStationObject().getCommonInfo().getVehicleRole() != null)
            line += fc.getStationObject().getCommonInfo().getVehicleRole().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getPerformanceClass() != null)
            line += fc.getStationObject().getPositionInfo().getPerformanceClass().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLaneNumber() != null)
            line += fc.getStationObject().getPositionInfo().getLaneNumber().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getDriveDirection() != null)
            line += fc.getStationObject().getPositionInfo().getDriveDirection().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2) != null)
            line += fc.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2) != null)
            line += fc.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2) != null)
            line += fc.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isEmbarkationStatus() != null)
            line += fc.getStationObject().getSpecialInfo().isEmbarkationStatus().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCurvatureValue() != null)
            line += fc.getStationObject().getPositionInfo().getCurvatureValue().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCurvatureCalculationMode() != null)
            line += fc.getStationObject().getPositionInfo().getCurvatureCalculationMode().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG) != null)
            line += fc.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG) != null)
            line += fc.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M) != null)
            line += fc.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M) != null)
            line += fc.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getPtActivationType() != null)
            line += fc.getStationObject().getSpecialInfo().getPtActivationType().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getSpecialTransportType() != null)
            line += fc.getStationObject().getSpecialInfo().getSpecialTransportType().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic() != null)
            line += fc.getStationObject().getSpecialInfo().getDangerousGoodsBasic().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode() != null)
            line += fc.getStationObject().getSpecialInfo().getRoadworksSubCauseCode().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getTrafficRule() != null)
            line += fc.getStationObject().getSpecialInfo().getTrafficRule().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH) != null)
            line += fc.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isSirenActivated() != null)
            line += fc.getStationObject().getSpecialInfo().isSirenActivated().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().getIndicationCauseCode() != null)
            line += fc.getStationObject().getSpecialInfo().getIndicationCauseCode().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getSpecialInfo() != null && fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight() != null)
            line += fc.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight().toString() + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
            line += String.valueOf(fc.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLatitude()) + ",";
        else
            line += "null,";

        if (fc.getStationObject().getPositionInfo() != null && fc.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
            line += String.valueOf(fc.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLongitude());
        else
            line += "null";


        return line;
    }

    private String fillMAPString(LdmObject lo) {
        String line = "MAP,";

        if (lo.getLongitude() != null)
            line += lo.getLongitude().toString() + ",";
        else
            line += "null,";

        if (lo.getLatitude() != null)
            line += lo.getLatitude().toString() + ",";
        else
            line += "null,";

        if (lo.getTimestamp() != null)
            line += String.valueOf(lo.getTimestamp().getTime()) + ",";
        else
            line += "null,";

        if (lo.getObjectID() != null)
            line += lo.getObjectID().toString() + ",";
        else
            line += "null,";

        if (lo.getSpeed(SpeedUnit.KMperH) != null)
            line += lo.getSpeed(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (lo.getSecurity() != null)
            line += lo.getSecurity().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(0).getRefPoint().getLongitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(0).getRefPoint().getLatitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1)
            line += String.valueOf(lo.getIntersections().get(0).getId()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getLaneWidth(LengthUnit.M) != null)
            line += lo.getIntersections().get(0).getLaneWidth(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1)
            line += String.valueOf(lo.getIntersections().get(0).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getName() != null)
            line += lo.getIntersections().get(0).getName().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 1 && lo.getIntersections().get(0).getIntersectionLaneSet() != null)
            line += String.valueOf(lo.getIntersections().get(0).getIntersectionLaneSet().size()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(1).getRefPoint().getLongitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(1).getRefPoint().getLatitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2)
            line += String.valueOf(lo.getIntersections().get(1).getId()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getLaneWidth(LengthUnit.M) != null)
            line += lo.getIntersections().get(1).getLaneWidth(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2)
            line += String.valueOf(lo.getIntersections().get(1).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getName() != null)
            line += lo.getIntersections().get(1).getName().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 2 && lo.getIntersections().get(1).getIntersectionLaneSet() != null)
            line += String.valueOf(lo.getIntersections().get(1).getIntersectionLaneSet().size()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(2).getRefPoint().getLongitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(2).getRefPoint().getLatitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3)
            line += String.valueOf(lo.getIntersections().get(2).getId()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getLaneWidth(LengthUnit.M) != null)
            line += lo.getIntersections().get(2).getLaneWidth(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3)
            line += String.valueOf(lo.getIntersections().get(2).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getName() != null)
            line += lo.getIntersections().get(2).getName().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 3 && lo.getIntersections().get(2).getIntersectionLaneSet() != null)
            line += String.valueOf(lo.getIntersections().get(2).getIntersectionLaneSet().size()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(3).getRefPoint().getLongitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getRefPoint() != null)
            line += String.valueOf(lo.getIntersections().get(3).getRefPoint().getLatitude()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4)
            line += String.valueOf(lo.getIntersections().get(3).getId()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getLaneWidth(LengthUnit.M) != null)
            line += lo.getIntersections().get(3).getLaneWidth(LengthUnit.M).toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4)
            line += String.valueOf(lo.getIntersections().get(3).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getName() != null)
            line += lo.getIntersections().get(3).getName().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersections() != null && lo.getIntersections().size() >= 4 && lo.getIntersections().get(3).getIntersectionLaneSet() != null)
            line += String.valueOf(lo.getIntersections().get(3).getIntersectionLaneSet().size());
        else
            line += "null";

        return line;
    }

    private String fillSPATString(LdmObject lo) {
        String line = "SPAT,";

        if (lo.getLongitude() != null)
            line += lo.getLongitude().toString() + ",";
        else
            line += "null,";

        if (lo.getLatitude() != null)
            line += lo.getLatitude().toString() + ",";
        else
            line += "null,";

        if (lo.getTimestamp() != null)
            line += String.valueOf(lo.getTimestamp().getTime()) + ",";
        else
            line += "null,";

        if (lo.getObjectID() != null)
            line += lo.getObjectID().toString() + ",";
        else
            line += "null,";

        if (lo.getSpeed(SpeedUnit.KMperH) != null)
            line += lo.getSpeed(SpeedUnit.KMperH).toString() + ",";
        else
            line += "null,";

        if (lo.getSecurity() != null)
            line += lo.getSecurity().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null)
            line += String.valueOf(lo.getIntersectionStates().size()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getTimestamp() != null)
            line += lo.getIntersectionStates().get(0).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getId() != null)
            line += lo.getIntersectionStates().get(0).getId().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getLaneCount() != null)
            line += lo.getIntersectionStates().get(0).getLaneCount().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1)
            line += String.valueOf(lo.getIntersectionStates().get(0).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getTimestamp() != null)
            line += lo.getIntersectionStates().get(1).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getId() != null)
            line += lo.getIntersectionStates().get(1).getId().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getLaneCount() != null)
            line += lo.getIntersectionStates().get(1).getLaneCount().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2)
            line += String.valueOf(lo.getIntersectionStates().get(1).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getTimestamp() != null)
            line += lo.getIntersectionStates().get(2).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getId() != null)
            line += lo.getIntersectionStates().get(2).getId().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getLaneCount() != null)
            line += lo.getIntersectionStates().get(2).getLaneCount().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3)
            line += String.valueOf(lo.getIntersectionStates().get(2).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getTimestamp() != null)
            line += lo.getIntersectionStates().get(3).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getId() != null)
            line += lo.getIntersectionStates().get(3).getId().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getLaneCount() != null)
            line += lo.getIntersectionStates().get(3).getLaneCount().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4)
            line += String.valueOf(lo.getIntersectionStates().get(3).getRevision());
        else
            line += "null";

        return line;
    }

    //ViewHolder class
    public static class ViewHolder {
        TextView tvType;
        TextView tvParamType1;
        TextView tvParamType2;
        TextView tvParamType3;
        TextView tvParam1;
        TextView tvParam2;
        TextView tvParam3;
    }
}
