package patrik.onlab_start.Model;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.utils.units.LengthUnit;
import com.commsignia.v2x.utils.units.SpeedUnit;

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
