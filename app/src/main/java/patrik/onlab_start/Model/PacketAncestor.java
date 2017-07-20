package patrik.onlab_start.Model;

/**
 * Common ancestor for the various Messages like Ldm, Facility, etc. to store in one list
 *
 * Created by Patrik on 2017.04.15..
 */
public class PacketAncestor {

    Object object;
    String type; //Facility, LDM
    int selected = 0;

    public PacketAncestor(Object object, String type) {
        this.object = object;
        this.type = type;
    }

    public String getType() {
        return type;
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
