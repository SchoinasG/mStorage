package com.example.johnnie.mstorage;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemsInventory extends AppCompatActivity {

    private ListView ItemsListView;
    private DBHandler dbHandler;
    private int Selected_Department_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_inventory);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get reference from listview
        ItemsListView = (ListView) findViewById(R.id.itemsList);

        //Get Departments data from extra
        Bundle getTheDepartmentID = getIntent().getExtras();
        Selected_Department_ID = getTheDepartmentID.getInt("Department_ID");

        dbHandler = new DBHandler(ItemsInventory.this);

        populateStoragesListView();
    }

    public void populateStoragesListView(){
        ArrayList<Item> ItemsList = new ArrayList<>();
        ItemsList.clear();

        String query = "SELECT * FROM " + DBContract.ItemEntry.TABLE_NAME; //+ " WHERE " + DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID + "=" + Selected_Department_ID;
        Cursor c = dbHandler.selectQuery(query);

        if(c != null && c.getCount() != 0){
            if(c.moveToFirst()){
                do{
                    int itemId = c.getInt(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_ID));
                    String itemName = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_NAME));
                    String itemDesc = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_DESCRIPTION));
                    String itemCategory = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_CATEGORY));
                    String itemPosition = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_POSITION));
                    String itemMesUnit = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_MEASUREMENT));
                    String itemSKU = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_SKU));
                    String itemBarcode = c.getString(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_BARCODE));
                    int itemQuantity = c.getInt(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEM_QUANTITY));
                    int departmentID = c.getInt(c.getColumnIndex(DBContract.ItemEntry.COLUMN_ITEMS_DEPARTMENT_ID));

                    Item item = new Item(itemId, itemName, itemDesc, itemCategory, itemPosition, itemMesUnit, itemSKU, itemBarcode, itemQuantity, departmentID);

                    ItemsList.add(item);
                }while(c.moveToNext());
            }
        }

        c.close();

        ItemAdapterForInventory itemsAdapter = new ItemAdapterForInventory(ItemsInventory.this, ItemsList);
        ItemsListView.setAdapter(itemsAdapter);

    }
}
