package patrik.onlab_start;

import android.content.DialogInterface;
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
import com.commsignia.v2x.client.ITSEventAdapter;
import com.commsignia.v2x.client.MessageSet;
import com.commsignia.v2x.client.exception.ClientException;
import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.FacilitySubscriptionMessages;
import com.commsignia.v2x.client.model.LdmFilter;
import com.commsignia.v2x.client.model.LdmObject;
import com.commsignia.v2x.client.model.LdmObjectType;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import patrik.onlab_start.Model.DataCommunicator;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;

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

    //Adapters for Spinners
    ArrayAdapter<CharSequence> adapterDENM_Properties;
    ArrayAdapter<CharSequence> adapterCAM_Properties;
    ArrayAdapter<CharSequence> adapterMAP_Properties;
    ArrayAdapter<CharSequence> adapterSPAT_Properties;

    //EditTExt
    EditText measuringInterval_eT;

    //Timer
    Timer timer;

    //Selected Properties
    int firstForDENM;
    int secondForDENM;
    int thirdForDENM;

    int firstForCAM;
    int secondForCAM;
    int thirdForCAM;

    int firstForMAP;
    int secondForMAP;
    int thirdForMAP;

    int firstForSPAT;
    int secondForSPAT;
    int thirdForSPAT;

    String selected = "DENM";

    //GraphFragment properties
    static int messageCounter = 0;
    static double avarageSNR = 0;
    static double snrSum = 0;


    //ITS application properties
    public static ITSApplication itsApplication = null;
    public static final int DEFAULT_ITS_AID = 55;
    public static final String DEFAULT_TARGET_HOST = "192.168.2.90";
    public static final int DEFAULT_TARGET_PORT = 7942;
    public static final MessageSet DEFAULT_MESSAGE_SET = MessageSet.C;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the components
        componentInitialization();

        //Set the adapter settings for the spinners
        setAdaptersForSpinners();

        //Set approtiate listeners for the spinners
        spinnerListeners();


        if (listFragment == null)
            listFragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.messageFragment);

        //Set approtiate listeners for the buttons
        initializeButtonListeners();




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
    public void updateDataDetails(PacketAncestor data) {

        //Set the appropriate fragment
        if (messageDetailsFragment == null) {
            messageDetailsFragment = (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }

        //Call the MessageDetailsFragment appropriate method to update values
        messageDetailsFragment.changeData(data);
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
        if (graphFragment == null) {
            graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
        }
        graphFragment.updateDataFromActivity(count,avarageSNR);
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

                    firstPropertySpinner.setSelection(firstForDENM);
                    secondPropertySpinner.setSelection(secondForDENM);
                    thirdPropertySpinner.setSelection(thirdForDENM);
                    Log.d("ÉRTÉKEK: ", firstForDENM + " " + secondForDENM + " " + thirdForDENM);
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {

                    selected = "CAM";

                    firstPropertySpinner.setAdapter(adapterCAM_Properties);
                    secondPropertySpinner.setAdapter(adapterCAM_Properties);
                    thirdPropertySpinner.setAdapter(adapterCAM_Properties);

                    firstPropertySpinner.setSelection(firstForCAM);
                    secondPropertySpinner.setSelection(secondForCAM);
                    thirdPropertySpinner.setSelection(thirdForCAM);
                    Log.d("ÉRTÉKEK: ", firstForCAM + " " + secondForCAM + " " + thirdForCAM);
                }

                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {

                    selected = "MAP";

                    firstPropertySpinner.setAdapter(adapterMAP_Properties);
                    secondPropertySpinner.setAdapter(adapterMAP_Properties);
                    thirdPropertySpinner.setAdapter(adapterMAP_Properties);

                    firstPropertySpinner.setSelection(firstForMAP);
                    secondPropertySpinner.setSelection(secondForMAP);
                    thirdPropertySpinner.setSelection(thirdForMAP);

                    Log.d("ÉRTÉKEK: ", firstForMAP + " " + secondForMAP + " " + thirdForMAP);
                }

                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {

                    selected = "SPAT";

                    firstPropertySpinner.setAdapter(adapterSPAT_Properties);
                    secondPropertySpinner.setAdapter(adapterSPAT_Properties);
                    thirdPropertySpinner.setAdapter(adapterSPAT_Properties);

                    firstPropertySpinner.setSelection(firstForSPAT);
                    secondPropertySpinner.setSelection(secondForSPAT);
                    thirdPropertySpinner.setSelection(thirdForSPAT);

                    Log.d("ÉRTÉKEK: ", firstForSPAT + " " + secondForSPAT + " " + thirdForSPAT);
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
                    firstForDENM = firstPropertySpinner.getSelectedItemPosition();
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    firstForCAM = firstPropertySpinner.getSelectedItemPosition();
                }
                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    firstForMAP = firstPropertySpinner.getSelectedItemPosition();
                }
                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    firstForSPAT = firstPropertySpinner.getSelectedItemPosition();
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
                    secondForDENM = secondPropertySpinner.getSelectedItemPosition();
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    secondForCAM = secondPropertySpinner.getSelectedItemPosition();
                }

                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    secondForMAP = secondPropertySpinner.getSelectedItemPosition();
                }

                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    secondForSPAT = secondPropertySpinner.getSelectedItemPosition();
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
                    thirdForDENM=thirdPropertySpinner.getSelectedItemPosition();
                }

                if(typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    thirdForCAM=thirdPropertySpinner.getSelectedItemPosition();
                }

                if(typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    thirdForMAP=thirdPropertySpinner.getSelectedItemPosition();
                }

                if(typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    thirdForSPAT=thirdPropertySpinner.getSelectedItemPosition();
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

        firstPropertySpinner.setAdapter(adapterDENM_Properties);
        secondPropertySpinner.setAdapter(adapterDENM_Properties);
        thirdPropertySpinner.setAdapter(adapterDENM_Properties);
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

        measuringInterval_eT = (EditText) findViewById(R.id.eTsetInterval);

        stopButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);

        //Set the messageFragment invisible
        FrameLayout messageFrame = (FrameLayout) findViewById(R.id.messageFrame);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 0);
        messageFrame.setLayoutParams(params);

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

                if(measuringInterval_eT.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Please set a measuring interval",Toast.LENGTH_LONG).show();
                    return;
                }

                //Set invisible the spinnerLayout
                final LinearLayout spinnerLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
                LinearLayout.LayoutParams paramsSpinnerLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 0);
                spinnerLayout.setLayoutParams(paramsSpinnerLayout);
                spinnerLayout.setVisibility(View.INVISIBLE);

                //Set larger the messageFragment to fill the spinnerLayout field
                FrameLayout messageFrame = (FrameLayout) findViewById(R.id.messageFrame);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 9);
                messageFrame.setLayoutParams(params);

                String[] denmProperties = getResources().getStringArray(R.array.denmProperties);
                String[] camProperties = getResources().getStringArray(R.array.camProperties);
                String[] mapProperties = getResources().getStringArray(R.array.mapProperties);
                String[] spatProperties = getResources().getStringArray(R.array.spatProperties);

                //Pass the adjested spinner values
                listFragment.startPacketCapturing(denmProperties[firstForDENM], denmProperties[secondForDENM], denmProperties[thirdForDENM],
                        camProperties[firstForCAM], camProperties[secondForCAM], camProperties[thirdForCAM],
                        mapProperties[firstForMAP], mapProperties[secondForMAP], mapProperties[thirdForMAP],
                        spatProperties[firstForSPAT], spatProperties[secondForSPAT], spatProperties[thirdForSPAT]);


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


                //Start the ITS Application
                try {
                    itsApplication = new ITSApplication(DEFAULT_ITS_AID, DEFAULT_TARGET_HOST, DEFAULT_TARGET_PORT, DEFAULT_MESSAGE_SET);

                    itsApplication.connect(10000);

                    itsApplication.registerBlocking();

                    itsApplication.setDeviceTimeBlocking(System.currentTimeMillis() / 1000L);

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
                                            messageCounter++;
                                            snrSum += facilityNotification.getRssi();
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
                                            messageCounter++;
                                            snrSum += ldmObject.getRssiDbm();
                                        }
                                        Log.d("Ldm received", ldmObject.getNotificationType().toString());
                                    }
                                });
                            }
                        });


                        finalItsApplication.facilitySubscribe(new FacilitySubscriptionMessages().setCamIncluded(true).setDenmIncluded(true));
                        finalItsApplication.ldmSubscribeBlocking(
                                new LdmFilter().setObjectTypeFilter(LdmObjectType.MAP, LdmObjectType.SPAT)
                        );

                    } catch (Exception e) {
                        Log.d("ClientException: ", "Cannot execute API commands");
                    }


                } catch (InterruptedException | ClientException | TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    if (itsApplication != null)
                        Log.d("Request", "was sent.");
                }

                startButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                restartButton.setVisibility(View.VISIBLE);
            }
        });

        //Stop the graph thread
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (graphFragment == null) {
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                }
                graphFragment.onPause();

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
                if (graphFragment == null) {
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                }
                graphFragment.onPause();
                graphFragment.clearDatas();

                if(messageDetailsFragment ==null)
                    messageDetailsFragment= (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
                messageDetailsFragment.clearDetails();

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

}
