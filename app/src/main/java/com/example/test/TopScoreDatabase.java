package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TopScoreDatabase extends SQLiteOpenHelper {
    public TopScoreDatabase(@Nullable Context context) {
        super(context, "topscore.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table If Not Exists TopScore(Id INTEGER PRIMARY KEY AUTOINCREMENT, UserName TEXT UNIQUE, Score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void InsertData (String UserName, Integer Score){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName",UserName);
        contentValues.put("Score",Score);

        db.insert("TopScore", null, contentValues);

    }

    public boolean CheckNameExists (String UserName){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from TopScore where UserName = ?", new String[]{UserName});

        if(cursor.getCount() == 0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor getScore(String UserName){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Score from TopScore where UserName = ?", new String[]{UserName});
        return cursor;
    }

    public void UpdateHighScore (String UserName, Integer Score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Score",Score);
        db.update("TopScore", contentValues, "UserName = ?", new String[]{UserName});
    }

    public Cursor getListScore(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from TopScore ORDER BY Score DESC ",null);
        return cursor;

    }
}
