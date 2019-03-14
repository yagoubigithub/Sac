package com.aek.yagoubi.sac;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class getPictureActivity extends AppCompatActivity {


    String  fileNames  = "";
    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    Button CaptureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);


        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        ImageButton closeCameraBtn = (ImageButton) findViewById(R.id.closeCameraBtn);

        closeCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("fileNames",fileNames);
                setResult(getPictureActivity.RESULT_OK,returnIntent);
                finish();
            }
        });


        //Open the Camera

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);

        CaptureBtn = (Button) findViewById(R.id.CaptureBtn);

        CaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.takePicture(null, null, mPictureCalback);
                }
            }
        });



    }

  Camera.PictureCallback mPictureCalback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            //Save picture

            if (data != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);

                if(bitmap!=null){

                    File file=new File(Environment.getExternalStorageDirectory()+"/dirr");
                    if(!file.isDirectory()){
                        file.mkdir();
                    }
                    String fileName = System.currentTimeMillis()+".jpg";


                    file=new File(Environment.getExternalStorageDirectory()
                            + "/dirr",fileName);
                  //  Toast.makeText(getPictureActivity.this, file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    fileNames += "," + fileName;
                    try
                    {
                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        camera.startPreview();

                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    catch(Exception exception)
                    {
                        exception.printStackTrace();
                    }

                }
            }



        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("fileNames",fileNames);
            setResult(getPictureActivity.RESULT_OK,returnIntent);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
