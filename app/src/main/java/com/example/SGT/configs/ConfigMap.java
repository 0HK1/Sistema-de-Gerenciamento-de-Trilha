package com.example.SGT.configs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.SGT.MainActivity;
import com.example.SGT.R;

public class ConfigMap extends AppCompatActivity {
    private int typeFormat, typeDirection;
    private final String ARQUIVO_PREFERENCIA = "ArquivoPreference";
    ConfigVariables configVariables = new ConfigVariables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_map);

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /* Setando Botão de Retorno */

        Button buttonBack = findViewById(R.id.ButtonBackConfigToBeginning);
        buttonBack.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.back_icon),null, null,null);
        buttonBack.setCompoundDrawablePadding(0);
        buttonBack.setOnClickListener(view -> {
            Intent mainIntent = new Intent(ConfigMap.this, MainActivity.class);
            startActivity(mainIntent);
        });

        typeFormat = sharedPreferences.getInt("TypeMap", 0);
        RadioGroup radioGroupTypeFormat = findViewById(R.id.radioGroupTypeMap);

        if (typeFormat == 0 || typeFormat == R.id.radioButtonMapaVetorial){
            radioGroupTypeFormat.check(R.id.radioButtonMapaVetorial);
            configVariables.setTypeMap(0);
        }else {
            radioGroupTypeFormat.check(R.id.radioButtonMapaSatelite);
            configVariables.setTypeMap(1);
        }

        radioGroupTypeFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton buttonVetorial = findViewById(R.id.radioButtonMapaVetorial);
                RadioButton buttonSatelite = findViewById(R.id.radioButtonMapaSatelite);

                if (buttonVetorial.getId() == checkedId){
                    typeFormat = 0;
                    configVariables.setTypeMap(0);
                    editor.putInt("TypeMap", typeFormat);
                    editor.apply();
                    editor.commit();
                }
                if (buttonSatelite.getId() == checkedId){
                    typeFormat = 1;
                    configVariables.setTypeMap(1);
                    editor.putInt("TypeMap", typeFormat);
                    editor.apply();
                    editor.commit();
                }
            }
        });
        typeDirection = sharedPreferences.getInt("TypeDirection", 0);
        RadioGroup radioGroupDirectionFormat = findViewById(R.id.radioGroupFormatNavegation);
        if (typeDirection == 0 || typeDirection == R.id.radioButtonNorthUP){
            radioGroupDirectionFormat.check(R.id.radioButtonNorthUP);
            configVariables.setTypeDirection(0);
        }else {
            radioGroupDirectionFormat.check(R.id.radioButtonCourseUP);
            configVariables.setTypeDirection(1);
        }
        radioGroupDirectionFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton buttonNorthUp = findViewById(R.id.radioButtonNorthUP);
                RadioButton buttonCourseUp = findViewById(R.id.radioButtonCourseUP);
                if (buttonNorthUp.getId() == checkedId){
                    typeDirection = 0;
                    configVariables.setTypeDirection(0);
                    editor.putInt("TypeDirection", buttonNorthUp.getId());
                    editor.apply();
                    editor.commit();
                }
                if (buttonCourseUp.getId() == checkedId){
                    typeDirection = 1;
                    configVariables.setTypeDirection(1);
                    editor.putInt("TypeDirection", buttonCourseUp.getId());
                    editor.apply();
                    editor.commit();
                }
            }
        });
    }
}
