package com.example.SGT.VisualizarTrilha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.SGT.MainActivity;
import com.example.SGT.R;
import com.example.SGT.Waypoint;
import com.example.SGT.configs.ConfigMap;
import com.example.SGT.ObterTrilha.TrilhasDB;

import java.util.ArrayList;

public class VisualizarTrilhaActivity extends AppCompatActivity {

    private TrilhasDB trilhadb;
    private ArrayList<Waypoint> waypoints;
    MapsFragment mapsFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_trilha);

        trilhadb = new TrilhasDB(this);
        waypoints = trilhadb.recuperarWaypoints();

        String dados = "Nenhum waypoint encontrado.";

        if (!waypoints.isEmpty()) {

            Waypoint ultimoWaypoint = waypoints.get(waypoints.size() - 1);
            Waypoint data = waypoints.get(1);

            double totalDistancia = ultimoWaypoint.getDistancia();
            double ultimaVelocidadeMedia = ultimoWaypoint.getVelocidadeMedia();
            String dataWaypoint = data.getDateString();


            dados = String.format("Data: %s\nDistância Total: %.2f metros\nVelocidade Média: %.2f km/h",
                    dataWaypoint, totalDistancia, ultimaVelocidadeMedia);
        }

        TextView textViewDados = findViewById(R.id.textViewDados);
        textViewDados.setText(dados);

        Button buttonBack = findViewById(R.id.buttonBackVTtoMain);
        buttonBack.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.back_icon), null, null, null);
        buttonBack.setCompoundDrawablePadding(0);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapsFragment.deletarCirculo();
                Intent backToMainIntent = new Intent(VisualizarTrilhaActivity.this, MainActivity.class);
                startActivity(backToMainIntent);

            }
        });

        Button buttonConfig = findViewById(R.id.buttonAVTtoConfig);
        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goConfig = new Intent(VisualizarTrilhaActivity.this, ConfigMap.class);
                startActivity(goConfig);
            }
        });

        MapsFragment mapsFragment = new MapsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerViewMaps, mapsFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (trilhadb != null) {
            trilhadb.close();
        }
    }
}
