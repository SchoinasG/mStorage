package com.example.johnnie.mstorage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.johnnie.mstorage.Audit.ItemAudit;

public class AddItem extends AppCompatActivity {

    private EditText Name, Description, Category, Position, SKU, Quantity, InDepartment, Mes;
    private DBHandler dbHandler;
    private String ItemName, ItemDesc, ItemCat, ItemPos, ItemSKU, MesUnit;
    private int ItemQuantity, ItemInDepartment;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        Name = (EditText) findViewById(R.id.itemNameInput);
        Description = (EditText) findViewById(R.id.itemDescInput);
        Category = (EditText) findViewById(R.id.itemCatInput);
        Position = (EditText) findViewById(R.id.itemPosInput);
        SKU = (EditText) findViewById(R.id.itemSKUInput);
        Quantity = (EditText) findViewById(R.id.itemQuantityInput);
        InDepartment = (EditText) findViewById(R.id.itemDepIDInput);
        Mes = (EditText) findViewById(R.id.itemMesUnitInput);

    }

    public void addItem(View v){

        try{
            if(Name.length()>0 && Description.length()>0 && Category.length()>0 && Position.length()>0 && SKU.length()>0 &&
                    Quantity.length()>0 && InDepartment.length()>0 && Mes.length()>0){
                dbHandler = new DBHandler(this);

                ItemName = Name.getText().toString();
                ItemDesc = Description.getText().toString();
                ItemCat = Category.getText().toString();
                ItemPos = Position.getText().toString();
                ItemSKU = SKU.getText().toString();
                MesUnit = Mes.getText().toString();
                ItemQuantity = Integer.parseInt(Quantity.getText().toString());
                ItemInDepartment = Integer.parseInt(InDepartment.getText().toString());

                if(dbHandler.AddItem(ItemName, ItemDesc, ItemCat, ItemPos, ItemSKU, MesUnit, ItemQuantity, ItemInDepartment)){
                    Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    i = new Intent(this, ItemAudit.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Error adding item. Please try again.", Toast.LENGTH_LONG).show();
                    Name.setText("");
                    Description.setText("");
                    Category.setText("");
                    Position.setText("");
                    SKU.setText("");
                    Quantity.setText("");
                    InDepartment.setText("");
                    Mes.setText("");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill in all the fields and then tap on Add again.", Toast.LENGTH_LONG).show();
            }
            Log.i("", ItemQuantity +  "Is int" );
            Log.i("", ItemInDepartment +  "Is int" );
        } catch(NumberFormatException e) {
            Log.i("", "Item quantity or ItemInDepartment is not int");
            Toast.makeText(getApplicationContext(), "Please check Item Quantity and In Department get INTEGER values only.", Toast.LENGTH_LONG).show();
        }

    }
}
