package patrik.onlab_start.Model;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.msgset.c.commsigniaapi.StructuralSeparationSigns1B;
import com.commsignia.v2x.utils.units.AccelerationUnit;
import com.commsignia.v2x.utils.units.DegreeUnit;
import com.commsignia.v2x.utils.units.LengthUnit;
import com.commsignia.v2x.utils.units.SpeedUnit;

import java.util.concurrent.TimeUnit;

/**
 * Created by kisspatrik on 2017.07.24..
 */

public class LdmNotificationFieldValue {

    private String field;
    private LdmObject ldmObject;

    public LdmNotificationFieldValue(String field, LdmObject ldmObject) {
        this.field = field;
        this.ldmObject = ldmObject;
    }

    public String getDENMValue() {

        if(field==null)
            return "null";

        return "null";
    }

    public String getCAMValue() {

        if(field==null)
            return "null";

        return "null";
    }

    public String getSPATValue() {

        if(field==null)
            return "null";

        switch (field) {
            case "Longitude":
                if (ldmObject.getLongitude() != null)
                    return ldmObject.getLongitude().toString();
                else
                    return "null";

            case "Latitude":
                if (ldmObject.getLatitude() != null)
                    return ldmObject.getLatitude().toString();
                else
                    return "null";

            case "TimeStamp":
                if (ldmObject.getTimestamp() != null)
                    return String.valueOf(ldmObject.getTimestamp().getTime());
                else
                    return "null";

            case "ObjectID":
                if (ldmObject.getObjectID() != null)
                    return ldmObject.getObjectID().toString();
                else
                    return "null";

            case "Speed":
                if (ldmObject.getSpeed(SpeedUnit.KMperH) != null)
                    return ldmObject.getSpeed(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "Security":
                if (ldmObject.getSecurity() != null)
                    return ldmObject.getSecurity().toString();
                else
                    return "null";

            case "Count of intersectionStates":
                if (ldmObject.getIntersectionStates() != null)
                    return String.valueOf(ldmObject.getIntersectionStates().size());
                else
                    return "null";

            case "1.IntersectionState TimeStamp":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 1 && ldmObject.getIntersectionStates().get(0).getTimestamp() != null)
                    return ldmObject.getIntersectionStates().get(0).getTimestamp().toString();
                else
                    return "null";

            case "1.IntersectionState ID":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 1)
                    return String.valueOf(ldmObject.getIntersectionStates().get(0).getId());
                else
                    return "null";

            /*case "1.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 1 && lo.getIntersectionStates().get(0).getLaneCount() != null)
                    return lo.getIntersectionStates().get(0).getLaneCount().toString();
                else
                    return "null";*/

            case "1.IntersectionState Revision":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 1)
                    return String.valueOf(ldmObject.getIntersectionStates().get(0).getRevision());
                else
                    return "null";

            case "2.IntersectionState TimeStamp":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 2 && ldmObject.getIntersectionStates().get(1).getTimestamp() != null)
                    return ldmObject.getIntersectionStates().get(1).getTimestamp().toString();
                else
                    return "null";

            case "2.IntersectionState ID":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 2)
                    return String.valueOf(ldmObject.getIntersectionStates().get(1).getId());
                else
                    return "null";
/*
            case "2.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 2 && lo.getIntersectionStates().get(1).getLaneCount() != null)
                    return lo.getIntersectionStates().get(1).getLaneCount().toString();
                else
                    return "null";
*/
            case "2.IntersectionState Revision":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 2)
                    return String.valueOf(ldmObject.getIntersectionStates().get(1).getRevision());
                else
                    return "null";

            case "3.IntersectionState TimeStamp":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 3 && ldmObject.getIntersectionStates().get(2).getTimestamp() != null)
                    return ldmObject.getIntersectionStates().get(2).getTimestamp().toString();
                else
                    return "null";

            case "3.IntersectionState ID":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 3)
                    return String.valueOf(ldmObject.getIntersectionStates().get(2).getId());
                else
                    return "null";
/*
            case "3.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 3 && lo.getIntersectionStates().get(2).getLaneCount() != null)
                    return lo.getIntersectionStates().get(2).getLaneCount().toString();
                else
                    return "null");
                break;*/

            case "3.IntersectionState Revision":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 3)
                    return String.valueOf(ldmObject.getIntersectionStates().get(2).getRevision());
                else
                    return "null";

            case "4.IntersectionState TimeStamp":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 4 && ldmObject.getIntersectionStates().get(3).getTimestamp() != null)
                    return ldmObject.getIntersectionStates().get(3).getTimestamp().toString();
                else
                    return "null";

            case "4.IntersectionState ID":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 4)
                    return String.valueOf(ldmObject.getIntersectionStates().get(3).getId());
                else
                    return "null";
/*
            case "4.IntersectionState LaneCount":
                if (lo.getIntersectionStates() != null && lo.getIntersectionStates().size() >= 4 && lo.getIntersectionStates().get(3).getLaneCount() != null)
                    return lo.getIntersectionStates().get(3).getLaneCount().toString();
                else
                    return "null";
*/

            case "4.IntersectionState Revision":
                if (ldmObject.getIntersectionStates() != null && ldmObject.getIntersectionStates().size() >= 4)
                    return String.valueOf(ldmObject.getIntersectionStates().get(3).getRevision());
                else
                    return "null";

            default:
                return "null";
        }
    }

    public String getBSMValue() {
        if(field==null)
            return "null";

        switch (field) {
            case "Longitude":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getLongitude());
                else
                    return "null";

            case "Latitude":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getLatitude());
                else
                    return "null";

            case "Id":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getId());
                else
                    return "null";

            case "Elevation":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getElevation(LengthUnit.M) != null)
                    return ldmObject.getBsmObject().getElevation(LengthUnit.M).toString();
                else
                    return "null";

            case "AbsStatus":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getAbsStatus() != null)
                    return ldmObject.getBsmObject().getAbsStatus().toString();
                else
                    return "null";

            case "AuxBrakeStatus":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getAuxBrakeStatus() != null)
                    return ldmObject.getBsmObject().getAuxBrakeStatus().toString();
                else
                    return "null";

            case "BrakeBoostStatus":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getBrakeBoostStatus() != null)
                    return ldmObject.getBsmObject().getBrakeBoostStatus().toString();
                else
                    return "null";

            case "isABSActivated":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isABSActivated());
                else
                    return "null";

            case "isAirBagDeployment":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isAirBagDeployment());
                else
                    return "null";

            case "isDisabledVehicle":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isDisabledVehicle());
                else
                    return "null";

            case "isEmergencyResponse":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isEmergencyResponse());
                else
                    return "null";

            case "isFlatTire":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isFlatTire());
                else
                    return "null";

            case "isHardBraking":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isHardBraking());
                else
                    return "null";

            case "isHazardLights":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isHazardLights());
                else
                    return "null";

            case "isHazardousMaterials":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isHazardousMaterials());
                else
                    return "null";

            case "isLightsChanged":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isLightsChanged());
                else
                    return "null";

            case "isStabilityControlActivated":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isStabilityControlActivated());
                else
                    return "null";

            case "isStopLineViolation":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isStopLineViolation());
                else
                    return "null";

            case "isTractionControlLoss":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isTractionControlLoss());
                else
                    return "null";

            case "isWipersChanged":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getEvents() != null)
                    return String.valueOf(ldmObject.getBsmObject().getEvents().isWipersChanged());
                else
                    return "null";

            case "isAutomaticLightControlOnOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isAutomaticLightControlOnOn());
                else
                    return "null";

            case "isDaytimeRunningLightsOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isDaytimeRunningLightsOn());
                else
                    return "null";

            case "isFogLightOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isFogLightOn());
                else
                    return "null";

            case "isHighBeamHeadlightsOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isHighBeamHeadlightsOn());
                else
                    return "null";

            case "isLeftTurnSignalOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isLeftTurnSignalOn());
                else
                    return "null";

            case "isLowBeamHeadlightsOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isLowBeamHeadlightsOn());
                else
                    return "null";

            case "isParkingLightsOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isParkingLightsOn());
                else
                    return "null";

            case "isReverseLightOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isReverseLightOn());
                else
                    return "null";

            case "isRightTurnSignalOn":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getExteriorLights() != null)
                    return String.valueOf(ldmObject.getBsmObject().getExteriorLights().isRightTurnSignalOn());
                else
                    return "null";

            case "PositionVectorDateTime":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getFullPositionVector() != null && ldmObject.getBsmObject().getFullPositionVector().getDateTime() != null)
                    return ldmObject.getBsmObject().getFullPositionVector().getDateTime().toString();
                else
                    return "null";

            case "PositionVectorHeading":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getFullPositionVector() != null && ldmObject.getBsmObject().getFullPositionVector().getHeading() != null)
                    return ldmObject.getBsmObject().getFullPositionVector().getHeading().toString();
                else
                    return "null";

            case "PositionVectorLatitude":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getFullPositionVector() != null)
                    return String.valueOf(ldmObject.getBsmObject().getFullPositionVector().getLatitude());
                else
                    return "null";

            case "PositionVectorLongitude":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getFullPositionVector() != null)
                    return String.valueOf(ldmObject.getBsmObject().getFullPositionVector().getLongitude());
                else
                    return "null";

            case "PositionVectorSpeed":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getFullPositionVector() != null && ldmObject.getBsmObject().getFullPositionVector().getSpeed() != null)
                    return ldmObject.getBsmObject().getFullPositionVector().getSpeed().toString();
                else
                    return "null";

            case "Heading":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getHeading(DegreeUnit.DEG_1_5_POSNEG));
                else
                    return "null";

            case "LateralAcceleration":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getLateralAcceleration(AccelerationUnit.MperS2) != null)
                    return ldmObject.getBsmObject().getLateralAcceleration(AccelerationUnit.MperS2).toString();
                else
                    return "null";

            case "LongitudinalAcceleration":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getLongitudinalAcceleration(AccelerationUnit.MperS2) != null)
                    return ldmObject.getBsmObject().getLongitudinalAcceleration(AccelerationUnit.MperS2).toString();
                else
                    return "null";

            case "LightBarInUse":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getLightBarInUse() != null)
                    return ldmObject.getBsmObject().getLightBarInUse().toString();
                else
                    return "null";

            case "MsgCount":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getMsgCount());
                else
                    return "null";

            case "isaPDOPofUnder5":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isaPDOPofUnder5());
                else
                    return "null";

            case "isBaseStationType":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String .valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isBaseStationType());
                else
                    return "null";

            case "isHealthy":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isHealthy());
                else
                    return "null";

            case "isInViewOfUnder5":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isInViewOfUnder5());
                else
                    return "null";

            case "isLocalCorrectionsPresent":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isLocalCorrectionsPresent());
                else
                    return "null";

            case "isMonitored":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isMonitored());
                else
                    return "null";

            case "isNetworkCorrectionsPresent":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isNetworkCorrectionsPresent());
                else
                    return "null";

            case "isUnavailable":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathHistory() != null && ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathHistory().getCurrGnssStatus().isUnavailable());
                else
                    return "null";

            case "PathPredictionConfidence":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathPrediction() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathPrediction().getConfidence());
                else
                    return "null";

            case "PathPredictionRadiusOfCurvature":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getPathPrediction() != null)
                    return String.valueOf(ldmObject.getBsmObject().getPathPrediction().getRadiusOfCurvature(LengthUnit.M));
                else
                    return "null";

            case "SecMark":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getSecMark(TimeUnit.DAYS));
                else
                    return "null";

            case "SemiMajorAccuracy":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getSemiMajorAccuracy(LengthUnit.M) != null)
                    return ldmObject.getBsmObject().getSemiMajorAccuracy(LengthUnit.M).toString();
                else
                    return "null";

            case "SemiMinorAccuracy":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getSemiMinorAccuracy(LengthUnit.M) != null)
                    return ldmObject.getBsmObject().getSemiMinorAccuracy(LengthUnit.M).toString();
                else
                    return "null";

            case "SemiMajorOrientation":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getSemiMajorOrientation(DegreeUnit.DEG_1_5_POSNEG) != null)
                    return ldmObject.getBsmObject().getSemiMajorOrientation(DegreeUnit.DEG_1_5_POSNEG).toString();
                else
                    return "null";

            case "Speed":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getSpeed(SpeedUnit.KMperH) != null)
                    return ldmObject.getBsmObject().getSpeed(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "YawRate":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getYawRate(DegreeUnit.DEG_1_5_POSNEG));
                else
                    return "null";

            case "WheelBrakeisLeftFront":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getWheelBrakeStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getWheelBrakeStatus().isLeftFront());
                else
                    return "null";

            case "WheelBrakeisRigthFront":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getWheelBrakeStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getWheelBrakeStatus().isRightFront());
                else
                    return "null";

            case "WheelBrakeisLeftRear":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getWheelBrakeStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getWheelBrakeStatus().isLeftRear());
                else
                    return "null";

            case "WheelBrakeisRightRear":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getWheelBrakeStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getWheelBrakeStatus().isRightRear());
                else
                    return "null";

            case "WheelBrakeUnavailable":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getWheelBrakeStatus() != null)
                    return String.valueOf(ldmObject.getBsmObject().getWheelBrakeStatus().isUnavailable());
                else
                    return "null";

            case "VerticalAcceleration":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getVerticalAcceleration());
                else
                    return "null";

            case "VehicleType":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getVehicleType() != null)
                    return ldmObject.getBsmObject().getVehicleType().toString();
                else
                    return "null";

            case "VehicleLength":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getVehicleLength(LengthUnit.M));
                else
                    return "null";

            case "VehicheWidth":
                if(ldmObject.getBsmObject() != null)
                    return String.valueOf(ldmObject.getBsmObject().getVehicheWidth(LengthUnit.M));
                else
                    return "null";

            case "TransmissionState":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getTransmissionState() != null)
                    return ldmObject.getBsmObject().getTransmissionState().toString();
                else
                    return "null";

            case "TractionControlStatus":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getTractionControlStatus() != null)
                    return ldmObject.getBsmObject().getTractionControlStatus().toString();
                else
                    return "null";

            case "ThrottlePosition":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getThrottlePosition() != null)
                    return ldmObject.getBsmObject().getThrottlePosition().toString();
                else
                    return "null";

            case "SteeringWheelAngle":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getSteeringWheelAngle(DegreeUnit.DEG_1_5_POSNEG) != null)
                    return ldmObject.getBsmObject().getSteeringWheelAngle(DegreeUnit.DEG_1_5_POSNEG).toString();
                else
                    return "null";

            case "StabilityControlStatus":
                if(ldmObject.getBsmObject() != null && ldmObject.getBsmObject().getStabilityControlStatus() != null)
                    return ldmObject.getBsmObject().getStabilityControlStatus().toString();
                else
                    return "null";

            default:
                return "null";
        }
    }

    public String getMAPValue() {

        if(field==null)
            return "null";

        switch (field) {
            case "Longitude":
                if (ldmObject.getLongitude() != null)
                    return ldmObject.getLongitude().toString();
                else
                    return "null";

            case "Latitude":
                if (ldmObject.getLatitude() != null)
                    return ldmObject.getLatitude().toString();
                else
                    return "null";

            case "TimeStamp":
                if (ldmObject.getTimestamp() != null)
                    return String.valueOf(ldmObject.getTimestamp().getTime());
                else
                    return "null";

            case "ObjectID":
                if (ldmObject.getObjectID() != null)
                    return ldmObject.getObjectID().toString();
                else
                    return "null";

            case "Speed":
                if (ldmObject.getSpeed(SpeedUnit.KMperH) != null)
                    return ldmObject.getSpeed(SpeedUnit.KMperH).toString();
                else
                    return "null";

            case "Security":
                if (ldmObject.getSecurity() != null)
                    return ldmObject.getSecurity().toString();
                else
                    return "null";

            case "1.Intersection Longitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1 && ldmObject.getIntersections().get(0).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(0).getRefPoint().getLongitude());
                else
                    return "null";

            case "1.Intersection Latitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1 && ldmObject.getIntersections().get(0).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(0).getRefPoint().getLatitude());
                else
                    return "null";

            case "1.Intersection ID":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1)
                    return String.valueOf(ldmObject.getIntersections().get(0).getId());
                else
                    return "null";

            case "1.Intersection laneWidth":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1 && ldmObject.getIntersections().get(0).getLaneWidth(LengthUnit.M) != null)
                    return ldmObject.getIntersections().get(0).getLaneWidth(LengthUnit.M).toString();
                else
                    return "null";

            case "1.Intersection revision":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1)
                    return String.valueOf(ldmObject.getIntersections().get(0).getRevision());
                else
                    return "null";

            case "1.Intersection name":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1 && ldmObject.getIntersections().get(0).getName() != null)
                    return ldmObject.getIntersections().get(0).getName().toString();
                else
                    return "null";

            case "1.Intersection laneCount":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 1 && ldmObject.getIntersections().get(0).getIntersectionLaneSet() != null)
                    return String.valueOf(ldmObject.getIntersections().get(0).getIntersectionLaneSet().size());
                else
                    return "null";

            case "2.Intersection Longitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2 && ldmObject.getIntersections().get(1).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(1).getRefPoint().getLongitude());
                else
                    return "null";

            case "2.Intersection Latitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2 && ldmObject.getIntersections().get(1).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(1).getRefPoint().getLatitude());
                else
                    return "null";

            case "2.Intersection ID":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2)
                    return String.valueOf(ldmObject.getIntersections().get(1).getId());
                else
                    return "null";

            case "2.Intersection laneWidth":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2 && ldmObject.getIntersections().get(1).getLaneWidth(LengthUnit.M) != null)
                    return ldmObject.getIntersections().get(1).getLaneWidth(LengthUnit.M).toString();
                else
                    return "null";

            case "2.Intersection revision":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2)
                    return String.valueOf(ldmObject.getIntersections().get(1).getRevision());
                else
                    return "null";

            case "2.Intersection name":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2 && ldmObject.getIntersections().get(1).getName() != null)
                    return ldmObject.getIntersections().get(1).getName().toString();
                else
                    return "null";

            case "2.Intersection laneCount":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 2 && ldmObject.getIntersections().get(1).getIntersectionLaneSet() != null)
                    return String.valueOf(ldmObject.getIntersections().get(1).getIntersectionLaneSet().size());
                else
                    return "null";

            case "3.Intersection Longitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3 && ldmObject.getIntersections().get(2).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(2).getRefPoint().getLongitude());
                else
                    return "null";

            case "3.Intersection Latitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3 && ldmObject.getIntersections().get(2).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(2).getRefPoint().getLatitude());
                else
                    return "null";

            case "3.Intersection ID":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3)
                    return String.valueOf(ldmObject.getIntersections().get(2).getId());
                else
                    return "null";

            case "3.Intersection laneWidth":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3 && ldmObject.getIntersections().get(2).getLaneWidth(LengthUnit.M) != null)
                    return ldmObject.getIntersections().get(2).getLaneWidth(LengthUnit.M).toString();
                else
                    return "null";

            case "3.Intersection revision":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3)
                    return String.valueOf(ldmObject.getIntersections().get(2).getRevision());
                else
                    return "null";

            case "3.Intersection name":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3 && ldmObject.getIntersections().get(2).getName() != null)
                    return ldmObject.getIntersections().get(2).getName().toString();
                else
                    return "null";

            case "3.Intersection laneCount":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 3 && ldmObject.getIntersections().get(2).getIntersectionLaneSet() != null)
                    return String.valueOf(ldmObject.getIntersections().get(2).getIntersectionLaneSet().size());
                else
                    return "null";

            case "4.Intersection Longitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4 && ldmObject.getIntersections().get(3).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(3).getRefPoint().getLongitude());
                else
                    return "null";

            case "4.Intersection Latitude":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4 && ldmObject.getIntersections().get(3).getRefPoint() != null)
                    return String.valueOf(ldmObject.getIntersections().get(3).getRefPoint().getLatitude());
                else
                    return "null";

            case "4.Intersection ID":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4)
                    return String.valueOf(ldmObject.getIntersections().get(3).getId());
                else
                    return "null";

            case "4.Intersection laneWidth":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4 && ldmObject.getIntersections().get(3).getLaneWidth(LengthUnit.M) != null)
                    return ldmObject.getIntersections().get(3).getLaneWidth(LengthUnit.M).toString();
                else
                    return "null";

            case "4.Intersection revision":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4)
                    return String.valueOf(ldmObject.getIntersections().get(3).getRevision());
                else
                    return "null";

            case "4.Intersection name":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4 && ldmObject.getIntersections().get(3).getName() != null)
                    return ldmObject.getIntersections().get(3).getName().toString();
                else
                    return "null";

            case "4.Intersection laneCount":
                if (ldmObject.getIntersections() != null && ldmObject.getIntersections().size() >= 4 && ldmObject.getIntersections().get(3).getIntersectionLaneSet() != null)
                    return String.valueOf(ldmObject.getIntersections().get(3).getIntersectionLaneSet().size());
                else
                    return "null";

            default:
                return "null";
        }
    }
}
