package com.aek.yagoubi.sac;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;

public class AjouterClientActivity extends AppCompatActivity {

    Button btnAjouterClient;
    TextInputEditText edit_text_nom_ajouter_client, edit_text_tele_ajouter_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_client);

        btnAjouterClient = (Button) findViewById(R.id.btnAjouterClient);
        edit_text_tele_ajouter_client = (TextInputEditText) findViewById(R.id.edit_text_tele_ajouter_client);
        edit_text_nom_ajouter_client = (TextInputEditText) findViewById(R.id.edit_text_nom_ajouter_client);

        final AjouterClient ajouterClient = new AjouterClient(this);
        btnAjouterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_nom_ajouter_client.getText().toString() != "" && edit_text_tele_ajouter_client.getText().toString() != "") {
                    ajouterClient.AjouterClient(edit_text_nom_ajouter_client.getText().toString(),
                            edit_text_tele_ajouter_client.getText().toString());
                    edit_text_tele_ajouter_client.setText("");
                    edit_text_nom_ajouter_client.setText("");
                    finish();


                }


            }
        });
    }
}
