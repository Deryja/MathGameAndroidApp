package com.example.mathematicalgame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

public class Preferanser extends AppCompatActivity {

    private SharedPreferences preferanser;







   //Metode for å lagre preferansen av antall spørsmål som skal stilles i matte spillet (preferansen velges i preferences)

    public void selectQuestions(int selectedOption) {

        //Lagrer alternativet i SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selectedOption", selectedOption);
        editor.apply();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);

        Button knapp5 = findViewById(R.id.knapp5);
        Button knapp10 = findViewById(R.id.knapp10);
        Button knapp15 = findViewById(R.id.knapp15);


        knapp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectQuestions(5); //Kaller selectQuestions med alternativ 5 --> vil si at kun 5 items tas ut av array random
            }
        });

        knapp10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectQuestions(10); // Kaller selectQuestions med alternativ 10 --> vil si at kun 10 items tas ut av array random
            }
        });

        knapp15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectQuestions(15); //Kaller selectQuestions med alternativ 15 --> vil si at kun 15 items tas ut av array random
            }
        });









//Knapper og alternativ for valg av språk

        Button KnappforNorsk = (Button) findViewById(R.id.norsk);
        Button KnappforTysk = (Button) findViewById(R.id.tysk);


        //For å hente fram sharedPreferences variabelen på toppen (innebygd funksjon)
   preferanser = PreferenceManager.getDefaultSharedPreferences(this);





   //Knapp for å skifte til norsk
   KnappforNorsk.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           String valgtSpråk = preferanser.getString("SpråkPreferanser", "no-NO");


           LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(valgtSpråk);
           AppCompatDelegate.setApplicationLocales(appLocale);
       }



   });



   //Knapp for å skifte til tysk
   KnappforTysk.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {


           LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("de-DE");
           AppCompatDelegate.setApplicationLocales(appLocale);

       }
   });









    }
}
