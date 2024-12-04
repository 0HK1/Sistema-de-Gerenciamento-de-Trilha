package com.example.SGT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.SGT.ObterTrilha.GetRouteActivity;
import com.example.SGT.VisualizarTrilha.*;
import com.example.SGT.configs.ConfigMap;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getRoutebutton=(Button) findViewById(R.id.getRoutebutton);
        getRoutebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this, GetRouteActivity.class);

                startActivity(i);
            }
        });
        Button configButton = (Button) findViewById(R.id.configButton);
        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent configMapActivity=new Intent(MainActivity.this, ConfigMap.class);
                startActivity(configMapActivity);
            }
        });
        Button visualizarTrilhaButton = (Button) findViewById(R.id.buttonAbrirVisualizarTrilha);
        visualizarTrilhaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent visualTrilhaActivity = new Intent(MainActivity.this, VisualizarTrilhaActivity.class);
                startActivity(visualTrilhaActivity);
            }
        });
    }
}