package com.example.SGT.ObterTrilha;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SGT.R;
import com.example.SGT.configs.ConfigVariables;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    ConfigVariables configVariables;
    private double latitude = 0;
    private double longitude = 0;
    private Marker marker;
    SharedPreferences sharedPreferences;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Carregar as preferências de mapa
            sharedPreferences = getContext().getSharedPreferences("ArquivoPreference", Context.MODE_PRIVATE);
            googleMap.getUiSettings().setScrollGesturesEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            if (sharedPreferences.getInt("TypeMap", 0) == 1) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Corrigido para NORMAL
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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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
                    LatLng updatedLocation = new LatLng(latitude, longitude);

                    if (marker != null) {
                        marker.remove();
                    }
                    marker = googleMap.addMarker(new MarkerOptions().position(updatedLocation).title("Localização Atualizada"));


                    if (sharedPreferences.getInt("TypeDirection", 0) == 0) {

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(updatedLocation, 15));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(updatedLocation)
                                .bearing(0) // Norte para cima
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
}
