package patrik.onlab_start.NavigationBoard.fragments;

import android.os.Bundle;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import patrik.onlab_start.R;

/**
 * Created by kisspatrik on 2017.08.01..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;

    boolean followCar = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        System.out.print("");
    }

    public void setMarktoCarPosition(long latitude, long longitude) {
        System.out.println(latitude * 0.0000001 + "    " + longitude * 0.0000001);
            map.clear();
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude * 0.0000001, longitude * 0.0000001))
                    .title("Hello world"));


        if(followCar) {
            LatLng latLng = new LatLng(latitude * 0.0000001, longitude * 0.0000001);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);

            map.animateCamera(cameraUpdate, 1, null);
            System.out.println("Camera update");
        }



    }

    public boolean isNull() {
        return map == null ? true : false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
