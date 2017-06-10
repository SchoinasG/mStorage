package com.example.johnnie.mstorage.Audit;

/**
 * Created by Johnnie on 8/6/2017.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johnnie.mstorage.DBContract;
import com.example.johnnie.mstorage.DBHandler;
import com.example.johnnie.mstorage.Department;
import com.example.johnnie.mstorage.DepartmentAdapterForInventory;
import com.example.johnnie.mstorage.ItemsInventory;
import com.example.johnnie.mstorage.R;

import java.util.ArrayList;

public class DepartmentAudit extends AppCompatActivity {
    private ListView DepartmentsListView;
    private DBHandler dbHandler;
    private int Selected_Storage_ID;
    private Intent i;

    private static final String TAG = ("/////--DepartmentAudit");

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_selection_inventory);

        //Simple call to functions to set a custom title bar. The custom title bar can be modified in the xml file titlebar.xml in layout folder.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        //Get reference from listview
        DepartmentsListView = (ListView) findViewById(R.id.departmentsList);

        //Get Departments data from extra
        Bundle getTheStorageID = getIntent().getExtras();
        Selected_Storage_ID = getTheStorageID.getInt("Storage_ID");

        dbHandler = new DBHandler(DepartmentAudit.this);

        this.builder = new AlertDialog.Builder(this);

        populateStoragesListView();



    }

    public void populateStoragesListView(){
        ArrayList<Department> DepartmentsList = new ArrayList<>();
        DepartmentsList.clear();

        String query = "SELECT * FROM " + DBContract.DepartmentEntry.TABLE_NAME + " WHERE " + DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE + "=" + Selected_Storage_ID;
        Cursor c = dbHandler.selectQuery(query);

        if(c != null && c.getCount() != 0){
            if(c.moveToFirst()){
                do{
                    int departmentId = c.getInt(c.getColumnIndex(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_ID));
                    String departmentName = c.getString(c.getColumnIndex(DBContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME));
                    int storageID = c.getInt(c.getColumnIndex(DBContract.DepartmentEntry.COLUMN_BELONGS_TO_STORAGE));

                    Department department = new Department(departmentId, storageID, departmentName);

                    DepartmentsList.add(department);
                }while(c.moveToNext());
            }
        }

        c.close();

        DepartmentAdapterForInventory departmentsAdapter = new DepartmentAdapterForInventory(DepartmentAudit.this, DepartmentsList);
        DepartmentsListView.setAdapter(departmentsAdapter);

        DepartmentsListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        final Department DepartmentClicked = (Department) parent.getItemAtPosition(position); //get the department object that the user clicked on and store it in a local object of type Department to manipulate it as we want

                        // Add dialog message
                        builder.setMessage("START NEW AUDIT OR RESUME");
                        // Add the buttons
                        builder.setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                if(dbHandler.CopyToAudit(DepartmentClicked)){
                                    i = new Intent(getApplicationContext(), ItemAudit.class); //Initiate intent object
                                    i.putExtra("Department_ID", DepartmentClicked.getId());
                                    startActivity(i); //Start the new activity
                                }
                                else{
                                    Toast.makeText(DepartmentAudit.this, "An Error has occured", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("RESUME", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                Log.d(TAG,"RESUMING PREVIOUS AUDIT");
                                i = new Intent(getApplicationContext(), ItemAudit.class); //Initiate intent object
                                i.putExtra("Department_ID", DepartmentClicked.getId());
                                startActivity(i); //Start the new activity
                            }
                        });

                        AlertDialog StartAuditAlert = builder.create();

                        StartAuditAlert.show();
                    }
                }
        );

    }

}


