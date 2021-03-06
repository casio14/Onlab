package patrik.onlab_start.Model;

/**
 * Common ancestor for the various Messages like Ldm, Facility, etc. to store in one list
 *
 * Created by Patrik on 2017.04.15..
 */
public class PacketAncestor {

    Object object;
    NotificationType notificationType; //Facility, LDM
    int selected = 0;

    public PacketAncestor(Object object, NotificationType notificationType) {
        this.object = object;
        this.notificationType = notificationType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Object getObject() {
        return object;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
