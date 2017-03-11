package patrik.onlab_start;

import patrik.onlab_start.Model.Packet;

/**
 * Created by Patrik on 2017.03.08..
 * Interface to update field in MessageDetailsFragment from MessageListFragment
 */
public interface PacketCommunicator {

    public void updateData(Packet data);

}
