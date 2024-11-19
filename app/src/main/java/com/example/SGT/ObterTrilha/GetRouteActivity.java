package com.example.SGT.ObterTrilha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SGT.MainActivity;
import com.example.SGT.R;
import com.example.SGT.Waypoint;
import com.example.SGT.configs.ConfigMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetRouteActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_UPDATES = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private TrilhasDB trilhadb;
    private Location lastLocation = null;
    private float totalDistance = 0;
    private int contador = 0;
    private Date dataInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_route);

        Button startbutton = findViewById(R.id.StartStopbutton);
        Button buttonBack = findViewById(R.id.ButtonBackGetRouteToBeginning);
        Button buttonConfig = findViewById(R.id.ButtonGoToConfig);

        buttonBack.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        buttonConfig.setOnClickListener(view -> startActivity(new Intent(this, ConfigMap.class)));

        startbutton.setOnClickListener(view -> {
            if ("Start".equals(startbutton.getText().toString())) {
                trilhadb = new TrilhasDB(this);
                trilhadb.apagaTrilha();
                startLocationUpdates();
                contador = 0;
                totalDistance = 0;
                startbutton.setText("Stop");
            } else {
                stopLocationUpdates();
                trilhadb.close();
                startbutton.setText("Start");
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            mLocationRequest = new LocationRequest.Builder(1000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .build();
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null && locationResult.getLastLocation() != null) {
                        Location location = locationResult.getLastLocation();
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        addWayPoint(location);
                    } else {
                        Toast.makeText(GetRouteActivity.this, "Localização indisponível", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            dataInicio = new Date(); // Registra a data e hora atual
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_UPDATES);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_UPDATES && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
        }
    }

    private void addWayPoint(Location location) {
        if (lastLocation != null) {
            totalDistance += lastLocation.distanceTo(location);
        }
        lastLocation = location;


        TextView textViewDistancia = findViewById(R.id.textViewDistancia);
        float speedInKmh = location.getSpeed() * 3.6f;
        textViewDistancia.setText(String.format("Distância: %.2f metros \nVelocidade: %.2f km/h \nContador: %d seg",
                totalDistance, speedInKmh, contador));


        Waypoint waypoint = new Waypoint(location);
        waypoint.setDistancia(totalDistance);
        waypoint.setVelocidadeMedia(speedInKmh);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = sdf.format(new Date());
        waypoint.setDateString(dateFormatted);
        trilhadb.registrarWaypoint(waypoint);

        contador++;
    }

    private void stopLocationUpdates() {
        if (mFusedLocationProviderClient != null) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }
}
