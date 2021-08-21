package com.example.weatherhistory.ui.historicalsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherhistory.R;
import com.example.weatherhistory.ui.stations.StationFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {

    MapView mMapView;
    GoogleMap mMap;
    LatLng unitedKingdom = new LatLng(54.351544,-2.4490735);//Latitude and Longitude to center map on UK


    /**
     * As fragment is inflated MMapView is assigned to the Layout ID
     * then initialized using the API key stored in the manifest xml
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        mMapView = root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                createMarkers();
                CameraPosition cameraPosition = new CameraPosition.Builder().target(unitedKingdom).zoom(5.5f).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));//sets map position and zoom level to view of UK and updates the map view

            }
        });

        return root;
    }

    /**
     * creates map markers used to select weather stations using their Lat Lon to plot their position on the map
     * each marker calls to the addMarkerToMap with all attributes each marker will need
     */
    public void createMarkers(){
        addMarkerToMap(51.092371,-4.1433202,"Chivenor", "Click here for Historical Data");
        addMarkerToMap(57.006,-3.396,"Braemar","Click here for Historical Data");
    }

    public void addMarkerToMap(Double latCord, Double longCord, String t_title, String t_description)
    {
        // create marker
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(latCord, longCord))//position of the marker
                .title(t_title)//name to be displayed when clicked
                .snippet(t_description);//description to be displayed when clicked


        // Add marker to map
        mMap.addMarker(marker);
        // Adds onInfoWindowClickLstener to each marker created
        mMap.setOnInfoWindowClickListener(this);
    }

    /**
     * onInfoWindowClick will display a small box on top of the marker
     * this displays the title and descripton of the marker
     * when this infowindow is clicked it will launch StationFragment with
     * the historical data for this marker
     * @param marker passes the position, title and description of the marker to the info window
     */
    @Override
    public void onInfoWindowClick (Marker marker) {
        String mMarker = marker.getTitle().toLowerCase();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        StationFragment station = new StationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Station",mMarker);
        station.setArguments(bundle);
        ft.replace(R.id.search_window, station);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

    }
}