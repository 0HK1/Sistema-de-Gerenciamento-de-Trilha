package com.example.SGT.VisualizarTrilha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.SGT.ObterTrilha.TrilhasDB;
import com.example.SGT.R;
import com.example.SGT.Waypoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {
    private double latitude = 0;
    private double longitude = 0;
    SharedPreferences sharedPreferences;
    private TrilhasDB trilhadb;
    private ArrayList<Waypoint> waypoints;
    private GoogleMap googleMap;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            trilhadb = new TrilhasDB(requireContext());
            waypoints = trilhadb.recuperarWaypoints();

            if (getContext() != null) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("ArquivoPreference", Context.MODE_PRIVATE);

                if (sharedPreferences != null) {
                    if (sharedPreferences.getInt("TypeMap", 0) == 1) {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    } else {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    }
                }
            } else {
                Toast.makeText(getContext(), "Contexto não disponível!", Toast.LENGTH_SHORT).show();
            }

            //googleMap.getUiSettings().setAllGesturesEnabled(false);


            if (waypoints != null && !waypoints.isEmpty()) {
                for (Waypoint waypoint : waypoints) {
                    if (waypoint.getLatitude() != 0 && waypoint.getLongitude() != 0) { // Verifica valores válidos
                        LatLng position = new LatLng(waypoint.getLatitude(), waypoint.getLongitude());
                        googleMap.addCircle(new CircleOptions()
                                .center(position)
                                .radius(10)
                                .strokeWidth(2)
                                .strokeColor(0xFF6750A4)
                                .fillColor(0x556750A4));
                    }
                }
            }

            updateLocation(googleMap);
        }


    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void updateLocation(GoogleMap googleMap) {
        try {

            LocationManager locationManager = (LocationManager) requireContext().getSystemService(getContext().LOCATION_SERVICE);

            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    location.getBearing();

                    LatLng updatedLocation = new LatLng(latitude, longitude);

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updatedLocation, 15));
                    sharedPreferences = getContext().getSharedPreferences("ArquivoPreference", Context.MODE_PRIVATE);
                    if (sharedPreferences.getInt("TypeDirection", 0) == 0) {

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updatedLocation, 15));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(updatedLocation)
                                .bearing(0)
                                .zoom(15)
                                .build()));
                    } else {

                        float bearing = location.getBearing();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updatedLocation, 15));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(updatedLocation)
                                .bearing(bearing)
                                .zoom(15)
                                .build()));
                    }
                } else {
                    Toast.makeText(getContext(), "Não foi possível obter a localização atual.", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (SecurityException e) {
            Toast.makeText(getContext(), "Permissões de localização necessárias!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deletarCirculo(){
        googleMap.clear();
    }

}