package com.example.mathematicalgame;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Random;

public class Spill extends AppCompatActivity implements
        MinDialog.MittInterface, MinDialog2.MittInterface{
    @Override
    public void onYesClick() {
        finish();
    }

    @Override
    public void onNoClick() {
        return;
    }


    public void visDialog() {
        DialogFragment dialog = new MinDialog();
        dialog.show(getSupportFragmentManager(),"Tittel");}


    //Denne variabelen er viktig for å kunne lage metode for onBackPressed (som trenger boolean)
    private boolean Spillet_Kjører = false;

//Kode over er for dialogboksene for når spiller prøver å avslutte spill ved å trykke på tilbake knappen på appen (onButtonPress key)





    //Metoden for å detektere når tilbake knappen på android blir brukt --> hvis true imens spillet kjører så får bruker opp dialogboks,
    //Hvis false (tilfeller den brukes når spill ikke kjøres, så skal ikke dialogboks dukke opp)
    @Override
    public void onBackPressed(){
        if (Spillet_Kjører == true){visDialog();}

        else {super.onBackPressed();} //Default verdi som navgerer tilbake
    }








    //Koden under er for å informere spiller om at alle spørsmål er fullført, og spør om brukeren vil fortsette nytt spill eller avslutte
    @Override
    public void AvsluttFullførtSpill() {finish();}

    @Override
    public void NyttSpill() {return;}

    public void visDialog2(){
        DialogFragment dialog2 = new MinDialog2();
        dialog2.show(getSupportFragmentManager(), "Tittel");
    }




    //Det som mangler:



    //1. Ha egen layout for liggende modus også






    int DetRiktigeSvaret;

    String randomSpørsmål;


    //For display av tilbakemeldinger for svar som er feil (med konstruktive tilbakemeldinger)
    String randomTilbakemeldingGALTSVAR;
    String tilbakeMeldingenFEILSVAR; //Generell variabel slik at jeg kan oppdatere den utenfor start metoden for å bruke den i enter metoden


    //For display av tilbakemeldinger for svar som er korrekte (med konstruktive tilbakemeldinger)
    String randomTilbakemeldingKORREKTSVAR;
    String tilbakeMeldingenKORREKTSVAR;




    int selectedOption; // Default value

    // Retrieve the selected option from the Bundle



    //Variabel for å holde styr på antall spørsmål som velges (5, 10 eller 15)
    private int clickAntall = 0;

    TextView Svar;
    TextView SpørsmålDisplay;
    private StringBuilder numbers = new StringBuilder(); // For å kunne trykke på f.eks samme knapp
    //Flere ganger og få flere tall etter hverandre, istedenfor å bare oppdatere svaret til en enkel verdi, f.eks 0


    private ArrayList AlleredeDisplayedSpørsmål = new ArrayList<>(); //For å midlertidig lagre allerede stilte spørsmål

    private ArrayList AlleredeDisplayedTilbakemeldinger = new ArrayList<>(); //For å midlertidig lagre allerede viste tilbakemeldinger
    private ArrayList AlleredeDisplayedKorrekteTilbakemeldinger = new ArrayList<>();







    //Metode for å lagre tilstand og språk slik at tilstanden er lik selv om brukeren roterer på mobilen til landscape layout
    //Del 1 (Kode fra forelesning)


    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("DetRiktigeSvaret", DetRiktigeSvaret);
        outstate.putString("randomSpørsmål", randomSpørsmål);
        outstate.putString("randomTilbakemeldingGALTSVAR", randomTilbakemeldingGALTSVAR);
        outstate.putString("tilbakeMeldingenFEILSVAR", tilbakeMeldingenFEILSVAR);
        outstate.putString("randomTilbakemeldingKORREKTSVAR", randomTilbakemeldingKORREKTSVAR);
        outstate.putString("tilbakeMeldingenKORREKTSVAR", tilbakeMeldingenKORREKTSVAR);
        outstate.putInt("selectedOption", selectedOption);
        outstate.putInt("clickAntall", clickAntall);


       TextView textView = (TextView) findViewById(R.id.spørsmål);
        outstate.putString("SpørsmålDisplay",textView.getText().toString());

        TextView textView2 = (TextView) findViewById(R.id.svaret);
        outstate.putString("Svar", textView2.getText().toString());


    }


    //Metode for å lagre tilstand og språk slik at tilstanden er lik selv om brukeren roterer på mobilen til landscape layout
    //Del 2 (Kode fra forelesning)
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

DetRiktigeSvaret = savedInstanceState.getInt("DetRiktigeSvaret");
        randomSpørsmål = savedInstanceState.getString("randomSpørsmål");
        randomTilbakemeldingGALTSVAR = savedInstanceState.getString("randomTilbakemeldingGALTSVAR");
        tilbakeMeldingenFEILSVAR = savedInstanceState.getString("tilbakeMeldingenFEILSVAR");
        randomTilbakemeldingKORREKTSVAR = savedInstanceState.getString("randomTilbakemeldingKORREKTSVAR");
        tilbakeMeldingenKORREKTSVAR = savedInstanceState.getString("tilbakeMeldingenKORREKTSVAR");
        selectedOption = savedInstanceState.getInt("selectedOption");
        clickAntall = savedInstanceState.getInt("clickAntall");



        TextView textView = (TextView) findViewById(R.id.spørsmål);
        textView.setText(savedInstanceState.getString("SpørsmålDisplay"));

        TextView textView2 = (TextView) findViewById(R.id.svaret);
        textView2.setText(savedInstanceState.getString("Svar"));
    }






    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.spill_main);










       //For å kunne velge preferansene av 5, 10 eller 15 spørsmål (som er laget i sin egen class)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedOption = sharedPreferences.getInt("selectedOption", 5); // Default to 5 questions




        //Definerer variablene for string arrayet og svarene i xml filen i ressurser for å kunne bruke de i start og enter metodene
        Resources resources = getResources();

        //Lage spørsmål arrayliste
        String[] spørsmålArray = resources.getStringArray(R.array.spørsmål);

        //Lage mattesvar arrayliste
        int[] MatteSvarArray = resources.getIntArray(R.array.svar);

        //For å lage arrayliste for gale svar (tilbakemeldinger)
        String[] tilbakemeldingerForFeilSvarArray = resources.getStringArray(R.array.Tilbakemeldinger);

        //For å lage arrayliste for korrekte svar (tilbakemeldinger)
        String[] tilbakemeldingerForKorrektSvarArray = resources.getStringArray(R.array.Tilbakemeldinger2);






        Button delete = (Button) findViewById(R.id.Delete);

        Button enter = (Button) findViewById(R.id.enter);

        Button start = (Button) findViewById(R.id.startSelveSpillet);

        Button tall0 = (Button) findViewById(R.id.Tall0);
        Button tall1 = (Button) findViewById(R.id.Tall1);
        Button tall2 = (Button) findViewById(R.id.Tall2);
        Button tall3 = (Button) findViewById(R.id.Tall3);
        Button tall4 = (Button) findViewById(R.id.Tall4);
        Button tall5 = (Button) findViewById(R.id.Tall5);
        Button tall6 = (Button) findViewById(R.id.Tall6);
        Button tall7 = (Button) findViewById(R.id.Tall7);
        Button tall8 = (Button) findViewById(R.id.Tall8);
        Button tall9 = (Button) findViewById(R.id.Tall9);


        Svar = findViewById(R.id.svaret);

        SpørsmålDisplay = findViewById(R.id.spørsmål);














        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.setLength(0);
                // Set the text of the TextView to the current input
                Svar.setText(numbers);

            }
        });


        tall0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("0");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });


        tall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("1");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });


        tall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("2");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });


        tall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("3");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });


        tall4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("4");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });

        tall5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("5");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });

        tall6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("6");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });

        tall7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("7");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });

        tall8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("8");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });


        tall9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers.append("9");
                // Set the text of the TextView to the current input
                Svar.setText(numbers);
            }
        });







        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean repetertSpørsmål = true; //Slik at while løkken fortsetter å kjøre koden så lenge boolean verdien er satt til true, og skrur av if setningen når den settes til false

                //Denne if setningen gir logikken av at spillet kjører (alt som skjer mens spill blir kjørt)
                    if (clickAntall < selectedOption) {


             while (repetertSpørsmål) { //while løkken gjør at koden kjører konstant, og er utenfor clickAntall++
                 // for at repeterte spørsmål ikke telles med i antall clicks (slik at brukeren faktisk for ønskede antall spørsmål)


                            //For å hente fram et random spørsmål fra arrayet
                            Random hentRandomSpørsmål = new Random();


                 //Teller går gjennom spørsmål arrayet og henter randome spørsmål
             int spørsmålTeller = hentRandomSpørsmål.nextInt(spørsmålArray.length);


                            //For å vise fram regnestykket som indeksen står på i arrayet
                            randomSpørsmål = spørsmålArray[spørsmålTeller];


     //For å vise fram tilbakemeldingen som indeksen står på i arrayet til tilbakeMeldingArray,
      // og som stemmer overens med indeksen til spørsmålArray (ved å bruke samme teller i begge!)
                          randomTilbakemeldingGALTSVAR = tilbakemeldingerForFeilSvarArray[spørsmålTeller];


                          //Samme forklaring som over
                          randomTilbakemeldingKORREKTSVAR = tilbakemeldingerForKorrektSvarArray[spørsmålTeller];




 //Oppdaterer den offentlige variabelen for å kunne bruke den i metoden enter
tilbakeMeldingenFEILSVAR = randomTilbakemeldingGALTSVAR;
tilbakeMeldingenKORREKTSVAR = randomTilbakemeldingKORREKTSVAR;

                            //Hvis ikke det randome spørsmålet/tilbakemeldingene allerede er i arraylisten, display den til brukeren:
                            if (!AlleredeDisplayedSpørsmål.contains(randomSpørsmål) &&
                                    !AlleredeDisplayedTilbakemeldinger.contains(randomTilbakemeldingGALTSVAR) &&
                                    !AlleredeDisplayedKorrekteTilbakemeldinger.contains(tilbakeMeldingenKORREKTSVAR)) {

                                SpørsmålDisplay.setText(randomSpørsmål);
                                repetertSpørsmål = false;

                            }





//Nå kan vi displaye et annet spørsmål istedenfor, slik at brukeren ikke bruker opp sine spørsmål med blank skjerm
//Dette kan gjøre med en while løkke som fortsetter å kjøre om igjen og om igjen så lenge en boolean verdi er falsk
//Når den settes til true (innenfor if setningen over) så skal spørsmålet ikke displayes, men kjøre til neste






                            //Hindre gjentakelse av spørsmål - logikken: lage arrayliste og automatisk legge til de randome regnestykkene som displayes,
                            // og deretter displaye et nytt random regnestykke så lenge DEN IKKE er inni arraylisten
                            AlleredeDisplayedSpørsmål.add(randomSpørsmål);



                            //Slik at regnestykket i string arrayet stemmer overens med det korrekte svaret i integer arrayer
                            int RiktigSvar = MatteSvarArray[spørsmålTeller];
                            DetRiktigeSvaret = RiktigSvar; //For å oppdatere den offentlige variabelen til riktig svar slik det kan brukes i andre metoder (metoden under for enter)


                 //Legger til tilbakemeldingene kun midlertidig inni arraylistene slik at de ikke kan bli vist to ganger i samme spill
                            AlleredeDisplayedTilbakemeldinger.add(randomTilbakemeldingGALTSVAR);

                            AlleredeDisplayedKorrekteTilbakemeldinger.add(randomTilbakemeldingKORREKTSVAR);



                        }

                            clickAntall++; //For å holde styr på antall spørsmål som displayes





/* Kan legge til kode her for å få opp dialogboks som spør "Er du sikker på at du vil avbryte spillet?"
 Grunnen til det er fordi jeg kan se på dette som en "sesjon" hvor så lenge teller
    (countAntall) fortsatt teller opp antall spørsmål, skal det
 Dermed bli spurt om bruker virkelig vil avrbyte spillet. Variabel for det må dermed settes til true.
  I else setningen under trengs det ikke fordi det kun skal komme når bruker er i en spillsesjon
   så boolean variabelen Spillet_Kjører må settes til false slik at metoden for
  funksjonen OnBackPressed deaktiveres (altså for å ikke få opp dialogboks igjen)  */


                            Spillet_Kjører = true; //Eneste som trengs for å aktivere onBackPressed metoden helt øverst


                        }




                    //Denne else setningen gir logikken av alt som skjer når spillet blir fullført/ikke kjører
                    else {
                        SpørsmålDisplay.setText("You have completed all the questions, exit game or play again by clicking play");
                        clickAntall = 0; //Setter tellingen av antall click's til 0 igjen for å resete, slik at det på nytt kan startes et nytt spill
                        //Som teller opp fra 0 til enten 5, 10 eller 15 spørsmål igjen

   Spillet_Kjører = false; //Eneste som trengs for å deaktivere onBackPressed metoden helt øverst
           // (ettersom if setningen over oppdaterte den til true, som dermed må deaktiveres med false igjen her i else setningen)


   AlleredeDisplayedSpørsmål.clear(); //For å tømme arraylisten av allerede stilte spørsmål slik at bruker kan spille nytt spill igjen med en helt tom arrayliste

   AlleredeDisplayedTilbakemeldinger.clear(); //For å tømme arraylisten av allerede viste tilbakemeldinger (slik at det fungerer til neste spill)

   AlleredeDisplayedKorrekteTilbakemeldinger.clear();

    visDialog2(); //Dialog for når alle spørsmålene er fullførte, og spiller blir spurt om han vil spille et nytt spill, eller avlutte spillet


                    }

            }
        });






        enter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {




              try {

                  int KonverterSvarTilInteger = Integer.parseInt(Svar.getText().toString());
                  //Svaret som blir skrevet inn i TextView svar er i form av string
                  //For å sammenligne det opp mot det korrekte svaret til regnestykket, må det derfor konverteres til Integer med parseint


                  if (KonverterSvarTilInteger == DetRiktigeSvaret) {
                     // Svar.setText("Well done, your answer is correct! Click on play again for next question");

                      Svar.setText(tilbakeMeldingenKORREKTSVAR); //Tilbakemeldingen til korrekte svar som hentes til de spesifikke svarene/spørsmålene
                  }



                  else {
                     // Svar.setText("Wrong answer, please try again or skip question");

                  Svar.setText(tilbakeMeldingenFEILSVAR); //Tilbakemeldingen som hentes til de spesifikke svarene/spørsmålene
                  }



              }







  //Hindrer at appen crasher hvis ingen verdi er skrevet inn (bruker får opp feilmelding om å skrive inn en verdi)
  /*Det fungerer slik at hvis ikke betingelsene for if setningen oppfylles, så skal ikke knappen være ufunksjonell slik at
  appen crasher, men heller at den har et fundament som den går tilbake til --> feilmelding til bruker

   */
              catch (Exception e){
                  e.printStackTrace();
                  Svar.setText("Skriv inn en verdi");}



          }



      });}
}








