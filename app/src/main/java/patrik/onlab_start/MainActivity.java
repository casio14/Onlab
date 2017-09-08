package patrik.onlab_start;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.commsignia.v2x.client.ITSApplication;

import com.commsignia.v2x.client.MessageSet;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import patrik.onlab_start.Model.DataCommunicator;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;
import patrik.onlab_start.NavigationBoard.fragments.GraphFragment;

public class MainActivity extends AppCompatActivity implements PacketCommunicator, DataCommunicator {

    //Fragments
    MessageDetailsFragment messageDetailsFragment;
    GraphFragment graphFragment;
    MessageListFragment listFragment;

    //Buttons
    Button startButton;
    Button stopButton;
    Button saveButton;
    Button restartButton;

    //Spinners
    Spinner typeSpinner;
    Spinner firstPropertySpinner;
    Spinner secondPropertySpinner;
    Spinner thirdPropertySpinner;
    Spinner messageSetsSpinner;

    //Adapters for Spinners
    ArrayAdapter<CharSequence> adapterDENM_Properties;
    ArrayAdapter<CharSequence> adapterCAM_Properties;
    ArrayAdapter<CharSequence> adapterMAP_Properties;
    ArrayAdapter<CharSequence> adapterSPAT_Properties;
    ArrayAdapter<CharSequence> adapterBSM_Properties;

    //EditTExt
    EditText measuringInterval_eT;
    EditText applicationID;
    EditText deviceAddress;
    EditText portNumber;

    //Timer
    Timer timer;

    //Selected Properties
    Map<String,Integer> selectedPropertiesPositions;
    HashMap<String,String> selectedPropertiesValues;

    String selected = "DENM";

    //GraphFragment properties
    static int messageCounter = 0;
    static double avarageSNR = 0;
    static double snrSum = 0;


    //ITS application properties
    public static ITSApplication itsApplication = null;
    public static final int DEFAULT_ITS_AID = 55;
    public static final String DEFAULT_TARGET_HOST = "192.168.0.76";
    public static final int DEFAULT_TARGET_PORT = 7942;
    public static final MessageSet DEFAULT_MESSAGE_SET = MessageSet.D;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the components
        componentInitialization();

        initializeSelectedPropertiesHashMap();

        //Set the adapter settings for the spinners
        setAdaptersForSpinners();

        //Set approtiate listeners for the spinners
        spinnerListeners();

/*
        if (listFragment == null)
            listFragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.messageFragment);*/

        //Set approtiate listeners for the buttons
        initializeButtonListeners();

        AndroidThreeTen.init(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.load_settings:
                showLoadAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    //Show a message info in the MessageDetailsFragment
    public void showPacketDetails(PacketAncestor data) {
/*
        //Set the appropriate fragment
        if (messageDetailsFragment == null) {
            messageDetailsFragment = (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }

        //Call the MessageDetailsFragment appropriate method to update values
        messageDetailsFragment.changeData(data);*/
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

    /*public void showLoadAlertDialog() {

        final int[] lastClickedIndex = {-1};

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Choose a file");

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.load_window_layout, null);
        final TextView choosenFile = (TextView) view.findViewById(R.id.load_tvChoosen);
        choosenFile.setText("-");

        File dir = new File(getApplicationContext().getFilesDir().getPath(), "SavedDatas"); //get folder
        final File[] files = dir.listFiles();
        final String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }

        ListView lv = (ListView) view.findViewById(R.id.load_listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastClickedIndex[0] = i;
                choosenFile.setText(names[i]);
            }
        });

        lv.setAdapter(adapter);

        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (graphFragment == null)
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                try {
                    if (!choosenFile.getText().toString().equals("-"))
                        graphFragment.loadDatas(getApplicationContext(), choosenFile.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        alertbox.setView(view);
        alertbox.show();
    }*/

    //Send incoming packet infos to the GraphFragment
    @Override
    public void sendDatasForShowing(double count, double avarageSNR) {
        /*if (graphFragment == null) {
            graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
        }
        graphFragment.updateDataFromActivity(count,avarageSNR);*/
    }

    public void spinnerListeners() {
        //Change listeners for Spinners
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (typeSpinner.getSelectedItem().toString().equals("DENM")) {

                    selected = "DENM";

                    firstPropertySpinner.setAdapter(adapterDENM_Properties);
                    secondPropertySpinner.setAdapter(adapterDENM_Properties);
                    thirdPropertySpinner.setAdapter(adapterDENM_Properties);

                    firstPropertySpinner.setSelection(selectedPropertiesPositions.get("DENM_1"));
                    secondPropertySpinner.setSelection(selectedPropertiesPositions.get("DENM_2"));
                    thirdPropertySpinner.setSelection(selectedPropertiesPositions.get("DENM_3"));
                    Log.d("ÉRTÉKEK: ", selectedPropertiesPositions.get("DENM_1") + " " + selectedPropertiesPositions.get("DENM_2")
                            + " " + selectedPropertiesPositions.get("DENM_3"));
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {

                    selected = "CAM";

                    firstPropertySpinner.setAdapter(adapterCAM_Properties);
                    secondPropertySpinner.setAdapter(adapterCAM_Properties);
                    thirdPropertySpinner.setAdapter(adapterCAM_Properties);

                    firstPropertySpinner.setSelection(selectedPropertiesPositions.get("CAM_1"));
                    secondPropertySpinner.setSelection(selectedPropertiesPositions.get("CAM_2"));
                    thirdPropertySpinner.setSelection(selectedPropertiesPositions.get("CAM_3"));
                    Log.d("ÉRTÉKEK: ", selectedPropertiesPositions.get("CAM_1") + " " + selectedPropertiesPositions.get("CAM_2")
                            + " " + selectedPropertiesPositions.get("CAM_3"));
                }

                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {

                    selected = "MAP";

                    firstPropertySpinner.setAdapter(adapterMAP_Properties);
                    secondPropertySpinner.setAdapter(adapterMAP_Properties);
                    thirdPropertySpinner.setAdapter(adapterMAP_Properties);

                    firstPropertySpinner.setSelection(selectedPropertiesPositions.get("MAP_1"));
                    secondPropertySpinner.setSelection(selectedPropertiesPositions.get("MAP_2"));
                    thirdPropertySpinner.setSelection(selectedPropertiesPositions.get("MAP_3"));

                    Log.d("ÉRTÉKEK: ", selectedPropertiesPositions.get("MAP_1") + " " + selectedPropertiesPositions.get("MAP_2")
                            + " " + selectedPropertiesPositions.get("MAP_3"));
                }

                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {

                    selected = "SPAT";

                    firstPropertySpinner.setAdapter(adapterSPAT_Properties);
                    secondPropertySpinner.setAdapter(adapterSPAT_Properties);
                    thirdPropertySpinner.setAdapter(adapterSPAT_Properties);

                    firstPropertySpinner.setSelection(selectedPropertiesPositions.get("SPAT_1"));
                    secondPropertySpinner.setSelection(selectedPropertiesPositions.get("SPAT_2"));
                    thirdPropertySpinner.setSelection(selectedPropertiesPositions.get("SPAT_3"));

                    Log.d("ÉRTÉKEK: ", selectedPropertiesPositions.get("SPAT_1") + " " + selectedPropertiesPositions.get("SPAT_2")
                            + " " + selectedPropertiesPositions.get("SPAT_3"));
                }

                if (typeSpinner.getSelectedItem().toString().equals("BSM")) {

                    selected = "BSM";

                    firstPropertySpinner.setAdapter(adapterBSM_Properties);
                    secondPropertySpinner.setAdapter(adapterBSM_Properties);
                    thirdPropertySpinner.setAdapter(adapterBSM_Properties);

                    firstPropertySpinner.setSelection(selectedPropertiesPositions.get("BSM_1"));
                    secondPropertySpinner.setSelection(selectedPropertiesPositions.get("BSM_2"));
                    thirdPropertySpinner.setSelection(selectedPropertiesPositions.get("BSM_3"));

                    Log.d("ÉRTÉKEK: ", selectedPropertiesPositions.get("BSM_1") + " " + selectedPropertiesPositions.get("BSM_2")
                            + " " + selectedPropertiesPositions.get("BSM_3"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firstPropertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (typeSpinner.getSelectedItem().toString().equals("DENM")) {
                    selectedPropertiesPositions.replace("DENM_1",firstPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    selectedPropertiesPositions.replace("CAM_1",firstPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    selectedPropertiesPositions.replace("MAP_1",firstPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    selectedPropertiesPositions.replace("SPAT_1",firstPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    selectedPropertiesPositions.replace("BSM_1",firstPropertySpinner.getSelectedItemPosition());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        secondPropertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (typeSpinner.getSelectedItem().toString().equals("DENM")) {
                    selectedPropertiesPositions.replace("DENM_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    selectedPropertiesPositions.replace("CAM_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    selectedPropertiesPositions.replace("MAP_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    selectedPropertiesPositions.replace("SPAT_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    selectedPropertiesPositions.replace("BSM_2",secondPropertySpinner.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        thirdPropertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(typeSpinner.getSelectedItem().toString().equals("DENM")) {
                    selectedPropertiesPositions.replace("DENM_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    selectedPropertiesPositions.replace("CAM_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    selectedPropertiesPositions.replace("MAP_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    selectedPropertiesPositions.replace("SPAT_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    selectedPropertiesPositions.replace("BSM_3",thirdPropertySpinner.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setAdaptersForSpinners() {
        //Set adapters for Spinners
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterType);

        adapterDENM_Properties = ArrayAdapter.createFromResource(this, R.array.denmProperties, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterCAM_Properties = ArrayAdapter.createFromResource(this, R.array.camProperties, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMAP_Properties = ArrayAdapter.createFromResource(this, R.array.mapProperties, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterSPAT_Properties = ArrayAdapter.createFromResource(this, R.array.spatProperties, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterBSM_Properties = ArrayAdapter.createFromResource(this, R.array.bsmProperties, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapterMessageSets = ArrayAdapter.createFromResource(this, R.array.messageSets, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstPropertySpinner.setAdapter(adapterDENM_Properties);
        secondPropertySpinner.setAdapter(adapterDENM_Properties);
        thirdPropertySpinner.setAdapter(adapterDENM_Properties);

        messageSetsSpinner.setAdapter(adapterMessageSets);
        messageSetsSpinner.setSelection(adapterMessageSets.getPosition("D"));
    }

    public void componentInitialization() {
        //Components initialization
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        saveButton = (Button) findViewById(R.id.mainSaveButton);
        restartButton = (Button) findViewById(R.id.restartButton);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        firstPropertySpinner = (Spinner) findViewById(R.id.firstPropertySpinner);
        secondPropertySpinner = (Spinner) findViewById(R.id.secondPropertySpinner);
        thirdPropertySpinner = (Spinner) findViewById(R.id.thirdPropertySpinner);
        messageSetsSpinner = (Spinner) findViewById(R.id.messageSetsSpinner);

        measuringInterval_eT = (EditText) findViewById(R.id.eTsetInterval);
        applicationID = (EditText) findViewById(R.id.et_appID);
        deviceAddress = (EditText) findViewById(R.id.et_deviceAddress);
        portNumber = (EditText) findViewById(R.id.et_portNumber);

        stopButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);

       /* //Set the messageFragment invisible
        FrameLayout messageFrame = (FrameLayout) findViewById(R.id.messageFrame);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 0);
        messageFrame.setLayoutParams(params);*/

        //Set the spinnerLayout larger
        final LinearLayout spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
        LinearLayout.LayoutParams paramsSpinnerLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 9);
        spinnerLayout.setLayoutParams(paramsSpinnerLayout);
    }

    public void initializeButtonListeners() {
        //Start the graph thread
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateEditTexts())
                    return;

                updateSelectedPropertiesValuesHashMap();


//                //Start the ITS Application
//                try {
//                    itsApplication = new ITSApplication(DEFAULT_ITS_AID, DEFAULT_TARGET_HOST, DEFAULT_TARGET_PORT, DEFAULT_MESSAGE_SET);
//
//                    itsApplication.connect(10000);
//
//                    itsApplication.commands().registerBlocking();
//
//                    itsApplication.commands().setDeviceTimeBlocking(System.currentTimeMillis() / 1000L);
//
//                    String id = itsApplication.getHost(); // Get host
//                    Log.d("ITS station ID:", String.valueOf(id));
//
//
//                    final ITSApplication finalItsApplication = itsApplication;
//
//                    //Add evenet listeners
//                    try {
//                        finalItsApplication.addEventListener(new ITSEventAdapter() {    // Facility subscribe
//                            @Override
//                            public void onFacilityNotificationReceived(final FacilityNotification facilityNotification) {
//                                //that we can update the UI from other thread
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        synchronized (facilityNotification) {
//                                            PacketAncestor packet =
//                                                    new PacketAncestor(facilityNotification, NotificationType.FAC_NOTIFICATION);
//                                            listFragment.adapter.addPacket(packet);
//                                            messageCounter++;
//                                            snrSum += facilityNotification.getRssi();
//                                            Log.d("SNR : ",String.valueOf(facilityNotification.getRssi()));
//                                        }
//                                        Log.d("Facility not. received", facilityNotification.getType().toString());
//                                    }
//                                });
//                            }
//
//
//
//                            @Override
//                            public void onLdmNotificationReceived(final LdmObject ldmObject) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        synchronized (ldmObject) {
//                                            PacketAncestor packet = new PacketAncestor(ldmObject, NotificationType.LDM_NOTIFICATION);
//                                            listFragment.adapter.addPacket(packet);
//                                            messageCounter++;
//                                            snrSum += ldmObject.getRssiDbm();
//                                        }
//                                        Log.d("Ldm received", ldmObject.getNotificationType().toString());
//                                    }
//                                });
//                            }
//                        });
//
//
//                        finalItsApplication.commands().facilitySubscribeBlocking(new FacilitySubscriptionMessages().setCamIncluded(true).setDenmIncluded(true));
//                        finalItsApplication.commands().ldmSubscribeBlocking(
//                                new LdmFilter().setObjectTypeFilter(LdmObjectType.MAP, LdmObjectType.SPAT)
//                        );
//
//                    } catch (Exception e) {
//                        Log.d("ClientException: ", "Cannot execute API commands");
//                    }
//
//
//                } catch (InterruptedException | ClientException |TimeoutException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
//                    return;
//                } finally {
//                    if (itsApplication != null)
//                        Log.d("Request", "was sent.");
//                }

                Intent intent = new Intent(getApplicationContext(),BoardActivity.class);
                intent.putExtra("selectedValues",selectedPropertiesValues);
                intent.putExtra("applicationID",applicationID.getText().toString());
                intent.putExtra("deviceAddress",deviceAddress.getText().toString());
                intent.putExtra("portNumber",portNumber.getText().toString());
                intent.putExtra("messageSet",messageSetsSpinner.getSelectedItem().toString());
                startActivity(intent);
/*
                //Set invisible the spinnerLayout
                final LinearLayout spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
                LinearLayout.LayoutParams paramsSpinnerLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 0);
                spinnerLayout.setLayoutParams(paramsSpinnerLayout);
                spinnerLayout.setVisibility(View.INVISIBLE);

                //Set larger the messageFragment to fill the spinnerLayout field
                FrameLayout messageFrame = (FrameLayout) findViewById(R.id.messageFrame);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 9);
                messageFrame.setLayoutParams(params);

                updateSelectedPropertiesValuesHashMap();
                //Pass the adjested spinner values
                listFragment.startPacketCapturing(selectedPropertiesValues);


                if (graphFragment == null) {
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                }

                graphFragment.onResume();


                //Timer for measure the incoming packets
                if (!measuringInterval_eT.getText().toString().equals("")) {
                    int interval = Integer.parseInt(measuringInterval_eT.getText().toString());
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("ÜZENETEK SZÁMA", ":::::" + messageCounter);
                                    avarageSNR = snrSum / messageCounter;
                                    sendDatasForShowing(messageCounter,avarageSNR);
                                    messageCounter = 0;
                                    snrSum=0;
                                    avarageSNR=0;
                                }
                            });
                        }
                    }, 0, interval * 1000);

                } else {
                    Toast.makeText(MainActivity.this, "Give an interval number!", Toast.LENGTH_SHORT).show();
                }




                startButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);*/
            }
        });

        //Stop the graph thread
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (graphFragment == null) {
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                }
                graphFragment.onPause();*/

                snrSum=0;
                avarageSNR=0;
                messageCounter=0;

                if(timer!=null)
                    timer.cancel();

                if(itsApplication!=null)
                    itsApplication.shutdown();


                stopButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaveAlertDialog();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (graphFragment == null) {
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                }*/
                graphFragment.onPause();
                graphFragment.clearDatas();
/*
                if(messageDetailsFragment ==null)
                    messageDetailsFragment= (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
                messageDetailsFragment.clearDetails();
*/
                //Set the spinnerLayout to the original size
                final LinearLayout spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
                LinearLayout.LayoutParams paramsSpinnerLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 9);
                spinnerLayout.setLayoutParams(paramsSpinnerLayout);
                spinnerLayout.setVisibility(View.VISIBLE);

                //Set the messageFrame to the original size
                FrameLayout messageFrame = (FrameLayout) findViewById(R.id.messageFrame);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 0);
                messageFrame.setLayoutParams(params );

                if(timer!=null)
                    timer.cancel();

                if(itsApplication!=null)
                    itsApplication.shutdown();


                stopButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                restartButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initializeSelectedPropertiesHashMap() {
        selectedPropertiesPositions = new HashMap<>();
        selectedPropertiesPositions.put("DENM_1",0);
        selectedPropertiesPositions.put("DENM_2",0);
        selectedPropertiesPositions.put("DENM_3",0);
        selectedPropertiesPositions.put("CAM_1",0);
        selectedPropertiesPositions.put("CAM_2",0);
        selectedPropertiesPositions.put("CAM_3",0);
        selectedPropertiesPositions.put("MAP_1",0);
        selectedPropertiesPositions.put("MAP_2",0);
        selectedPropertiesPositions.put("MAP_3",0);
        selectedPropertiesPositions.put("SPAT_1",0);
        selectedPropertiesPositions.put("SPAT_2",0);
        selectedPropertiesPositions.put("SPAT_3",0);
        selectedPropertiesPositions.put("BSM_1",0);
        selectedPropertiesPositions.put("BSM_2",0);
        selectedPropertiesPositions.put("BSM_3",0);

        selectedPropertiesValues = new HashMap<>();
        selectedPropertiesValues.put("DENM_1","");
        selectedPropertiesValues.put("DENM_2","");
        selectedPropertiesValues.put("DENM_3","");
        selectedPropertiesValues.put("CAM_1","");
        selectedPropertiesValues.put("CAM_2","");
        selectedPropertiesValues.put("CAM_3","");
        selectedPropertiesValues.put("MAP_1","");
        selectedPropertiesValues.put("MAP_2","");
        selectedPropertiesValues.put("MAP_3","");
        selectedPropertiesValues.put("SPAT_1","");
        selectedPropertiesValues.put("SPAT_2","");
        selectedPropertiesValues.put("SPAT_3","");
        selectedPropertiesValues.put("BSM_1","");
        selectedPropertiesValues.put("BSM_2","");
        selectedPropertiesValues.put("BSM_3","");
    }

    public void updateSelectedPropertiesValuesHashMap() {
        selectedPropertiesValues.replace("DENM_1",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_1")).toString());
        selectedPropertiesValues.replace("DENM_2",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_2")).toString());
        selectedPropertiesValues.replace("DENM_3",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_3")).toString());

        selectedPropertiesValues.replace("CAM_1",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_1")).toString());
        selectedPropertiesValues.replace("CAM_2",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_2")).toString());
        selectedPropertiesValues.replace("CAM_3",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_3")).toString());

        selectedPropertiesValues.replace("MAP_1",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_1")).toString());
        selectedPropertiesValues.replace("MAP_2",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_2")).toString());
        selectedPropertiesValues.replace("MAP_3",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_3")).toString());

        selectedPropertiesValues.replace("SPAT_1",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_1")).toString());
        selectedPropertiesValues.replace("SPAT_2",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_2")).toString());
        selectedPropertiesValues.replace("SPAT_3",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_3")).toString());

        selectedPropertiesValues.replace("BSM_1",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_1")).toString());
        selectedPropertiesValues.replace("BSM_2",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_2")).toString());
        selectedPropertiesValues.replace("BSM_3",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_3")).toString());
    }

    public boolean validateEditTexts() {
        String deviceAddressRegExp = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";

        if(measuringInterval_eT.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Invalid measuring interval!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(applicationID.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Invalid application ID!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!deviceAddress.getText().toString().matches(deviceAddressRegExp)) {
            Toast.makeText(getApplicationContext(),"Invalid device address!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(portNumber.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Invalid port number!",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
