package com.example.johnnie.mstorage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ImportData extends AppCompatActivity {
    private static final String JSON_OBJECT_REQUEST_URL = "http://10.0.2.2:4567/storages";
    ProgressDialog progressDialog;
    private static final String TAG = "ImportData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_data);
        progressDialog = new ProgressDialog(this);
        volleyJsonObjectRequest(JSON_OBJECT_REQUEST_URL);
        Log.d(TAG, "On ImportData Activity");
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
                        progressDialog.hide();                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG+"ERROR IS: "+error);
                progressDialog.hide();
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }
}
