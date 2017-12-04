package patrik.onlab_start;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import patrik.onlab_start.Model.DataCommunicator;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;
import patrik.onlab_start.NavigationBoard.MenuItemId;
import patrik.onlab_start.NavigationBoard.fragments.GraphFragment;
import patrik.onlab_start.NavigationBoard.fragments.MapFragment;

public class BoardActivity extends AppCompatActivity implements PacketCommunicator, DataCommunicator {
    private NavigationView navigationView;

    private Fragment[] fragments;

    private GraphFragment graphFragment;

    private MessageListFragment listFragment;

    private MapFragment mapFragment;

    private CounterTimer timer;

    private MenuItem stopMenuItem;

    private MenuItem restartMenuItem;

    private MenuItem saveMenuItem;

    private Fragment lastSelectedFragment;

    // ITS application properties
    public static ITSApplication itsApplication = null;
    public static int DEFAULT_ITS_AID = 85;
    public static String DEFAULT_TARGET_HOST = "192.168.0.96";
    public static int DEFAULT_TARGET_PORT = 7942;
    public static MessageSet DEFAULT_MESSAGE_SET = MessageSet.D;

    private long localLatitude=0;
    private long localLongitude=0;

    private int interval;

    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        if (listFragment == null)
            listFragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.messageListFragment);

        if (graphFragment == null)
            graphFragment = (GraphFragment) getSupportFragmentManager().findFragmentById(R.id.graphFragment);

        if (mapFragment == null)
            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        HashMap<String, String> selectedPropertiesValues = (HashMap<String, String>) intent.getSerializableExtra("selectedValues");
        DEFAULT_ITS_AID = Integer.valueOf(intent.getStringExtra("applicationID"));
        DEFAULT_TARGET_HOST = intent.getStringExtra("deviceAddress");
        DEFAULT_TARGET_PORT = Integer.valueOf(intent.getStringExtra("portNumber"));
        String messageSet = intent.getStringExtra("messageSet");
        interval = Integer.valueOf(intent.getStringExtra("interval"));
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
        timer = new CounterTimer(counter, this, 0, interval*1000);
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
                                    packet.setLocalLatitude(localLatitude);
                                    packet.setLocalLongitude(localLongitude);
                                    listFragment.adapter.addPacket(packet);

                                    if (!mapFragment.isNull()) {
                                        mapFragment.setMarktoCarPosition(new PacketAncestor(facilityNotification,NotificationType.FAC_NOTIFICATION));
                                    }

                                    counter.incrementMessageCounter();
                                    counter.incrementSnrSum(facilityNotification.getRssi());
                                }
                            }
                        });
                    }


                    @Override
                    public void onLdmNotificationReceived(final LdmObject ldmObject) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (ldmObject) {
                                    if(ldmObject.isLocal()) {
                                        localLatitude = ldmObject.getLatitude();
                                        localLongitude = ldmObject.getLongitude();
                                    }
                                    PacketAncestor packet = new PacketAncestor(ldmObject, NotificationType.LDM_NOTIFICATION);
                                    packet.setLocalLatitude(localLatitude);
                                    packet.setLocalLongitude(localLongitude);
                                    if (!mapFragment.isNull()) {
                                        mapFragment.setMarktoCarPosition(new PacketAncestor(ldmObject, NotificationType.LDM_NOTIFICATION));
                                    }
                                    if(!ldmObject.isLocal()) {
                                        listFragment.adapter.addPacket(packet);
                                        counter.incrementMessageCounter();
                                        counter.incrementSnrSum(ldmObject.getRssiDbm());
                                    }
                                }
                            }
                        });
                    }
                });


                finalItsApplication.commands().facilitySubscribeBlocking(new FacilitySubscriptionMessages().setCamIncluded(true).setDenmIncluded(true));
                finalItsApplication.commands().ldmSubscribeBlocking(
                        new LdmFilter().setObjectTypeFilter(LdmObjectType.MAP, LdmObjectType.SPAT, LdmObjectType.BSM).includeLocalLDMs()
                );

            } catch (ClientException e) {
                Log.d("ClientException: ", "Cannot execute API commands (Failed to subscribe to event notifications)");
                Toast.makeText(getApplicationContext(),"Cannot execute API commands (Failed to subscribe to event notifications", Toast.LENGTH_LONG).show();
            }

        } catch (InterruptedException | ClientException | TimeoutException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
            finish();
            return;
        } finally {
            if (itsApplication != null)
                Log.d("Request", "was sent.");
        }

        lastSelectedFragment = listFragment;

        setUpNavigation();

        mapFragment.getView().setVisibility(View.INVISIBLE);
        graphFragment.getView().setVisibility(View.INVISIBLE);


        listFragment.startPacketCapturing(selectedPropertiesValues);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        stopMenuItem = (MenuItem) menu.findItem(R.id.stopItem);
        stopMenuItem.setEnabled(true);
        restartMenuItem = (MenuItem) menu.findItem(R.id.restartItem);
        restartMenuItem.setEnabled(true);
        saveMenuItem = (MenuItem) menu.findItem(R.id.saveItem);
        saveMenuItem.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.stopItem:
                if (itsApplication != null) {
                    try {
                        itsApplication.commands().deregisterBlocking();
                    } catch (ClientException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "DeregisterBlocking failed!", Toast.LENGTH_LONG).show();
                    } finally {
                        itsApplication.shutdown();
                    }
                }
                counter.resetMessageCounter();
                counter.resetSnrSum();

                timer.stop();

                stopMenuItem.setEnabled(false);
                restartMenuItem.setEnabled(true);
                saveMenuItem.setEnabled(true);

                graphFragment.enableSaving();

                mapFragment.stopClearing();

                return true;

            case R.id.restartItem:
                try {
                    itsApplication.commands().deregisterBlocking();
                } catch (ClientException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "DeregisterBlocking failed!", Toast.LENGTH_LONG).show();
                } finally {
                    itsApplication.shutdown();
                    finish();
                }
                return true;

            case R.id.saveItem:
                showSaveAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            itsApplication.commands().deregisterBlocking();
            mapFragment.stopClearing();
        } catch (ClientException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "DeregisterBlocking failed!", Toast.LENGTH_LONG).show();
        } finally {
            itsApplication.shutdown();
            finish();
        }
    }

    public void showSaveAlertDialog() {

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.save_window_layout, null);

        final EditText et = (EditText) view.findViewById(R.id.save_etFileName);
        alertbox.setView(view);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    listFragment.adapter.savePackets(et.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertbox.show();
    }

    public void setUpNavigation() {
        fragments = new Fragment[]{
                listFragment,
                graphFragment,
                mapFragment,
        };

        addNavigationMenuItem(0, fragments[0], MenuItemId.PACKETS_ITEM);
        addNavigationMenuItem(1, fragments[1], MenuItemId.GRAPHS_ITEM);
        addNavigationMenuItem(2, fragments[2], MenuItemId.LIVEMAP_ITEM);

        navigationView.getMenu().setGroupCheckable(1, true, true);
    }

    private void addNavigationMenuItem(int index, final Fragment fragment, MenuItemId menuItemId) {
        int id = 0;
        switch (menuItemId) {
            case PACKETS_ITEM:
                id = R.string.list_menu_item;
                break;
            case GRAPHS_ITEM:
                id = R.string.graph_menu_item;
                break;
            case LIVEMAP_ITEM:
                id = R.string.livemap_menu_item;
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

        setTitle(item.getTitle());

        lastSelectedFragment.getView().setVisibility(View.INVISIBLE);
        fragment.getView().setVisibility(View.VISIBLE);

        lastSelectedFragment = fragment;
    }

    @Override
    public void showPacketDetails(PacketAncestor data) {
        showEditDialog(data);
    }

    @Override
    public void sendDatasForShowing(double count, double avarageSNR) {
        if (!graphFragment.isNull()) {
            graphFragment.updateDataFromActivity(count, avarageSNR);
        }
    }

    private void showEditDialog(PacketAncestor data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MessageDetailsFragment editNameDialogFragment = MessageDetailsFragment.newInstance("Some Title", data);
        editNameDialogFragment.show(fragmentManager, "tag");
    }
}
