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

import patrik.onlab_start.Model.DataCommunicator;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;
import patrik.onlab_start.NavigationBoard.MenuItemId;
import patrik.onlab_start.NavigationBoard.fragments.GraphFragment;
import patrik.onlab_start.NavigationBoard.fragments.MapFragment;

public class BoardActivity extends AppCompatActivity implements PacketCommunicator,DataCommunicator {
    private NavigationView navigationView;

    private Fragment[] fragments;

    private GraphFragment graphFragment;

    private MessageListFragment listFragment;

    private MapFragment mapFragment;

    private MessageDetailsFragment messageDetailsFragment;

    private int selectedMenuItemIndex = 0;

    private int counterM = 0;

    // ITS application properties
    public static ITSApplication itsApplication = null;
    public static int DEFAULT_ITS_AID = 85;
    public static String DEFAULT_TARGET_HOST = "192.168.0.96";
    public static int DEFAULT_TARGET_PORT = 7942;
    public static MessageSet DEFAULT_MESSAGE_SET = MessageSet.D;

    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        if (listFragment == null)
            listFragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.messageFragment);

        if (graphFragment == null)
            graphFragment = new GraphFragment();

        if (mapFragment == null)
            mapFragment = new MapFragment();

        if (messageDetailsFragment == null)
            messageDetailsFragment = new MessageDetailsFragment();


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        HashMap<String,String > selectedPropertiesValues = (HashMap<String, String>) intent.getSerializableExtra("selectedValues");
        DEFAULT_ITS_AID = Integer.valueOf(intent.getStringExtra("applicationID"));
        DEFAULT_TARGET_HOST = intent.getStringExtra("deviceAddress");
        DEFAULT_TARGET_PORT = Integer.valueOf(intent.getStringExtra("portNumber"));
        String messageSet = intent.getStringExtra("messageSet");
        switch (messageSet) {
            case "C":
                DEFAULT_MESSAGE_SET = MessageSet.C;
                break;
            case "D":
                DEFAULT_MESSAGE_SET = MessageSet.D;
                break;
            case "E":
                DEFAULT_MESSAGE_SET = MessageSet.E;
                break;
            default:
                DEFAULT_MESSAGE_SET = MessageSet.D;
                break;
        }

        counter = new Counter();
        CounterTimer timer = new CounterTimer(counter,this,0,3000);
        timer.start();

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
//                                    counterM++;
                                    counter.incrementMessageCounter();
                                    counter.incrementSnrSum(facilityNotification.getRssi());
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
                                    if(!mapFragment.isNull()) {
                                        mapFragment.setMarktoCarPosition(ldmObject.getLatitude(),ldmObject.getLongitude());
                                    }
//                                    counterM++;
                                    counter.incrementMessageCounter();
                                    counter.incrementSnrSum(ldmObject.getRssiDbm());
//                                    snrSum += ldmObject.getRssiDbm();
                                }
                                Log.d("Ldm received", ldmObject.getNotificationType().toString());
                            }
                        });
                    }
                });


                finalItsApplication.commands().facilitySubscribeBlocking(new FacilitySubscriptionMessages().setCamIncluded(true).setDenmIncluded(true));
                finalItsApplication.commands().ldmSubscribeBlocking(
                        new LdmFilter().setObjectTypeFilter(LdmObjectType.MAP, LdmObjectType.SPAT, LdmObjectType.BSM)
                );

            } catch (Exception e) {
                Log.d("ClientException: ", "Cannot execute API commands");
            }


//            //Timer for measure the incoming packets
//                Timer timer = new Timer();
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                sendDatasForShowing(counter,0);
//                                counter = 0;
//                            }
//                        });
//                    }
//                }, 0, 5000);


        } catch (InterruptedException | ClientException |TimeoutException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
            return;
        } finally {
            if (itsApplication != null)
                Log.d("Request", "was sent.");
        }

        setUpNavigation();

        setupStartingFragment(savedInstanceState);

        listFragment.startPacketCapturing(selectedPropertiesValues);
    }

    public void setUpNavigation() {
        fragments = new Fragment[]{
                listFragment,
                graphFragment,
                mapFragment,
//                messageDetailsFragment
        };

        addNavigationMenuItem(0,fragments[0],MenuItemId.PACKETS_ITEM);
        addNavigationMenuItem(1,fragments[1],MenuItemId.GRAPHS_ITEM);
        addNavigationMenuItem(2,fragments[2],MenuItemId.LIVEMAP_ITEM);
        //addNavigationMenuItem(3,fragments[3],MenuItemId.DETAILS_ITEM);

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
            case DETAILS_ITEM:
                id=R.string.details_menu_item;
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
    public void showPacketDetails(PacketAncestor data) {
//            messageDetailsFragment.changeData(data);
        //messageDetailsFragment.showDialog();
        showEditDialog(data);

    }

    public void setupStartingFragment(Bundle savedInstanceState) {
        selectMenuItem(0);
    }

    private void selectMenuItem(int index) {
        MenuItem item = navigationView.getMenu().getItem(index);
        onNavigationItemSelected(item, fragments[index]);
    }

    @Override
    public void sendDatasForShowing(double count, double avarageSNR) {
        if(!graphFragment.isNull()) {
            graphFragment.updateDataFromActivity(count, avarageSNR);
        }
    }

    private void showEditDialog(PacketAncestor data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MessageDetailsFragment editNameDialogFragment = MessageDetailsFragment.newInstance("Some Title",data);
        editNameDialogFragment.show(fragmentManager,"tag");
    }
}
