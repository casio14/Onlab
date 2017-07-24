package patrik.onlab_start;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.commsignia.v2x.client.ITSApplication;
import com.commsignia.v2x.client.ITSEventAdapter;
import com.commsignia.v2x.client.MessageSet;
import com.commsignia.v2x.client.exception.ClientException;
import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.FacilitySubscriptionMessages;
import com.commsignia.v2x.client.model.LdmFilter;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.client.model.LdmObjectType;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;
import patrik.onlab_start.NavigationBoard.fragments.GraphFragment;
import patrik.onlab_start.NavigationBoard.MenuItemId;

public class BoardActivity extends AppCompatActivity implements PacketCommunicator {
    private NavigationView navigationView;

    private Fragment[] fragments;

    private GraphFragment graphFragment;

    private MessageListFragment listFragment;

    private int selectedMenuItemIndex = 0;


    //ITS application properties
    public static ITSApplication itsApplication = null;
    public static final int DEFAULT_ITS_AID = 55;
    public static final String DEFAULT_TARGET_HOST = "192.168.0.96";
    public static final int DEFAULT_TARGET_PORT = 7942;
    public static final MessageSet DEFAULT_MESSAGE_SET = MessageSet.D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        if (listFragment == null)
            listFragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.messageFragment);

        if (graphFragment == null)
            graphFragment = new GraphFragment();


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        HashMap<String,String > selectedPropertiesValues = (HashMap<String, String>) intent.getSerializableExtra("selectedValues");

        //Start the ITS Application
        try {
            itsApplication = new ITSApplication(DEFAULT_ITS_AID, DEFAULT_TARGET_HOST, DEFAULT_TARGET_PORT, DEFAULT_MESSAGE_SET);

            itsApplication.connect(10000);

            itsApplication.commands().registerBlocking();

            itsApplication.commands().setDeviceTimeBlocking(System.currentTimeMillis() / 1000L);

            String id = itsApplication.getHost(); // Get host
            Log.d("ITS station ID:", String.valueOf(id));


            final ITSApplication finalItsApplication = itsApplication;

            //Add evenet listeners
            try {
                finalItsApplication.addEventListener(new ITSEventAdapter() {    // Facility subscribe
                    @Override
                    public void onFacilityNotificationReceived(final FacilityNotification facilityNotification) {
                        //that we can update the UI from other thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (facilityNotification) {
                                    PacketAncestor packet =
                                            new PacketAncestor(facilityNotification, NotificationType.FAC_NOTIFICATION);
                                    listFragment.adapter.addPacket(packet);
//                                    messageCounter++;
//                                    snrSum += facilityNotification.getRssi();
                                    Log.d("SNR : ",String.valueOf(facilityNotification.getRssi()));
                                }
                                Log.d("Facility not. received", facilityNotification.getType().toString());
                            }
                        });
                    }



                    @Override
                    public void onLdmNotificationReceived(final LdmObject ldmObject) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (ldmObject) {
                                    PacketAncestor packet = new PacketAncestor(ldmObject, NotificationType.LDM_NOTIFICATION);
                                    listFragment.adapter.addPacket(packet);
//                                    messageCounter++;
//                                    snrSum += ldmObject.getRssiDbm();
                                }
                                Log.d("Ldm received", ldmObject.getNotificationType().toString());
                            }
                        });
                    }
                });


                finalItsApplication.commands().facilitySubscribeBlocking(new FacilitySubscriptionMessages().setCamIncluded(true).setDenmIncluded(true));
                finalItsApplication.commands().ldmSubscribeBlocking(
                        new LdmFilter().setObjectTypeFilter(LdmObjectType.MAP, LdmObjectType.SPAT)
                );

            } catch (Exception e) {
                Log.d("ClientException: ", "Cannot execute API commands");
            }


        } catch (InterruptedException | ClientException |TimeoutException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
            return;
        } finally {
            if (itsApplication != null)
                Log.d("Request", "was sent.");
        }

        setUpNavigation();

        setupStartingFragment(savedInstanceState);

        listFragment.startPacketCapturing(selectedPropertiesValues);

        System.out.print("");


    }

    public void setUpNavigation() {
        fragments = new Fragment[]{
                listFragment,
                graphFragment,
        };

        addNavigationMenuItem(0,fragments[0],MenuItemId.PACKETS_ITEM);
        addNavigationMenuItem(1,fragments[1],MenuItemId.GRAPHS_ITEM);
//        addNavigationMenuItem(2,fragments[2],MenuItemId.LIVEMAP_ITEM);

        navigationView.getMenu().setGroupCheckable(1, true, true);
    }

    private void addNavigationMenuItem(int index, final Fragment fragment, MenuItemId menuItemId) {
        int id=0;
        switch (menuItemId) {
            case PACKETS_ITEM:
               id=R.string.list_menu_item;
                break;
            case GRAPHS_ITEM:
               id=R.string.graph_menu_item;
                break;
            case LIVEMAP_ITEM:
               id=R.string.livemap_menu_item;
                break;
        }
        navigationView.getMenu()
                .add(1, index, Menu.NONE, id)
                .setCheckable(true)
                .setOnMenuItemClickListener(item -> {
                    onNavigationItemSelected(item, fragment);
                    return true;
                });
    }

    private void onNavigationItemSelected(MenuItem item, Fragment fragment) {
        item.setChecked(true);
        selectedMenuItemIndex = item.getItemId();

        setTitle(item.getTitle());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.messageFragment, fragment)
                .commit();
    }

    @Override
    public void updateDataDetails(PacketAncestor data) {

    }

    public void setupStartingFragment(Bundle savedInstanceState) {
        selectMenuItem(0);
    }

    private void selectMenuItem(int index) {
        MenuItem item = navigationView.getMenu().getItem(index);
        onNavigationItemSelected(item, fragments[index]);
    }
}
