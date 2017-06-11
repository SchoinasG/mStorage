package com.example.johnnie.mstorage.Audit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johnnie.mstorage.AddItem;
import com.example.johnnie.mstorage.DBContract;
import com.example.johnnie.mstorage.DBHandler;
import com.example.johnnie.mstorage.Item;
import com.example.johnnie.mstorage.ItemAdapterForInventory;
import com.example.johnnie.mstorage.R;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Johnnie on 9/6/2017.
 */

public class ItemAudit extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ListView ItemsListView;
    private ImageButton QrScanner;
    private DBHandler dbHandler;
    private int Selected_Department_ID;
    private Intent i;
    private boolean inCamera;
    private ZXingScannerView Qrview;
    private static final String TAG = ("/////--ItemAudit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_audit);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get reference from Views
        ItemsListView = (ListView) findViewById(R.id.itemsList);
        QrScanner = (ImageButton) findViewById(R.id.ScanQrCode);

        //Get Departments data from extra
        Bundle getTheDepartmentID = getIntent().getExtras();
        Selected_Department_ID = getTheDepartmentID.getInt("Department_ID");

        dbHandler = new DBHandler(ItemAudit.this);

        populateStoragesListView();

        inCamera = false;
    }

    public void populateStoragesListView(){
        ArrayList<Item> ItemsList = new ArrayList<>();
        ItemsList.clear();

        ItemsList = dbHandler.ItemPopulator(Selected_Department_ID);

        ItemAdapterForAudit itemsAdapter = new ItemAdapterForAudit(ItemAudit.this, ItemsList);
        ItemsListView.setAdapter(itemsAdapter);

        ItemsListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        Item ItemClicked = (Item) parent.getItemAtPosition(position); //get the department object that the user clicked on and store it in a local object of type Department to manipulate it as we want

                        i = new Intent(getApplicationContext(), ItemDetailsAudit.class); //Initiate intent object
                        i.putExtra("ClickedItem",ItemClicked);
                        startActivityForResult(i,0); //Start the new activity
                    }
                }
        );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateStoragesListView();
    }

    public void StartQrScan(View v){
        Qrview = new ZXingScannerView(this);
        setContentView(Qrview);
        Qrview.setResultHandler(this);
        Qrview.startCamera();
        inCamera = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            Qrview.stopCamera();
        }
        catch (RuntimeException exception){
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
//                && keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0 && inCamera) {
//            Log.d("CDATA", "onKeyDown Called");
//            Qrview.stopCamera();
//            setContentView(R.layout.activity_items_audit);
//            populateStoragesListView();
//            inCamera = false;
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void handleResult(Result result) {
        String ScannedQrCode = result.getText();
        ArrayList<Item> ScannedItems = dbHandler.getItemsfromQr(ScannedQrCode , Selected_Department_ID);
        Toast.makeText(this, "Qr Scanned", Toast.LENGTH_SHORT).show();
        if (ScannedItems.size() > 0){

            AlertDialog.Builder builder = new AlertDialog.Builder(ItemAudit.this);
            // Add dialog message
            builder.setTitle(ScannedItems.size()+" Items matching the Qr");
            // Add the buttons
            final ItemAdapterForAudit QrItemsAdapter = new ItemAdapterForAudit(ItemAudit.this, ScannedItems);
            ItemsListView.setAdapter(QrItemsAdapter);
            builder.setAdapter(QrItemsAdapter, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    Item ItemClicked = (Item) QrItemsAdapter.getItem(position); //get the department object that the user clicked on and store it in a local object of type Department to manipulate it as we want
                    i = new Intent(getApplicationContext(), ItemDetailsAudit.class); //Initiate intent object
                    i.putExtra("ClickedItem",ItemClicked);
                    startActivityForResult(i,0);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //do whatever you want the back key to do
                    Qrview.resumeCameraPreview(ItemAudit.this);
                }
            });

            AlertDialog QrItemsPick = builder.create();

            QrItemsPick.show();

        }else{
            //Only one Item found
            Toast.makeText(this, "No Items found with this Qr", Toast.LENGTH_SHORT).show();
            Qrview.resumeCameraPreview(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.audit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.addItem:
                i = new Intent(this, AddItem.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
