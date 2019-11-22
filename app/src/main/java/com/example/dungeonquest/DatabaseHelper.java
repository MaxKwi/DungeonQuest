package com.example.dungeonquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "savedata.db";
    public static final String TABLE_NAME = "savedata_table";
    //Columns
    public static final String  COL_1 = "ID";
    public static final String COL_2 = "LEVEL";
    public static final String COL_3 = "HEALTH";
    public static final String COL_4 = "STAMINA";
    public static final String COL_5 = "MAGIC";
    public static final String COL_6 = "EXPERIENCE";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,LEVEL TEXT,HEALTH TEXT,STAMINA TEXT, MAGIC TEXT, EXPERIENCE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String level, String health, String stamina, String magic, String experience){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,level);
        contentValues.put(COL_3,health);
        contentValues.put(COL_4,stamina);
        contentValues.put(COL_5, magic);
        contentValues.put(COL_6, experience);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id, String level, String health, String stamina, String magic, String experience){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,level);
        contentValues.put(COL_3,health);
        contentValues.put(COL_4,stamina);
        contentValues.put(COL_5, magic);
        contentValues.put(COL_6, experience);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{ id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }
}
