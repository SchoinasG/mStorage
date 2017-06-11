package com.example.johnnie.mstorage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ImportData extends AppCompatActivity {

    //Bunch of attributes we're gonna need .          //Schoinas - 192.168.1.7 //Telis - 192.168.1.3 //Katerina - 192.168.1.103
    private static final String JSON_OBJECT_REQUEST_URL = "http://192.168.1.103:4567/storages"; //Everyone change this ip address to the corresponding ip address of your emulator (ipconfig IPv4 address)
    ProgressDialog progressDialog;
    private static final String TAG = "ImportData";
    ArrayList<Storage> StList = new ArrayList<>();
    private ListAdapter StoragesList;
    private ListView StoragesListView;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_data);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Call to methods for the Volley Tasks
        progressDialog = new ProgressDialog(this);
        volleyJsonObjectRequest(JSON_OBJECT_REQUEST_URL);
        Log.d(TAG, "On ImportData Activity");

        //Get reference from listview
        StoragesListView = (ListView) findViewById(R.id.storagesList);
    }

    public void volleyJsonObjectRequest(String url){
        String  REQUEST_TAG = "";
        progressDialog.setMessage("Loading...!");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressDialog.hide();
                        try {
                            StList = JasonParser.parseResponse(response); //Send Jason response to JasonParser class using method parseResponse which will extract all the data we need for storages and departments and store them in Storage and Department objects, inside ObjectArrays
                            //parseResponse will return an array containing objects of type Storage. A storage object also contains an array with its departments objects
                            populateStorageList(StList); //So we get the list from the parseResponse and we use it to populate our ListView for the storages selection screen
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG+"ERROR IS: "+error);
                        progressDialog.hide();
                    }
                });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public void populateStorageList(ArrayList<Storage> List) {

        StoragesList = new StorageAdapter(this, List); //Initate our adapter object
        StoragesListView.setAdapter(StoragesList); //Set the adapter to our listview

        StoragesListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        Storage StorageClicked = (Storage) parent.getItemAtPosition(position); //get the storage object that the user clicked on and store it in a local object of type Storage to manipulate it as we want

                        if(StorageClicked.getStorageDepartments().size() == 0){ //If storage contain no departments, show a toast and don't enter the intent
                            Toast toast = Toast.makeText(getApplicationContext(), "This storage contains no departments!", Toast.LENGTH_LONG);
                            toast.show();
                        } else { //If storage contains departments go to departments selection intent
                            i = new Intent(getApplicationContext(), DepartmentsSelection.class); //Initiate intent object
                            i.putExtra("Departments", StorageClicked.getStorageDepartments()); //Pass Departments Array of selected Storage to next activity
                            i.putExtra("Storage_ID", StorageClicked.getId());
                            i.putExtra("Storage_Name", StorageClicked.getName());
                            startActivity(i); //Start the new activity
                        }
                    }
                }
        );
    }
}
