package com.example.johnnie.mstorage.Audit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.johnnie.mstorage.DBContract;
import com.example.johnnie.mstorage.DBHandler;
import com.example.johnnie.mstorage.Item;
import com.example.johnnie.mstorage.ItemAdapterForInventory;
import com.example.johnnie.mstorage.R;

import java.util.ArrayList;

/**
 * Created by Johnnie on 9/6/2017.
 */

public class ItemAudit extends AppCompatActivity {
    private ListView ItemsListView;
    private DBHandler dbHandler;
    private int Selected_Department_ID;
    private Intent i;

    private static final String TAG = ("/////--ItemAudit");

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

        dbHandler = new DBHandler(ItemAudit.this);

        populateStoragesListView();
    }

    public void populateStoragesListView(){
        ArrayList<Item> ItemsList = new ArrayList<>();
        ItemsList.clear();

        ItemsList = dbHandler.ItemPopulator(Selected_Department_ID);

        ItemAdapterForInventory itemsAdapter = new ItemAdapterForInventory(ItemAudit.this, ItemsList);
        ItemsListView.setAdapter(itemsAdapter);

        ItemsListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        Item ItemClicked = (Item) parent.getItemAtPosition(position); //get the department object that the user clicked on and store it in a local object of type Department to manipulate it as we want

                        i = new Intent(getApplicationContext(), ItemDetailsAudit.class); //Initiate intent object
//                        i.putExtra("Item_Name", ItemClicked.getName());
//                        i.putExtra("Item_Code", ItemClicked.getSKU());
//                        i.putExtra("Item_Description", ItemClicked.getDescription());
//                        i.putExtra("Items_Department_ID", ItemClicked.getItems_department_id());
//                        i.putExtra("Item_Category", ItemClicked.getCategory());
//                        i.putExtra("Item_Position", ItemClicked.getPosition());
//                        i.putExtra("Item_Quantity", ItemClicked.getQuantity());
//                        i.putExtra("Item_Mes_Unit", ItemClicked.getMeasurement_unit());
                        i.putExtra("ClickedItem",ItemClicked);
                        startActivityForResult(i,1); //Start the new activity
                    }
                }
        );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateStoragesListView();
    }
}
