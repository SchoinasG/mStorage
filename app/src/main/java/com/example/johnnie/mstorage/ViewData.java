package com.example.johnnie.mstorage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private ListView StoragesListView;
    private DBHandler dbHandler;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get reference from listview
        StoragesListView = (ListView) findViewById(R.id.storagesList);

        dbHandler = new DBHandler(ViewData.this);

        populateStoragesListView();
    }

    public void populateStoragesListView(){
        ArrayList<Storage> StoragesList = new ArrayList<>();
        StoragesList.clear();

        String query = "SELECT * FROM " + DBContract.StorageEntry.TABLE_NAME;
        Cursor c = dbHandler.selectQuery(query);

        if(c != null && c.getCount() != 0){
            if(c.moveToFirst()){
                do{
                    int storageId = c.getInt(c.getColumnIndex(DBContract.StorageEntry.COLUMN_STORAGE_ID));
                    String storageName = c.getString(c.getColumnIndex(DBContract.StorageEntry.COLUMN_STORAGE_NAME));

                    Storage storage = new Storage(storageId, storageName, null);

                    StoragesList.add(storage);
                }while(c.moveToNext());
            }
        }

        c.close();

        StorageAdapterForInventory storagesAdapter = new StorageAdapterForInventory(ViewData.this, StoragesList);
        StoragesListView.setAdapter(storagesAdapter);

        StoragesListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        Storage StorageClicked = (Storage) parent.getItemAtPosition(position); //get the storage object that the user clicked on and store it in a local object of type Storage to manipulate it as we want

                        i = new Intent(getApplicationContext(), DepartmentsSelectionInventory.class); //Initiate intent object
                        i.putExtra("Storage_ID", StorageClicked.getId());
                        startActivity(i); //Start the new activity
                    }
                }
        );

    }

    public void wipeData(View v){
        dbHandler.wipeData();
        populateStoragesListView();
        Toast.makeText(getApplicationContext(), "Storages successfully deleted.", Toast.LENGTH_LONG).show();
    }
}
