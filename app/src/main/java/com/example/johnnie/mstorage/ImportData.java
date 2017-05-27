package com.example.johnnie.mstorage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.johnnie.mstorage.R.drawable.view;

public class ImportData extends AppCompatActivity {
    private static final String JSON_OBJECT_REQUEST_URL = "http://10.0.2.2:4567/storages";
    ProgressDialog progressDialog;
    private static final String TAG = "ImportData";
    private String ObjectinString;
    ArrayList<Storage> StList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_data);
        progressDialog = new ProgressDialog(this);
        volleyJsonObjectRequest(JSON_OBJECT_REQUEST_URL);
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
                        StList   = JsonParser.parseResponse(response);
                        populateStorageList(StList);
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



    private void populateStorageList(ArrayList<Storage> List) {
        // Create the adapter to convert the array to views
        StoragesAdapter adapter = new StoragesAdapter(this, List);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.storage_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Storage thest = (Storage) parent.getItemAtPosition(position);
                ArrayList<Department> deps = thest.getStorageDepartments();
                for(Department dep: deps){
                    Log.d("Clicked Storage Name ", ""+dep.getName());
                }
                Intent myIntent = new Intent(ImportData.this, ImportDepartments.class);
                myIntent.putExtra("StorageID", thest.getId());
                startActivity(myIntent);
            }
        });
    }



}
