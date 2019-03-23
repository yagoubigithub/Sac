package com.aek.yagoubi.sac;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentIntegrator;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AjouterSac extends AppCompatActivity {
    ArrayList<String> fileNames;
     TextView codebare_text;
     String sFormat = "", sContent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_sac);
        fileNames = new ArrayList<>();
        Intent intent = getIntent();
        final Client client = (Client)intent.getSerializableExtra("client");
        final AjouterClient database = new AjouterClient(this);
       Button save_sac_btn = (Button)findViewById(R.id.save_sac_btn);
       final EditText edit_text_name_ajouterArticle = (EditText)findViewById(R.id.edit_text_name_ajouterArticle);
        final EditText edit_text_prix_ajouterArticle = (EditText)findViewById(R.id.edit_text_prix_ajouterArticle);
        final  EditText edit_text_qte_ajouterArticle = (EditText)findViewById(R.id.edit_text_qte_ajouterArticle);
     codebare_text = (TextView) findViewById(R.id.codebare_text);

       Button scan_btn_ajouterArticle = (Button) findViewById(R.id.scan_btn_ajouterArticle);

       scan_btn_ajouterArticle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               IntentIntegrator scanIntegrator = new IntentIntegrator(AjouterSac.this);
               scanIntegrator.initiateScan();
           }
       });
        save_sac_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_client = client.getId();
                String nom = edit_text_name_ajouterArticle.getText().toString();
                String prix = edit_text_prix_ajouterArticle.getText().toString();
                String qte = edit_text_qte_ajouterArticle.getText().toString();

                if(nom.equals("")){
                    Toast.makeText(AjouterSac.this,"Le Nom svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(prix.equals("")){
                    Toast.makeText(AjouterSac.this,"Le Prix svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(qte.equals("")){
                    Toast.makeText(AjouterSac.this,"Le quantité svp",Toast.LENGTH_LONG).show();
                    return;
                }
                try
                {
                    Integer.parseInt(qte);
                    Float.parseFloat(prix);
                }
                catch(NumberFormatException e)
                {

                    //not a Float
                    Toast.makeText(AjouterSac.this,"Le quantité et le prix doit être un nombre svp",Toast.LENGTH_LONG).show();
                    return;
                }

               boolean isSave =  database.AjouterSac(id_client,nom,sContent,sFormat,0,Float.parseFloat(prix), Integer.parseInt(qte),fileNames);

                if(isSave){
                    Toast.makeText(AjouterSac.this,"Article enregistré avec succès",Toast.LENGTH_LONG).show();
                    finish();
                }



            }
        });



        ImageButton ajouter_picture_btn = (ImageButton) findViewById(R.id.ajouter_picture_btn);


        ajouter_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjouterSac.this, getPictureActivity2.class);


                startActivityForResult(intent1,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == AjouterSac.RESULT_OK) {
                // result camera
               String[] fileNamesArray =  data.getStringExtra("fileNames").split(",");
               for (int i = 0;i <fileNamesArray.length;i++){
                   fileNames.add(fileNamesArray[i]);
               }

            }

        }else{
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if(scanContent != null && scanFormat != null){
                    //formatTxt.setText("FORMAT: " + scanFormat);
                    //contentTxt.setText("CONTENT: " + scanContent);
                    sContent = scanContent;
                    sFormat = scanFormat;
                    codebare_text.setText(scanContent);


                }

            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }


    }




}



