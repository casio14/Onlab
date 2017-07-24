package patrik.onlab_start.Model;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.utils.units.AccelerationUnit;
import com.commsignia.v2x.utils.units.DegreeUnit;
import com.commsignia.v2x.utils.units.LengthUnit;
import com.commsignia.v2x.utils.units.SpeedUnit;
import com.commsignia.v2x.utils.units.TemperatureUnit;

import java.util.concurrent.TimeUnit;

/**
 * Created by kisspatrik on 2017.07.24..
 */

public class FacilityNotificationFieldValue {

    private String field;
    private FacilityNotification facilityNotification;

    public FacilityNotificationFieldValue(String field, FacilityNotification facilityNotification) {
        this.field = field;
        this.facilityNotification = facilityNotification;
    }

    public String getDENMValue() {

        if(field==null)
            return "null";

        switch (field) {
            case "ActionID":
                if (facilityNotification.getDENM().getActionID() != null)
                    return String.valueOf(facilityNotification.getDENM().getActionID().getStationID());
                else
                    return "null";

            case "DetectionTime":
                if (facilityNotification.getDENM().getDetectionTime() != null)
                    return facilityNotification.getDENM().getDetectionTime().toString();
                else
                    return "null";

            case "ReferenceTime":
                if (facilityNotification.getDENM().getReferenceTime() != null)
                    return facilityNotification.getDENM().getReferenceTime().toString();
                else
                    return "null";

            case "StationType":
                if (facilityNotification.getDENM().getStationType() != null)
                    return facilityNotification.getDENM().getStationType().toString();
                else
                    return "null";

            case "ValidityDuration":
                if (facilityNotification.getDENM().getValidityDuration(TimeUnit.MINUTES) != null)
                    return facilityNotification.getDENM().getValidityDuration(TimeUnit.MINUTES).toString();
                else
                    return "null";
            //
            case "Altitude":
                if (facilityNotification.getDENM().getEventPosition() != null && facilityNotification.getDENM().getEventPosition().getAltitude(LengthUnit.M) != null)
                    return facilityNotification.getDENM().getEventPosition().getAltitude(LengthUnit.M).toString();
                else
                    return "null";

            case "Latitude":
                if (facilityNotification.getDENM().getEventPosition() != null && facilityNotification.getDENM().getEventPosition().getLatitude() != null)
                    return facilityNotification.getDENM().getEventPosition().getLatitude().toString();
                else
                    return "null";

            case "Longitude":
                if (facilityNotification.getDENM().getEventPosition() != null && facilityNotification.getDENM().getEventPosition().getLongitude() != null)
                    return facilityNotification.getDENM().getEventPosition().getLongitude().toString();
                else
                    return "null";
            //

            case "DangerousGoods":
                if (facilityNotification.getDENM().getStationaryVehicle() != null && facilityNotification.getDENM().getStationaryVehicle().getCarryingDangerousGoods() != null)
                    return facilityNotification.getDENM().getStationaryVehicle().getCarryingDangerousGoods().toString();
                else
                    return "null";

            case "LaneClosed":
                if (facilityNotification.getDENM().getRoadWorks() != null && facilityNotification.getDENM().getRoadWorks().getDrivingLaneStatus() != null)
                    return String.valueOf(facilityNotification.getDENM().getRoadWorks().getDrivingLaneStatus().isLaneClosed(((facilityNotification.getDENM().getLaneNumber().intValue()))));
                else
                    return "null";

            case "Heading":
                if (facilityNotification.getDENM().getEventPosition() != null && facilityNotification.getDENM().getEventPosition().getHeading(DegreeUnit.DEG) != null)
                    return facilityNotification.getDENM().getEventPosition().getHeading(DegreeUnit.DEG).toString();
                else
                    return "null";

            case "Speed":
                if (facilityNotification.getDENM().getSpeedValue(SpeedUnit.KMperH) != null)
                    return facilityNotification.getDENM().getSpeedValue(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "EventType":
                if (facilityNotification.getDENM().getEventType() != null)
                    return facilityNotification.getDENM().getEventType().toString();
                else
                    return "null";

            case "Ext.Temperature":
                if (facilityNotification.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS) != null)
                    return facilityNotification.getDENM().getExternalTemperature(TemperatureUnit.CELSIUS).toString();
                else
                    return "null";

            case "InformationQuality":
                if (facilityNotification.getDENM().getInformationQuality() != null)
                    return facilityNotification.getDENM().getInformationQuality().toString();
                else
                    return "null";

            case "LaneNumber":
                if (facilityNotification.getDENM().getLaneNumber() != null)
                    return facilityNotification.getDENM().getLaneNumber().toString();
                else
                    return "null";

            case "RelevanceDistance":
                if (facilityNotification.getDENM().getRelevanceDistance() != null)
                    return facilityNotification.getDENM().getRelevanceDistance().toString();
                else
                    return "null";

            case "RelevanceTrafficDirection":
                if (facilityNotification.getDENM().getRelevanceTrafficDirection() != null)
                    return facilityNotification.getDENM().getRelevanceTrafficDirection().toString();
                else
                    return "null";

            case "RoadType":
                if (facilityNotification.getDENM().getRoadType() != null)
                    return facilityNotification.getDENM().getRoadType().toString();
                else
                    return "null";

            case "SpeedLimit":
                if (facilityNotification.getDENM().getRoadWorks() != null && facilityNotification.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH) != null)
                    return facilityNotification.getDENM().getRoadWorks().getSpeedLimit(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "StartingPointSpeedLimit":
                if (facilityNotification.getDENM().getRoadWorks() != null && facilityNotification.getDENM().getRoadWorks().getStartingPointSpeedLimit() != null)
                    return facilityNotification.getDENM().getRoadWorks().getStartingPointSpeedLimit().toString();
                else
                    return "null";

            case "StationaryCause":
                if (facilityNotification.getDENM().getStationaryVehicle() != null && facilityNotification.getDENM().getStationaryVehicle().getStationaryCause() != null)
                    return facilityNotification.getDENM().getStationaryVehicle().getStationaryCause().toString();
                else
                    return "null";

            case "StationarySince":
                if (facilityNotification.getDENM().getStationaryVehicle() != null && facilityNotification.getDENM().getStationaryVehicle().getStationarySince() != null)
                    return facilityNotification.getDENM().getStationaryVehicle().getStationarySince().toString();
                else
                    return "null";

            case "TransmissionInterval":
                if (facilityNotification.getDENM().getTransmissionInterval(TimeUnit.MINUTES) != null)
                    return facilityNotification.getDENM().getTransmissionInterval(TimeUnit.MINUTES).toString();
                else
                    return "null";

            default:
                return "null";
        }
    }

    public String getCAMValue() {
        if(field==null)
            return "null";

        if (facilityNotification.getStationObject() == null) {
            return "null";
        }

        switch (field) {
            case "Heading":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getHeadingValue(DegreeUnit.DEG).toString();
                else
                    return "null";

            case "Speed":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getSpeedValue(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "StationType":
                if (facilityNotification.getStationObject().getBasicInfo() != null && facilityNotification.getStationObject().getBasicInfo().getStationType() != null)
                    return facilityNotification.getStationObject().getBasicInfo().getStationType().toString();
                else
                    return "null";
            //
            case "Altitude":
                if (facilityNotification.getStationObject().getBasicInfo() != null && facilityNotification.getStationObject().getBasicInfo().getReferencePosition() != null &&
                        facilityNotification.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M) != null)
                    return facilityNotification.getStationObject().getBasicInfo().getReferencePosition().getAltitude(LengthUnit.M).toString();
                else
                    return "null";

            case "Latitude":
                if (facilityNotification.getStationObject().getBasicInfo() != null && facilityNotification.getStationObject().getBasicInfo().getReferencePosition() != null)
                    return facilityNotification.getStationObject().getBasicInfo().getReferencePosition().getLatitude().toString();
                else
                    return "null";

            case "Longitude":
                if (facilityNotification.getStationObject().getBasicInfo() != null && facilityNotification.getStationObject().getBasicInfo().getReferencePosition() != null)
                    return facilityNotification.getStationObject().getBasicInfo().getReferencePosition().getLongitude().toString();
                else
                    return "null";

            //
            case "VehicleRole":
                if (facilityNotification.getStationObject().getCommonInfo() != null && facilityNotification.getStationObject().getCommonInfo().getVehicleRole() != null)
                    return facilityNotification.getStationObject().getCommonInfo().getVehicleRole().toString();
                else
                    return "null";

            case "PerformanceClass":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getPerformanceClass() != null)
                    return facilityNotification.getStationObject().getPositionInfo().getPerformanceClass().toString();
                else
                    return "null";

            case "LaneNumber":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getLaneNumber() != null)
                    return facilityNotification.getStationObject().getPositionInfo().getLaneNumber().toString();
                else
                    return "null";

            case "DriveDirection":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getDriveDirection() != null)
                    return facilityNotification.getStationObject().getPositionInfo().getDriveDirection().toString();
                else
                    return "null";

            case "LongitudinalAcceleration":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getLongitudinalAccelerationValue(AccelerationUnit.MperS2).toString();
                else
                    return "null";

            case "LateralAcceleration":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getLateralAccelerationValue(AccelerationUnit.MperS2).toString();
                else
                    return "null";

            case "VerticalAcceleration":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getVerticalAccelerationValue(AccelerationUnit.MperS2).toString();
                else
                    return "null";

            case "EmbarkationStatus":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().isEmbarkationStatus() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().isEmbarkationStatus().toString();
                else
                    return "null";

            case "CurvaureValue":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getCurvatureValue() != null)
                    return facilityNotification.getStationObject().getPositionInfo().getCurvatureValue().toString();
                else
                    return "null";

            case "CurvatureCalculationMode":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getCurvatureCalculationMode() != null)
                    return facilityNotification.getStationObject().getPositionInfo().getCurvatureCalculationMode().toString();
                else
                    return "null";

            case "YawRate":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getYawRateValue(DegreeUnit.DEG).toString();
                else
                    return "null";

            case "SteeringWheelAngle":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getSteeringWheelAngleValue(DegreeUnit.DEG).toString();
                else
                    return "null";

            case "VehicleLength":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getVehicleLength(LengthUnit.M).toString();
                else
                    return "null";

            case "VehhicleWidth":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M) != null)
                    return facilityNotification.getStationObject().getPositionInfo().getVehicleWidth(LengthUnit.M).toString();
                else
                    return  "null";

            case "PtActivationType":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getPtActivationType() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getPtActivationType().toString();
                else
                    return "null";

            case "SpecialTransportType":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getSpecialTransportType() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getSpecialTransportType().toString();
                else
                    return "null";

            case "DangerousGoodsBasic":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getDangerousGoodsBasic() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getDangerousGoodsBasic().toString();
                else
                    return "null";

            case "RoadworksSubCauseCode":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getRoadworksSubCauseCode() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getRoadworksSubCauseCode().toString();
                else
                    return "null";

            case "TrafficRule":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getTrafficRule() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getTrafficRule().toString();
                else
                    return "null";

            case "SpeedLimit":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH) != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getSpeedLimit(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "SirenActivated":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().isSirenActivated() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().isSirenActivated().toString();
                else
                    return "null";

            case "IndicationCauseCode":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().getIndicationCauseCode() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().getIndicationCauseCode().toString();
                else
                    return "null";


            case "EmergencyPriorityRequestForFreeCrossingAtATrafficLight":
                if (facilityNotification.getStationObject().getSpecialInfo() != null && facilityNotification.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight() != null)
                    return facilityNotification.getStationObject().getSpecialInfo().isEmergencyPriorityRequestForFreeCrossingAtATrafficLight().toString();
                else
                    return "null";

            case "ProtectedZonneLatitude":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
                    return String.valueOf(facilityNotification.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLatitude());
                else
                    return "null";


            case "ProtectedZoneLongitude":
                if (facilityNotification.getStationObject().getPositionInfo() != null && facilityNotification.getStationObject().getPositionInfo().getCenDsrcTollingZone() != null)
                    return String.valueOf(facilityNotification.getStationObject().getPositionInfo().getCenDsrcTollingZone().getProtectedZoneLongitude());
                else
                    return "null";

            default:
                return "null";
        }
    }
}
