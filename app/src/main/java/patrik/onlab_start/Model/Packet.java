package patrik.onlab_start.Model;

/**
 * Created by Patrik on 2017.03.11..
 */
public class Packet {

    private String DetectionTime;
    private String type;
    private String ActionID;
    private String CauseCode;

    public Packet(String detectionTime, String type, String actionID, String causeCode) {
        DetectionTime = detectionTime;
        this.type = type;
        ActionID = actionID;
        CauseCode = causeCode;
    }

    public String getDetectionTime() {
        return DetectionTime;
    }

    public String getType() {
        return type;
    }

    public String getActionID() {
        return ActionID;
    }

    public String getCauseCode() {
        return CauseCode;
    }
}
