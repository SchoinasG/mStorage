package com.example.johnnie.mstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Masterace on 5/6/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "mStorage.db";
    private String TAG = "DbHelper";

    Context context;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_STORAGE_TABLE = "CREATE TABLE "+ DBContract.StorageEntry.TABLE_NAME + " (" +
                DBContract.StorageEntry.COLUMN_STORAGE_ID + " INTEGER PRIMARY KEY, " +
                DBContract.StorageEntry.COLUMN_STORAGE_NAME + " TEXT" +
                " );";

        final String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE "+ DBContract.DepartmentEntry.TABLE_NAME + " (" +
                DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID + " INTEGER PRIMARY KEY, " +
                DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME + " TEXT NOT NULL, " +
                DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE + " INTEGER NOT NULL" +
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
                DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID + " INTEGER NOT NULL," +
                " PRIMARY KEY (" + DBContract.ItemEntry.COLUMN_ITEM_ID + ", " +
                DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID + ", " +
                DBContract.ItemEntry.COLUMN_ITEM_POSITION + ") )";

        final String SQL_CREATE_AUDIT_TABLE = "CREATE TABLE "+ DBContract.ItemAudit.TABLE_NAME + " (" +
                DBContract.ItemAudit.COLUMN_ITEM_ID + " INTEGER NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_DESCRIPTION + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_CATEGORY + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_MEASUREMENT + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_POSITION + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_BARCODE + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_SKU + " TEXT NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " INTEGER NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_QUANTITY_FOUND + " INTEGER DEFAULT 0, " +
                DBContract.ItemAudit.COLUMN_ITEM_NOTES + " TEXT, " +
                DBContract.ItemAudit.COLUMN_ITEM_DATE_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
                DBContract.ItemAudit.COLUMN_ITEM_IS_CHECKED + " INTEGER DEFAULT 0," +
                " PRIMARY KEY (" + DBContract.ItemAudit.COLUMN_ITEM_ID + ", " +
                DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + ", " +
                DBContract.ItemAudit.COLUMN_ITEM_POSITION + ") )";

        final String SQL_AUDIT_TABLE_TRIGGER =
                "CREATE TRIGGER DATE_MODIFIED AFTER UPDATE ON " + DBContract.ItemAudit.TABLE_NAME +
                " FOR EACH ROW BEGIN UPDATE " + DBContract.ItemAudit.TABLE_NAME + " SET "+ DBContract.ItemAudit.COLUMN_ITEM_DATE_MODIFIED +
                " = CURRENT_TIMESTAMP WHERE "
                +DBContract.ItemAudit.COLUMN_ITEM_ID + "=OLD." + DBContract.ItemAudit.COLUMN_ITEM_ID +
                " AND "+DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + "=OLD." +DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID +
                " AND "+ DBContract.ItemAudit.COLUMN_ITEM_POSITION + " LIKE OLD." + DBContract.ItemAudit.COLUMN_ITEM_POSITION + ";"+
                " END";

        db.execSQL(SQL_CREATE_STORAGE_TABLE);
        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);
        db.execSQL(SQL_CREATE_AUDIT_TABLE);
        db.execSQL(SQL_AUDIT_TABLE_TRIGGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.StorageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DepartmentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ItemEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ItemAudit.TABLE_NAME);
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

    public boolean CopyToAudit(Department department){
        Log.d(TAG," inside Copy to Audit");
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+  DBContract.ItemAudit.TABLE_NAME +
                " WHERE " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " = " + department.getId() + ";";

        String SQL_COPY_ITEMS_TO_AUDIT = "INSERT INTO " + DBContract.ItemAudit.TABLE_NAME +
                "( "+ DBContract.ItemAudit.COLUMN_ITEM_ID +", "+ DBContract.ItemAudit.COLUMN_ITEM_NAME +
                ", "+ DBContract.ItemAudit.COLUMN_ITEM_DESCRIPTION +", "+ DBContract.ItemAudit.COLUMN_ITEM_CATEGORY +
                ", "+ DBContract.ItemAudit.COLUMN_ITEM_MEASUREMENT +", "+ DBContract.ItemAudit.COLUMN_ITEM_POSITION +
                ", "+ DBContract.ItemAudit.COLUMN_ITEM_QUANTITY + ", "+ DBContract.ItemAudit.COLUMN_ITEM_BARCODE +
                ", "+ DBContract.ItemAudit.COLUMN_ITEM_SKU + ", "+ DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID +
                " ) SELECT * FROM " + DBContract.ItemEntry.TABLE_NAME +
                " WHERE "+ DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID +" = " + department.getId() + ";";

        String SQL_DELETE_ITEMS_FROM_AUDIT = "DELETE FROM " + DBContract.ItemAudit.TABLE_NAME +
                " WHERE "+ DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID +" = " + department.getId() + ";";

        Cursor c = db.rawQuery(query, null);
        try {
            if (c.getCount() <= 0)
            {
                Log.d(TAG,"NOT FOUND ANY DATA IN TABLE");
                db = getWritableDatabase();
                db.execSQL(SQL_COPY_ITEMS_TO_AUDIT);
            } else
            {
                Log.d(TAG,"FOUND DATA IN TABLE");

                db = getWritableDatabase();
                db.execSQL(SQL_DELETE_ITEMS_FROM_AUDIT);
                db.execSQL(SQL_COPY_ITEMS_TO_AUDIT);

            }
        }
        catch (final SQLException e)   {
            Log.d(TAG, e.toString());
            return false;
        }
        finally{
            db.close();
            c.close();
            return true;
        }

    }

    public void wipeData(){
        String query[] = new String[] {"DELETE FROM " + DBContract.StorageEntry.TABLE_NAME,
                "DELETE FROM " + DBContract.DepartmentEntry.TABLE_NAME,
                "DELETE FROM " + DBContract.ItemEntry.TABLE_NAME};

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

    public JSONArray TableToJSONArray(Department department)
    {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+  DBContract.ItemAudit.TABLE_NAME +
                " WHERE " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " = " + department.getId() + ";";

        Cursor cursor = db.rawQuery(query, null );

        JSONArray resultSet  = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString() );
        return resultSet;
    }

    public ArrayList<Item> ItemPopulator(int Selected_Department_ID){
        ArrayList<Item> ItemList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        //+ " ORDER BY " + DBContract.ItemAudit.COLUMN_ITEM_ID + " ASC "
        String query = "SELECT * FROM " + DBContract.ItemAudit.TABLE_NAME + " WHERE " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + "=" + Selected_Department_ID;

        Cursor c = db.rawQuery(query, null);

        if(c != null && c.getCount() != 0){
            if(c.moveToFirst()){
                do{
                    int itemId = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_ID));
                    String itemName = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_NAME));
                    String itemDesc = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_DESCRIPTION));
                    String itemCategory = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_CATEGORY));
                    String itemPosition = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_POSITION));
                    String itemMesUnit = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_MEASUREMENT));
                    String itemSKU = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_SKU));
                    String itemBarcode = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_BARCODE));
                    int itemQuantity = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_QUANTITY));
                    int departmentID = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID));
                    int itemQuantityFound = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_QUANTITY_FOUND));
                    String itemsNotes = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_NOTES));
                    String itemsDateModified = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_DATE_MODIFIED));
                    int itemCheckedStatus = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_IS_CHECKED));

                    Item item = new Item(itemId, itemName, itemDesc, itemCategory, itemPosition, itemMesUnit, itemSKU, itemBarcode, itemQuantity, departmentID);

                    item.setQuantity_found(itemQuantityFound);
                    item.setNotes(itemsNotes);
                    item.setDate_modified(itemsDateModified);
                    item.setIs_checked(itemCheckedStatus);

                    ItemList.add(item);
                }while(c.moveToNext());
            }
        }

        c.close();

        return ItemList;
    }

    public boolean UpdateQuantityFound(Item Updateitem){
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBContract.ItemAudit.COLUMN_ITEM_QUANTITY_FOUND, Updateitem.getQuantity_found());
            db.update(DBContract.ItemAudit.TABLE_NAME, values , DBContract.ItemAudit.COLUMN_ITEM_ID + "=" + Updateitem.getId() +
                    " AND " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " = "+ Updateitem.getItems_department_id() +
                    " AND " + DBContract.ItemAudit.COLUMN_ITEM_POSITION + " Like \"" + Updateitem.getPosition() +"\"", null);
        }
        catch (final SQLException e)   {
            Log.d(TAG, e.toString());
            return false;
        }
        finally{
            db.close();
            return true;
        }
    }
    public boolean UpdateNoteOfItem(Item Updateitem){
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DBContract.ItemAudit.COLUMN_ITEM_NOTES, Updateitem.getNotes());
            db.update(DBContract.ItemAudit.TABLE_NAME, values , DBContract.ItemAudit.COLUMN_ITEM_ID + "=" + Updateitem.getId() +
                    " AND " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " = "+ Updateitem.getItems_department_id() +
                    " AND " + DBContract.ItemAudit.COLUMN_ITEM_POSITION + " LIKE \"" + Updateitem.getPosition() + "\"", null);
        }
        catch (final SQLException e)   {
            Log.d(TAG, e.toString());
            return false;
        }
        finally{
            db.close();
            return true;
        }
    }

    public ArrayList<Item> getItemsfromQr(String QrCode, int Departmens_id){
        ArrayList FoundItems = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + DBContract.ItemAudit.TABLE_NAME + " WHERE " + DBContract.ItemAudit.COLUMN_ITEM_SKU + "= \"" + QrCode + "\" AND "
                + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID +" = " + Departmens_id;

        Cursor c = db.rawQuery(query, null);

        if(c != null && c.getCount() != 0){
            if(c.moveToFirst()){
                do{
                    int itemId = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_ID));
                    String itemName = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_NAME));
                    String itemDesc = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_DESCRIPTION));
                    String itemCategory = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_CATEGORY));
                    String itemPosition = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_POSITION));
                    String itemMesUnit = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_MEASUREMENT));
                    String itemSKU = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_SKU));
                    String itemBarcode = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_BARCODE));
                    int itemQuantity = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_QUANTITY));
                    int departmentID = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID));
                    int itemQuantityFound = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_QUANTITY_FOUND));
                    String itemsNotes = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_NOTES));
                    String itemsDateModified = c.getString(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_DATE_MODIFIED));
                    int itemCheckedStatus = c.getInt(c.getColumnIndex(DBContract.ItemAudit.COLUMN_ITEM_IS_CHECKED));

                    Item ScannedItem = new Item(itemId, itemName, itemDesc, itemCategory, itemPosition, itemMesUnit, itemSKU, itemBarcode, itemQuantity, departmentID);

                    ScannedItem.setQuantity_found(itemQuantityFound);
                    ScannedItem.setNotes(itemsNotes);
                    ScannedItem.setDate_modified(itemsDateModified);
                    ScannedItem.setIs_checked(itemCheckedStatus);

                    FoundItems.add(ScannedItem);

                }while(c.moveToNext());
            }
        }

        c.close();

        return  FoundItems;
    }

    public boolean DepartmentExists(Department department){
        Log.d(TAG," DepartmentExists");
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+  DBContract.ItemAudit.TABLE_NAME +
                " WHERE " + DBContract.ItemAudit.COLUMN_ITEMS_DEPARTMENT_ID + " = " + department.getId() + ";";

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() <= 0)
        {
            c.close();
            return false;
        } else
        {
            close();
            return true;
        }
    }


}