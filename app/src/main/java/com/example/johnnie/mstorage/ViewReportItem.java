package com.example.johnnie.mstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnnie.mstorage.Audit.ItemDetailsAudit;
public class ViewReportItem extends AppCompatActivity {

    private TextView ItemName, ItemCode, ItemDescription, ItemsDepartmentId, ItemCategory, ItemPosition, ItemQuantity, ItemMesUnit, ItemNotes, ItemDateM, ItemQuantityFound, Note;
    private Button DetailsButton;
    private boolean detailsVisibility = true;

    private DBHandler dbHandler ;

    private Item ClickedItem;

    private AlertDialog.Builder builder;

    private static final String TAG = ("/////--ItemDetailsAudit");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_item);

        Intent i = getIntent();
        ClickedItem = (Item)i.getSerializableExtra("ClickedItem");

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
        ItemQuantityFound = (TextView) findViewById(R.id.itemQuantityFound);
        ItemDateM = (TextView) findViewById(R.id.itemDateModified);
        Note = (TextView) findViewById(R.id.itemNote);


        ItemName.setText("Item name: " + ClickedItem.getName());
        ItemCode.setText("Item SKU: " + ClickedItem.getSKU());
        ItemDescription.setText("Item description: " + ClickedItem.getDescription());
        ItemsDepartmentId.setText("Item is in department: " + ClickedItem.getId());
        ItemCategory.setText("Item category: " + ClickedItem.getCategory());
        ItemPosition.setText("Item position: " + ClickedItem.getPosition());
        ItemQuantity.setText(Integer.toString(ClickedItem.getQuantity()));
        ItemMesUnit.setText(ClickedItem.getMeasurement_unit());
        //Additional Audit collumns
        ItemQuantityFound.setText(Integer.toString(ClickedItem.getQuantity_found()));
        ItemDateM.setText(ClickedItem.getDate_modified());
        Note.setText(ClickedItem.getNotes());

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
