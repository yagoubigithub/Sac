package com.aek.yagoubi.sac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class SacActivity extends AppCompatActivity {

   Client client;
   Sac sac;
   EditText edit_text_change_article_name,edit_text_change_article_prix,edit_text_change_article_qte;
   FloatingActionButton delete_article_btn;
    AjouterClient database;
    TextView text_view_change_article_codebare;
    Button change_codebare_btn,see_galerie_btn;
    String fileNames = "";
    ImageButton change_pictures_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sac);
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("client");
        sac = (Sac) intent.getSerializableExtra("sac");
        database = new AjouterClient(this);

     Cursor res =  database.getArticleImages(sac.getId());

     while (res.moveToNext()){
      fileNames += ","  +   res.getString(2);


     }

        edit_text_change_article_name =(EditText) findViewById(R.id.edit_text_change_article_name);
        edit_text_change_article_prix =(EditText) findViewById(R.id.edit_text_change_article_prix);
        edit_text_change_article_qte =(EditText) findViewById(R.id.edit_text_change_article_qte);
        text_view_change_article_codebare =(TextView) findViewById(R.id.text_view_change_article_codebare);
        change_codebare_btn =(Button) findViewById(R.id.change_codebare_btn);
        see_galerie_btn =(Button) findViewById(R.id.see_galerie_btn);
        change_pictures_btn =(ImageButton) findViewById(R.id.change_pictures_btn);


        edit_text_change_article_name.setText(sac.getNom());

        edit_text_change_article_prix.setText(sac.getPrix() + "");

        edit_text_change_article_qte.setText(sac.getQte() + "");

        text_view_change_article_codebare.setText(sac.getCode_bare() + "");




        delete_article_btn = (FloatingActionButton) findViewById(R.id.delete_article_btn);

        delete_article_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SacActivity.this);

                builder.setMessage("")
                        .setTitle("Suprimer");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       boolean b =  database.deleteArticleByHisId(sac.getId());
                       if(b)
                        finish();
                       else
                           Toast.makeText(SacActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        change_codebare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(SacActivity.this);
                scanIntegrator.initiateScan();
            }
        });


        change_pictures_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SacActivity.this, getPictureActivity.class);




                startActivityForResult(intent1,1);
            }
        });
        see_galerie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SacActivity.this, GalleryActivity.class);
                intent1.putExtra("article_id", sac.getId());

                startActivity(intent1);
            }
        });


       FloatingActionButton save_the_change_sac = (FloatingActionButton) findViewById(R.id.save_the_change_sac);

        save_the_change_sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update article

                String nomarticle = edit_text_change_article_name.getText().toString();


                String prix = edit_text_change_article_prix.getText().toString();
                String qte = edit_text_change_article_qte.getText().toString();
                String codeBare = sac.getCode_bare();
                String codeBareFormat = sac.getCodeBareFormat();

                if(nomarticle.equals("")){
                    Toast.makeText(SacActivity.this,"Le Nom de l'article svp",Toast.LENGTH_LONG).show();
                    return;
                }

                if(prix.equals("")){
                    Toast.makeText(SacActivity.this,"Le Prix svp",Toast.LENGTH_LONG).show();
                    return;
                }
                if(qte.equals("")){
                    Toast.makeText(SacActivity.this,"Le quantité svp",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SacActivity.this,"Le quantité et le prix doit être un nombre svp",Toast.LENGTH_LONG).show();
                    return;
                }

                //

                ArrayList<String> fileNamesList = new ArrayList<>();

                String[] fileNamesArray = fileNames.split(",");
                for (int i = 0;i <fileNamesArray.length;i++){
                    if(fileNamesArray[i].length()> 0){
                        fileNamesList.add(fileNamesArray[i]);
                    }
                }
                boolean isSave = database.updateArticleInformation(sac.getId(), sac.getId_client(), nomarticle,
                        codeBare,codeBareFormat, sac.isPayee() ? 1 : 0,   Float.parseFloat(prix),  Integer.parseInt(qte));
                if(isSave){
                    Toast.makeText(SacActivity.this,"Article enregistré avec succès",Toast.LENGTH_LONG).show();
                    finish();
                }



            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == 1) {
            if (resultCode == AjouterArticles.RESULT_OK) {
                // result camera
                String[] fileNamesArray =  data.getStringExtra("fileNames").split(",");
                for (int i = 0;i <fileNamesArray.length;i++){

                    if(fileNamesArray[i].length()> 0){
                        fileNames += "," + fileNamesArray[i];
                    }
                }

            }

        }else{
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if(scanContent != null && scanFormat != null){
                    sac.setCode_bare(scanContent);
                    sac.setCodeBareFormat(scanFormat);
                    text_view_change_article_codebare = (TextView) findViewById(R.id.text_view_change_article_codebare);
                    text_view_change_article_codebare.setText(scanContent);

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


