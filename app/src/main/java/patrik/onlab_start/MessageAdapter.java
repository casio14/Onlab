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
import java.util.Map;
import java.util.concurrent.TimeUnit;

import patrik.onlab_start.Model.FacilityNotificationFieldValue;
import patrik.onlab_start.Model.LdmNotificationFieldValue;
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
    Map<String ,String> selectedValues;

    //Constructor
    public MessageAdapter(Context context, int resource, List<PacketAncestor> input_data, Map<String,String> selectedValues) {
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

            if (element.getNotificationType().equals(NotificationType.FAC_NOTIFICATION)) {
                FacilityNotification fc = (FacilityNotification) element.getObject();

                if (fc.getType().toString().equals("DENM")) {
                    holder.tvType.setText(fc.getType().toString());
                    holder.tvParamType1.setText(selectedValues.get("DENM_1"));
                    holder.tvParamType2.setText(selectedValues.get("DENM_2"));
                    holder.tvParamType3.setText(selectedValues.get("DENM_3"));

                    holder.tvParam1.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("DENM_1"),fc).getDENMValue()
                    );
                    holder.tvParam2.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("DENM_2"),fc).getDENMValue()
                    );
                    holder.tvParam3.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("DENM_3"),fc).getDENMValue()
                    );
                }

                if (fc.getType().toString().equals("CAM")) {
                    holder.tvType.setText(fc.getType().toString());
                    holder.tvParamType1.setText(selectedValues.get("CAM_1"));
                    holder.tvParamType2.setText(selectedValues.get("CAM_2"));
                    holder.tvParamType3.setText(selectedValues.get("CAM_3"));

                    holder.tvParam1.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("CAM_1"),fc).getCAMValue()
                    );
                    holder.tvParam2.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("CAM_2"),fc).getCAMValue()
                    );
                    holder.tvParam3.setText(
                            new FacilityNotificationFieldValue(selectedValues.get("CAM_3"),fc).getCAMValue()
                    );
                }
            }


            if (element.getNotificationType().equals(NotificationType.LDM_NOTIFICATION)) {
                LdmObject lo = (LdmObject) element.getObject();

                if (lo.getObjectType().toString().equals("MAP")) {
                    holder.tvType.setText(lo.getObjectType().toString());
                    holder.tvParamType1.setText(selectedValues.get("MAP_1"));
                    holder.tvParamType2.setText(selectedValues.get("MAP_2"));
                    holder.tvParamType3.setText(selectedValues.get("MAP_3"));
                    holder.tvParam1.setText(
                            new LdmNotificationFieldValue(selectedValues.get("MAP_1"),lo).getMAPValue()
                    );
                    holder.tvParam2.setText(
                            new LdmNotificationFieldValue(selectedValues.get("MAP_2"),lo).getMAPValue()
                    );
                    holder.tvParam3.setText(
                            new LdmNotificationFieldValue(selectedValues.get("MAP_3"),lo).getMAPValue()
                    );
                }

                if (lo.getObjectType().toString().equals("SPAT")) {
                    holder.tvType.setText(lo.getObjectType().toString());
                    holder.tvParamType1.setText(selectedValues.get("SPAT_1"));
                    holder.tvParamType2.setText(selectedValues.get("SPAT_2"));
                    holder.tvParamType3.setText(selectedValues.get("SPAT_3"));
                    holder.tvParam1.setText(
                            new LdmNotificationFieldValue(selectedValues.get("SPAT_1"),lo).getSPATValue()
                    );
                    holder.tvParam2.setText(
                            new LdmNotificationFieldValue(selectedValues.get("SPAT_2"),lo).getSPATValue()
                    );
                    holder.tvParam3.setText(
                            new LdmNotificationFieldValue(selectedValues.get("SPAT_3"),lo).getSPATValue()
                    );
                }

                if (lo.getObjectType().toString().equals("BSM")) {
                    holder.tvType.setText(lo.getObjectType().toString());
                    holder.tvParamType1.setText(selectedValues.get("BSM_1"));
                    holder.tvParamType2.setText(selectedValues.get("BSM_2"));
                    holder.tvParamType3.setText(selectedValues.get("BSM_3"));
                    holder.tvParam1.setText(
                            new LdmNotificationFieldValue(selectedValues.get("BSM_1"),lo).getBSMValue()
                    );
                    holder.tvParam2.setText(
                            new LdmNotificationFieldValue(selectedValues.get("BSM_2"),lo).getBSMValue()
                    );
                    holder.tvParam3.setText(
                            new LdmNotificationFieldValue(selectedValues.get("BSM_3"),lo).getBSMValue()
                    );
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

            String firstLineBSM = "Longitude,Latitude,Id,Elevation,AbsStatus,AuxBrakeStatus,BrakeBoostStatus,isABSActivated,isAirBagDeployment,isDisabledVehicle," +
                    "isEmergencyResponse,isFlatTire,isHardBraking,isHazardLights,isHazardousMaterials,isLightsChanged,isStabilityControlActivated," +
                    "isStopLineViolation,isDisabledVehicle,isEmergencyResponse,isTractionControlLoss,isWipersChanged,isAutomaticLightControlOnOn," +
                    "isDaytimeRunningLightsOn,isFogLightOn,isHighBeamHeadlightsOn,isLeftTurnSignalOn,isLowBeamHeadlightsOn,isParkingLightsOn,isReverseLightOn," +
                    "isRightTurnSignalOn,PositionVectorDateTime,PositionVectorHeading,PositionVectorLatitude,PositionVectorLongitude,PositionVectorSpeed," +
                    "Heading,LateralAcceleration,LongitudinalAcceleration,LightBarInUse,MsgCount,isaPDOPofUnder5,isBaseStationType,isHealthy,isInViewOfUnder5," +
                    "isLocalCorrectionsPresent,isMonitored,isNetworkCorrectionsPresent,isUnavailable,PathPredictionConfidence,PathPredictionRadiusOfCurvature," +
                    "SecMark,SemiMajorAccuracy,SemiMinorAccuracy,SemiMajorOrientation,Speed,YawRate,WheelBrakeisLeftFront,WheelBrakeisRigthFront,WheelBrakeisLeftRear," +
                    "WheelBrakeisRightRear,WheelBrakeUnavailable,VerticalAcceleration,VehicleType,VehicleLength,VehicheWidth,TransmissionState,TractionControlStatus,ThrottlePosition," +
                    "SteeringWheelAngle,StabilityControlStatus" + "\n";

            fw.write(firstLineDENM);
            fw.write(firstLineCAM);
            fw.write(firstLineMAP);
            fw.write(firstLineSPAT);
            fw.write(firstLineBSM);

            for (PacketAncestor p :
                    list) {
                NotificationType notificationType = p.getNotificationType();
                if (notificationType.equals(NotificationType.FAC_NOTIFICATION)) {
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

                if (notificationType.equals(NotificationType.LDM_NOTIFICATION)) {
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
                        case "BSM":
                            String lineBSM = fillBSMString(lo);
                            lineBSM += "\n";
                            fw.write(lineBSM);
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

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1)
            line += lo.getIntersectionStates().get(0).getId() + ",";
        else
            line += "null,";
/*
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getLaneCount() != null)
            line += lo.getIntersectionStates().get(0).getLaneCount().toString() + ",";
        else
            line += "null,";
*/
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1)
            line += String.valueOf(lo.getIntersectionStates().get(0).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getTimestamp() != null)
            line += lo.getIntersectionStates().get(1).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2)
            line += lo.getIntersectionStates().get(1).getId() + ",";
        else
            line += "null,";
/*
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getLaneCount() != null)
            line += lo.getIntersectionStates().get(1).getLaneCount().toString() + ",";
        else
            line += "null,";
*/
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2)
            line += String.valueOf(lo.getIntersectionStates().get(1).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getTimestamp() != null)
            line += lo.getIntersectionStates().get(2).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3)
            line += lo.getIntersectionStates().get(2).getId() + ",";
        else
            line += "null,";
/*
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getLaneCount() != null)
            line += lo.getIntersectionStates().get(2).getLaneCount().toString() + ",";
        else
            line += "null,";
*/
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3)
            line += String.valueOf(lo.getIntersectionStates().get(2).getRevision()) + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getTimestamp() != null)
            line += lo.getIntersectionStates().get(3).getTimestamp().toString() + ",";
        else
            line += "null,";

        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4)
            line += lo.getIntersectionStates().get(3).getId() + ",";
        else
            line += "null,";
/*
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getLaneCount() != null)
            line += lo.getIntersectionStates().get(3).getLaneCount().toString() + ",";
        else
            line += "null,";
*/
        if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4)
            line += String.valueOf(lo.getIntersectionStates().get(3).getRevision());
        else
            line += "null";

        return line;
    }

    private String fillBSMString(LdmObject lo) {

                String line = "BSM,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getLongitude()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getLatitude()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getId()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getElevation(LengthUnit.M) != null)
                    line += lo.getBsmObject().getElevation(LengthUnit.M).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getAbsStatus() != null)
                    line += lo.getBsmObject().getAbsStatus().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getAuxBrakeStatus() != null)
                    line += lo.getBsmObject().getAuxBrakeStatus().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getBrakeBoostStatus() != null)
                    line += lo.getBsmObject().getBrakeBoostStatus().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isABSActivated()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isAirBagDeployment()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isDisabledVehicle()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isEmergencyResponse()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isFlatTire()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isHardBraking()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isHazardLights()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isHazardousMaterials()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isLightsChanged()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isStabilityControlActivated()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isStopLineViolation()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isTractionControlLoss()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getEvents() != null)
                    line += String.valueOf(lo.getBsmObject().getEvents().isWipersChanged()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isAutomaticLightControlOnOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isDaytimeRunningLightsOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(lo.getBsmObject().getExteriorLights().isFogLightOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(lo.getBsmObject().getExteriorLights().isHighBeamHeadlightsOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isLeftTurnSignalOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isLowBeamHeadlightsOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isParkingLightsOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isReverseLightOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getExteriorLights() != null)
                    line += String.valueOf(lo.getBsmObject().getExteriorLights().isRightTurnSignalOn()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getFullPositionVector() != null && lo.getBsmObject().getFullPositionVector().getDateTime() != null)
                    line += lo.getBsmObject().getFullPositionVector().getDateTime().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getFullPositionVector() != null && lo.getBsmObject().getFullPositionVector().getHeading() != null)
                    line += lo.getBsmObject().getFullPositionVector().getHeading().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getFullPositionVector() != null)
                    line += String.valueOf(lo.getBsmObject().getFullPositionVector().getLatitude()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getFullPositionVector() != null)
                    line += String.valueOf(lo.getBsmObject().getFullPositionVector().getLongitude()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getFullPositionVector() != null && lo.getBsmObject().getFullPositionVector().getSpeed() != null)
                    line += lo.getBsmObject().getFullPositionVector().getSpeed().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getHeading(DegreeUnit.DEG_1_5_POSNEG)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getLateralAcceleration(AccelerationUnit.MperS2) != null)
                    line += lo.getBsmObject().getLateralAcceleration(AccelerationUnit.MperS2).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getLongitudinalAcceleration(AccelerationUnit.MperS2) != null)
                    line += lo.getBsmObject().getLongitudinalAcceleration(AccelerationUnit.MperS2).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getLightBarInUse() != null)
                    line += lo.getBsmObject().getLightBarInUse().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getMsgCount()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isaPDOPofUnder5()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String .valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isBaseStationType()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isHealthy()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isInViewOfUnder5()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isLocalCorrectionsPresent()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isMonitored()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isNetworkCorrectionsPresent()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathHistory() != null && lo.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getPathHistory().getCurrGnssStatus().isUnavailable()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathPrediction() != null)
                    line += String.valueOf(lo.getBsmObject().getPathPrediction().getConfidence()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getPathPrediction() != null)
                    line += String.valueOf(lo.getBsmObject().getPathPrediction().getRadiusOfCurvature(LengthUnit.M)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getSecMark(TimeUnit.DAYS)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getSemiMajorAccuracy(LengthUnit.M) != null)
                    line += lo.getBsmObject().getSemiMajorAccuracy(LengthUnit.M).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getSemiMinorAccuracy(LengthUnit.M) != null)
                    line += lo.getBsmObject().getSemiMinorAccuracy(LengthUnit.M).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getSemiMajorOrientation(DegreeUnit.DEG_1_5_POSNEG) != null)
                    line += lo.getBsmObject().getSemiMajorOrientation(DegreeUnit.DEG_1_5_POSNEG).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getSpeed(SpeedUnit.KMperH) != null)
                    line += lo.getBsmObject().getSpeed(SpeedUnit.KMperH).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getYawRate(DegreeUnit.DEG_1_5_POSNEG)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getWheelBrakeStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getWheelBrakeStatus().isLeftFront()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getWheelBrakeStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getWheelBrakeStatus().isRightFront()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getWheelBrakeStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getWheelBrakeStatus().isLeftRear()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getWheelBrakeStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getWheelBrakeStatus().isRightRear()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getWheelBrakeStatus() != null)
                    line += String.valueOf(lo.getBsmObject().getWheelBrakeStatus().isUnavailable()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getVerticalAcceleration()) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getVehicleType() != null)
                    line += lo.getBsmObject().getVehicleType().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getVehicleLength(LengthUnit.M)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null)
                    line += String.valueOf(lo.getBsmObject().getVehicheWidth(LengthUnit.M)) + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getTransmissionState() != null)
                    line += lo.getBsmObject().getTransmissionState().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getTractionControlStatus() != null)
                    line += lo.getBsmObject().getTractionControlStatus().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getThrottlePosition() != null)
                    line += lo.getBsmObject().getThrottlePosition().toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getSteeringWheelAngle(DegreeUnit.DEG_1_5_POSNEG) != null)
                    line += lo.getBsmObject().getSteeringWheelAngle(DegreeUnit.DEG_1_5_POSNEG).toString() + ",";
                else
                    line += "null,";

                if(lo.getBsmObject() != null && lo.getBsmObject().getStabilityControlStatus() != null)
                    line += lo.getBsmObject().getStabilityControlStatus().toString();
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
