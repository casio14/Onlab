package patrik.onlab_start;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import patrik.onlab_start.Model.Packet;

public class MainActivity extends AppCompatActivity implements PacketCommunicator {

    MessageDetailsFragment messageDetailsFragment;
    GraphFragment graphFragment;

    Button startButton;
    Button stopButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Components initialization
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        //Start the graph thread
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(graphFragment==null)
                    graphFragment = (GraphFragment)getFragmentManager().findFragmentById(R.id.graphFragment);
                graphFragment.onResume();
            }
        });

        //Stop the graph thread
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(graphFragment==null)
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                graphFragment.onPause();
                //graphFragment.saveDatas(getApplicationContext());
                showSaveAlertDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.load_settings:
                showLoadAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateData(Packet data) {

        //Set the appropriate fragment
        if(messageDetailsFragment==null) {
            messageDetailsFragment = (MessageDetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }
        //Call the MessageDetailsFragment appropriate method to update values
        messageDetailsFragment.changeData(data);
    }

    public void showSaveAlertDialog() {

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.save_window_layout,null);

        final EditText et = (EditText) view.findViewById(R.id.save_etFileName);
        alertbox.setView(view);
        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if(graphFragment==null)
                        graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                    graphFragment.saveDatas(getApplicationContext(),et.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alertbox.show();
    }

    public void showLoadAlertDialog() {

        final int[] lastClickedIndex = {-1};

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Choose a file");

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.load_window_layout,null);
        final TextView choosenFile = (TextView) view.findViewById(R.id.load_tvChoosen);
        choosenFile.setText("-");

        final File[] files = getFilesDir().listFiles();
        final String[] names = new String[files.length];
        for(int i=0;i<files.length;i++) {
            names[i]=files[i].getName();
        }

        ListView lv = (ListView) view.findViewById(R.id.load_listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastClickedIndex[0] =i;
                choosenFile.setText(names[i]);
            }
        });

        lv.setAdapter(adapter);

        alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(graphFragment==null)
                    graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graphFragment);
                try {
                    if(!choosenFile.getText().toString().equals("-"))
                        graphFragment.loadDatas(getApplicationContext(), choosenFile.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        alertbox.setView(view);
        alertbox.show();
    }
}
