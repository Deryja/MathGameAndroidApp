package com.example.mathematicalgame;





import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;



//Klasse for å lage dialog bokser med tekst inni


public class MinDialog2 extends DialogFragment {
    private MittInterface callback;

    public interface MittInterface {
        public void NyttSpill();
        public void AvsluttFullførtSpill();



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (MittInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet!");}}



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new
                AlertDialog.Builder(getActivity()).setTitle(R.string.tittelspillfullført).setPositiveButton(R.string.avsluttfullført,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.AvsluttFullførtSpill();
                    }

                }).setNegativeButton(R.string.nyttspill, new
                        DialogInterface.OnClickListener() {
                            public void onClick (DialogInterface dialog,int whichButton){
                                callback.NyttSpill(); }
                        })
                .create();
    }






}