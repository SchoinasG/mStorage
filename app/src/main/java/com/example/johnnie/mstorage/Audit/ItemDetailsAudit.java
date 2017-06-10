package com.example.johnnie.mstorage.Audit;

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

import com.example.johnnie.mstorage.DBHandler;
import com.example.johnnie.mstorage.Item;
import com.example.johnnie.mstorage.R;

import org.w3c.dom.Text;

/**
 * Created by Johnnie on 9/6/2017.
 */

public class ItemDetailsAudit extends AppCompatActivity{

    private TextView ItemName, ItemCode, ItemDescription, ItemsDepartmentId, ItemCategory, ItemPosition, ItemQuantity, ItemMesUnit, ItemNotes, ItemDateM, ItemQuantityFound;
    private Button DetailsButton, AddtoQuantity, SubFromQuantity, SetNote;
    private EditText SetQuantity;
    private String TextForItem;
    private int TempItemQuantity, InitialItemQuantity;
    private boolean detailsVisibility = true;

    private DBHandler dbHandler ;

    private Item ClickedItem;

    private AlertDialog.Builder builder;

    private static final String TAG = ("/////--ItemDetailsAudit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_audit);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        Intent i = getIntent();
        ClickedItem = (Item)i.getSerializableExtra("ClickedItem");
        dbHandler = new DBHandler(ItemDetailsAudit.this);

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
        ItemNotes = (TextView) findViewById(R.id.itemNotes);
        ItemQuantityFound = (TextView) findViewById(R.id.itemQuantityFound);
        ItemDateM = (TextView) findViewById(R.id.itemDateModified);

        // Quantity setters
        SetQuantity = (EditText) findViewById(R.id.QuantitySetter);
        AddtoQuantity = (Button) findViewById(R.id.AddToQuantity);
        SubFromQuantity = (Button) findViewById(R.id.SubFromQuantity);
        //Add NOte
        SetNote = (Button) findViewById(R.id.AddNote);



        ItemName.setText("Item name: " + ClickedItem.getName());
        ItemCode.setText("Item SKU: " + ClickedItem.getSKU());
        ItemDescription.setText("Item description: " + ClickedItem.getDescription());
        ItemsDepartmentId.setText("Item is in department: " + ClickedItem.getId());
        ItemCategory.setText("Item category: " + ClickedItem.getCategory());
        ItemPosition.setText("Item position: " + ClickedItem.getPosition());
        ItemQuantity.setText(Integer.toString(ClickedItem.getQuantity()));
        ItemMesUnit.setText(ClickedItem.getMeasurement_unit());
        SetQuantity.setText(Integer.toString(ClickedItem.getQuantity_found()));
        //Additional Audit collumns
        ItemNotes.setText(ClickedItem.getNotes());
        ItemQuantityFound.setText(Integer.toString(ClickedItem.getQuantity_found()));
        ItemDateM.setText(ClickedItem.getDate_modified());

        TempItemQuantity = ClickedItem.getQuantity_found();
        InitialItemQuantity = ClickedItem.getQuantity_found();

        AddtoQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG ,"Clicked Add");
                TempItemQuantity++;
                ClickedItem.setQuantity_found(TempItemQuantity);
                UpdateIt(ClickedItem);
            }
        });

        SubFromQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG ,"Clicked Sub");
                TempItemQuantity--;
                ClickedItem.setQuantity_found(TempItemQuantity);
                UpdateIt(ClickedItem);
            }
        });


        SetNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Add note");
                builder = new AlertDialog.Builder(ItemDetailsAudit.this);
                final EditText input = new EditText(ItemDetailsAudit.this);
                builder.setView(input);
                builder.setTitle("Add Note to Item");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // ADD DIALOG TO ADD NOTE
                        String InitialText = TextForItem;
                        TextForItem = input.getText().toString();
                        ClickedItem.setNotes(TextForItem);
                        if(dbHandler.UpdateNoteOfItem(ClickedItem)){
                            ItemNotes.setText(ClickedItem.getNotes());
                            Toast.makeText(ItemDetailsAudit.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            TextForItem = InitialText;
                            ClickedItem.setNotes(InitialText);
                            Toast.makeText(ItemDetailsAudit.this, "An Error occurred try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });

                builder.show();

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

    private void UpdateIt(Item theitem){

        Log.d("THIS IS TO BE UPDATED",""+theitem.getQuantity_found());
        if( dbHandler.UpdateQuantityFound(theitem) )
        {
            SetQuantity.setText(String.valueOf(TempItemQuantity));
            ItemQuantityFound.setText(String.valueOf(ClickedItem.getQuantity_found()));
            InitialItemQuantity = TempItemQuantity;
            Toast.makeText(ItemDetailsAudit.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
        }
        else{
            TempItemQuantity = InitialItemQuantity;
            ClickedItem.setQuantity_found(InitialItemQuantity);
            Toast.makeText(ItemDetailsAudit.this, "An Error occurred try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent intent = new Intent();
        setResult(RESULT_OK,intent );
        finish();
    }

}
