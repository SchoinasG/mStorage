package com.example.johnnie.mstorage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImportDepartments extends AppCompatActivity {
    private static final String JSON_OBJECT_REQUEST_URL = "http://10.0.2.2:4567/deparments";
    ProgressDialog progressDialog;
    private static final String TAG = "Import Departments";
    int StorageId;
    ArrayList<Department> DepList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_departments);
        Intent mIntent = getIntent();
        this.StorageId = mIntent.getIntExtra("StorageID", 0);
        Log.d("THE STORAGE PASSED", ""+this.StorageId );
//        volleyJsonObjectRequest(JSON_OBJECT_REQUEST_URL);
    }

//    public void volleyJsonObjectRequest(String url){
//        String  REQUEST_TAG = "";
//        progressDialog.setMessage("Loading...!");
//        progressDialog.show();
//
//        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//                        progressDialog.hide();
////                        try {
////                            DepList   = JsonParser.parseResponse(response);
////                            populateStorageList(StList);
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG+"ERROR IS: "+error);
//                        progressDialog.hide();
//                    }
//                });
//
//        // Adding JsonObject request to request queue
//        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
//    }
}
