package com.example.johnnie.mstorage.Audit;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.johnnie.mstorage.R;

/**
 * Created by Johnnie on 9/6/2017.
 */

public class ItemDetailsAudit extends AppCompatActivity{

    private TextView ItemName, ItemCode, ItemDescription, ItemsDepartmentId, ItemCategory, ItemPosition, ItemQuantity, ItemMesUnit;
    private Button DetailsButton, AddtoQuantity, SubFromQuantity;
    private EditText SetQuantity;
    private String Item_Name, Item_Code, Item_Description, Item_Category, Item_Position, Item_Mes_Unit;
    private int Items_Department_ID, Item_Quantity, TempItemQuantity;
    private boolean detailsVisibility = true;

    private static final String TAG = ("/////--ItemDetailsAudit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_audit);

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
        SetQuantity = (EditText) findViewById(R.id.QuantitySeter);
        AddtoQuantity = (Button) findViewById(R.id.AddToQuantity);
        SubFromQuantity = (Button) findViewById(R.id.SubFromQuantity);

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


        TempItemQuantity = 0;

        SetQuantity.setText(String.valueOf(TempItemQuantity));

        AddtoQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG ,"Clicked Add");
                TempItemQuantity++;
                SetQuantity.setText(String.valueOf(TempItemQuantity));
            }
        });

        SubFromQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG ,"Clicked Sub");
                TempItemQuantity--;
                SetQuantity.setText(String.valueOf(TempItemQuantity));
            }
        });

        Log.d(TAG, "On Create");
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
