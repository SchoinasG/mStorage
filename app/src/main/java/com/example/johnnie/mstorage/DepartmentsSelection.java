package com.example.johnnie.mstorage;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.list;

public class DepartmentsSelection extends AppCompatActivity {

    private ListView DepartmentsListView;
    private ListAdapter DepartmentsList;
    private int Selected_Storage_ID;
    private String Selected_Storage_Name;

    private ArrayList<Department> StorageDepartments;

    private DBHandler dbHandler;


    private static final String JSON_OBJECT_REQUEST_URL = "http://192.168.1.7:4567/departments/"; //Everyone change this ip address to the corresponding ip address of your emulator (ipconfig IPv4 address)
    ProgressDialog progressDialog;

    private static final String TAG = "DepartmensSelection";

    private ArrayList<Item> ItemsToDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_selection);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get Departments data from extra
        Bundle getTheID = getIntent().getExtras();
        StorageDepartments = (ArrayList<Department>) getTheID.getSerializable("Departments");
        Selected_Storage_ID = getTheID.getInt("Storage_ID");
        Selected_Storage_Name = getTheID.getString("Storage_Name");

        //Get reference from listview
        DepartmentsListView = (ListView) findViewById(R.id.departmentsList);

        dbHandler = new DBHandler(this);

        progressDialog = new ProgressDialog(this);

        populateDepartmentList(StorageDepartments);
    }

    public void populateDepartmentList(ArrayList<Department> List) {

        DepartmentsList = new DepartmentAdapter(this, List);
        DepartmentsListView.setAdapter(DepartmentsList);

        DepartmentsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Nothing yet
                    }
                }
        );
    }

    public void ImportDepartments(View view){

        StringBuffer responseText = new StringBuffer();
        responseText.append("The following were selected...\n");

        Storage storage = new Storage(Selected_Storage_ID, Selected_Storage_Name, null);
        dbHandler.addStorage(storage);
        dbHandler.close();

        for(int i=0; i<StorageDepartments.size(); i++){
            Department depp = StorageDepartments.get(i);

            if(depp.isSelected()){
                responseText.append("\n" + depp.getName());
                dbHandler.addDepartment(depp);
                String idtostring = Integer.toString(depp.getId());
                String current_Departments_Items_Url =  JSON_OBJECT_REQUEST_URL + idtostring + "/items";
                volleyJsonObjectRequest(current_Departments_Items_Url);
            }
        }

    }

    public void volleyJsonObjectRequest(String url){
        String  REQUEST_TAG = "";
        progressDialog.setMessage("Loading Items...!");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressDialog.hide();
                        try {
                            ItemsToDB = JasonParser.parseItems(response); //Send Jason response to JasonParser class using method parseResponse which will extract all the data we need for storages and departments and store them in Storage and Department objects, inside ObjectArrays
                            //parseResponse will return an array containing objects of type Storage. A storage object also contains an array with its departments objects
                            for(final Item item_to_db :  ItemsToDB) {
                                dbHandler.addItems(item_to_db);
                            }
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

    public void CheckAll(View view){
        //Nothing yet
    }
}
