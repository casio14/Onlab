package patrik.onlab_start.NavigationBoard.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.commsignia.v2x.client.model.FacilityNotification;
import com.commsignia.v2x.client.model.LdmObject;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import patrik.onlab_start.CarListAdapter;
import patrik.onlab_start.MessageAdapter;
import patrik.onlab_start.Model.NotificationType;
import patrik.onlab_start.Model.PacketAncestor;
import patrik.onlab_start.R;

/**
 * Created by kisspatrik on 2017.08.01..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;

    private Button changeViewButton;

    CarListAdapter adapter;
    ListView carList;

    String followedCar="";

    boolean followCar = false;

    HashMap<Long, LatLng> bsmIDs;

    private Timer clearTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        bsmIDs = new HashMap<>();

        changeViewButton = (Button) view.findViewById(R.id.followButton);

        changeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewButton.getText().toString().equals("Follow")) {
                    followCar=true;
                    changeViewButton.setText("Manual");
                }
                else  if(changeViewButton.getText().toString().equals("Manual")) {
                    followCar=false;
                    changeViewButton.setText("Follow");
                }
            }
        });

        carList = (ListView) view.findViewById(R.id.carListView);

        List<String> stringList = new ArrayList<>();
        stringList.add("11");
        stringList.add("12");

        adapter = new CarListAdapter(getActivity(),android.R.layout.simple_list_item_1,stringList);
        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                followedCar = adapter.getItem(i);

                LatLng latLng;
                latLng = bsmIDs.get(Long.valueOf(followedCar));

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);

                map.animateCamera(cameraUpdate, 1, null);
                System.out.println("Camera update");
            }
        });
        carList.setAdapter(adapter);

        clearIdMapPeriodicly(5);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
    }

    public void setMarktoCarPosition(PacketAncestor packetAncestor) {

        boolean isLdm = false;
        boolean isFac = false;
        LdmObject ldmObject = null;
        FacilityNotification facilityNotification = null;

        if(packetAncestor.getNotificationType() == NotificationType.FAC_NOTIFICATION) {
                isFac=true;
                facilityNotification = (FacilityNotification) packetAncestor.getObject();
        }
        else if(packetAncestor.getNotificationType() == NotificationType.LDM_NOTIFICATION) {
            isLdm=true;
            ldmObject = (LdmObject) packetAncestor.getObject();
        }

        if(isLdm && !ldmObject.isLocal()) {
            if (bsmIDs.get(ldmObject.getObjectID()) != null) {
                bsmIDs.remove(ldmObject.getObjectID());
                bsmIDs.put(ldmObject.getObjectID(),new LatLng(ldmObject.getLatitude() * 0.0000001, ldmObject.getLongitude() * 0.0000001));
            } else {
                bsmIDs.put(ldmObject.getObjectID(), new LatLng(ldmObject.getLatitude() * 0.0000001, ldmObject.getLongitude() * 0.0000001));
            }
        }

        else if(isFac) {
            if (bsmIDs.get(facilityNotification.getStationObject().getStationId()) != null) {
                bsmIDs.remove(facilityNotification.getStationObject().getStationId());
                bsmIDs.put(facilityNotification.getStationObject().getStationId(),new LatLng(facilityNotification.getLatitude() * 0.0000001, facilityNotification.getLongitude() * 0.0000001));
            } else {
                bsmIDs.put(facilityNotification.getStationObject().getStationId(), new LatLng(facilityNotification.getLatitude() * 0.0000001, facilityNotification.getLongitude() * 0.0000001));
            }
        }

        adapter.clear();
        map.clear();
        for (Map.Entry<Long, LatLng> entry : bsmIDs.entrySet()) {
            Long id = entry.getKey();
            LatLng position = entry.getValue();

            adapter.addCarID(id.toString());

            map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(id.toString())
                    .icon(BitmapDescriptorFactory.defaultMarker()));
        }

        if(isLdm && ldmObject.isLocal()) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(ldmObject.getLatitude() * 0.0000001, ldmObject.getLongitude() * 0.0000001))
                    .title(ldmObject.getObjectID().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            System.out.println("LOCAL MAPPED");
        }


        if(followCar) {
            LatLng latLng=null;
            if(!followedCar.equals("")) {
                latLng = bsmIDs.get(Long.valueOf(followedCar));
            }
            else {
                if(isLdm) {
                    latLng = new LatLng(ldmObject.getLatitude() * 0.0000001, ldmObject.getLongitude() * 0.0000001);
                }
                else if (isFac) {
                    latLng = new LatLng(facilityNotification.getLatitude()* 0.0000001, facilityNotification.getLongitude() * 0.0000001);
                }
            }
            if(latLng!=null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);

                map.animateCamera(cameraUpdate, 1, null);
            }
        }



    }

    public boolean isNull() {
        return map == null ? true : false;
    }

    private void clearIdMapPeriodicly(int seconds) {
        clearTimer = new Timer();
        clearTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bsmIDs.clear();
                            adapter.clear();
                            if (map != null)
                                map.clear();
                        }
                    });
                }
            }
            }
         ,0, seconds*1000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    public void stopClearing() {
        clearTimer.cancel();
    }
}
