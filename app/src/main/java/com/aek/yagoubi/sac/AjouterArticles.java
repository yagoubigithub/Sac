package com.aek.yagoubi.sac;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aek.yagoubi.sac.Databases_P.AjouterClient;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentIntegrator;
import com.aek.yagoubi.sac.com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AjouterArticles extends AppCompatActivity {
    AutoCompleteTextView edit_text_client_name;

    AjouterClient database;
    Cursor res;
    EditText edit_text_client_tele,edit_text_save_article_name,edit_text_save_article_prix,edit_text_save_article_qte;
    Button ajouter_codebare_btn;
    ImageButton ajouter_picture_btn;
    ArrayList<String> fileNames;
    ArrayList<Client> clients;
    TextView text_view_save_article_codebare;

    String codeBare,codeBareFormat,filenames;
    int id_client = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_articles);
        edit_text_client_name = (AutoCompleteTextView) findViewById(R.id.edit_text_client_name);
        edit_text_client_tele = (EditText) findViewById(R.id.edit_text_client_tele);
        edit_text_save_article_qte = (EditText) findViewById(R.id.edit_text_save_article_qte);
        edit_text_save_article_prix = (EditText) findViewById(R.id.edit_text_save_article_prix);
        edit_text_save_article_name = (EditText) findViewById(R.id.edit_text_save_article_name);
        text_view_save_article_codebare = (TextView) findViewById(R.id.text_view_save_article_codebare);


        database = new AjouterClient(this);
        res = database.getAllClients();

        clients = new ArrayList<>();


        while (res.moveToNext()) {

            clients.add(new Client(res.getInt(0), res.getString(1), res.getString(2)));


        }
        edit_text_client_name.setThreshold(1);

       final  ClientDropDownAdapert adapert = new ClientDropDownAdapert(this,clients);
        edit_text_client_name.setAdapter(adapert);


        edit_text_client_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                id_client = clients.get(position).getId();
                edit_text_client_tele.setText(clients.get(position).getNumeroTele());


            }
        });

        ajouter_picture_btn = (ImageButton) findViewById(R.id.ajouter_picture_btn);
        ajouter_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjouterArticles.this, getPictureActivity.class);
                intent1.putExtra("fileNames", filenames);
                startActivityForResult(intent1,1);
            }
        });

        ajouter_codebare_btn = (Button) findViewById(R.id.ajouter_codebare_btn);

        ajouter_codebare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(AjouterArticles.this);
                scanIntegrator.initiateScan();
            }
        });





        Button btnAjouterArticle = (Button) findViewById(R.id.btnAjouterArticle);


        btnAjouterArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomarticle = edit_text_save_article_name.getText().toString();
                String nomeClient = edit_text_client_name.getText().toString();
                String tele = edit_text_client_tele.getText().toString();
                String prix = edit_text_save_article_prix.getText().toString();
                String qte = edit_text_save_article_qte.getText().toString();

                if(nomarticle.equals("")){
                    Toast.makeText(AjouterArticles.this,"Le Nom de l'article svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(nomeClient.equals("")){
                    Toast.makeText(AjouterArticles.this,"Le Nom du client svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(prix.equals("")){
                    Toast.makeText(AjouterArticles.this,"Le Prix svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(qte.equals("")){
                    Toast.makeText(AjouterArticles.this,"Le quantité svp",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AjouterArticles.this,"Le quantité et le prix doit être un nombre svp",Toast.LENGTH_LONG).show();
                    return;
                }


                boolean isSave = false;
                if(id_client == -1){
                    //new Client

                  long  id =    database.AjouterClientAndReturnId(nomeClient,tele);
                    isSave =  database.AjouterSac((int)id,nomarticle,codeBare,codeBareFormat,0,Float.parseFloat(prix), Integer.parseInt(qte),fileNames);
                }else{
                    isSave =  database.AjouterSac(id_client,nomarticle,codeBare,codeBareFormat,0,Float.parseFloat(prix), Integer.parseInt(qte),fileNames);
                }


                if(isSave){
                    Toast.makeText(AjouterArticles.this,"Article enregistré avec succès",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == AjouterArticles.RESULT_OK) {
                // result camera
                String[] fileNamesArray =  data.getStringExtra("fileNames").split(",");
                for (int i = 0;i <fileNamesArray.length;i++){

                    fileNames.add(fileNamesArray[i]);
                    filenames += "," + fileNamesArray[i];
                }

            }

        }

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if(scanContent != null && scanFormat != null){
                codeBare = scanContent;
                codeBareFormat = scanFormat;
                text_view_save_article_codebare.setText(codeBare);

                //serch for the code bare if exixst
              res =   database.searchCodeBare(codeBare, codeBareFormat);
              if(res.getCount() > 0){
              /* Sac sac =   new Sac(res.getInt(0), res.getInt(1), res.getString(2),
                          res.getString(3), res.getString(4), res.getInt(5),
                          res.getFloat(6), res.getInt(7));*/
               while (res.moveToNext()){
                   edit_text_save_article_name.setText(res.getInt(1));
                   edit_text_save_article_prix.setText(res.getFloat(6) + "");
                   edit_text_save_article_qte.setText(res.getInt(7) + "");

               }


                  res = database.getArticleImages( res.getInt(0));
                  if(res.getCount() > 0){
                      fileNames.clear();
                      while (res.moveToNext()){
                          fileNames.add(res.getString(2));

                      }

                  }



              }

            }

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
