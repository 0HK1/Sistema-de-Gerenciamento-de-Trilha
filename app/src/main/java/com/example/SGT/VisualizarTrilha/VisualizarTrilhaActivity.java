package com.example.SGT.VisualizarTrilha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.SGT.MainActivity;
import com.example.SGT.R;

public class VisualizarTrilhaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_trilha);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Button buttonBack = findViewById(R.id.buttonBackVTtoMain);
        buttonBack.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.back_icon),null, null,null);
        buttonBack.setCompoundDrawablePadding(0);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainIntent = new Intent(VisualizarTrilhaActivity.this, MainActivity.class);
                startActivity(backToMainIntent);
            }
        });


        MapsFragment mapsFragment = new MapsFragment();
        fragmentTransaction.add(R.id.fragmentContainerViewMaps, mapsFragment);
        fragmentTransaction.commit();
    }
}
