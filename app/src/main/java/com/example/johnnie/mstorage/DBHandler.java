package com.example.johnnie.mstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Masterace on 5/6/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
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

        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE "+ DBContract.ItemEntry.TABLE_NAME + " (" +
                DBContract.ItemEntry.COLUMN_ITEM_ID + " INTEGER NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_DESCRIPTION + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_CATEGORY + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_MEASUREMENT + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_POSITION + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_BARCODE + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEM_SKU + " TEXT NOT NULL, " +
                DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID + " INTEGER NOT NULL,"+
                " PRIMARY KEY ("+DBContract.ItemEntry.COLUMN_ITEM_ID+", "+
                DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID+", "+
                DBContract.ItemEntry.COLUMN_ITEM_POSITION+") )";

        db.execSQL(SQL_CREATE_STORAGE_TABLE);
        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.StorageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DepartmentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ItemEntry.TABLE_NAME);
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
        Log.d(TAG," inside addDepartment");
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + DBContract.DepartmentEntry.TABLE_NAME + " WHERE " + DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + "=" +department.getId() + ";";
        Cursor c = db.rawQuery(query, null);
        try {
            if (c.getCount() <= 0) {
                Log.d(TAG, " create New Department");
                ContentValues values = new ContentValues();
                values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID, department.getId());
                values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME, department.getName());
                values.put(DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE, department.getStorageid());
                db = getWritableDatabase();
                db.insertOrThrow(DBContract.DepartmentEntry.TABLE_NAME, null, values);
            } else {
                Log.d(TAG, " update Existing Department");
                ContentValues values = new ContentValues();
                values.put(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME, department.getName());
                values.put(DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE, department.getStorageid());
                db = getWritableDatabase();
                db.update(DBContract.DepartmentEntry.TABLE_NAME, values, DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + "=" + department.getId(), null);
            }
        }
        catch (final SQLException e)   {
            Log.d(TAG, e.toString());
        }
        finally{
            db.close();
        }
        c.close();
    }

    public void addItems(Item ItemtoDB){
        Log.d(TAG," inside addItems");
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + DBContract.ItemEntry.TABLE_NAME + " WHERE " + DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID + "=" + ItemtoDB.getItems_department_id()
                + " AND " + DBContract.ItemEntry.COLUMN_ITEM_ID + "=" + ItemtoDB.getId() + " AND " + DBContract.ItemEntry.COLUMN_ITEM_POSITION + " LIKE \"" +
                ItemtoDB.getPosition() + "\"";
        Cursor c = db.rawQuery(query, null);
        try {
            if (c.getCount() <= 0) {
                Log.d(TAG, " create New Item");
                ContentValues values = new ContentValues();
                values.put(DBContract.ItemEntry.COLUMN_ITEM_ID, ItemtoDB.getId());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_NAME, ItemtoDB.getName());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_DESCRIPTION, ItemtoDB.getDescription());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_CATEGORY, ItemtoDB.getCategory());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_POSITION, ItemtoDB.getPosition());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_MEASUREMENT, ItemtoDB.getMeasurement_unit());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_SKU, ItemtoDB.getSKU());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_BARCODE, ItemtoDB.getBarcode());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_QUANTITY, ItemtoDB.getQuantity());
                values.put(DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID, ItemtoDB.getItems_department_id());
                db = getWritableDatabase();
                db.insertOrThrow(DBContract.ItemEntry.TABLE_NAME, null, values);
            } else {
                Log.d(TAG, " update Existing Item");
                ContentValues values = new ContentValues();
                values.put(DBContract.ItemEntry.COLUMN_ITEM_NAME, ItemtoDB.getName());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_DESCRIPTION, ItemtoDB.getDescription());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_CATEGORY, ItemtoDB.getCategory());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_POSITION, ItemtoDB.getPosition());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_MEASUREMENT, ItemtoDB.getMeasurement_unit());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_SKU, ItemtoDB.getSKU());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_BARCODE, ItemtoDB.getBarcode());
                values.put(DBContract.ItemEntry.COLUMN_ITEM_QUANTITY, ItemtoDB.getQuantity());
                values.put(DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID, ItemtoDB.getItems_department_id());
                db = getWritableDatabase();
                db.update(DBContract.ItemEntry.TABLE_NAME, values, DBContract.ItemEntry.COLUMN_ITEM_ID + "=" + ItemtoDB.getId(), null);
            }
        }
        catch (final SQLException e)   {
            Log.d(TAG, e.toString());
        }
        finally{
            db.close();
        }
        c.close();
    }

    public void wipeData(){
        String query[] = new String[] {"DELETE " + DBContract.StorageEntry.TABLE_NAME,
                "DELETE TABLE " + DBContract.DepartmentEntry.TABLE_NAME,
                "DELETE TABLE " + DBContract.ItemEntry.TABLE_NAME};

        for(int i=0; i<query.length; i++){
            executeQuery(query[i]);
        }
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