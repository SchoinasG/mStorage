package com.example.johnnie.mstorage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ItemDetailsInventory extends AppCompatActivity {

    private TextView ItemName, ItemCode, ItemDescription, ItemsDepartmentId, ItemCategory, ItemPosition, ItemQuantity, ItemMesUnit;
    private Button DetailsButton;
    private String Item_Name, Item_Code, Item_Description, Item_Category, Item_Position, Item_Mes_Unit;
    private int Items_Department_ID, Item_Quantity;
    private boolean detailsVisibility = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_inventory);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get reference from listview
        ItemName = (TextView) findViewById(R.id.itemName);
        ItemCode = (TextView) findViewById(R.id.itemCode);
        ItemDescription = (TextView) findViewById(R.id.itemDescription);
        ItemsDepartmentId = (TextView) findViewById(R.id.itemsDepartmentId);
        ItemCategory = (TextView) findViewById(R.id.itemCategory);
        ItemPosition = (TextView) findViewById(R.id.itemPosition);
        ItemQuantity = (TextView) findViewById(R.id.itemQuantity);
        ItemMesUnit = (TextView) findViewById(R.id.itemMesUnit);
        DetailsButton = (Button) findViewById(R.id.detailsButton);

        //Get Departments data from extra
        Bundle getTheItem = getIntent().getExtras();
        Item_Name = getTheItem.getString("Item_Name");
        Item_Code = getTheItem.getString("Item_Code");
        Item_Description = getTheItem.getString("Item_Description");
        Items_Department_ID = getTheItem.getInt("Items_Department_ID");
        Item_Category = getTheItem.getString("Item_Category");
        Item_Position = getTheItem.getString("Item_Position");
        Item_Quantity = getTheItem.getInt("Item_Quantity");
        Item_Mes_Unit = getTheItem.getString("Item_Mes_Unit");

        ItemName.setText("Item name: " + Item_Name);
        ItemCode.setText("Item SKU: " + Item_Code);
        ItemDescription.setText("Item description: " + Item_Description);
        ItemsDepartmentId.setText("Item is in department: " + Integer.toString(Items_Department_ID));
        ItemCategory.setText("Item category: " + Item_Category);
        ItemPosition.setText("Item position: " + Item_Position);
        ItemQuantity.setText(Integer.toString(Item_Quantity));
        ItemMesUnit.setText(Item_Mes_Unit);
    }

    public void viewDetails(View v){

        if(detailsVisibility){
            ItemDescription.setVisibility(View.GONE);
            ItemsDepartmentId.setVisibility(View.GONE);
            ItemCategory.setVisibility(View.GONE);
            ItemPosition.setVisibility(View.GONE);
            DetailsButton.setText("DETAILS    ▼");
            detailsVisibility = false;
        } else {
            ItemDescription.setVisibility(View.VISIBLE);
            ItemsDepartmentId.setVisibility(View.VISIBLE);
            ItemCategory.setVisibility(View.VISIBLE);
            ItemPosition.setVisibility(View.VISIBLE);
            DetailsButton.setText("DETAILS    ▲");
            detailsVisibility = true;
        }
    }
}
