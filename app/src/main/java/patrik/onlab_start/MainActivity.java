package patrik.onlab_start;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.commsignia.v2x.client.ITSApplication;

import com.commsignia.v2x.client.MessageSet;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import patrik.onlab_start.Model.DataCommunicator;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.Model.PacketCommunicator;
import patrik.onlab_start.NavigationBoard.fragments.GraphFragment;

public class MainActivity extends AppCompatActivity {

    GraphFragment loadGraphFragment;

    //Buttons
    Button startButton;
    Button loadButton;

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

    //Selected Properties
    Map<String,Integer> selectedPropertiesPositions;
    HashMap<String,String> selectedPropertiesValues;

    String selected = "DENM";

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

        //Set approtiate listeners for the buttons
        initializeButtonListeners();

        AndroidThreeTen.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
            LinearLayout.LayoutParams paramsMainLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, 0);
            mainLayout.setLayoutParams(paramsMainLayout);
            mainLayout.setVisibility(View.VISIBLE);
            final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
            LinearLayout.LayoutParams paramsFrameLayout = new LinearLayout.LayoutParams(0, 0, 0);
            frameLayout.setLayoutParams(paramsFrameLayout);
            frameLayout.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    public void showLoadAlertDialog() {

        final int[] lastClickedIndex = {-1};

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Choose a file");

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.load_window_layout, null);
        final TextView choosenFile = (TextView) view.findViewById(R.id.load_tvChoosen);
        choosenFile.setText("-");
        File dir = new File(Environment.getExternalStorageDirectory().getPath().toString(), "V2XPacketAnalyzer"); //get folder
        if(!dir.exists()) {
            Toast.makeText(getApplicationContext(),"Directory doesn't exists.",Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(dir.toString());
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
                if (loadGraphFragment == null) {
                    loadGraphFragment = (GraphFragment) getSupportFragmentManager().findFragmentById(R.id.loadGraphFragment);
                }
                try {
                    if (!choosenFile.getText().toString().equals("-"))
                        loadGraphFragment.loadDatas(choosenFile.getText().toString());
                        //Set the spinnerLayout larger


                    final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
                    LinearLayout.LayoutParams paramsMainLayout = new LinearLayout.LayoutParams(0, 0, 0);
                    mainLayout.setLayoutParams(paramsMainLayout);
                    mainLayout.setVisibility(View.INVISIBLE);
                    final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
                    LinearLayout.LayoutParams paramsFrameLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0);
                    frameLayout.setLayoutParams(paramsFrameLayout);
                    frameLayout.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Load failed",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        alertbox.setView(view);
        alertbox.show();
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
                    //selectedPropertiesPositions.replace("DENM_1",firstPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("DENM_1");
                    selectedPropertiesPositions.put("DENM_1",firstPropertySpinner.getSelectedItemPosition());

                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    //selectedPropertiesPositions.replace("CAM_1",firstPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("CAM_1");
                    selectedPropertiesPositions.put("CAM_1",secondPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    //selectedPropertiesPositions.replace("MAP_1",firstPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("MAP_1");
                    selectedPropertiesPositions.put("MAP_1",secondPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    //selectedPropertiesPositions.replace("SPAT_1",firstPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("SPAT_1");
                    selectedPropertiesPositions.put("SPAT_1",secondPropertySpinner.getSelectedItemPosition());
                }
                if (typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    //selectedPropertiesPositions.replace("BSM_1",firstPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("BSM_1");
                    selectedPropertiesPositions.put("BSM_1",secondPropertySpinner.getSelectedItemPosition());
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
                    //selectedPropertiesPositions.replace("DENM_2",secondPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("DENM_2");
                    selectedPropertiesPositions.put("DENM_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    //selectedPropertiesPositions.replace("CAM_2",secondPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("CAM_2");
                    selectedPropertiesPositions.put("CAM_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    //selectedPropertiesPositions.replace("MAP_2",secondPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("MAP_2");
                    selectedPropertiesPositions.put("MAP_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    //selectedPropertiesPositions.replace("SPAT_2",secondPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("SPAT_2");
                    selectedPropertiesPositions.put("SPAT_2",secondPropertySpinner.getSelectedItemPosition());
                }

                if (typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    //selectedPropertiesPositions.replace("BSM_2",secondPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("BSM_2");
                    selectedPropertiesPositions.put("BSM_2",secondPropertySpinner.getSelectedItemPosition());
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
                    //selectedPropertiesPositions.replace("DENM_3",thirdPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("DENM_3");
                    selectedPropertiesPositions.put("DENM_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("CAM")) {
                    //selectedPropertiesPositions.replace("CAM_3",thirdPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("CAM_3");
                    selectedPropertiesPositions.put("CAM_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("MAP")) {
                    //selectedPropertiesPositions.replace("MAP_3",thirdPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("MAP_3");
                    selectedPropertiesPositions.put("MAP_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("SPAT")) {
                    //selectedPropertiesPositions.replace("SPAT_3",thirdPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("SPAT_3");
                    selectedPropertiesPositions.put("SPAT_3",thirdPropertySpinner.getSelectedItemPosition());
                }

                if(typeSpinner.getSelectedItem().toString().equals("BSM")) {
                    //selectedPropertiesPositions.replace("BSM_3",thirdPropertySpinner.getSelectedItemPosition());
                    selectedPropertiesPositions.remove("BSM_3");
                    selectedPropertiesPositions.put("BSM_3",thirdPropertySpinner.getSelectedItemPosition());
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
        loadButton = (Button) findViewById(R.id.loadButton);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        firstPropertySpinner = (Spinner) findViewById(R.id.firstPropertySpinner);
        secondPropertySpinner = (Spinner) findViewById(R.id.secondPropertySpinner);
        thirdPropertySpinner = (Spinner) findViewById(R.id.thirdPropertySpinner);
        messageSetsSpinner = (Spinner) findViewById(R.id.messageSetsSpinner);

        measuringInterval_eT = (EditText) findViewById(R.id.eTsetInterval);
        applicationID = (EditText) findViewById(R.id.et_appID);
        deviceAddress = (EditText) findViewById(R.id.et_deviceAddress);
        portNumber = (EditText) findViewById(R.id.et_portNumber);

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

                Intent intent = new Intent(getApplicationContext(),BoardActivity.class);
                intent.putExtra("selectedValues",selectedPropertiesValues);
                intent.putExtra("applicationID",applicationID.getText().toString());
                intent.putExtra("deviceAddress",deviceAddress.getText().toString());
                intent.putExtra("portNumber",portNumber.getText().toString());
                intent.putExtra("messageSet",messageSetsSpinner.getSelectedItem().toString());
                intent.putExtra("interval",measuringInterval_eT.getText().toString());
                startActivity(intent);
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadAlertDialog();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
    }

    public void initializeSelectedPropertiesHashMap() {
        selectedPropertiesPositions = new HashMap<>();
        selectedPropertiesPositions.put("DENM_1",0);
        selectedPropertiesPositions.put("DENM_2",1);
        selectedPropertiesPositions.put("DENM_3",2);
        selectedPropertiesPositions.put("CAM_1",0);
        selectedPropertiesPositions.put("CAM_2",1);
        selectedPropertiesPositions.put("CAM_3",2);
        selectedPropertiesPositions.put("MAP_1",0);
        selectedPropertiesPositions.put("MAP_2",1);
        selectedPropertiesPositions.put("MAP_3",2);
        selectedPropertiesPositions.put("SPAT_1",0);
        selectedPropertiesPositions.put("SPAT_2",1);
        selectedPropertiesPositions.put("SPAT_3",2);
        selectedPropertiesPositions.put("BSM_1",0);
        selectedPropertiesPositions.put("BSM_2",1);
        selectedPropertiesPositions.put("BSM_3",2);

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
        selectedPropertiesValues.remove("DENM_1"); selectedPropertiesValues.put("DENM_1",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_1")).toString());
        selectedPropertiesValues.remove("DENM_2"); selectedPropertiesValues.put("DENM_2",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_2")).toString());
        selectedPropertiesValues.remove("DENM_3"); selectedPropertiesValues.put("DENM_3",adapterDENM_Properties.getItem(selectedPropertiesPositions.get("DENM_3")).toString());

        selectedPropertiesValues.remove("CAM_1"); selectedPropertiesValues.put("CAM_1",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_1")).toString());
        selectedPropertiesValues.remove("CAM_2"); selectedPropertiesValues.put("CAM_2",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_2")).toString());
        selectedPropertiesValues.remove("CAM_3"); selectedPropertiesValues.put("CAM_3",adapterCAM_Properties.getItem(selectedPropertiesPositions.get("CAM_3")).toString());

        selectedPropertiesValues.remove("MAP_1"); selectedPropertiesValues.put("MAP_1",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_1")).toString());
        selectedPropertiesValues.remove("MAP_2"); selectedPropertiesValues.put("MAP_2",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_2")).toString());
        selectedPropertiesValues.remove("MAP_3"); selectedPropertiesValues.put("MAP_3",adapterMAP_Properties.getItem(selectedPropertiesPositions.get("MAP_3")).toString());

        selectedPropertiesValues.remove("SPAT_1"); selectedPropertiesValues.put("SPAT_1",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_1")).toString());
        selectedPropertiesValues.remove("SPAT_2"); selectedPropertiesValues.put("SPAT_2",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_2")).toString());
        selectedPropertiesValues.remove("SPAT_3"); selectedPropertiesValues.put("SPAT_3",adapterSPAT_Properties.getItem(selectedPropertiesPositions.get("SPAT_3")).toString());

        selectedPropertiesValues.remove("BSM_1"); selectedPropertiesValues.put("BSM_1",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_1")).toString());
        selectedPropertiesValues.remove("BSM_2"); selectedPropertiesValues.put("BSM_2",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_2")).toString());
        selectedPropertiesValues.remove("BSM_3"); selectedPropertiesValues.put("BSM_3",adapterBSM_Properties.getItem(selectedPropertiesPositions.get("BSM_3")).toString());
    }

    public boolean validateEditTexts() {
        String deviceAddressRegExp = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";

        if(measuringInterval_eT.getText().toString().equals("") || !measuringInterval_eT.getText().toString().matches("\\d{1,5}")) {
            Snackbar.make(getCurrentFocus(),"Invalid measuring interval!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(applicationID.getText().toString().equals("") || !applicationID.getText().toString().matches("\\d{1,10}")) {
            Snackbar.make(getCurrentFocus(),"Invalid application ID!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(!deviceAddress.getText().toString().matches(deviceAddressRegExp)) {
            Snackbar.make(getCurrentFocus(),"Invalid device address!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(portNumber.getText().toString().equals("") || !portNumber.getText().toString().matches("\\d{1,5}")) {
            Snackbar.make(getCurrentFocus(),"Invalid port number!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
