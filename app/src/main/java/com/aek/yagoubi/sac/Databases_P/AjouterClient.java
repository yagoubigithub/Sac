package com.aek.yagoubi.sac.Databases_P;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AjouterClient extends SQLiteOpenHelper {

   private static String DATABASE_NAME = "s";
   private static String TABLE_NAME = "dd";

    public AjouterClient(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "NAME TEXT , Tele TEXT)");
        db.execSQL("create table  article (ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "ID_CLIENT INTEGER  , NAME TEXT , CODE_BARE TEXT ,codeBareFormat TEXT, PAYEE INTEGER , PRIX REAL, qte INTEGER)");
        db.execSQL("create table  images (ID INTEGER PRIMARY KEY AUTOINCREMENT , ID_ARTICLE INTEGER," +
                "filename TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }


    public boolean AjouterClient(String name, String tele ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("tele",tele);

        long result =  db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    public Cursor getAllClients(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return res;
    }

    public Cursor getClientsByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE name LIKE '"+name + "%'",null);
        return res;
    }

    public Cursor getAllArticlesById(int client_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("SELECT * FROM article WHERE ID_CLIENT=" + client_id,null);
        return res;
    }

    public boolean AjouterSac(int id_client, String name, String code_bare,String codeBareFormat, int payee, double prix,double qte , ArrayList<String> images){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id_client",id_client);
        contentValues.put("name",name);
        contentValues.put("code_bare",code_bare);
        contentValues.put("codeBareFormat",codeBareFormat);
        contentValues.put("payee",payee);
        contentValues.put("prix",prix);
        contentValues.put("qte",qte);



        long result =  db.insert("article",null,contentValues);

        for (int i = 0;i < images.size();i++){
            ContentValues newContent = new ContentValues();

            newContent.put("ID_ARTICLE",result);
            newContent.put("filename",images.get(i));

            db.insert("images",null,contentValues);
        }
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllArticlesAndClients(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("SELECT \n" +
                "article.ID id_article, article.ID_CLIENT ID_CLIENT,\n" +
                " article.NAME name_article,article.CODE_BARE, article.codeBareFormat,\n" +
                " article.PAYEE,article.PRIX,article.qte,dd.NAME name_client,dd.Tele \n" +
                " FROM article  JOIN dd  ON article.ID_CLIENT = dd.ID \n",null);
        return res;
    }

}