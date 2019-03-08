package com.aek.yagoubi.sac;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AjouterSac extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_sac);
        Intent intent = getIntent();
        Client client = (Client)intent.getSerializableExtra("client");

        Toast.makeText(this,client.getNom(),Toast.LENGTH_SHORT).show();

        ImageButton ajouter_picture_btn = (ImageButton) findViewById(R.id.ajouter_picture_btn);


        ajouter_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjouterSac.this, getPictureActivity.class);


                startActivityForResult(intent1,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(AjouterSac.this,"fileNames",Toast.LENGTH_LONG ).show();
        if (requestCode == 1) {
            if (resultCode == AjouterSac.RESULT_OK) {
                // result camera
                Toast.makeText(AjouterSac.this, data.getStringExtra("fileNames"), Toast.LENGTH_LONG).show();
            }
            if (resultCode == AjouterSac.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(AjouterSac.this, data.getStringExtra("fileNames"), Toast.LENGTH_LONG).show();
            }
        }
    }


}



