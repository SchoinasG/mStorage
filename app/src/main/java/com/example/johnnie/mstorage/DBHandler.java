package com.example.johnnie.mstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Masterace on 5/6/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "mStorage.db";
    private String TAG = "DbHelper";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_STORAGE_TABLE = "CREATE TABLE "+ DBContract.StorageEntry.TABLE_NAME + " (" +
                DBContract.StorageEntry.COLUMN_STORAGE_ID + " INTEGER PRIMARY KEY, " +
                DBContract.StorageEntry.COLUMN_STORAGE_NAME+" TEXT"+
                " );";

        final String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE "+ DBContract.DepartmentEntry.TABLE_NAME + " (" +
                DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + " INTEGER PRIMARY KEY, " +
                DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME+" TEXT NOT NULL, "+
                DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE+" INTEGER NOT NULL"+
                " );";

        db.execSQL(SQL_CREATE_STORAGE_TABLE);
        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.StorageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DepartmentEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addStorage(Storage storage){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + DBContract.StorageEntry.TABLE_NAME + " WHERE " + DBContract.StorageEntry.COLUMN_STORAGE_ID + "=" + storage.getId() + ";";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() <= 0){
            ContentValues values = new ContentValues();
            values.put(DBContract.StorageEntry.COLUMN_STORAGE_ID, storage.getId());
            values.put(DBContract.StorageEntry.COLUMN_STORAGE_NAME, storage.getName());
            db = getWritableDatabase();
            db.insert(DBContract.StorageEntry.TABLE_NAME, null, values);
            db.close();
        } else {
            ContentValues values = new ContentValues();
            values.put(DBContract.StorageEntry.COLUMN_STORAGE_NAME, storage.getName());
            db = getWritableDatabase();
            db.update(DBContract.StorageEntry.TABLE_NAME, values, DBContract.StorageEntry.COLUMN_STORAGE_ID + "=" + storage.getId(), null);
            //Or it could be like this db.replace(DBContract.StorageEntry.TABLE_NAME, null, values);
            db.close();
        }
        c.close();
    }

    public void addDepartment(Department department){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + DBContract.DepartmentEntry.TABLE_NAME + " WHERE " + DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + "=" + department.getId() + ";";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount()<=0){
            ContentValues values = new ContentValues();
            values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID, department.getId());
            values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME, department.getName());
            values.put(DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE, department.getStorageid());
            db = getWritableDatabase();
            db.insert(DBContract.DepartmentEntry.TABLE_NAME, null, values);
            db.close();
        } else {
            ContentValues values = new ContentValues();
            values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME, department.getName());
            values.put(DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE, department.getStorageid());
            db = getWritableDatabase();
            db.update(DBContract.StorageEntry.TABLE_NAME, values, DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + "=" + department.getId(), null);
            db.close();
        }
        c.close();
    }

    public void wipeData(){
        String query = "DELETE FROM " + DBContract.StorageEntry.TABLE_NAME;

        executeQuery(query);
    }

    public void executeQuery(String query){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(query);
    }

    public Cursor selectQuery(String query){
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

}