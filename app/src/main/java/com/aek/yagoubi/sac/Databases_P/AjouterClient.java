package com.aek.yagoubi.sac.Databases_P;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                "ID_CLIENT INTEGER  , NAME TEXT , CODE_BARE TEXT , PAYEE INTEGER , PRIX REAL)");
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

    public Cursor getAllArticles(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("SELECT * FROM article",null);
        return res;
    }


}
