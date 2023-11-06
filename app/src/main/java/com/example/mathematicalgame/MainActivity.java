package com.example.mathematicalgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button StartSpill = (Button) findViewById(R.id.StartSpill);
        Button Preferanser = (Button) findViewById(R.id.preferanser);
        Button OmSpill = (Button) findViewById(R.id.omspill);



        Intent StartSpillet = new Intent(this, Spill.class);

        StartSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StartSpillet);
            }
        });


        Intent preferansene = new Intent(this, Preferanser.class);

        Preferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(preferansene);

            }
        });

        Intent omspill123 = new Intent(this, OmSpillet.class);

        OmSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(omspill123);

            }
        });



    }







}