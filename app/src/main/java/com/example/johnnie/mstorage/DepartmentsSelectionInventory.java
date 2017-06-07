package com.example.johnnie.mstorage;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DepartmentsSelectionInventory extends AppCompatActivity {

    private ListView DepartmentsListView;
    private DBHandler dbHandler;
    private int Selected_Storage_ID;

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

        dbHandler = new DBHandler(DepartmentsSelectionInventory.this);

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

        DepartmentAdapterForInventory departmentsAdapter = new DepartmentAdapterForInventory(DepartmentsSelectionInventory.this, DepartmentsList);
        DepartmentsListView.setAdapter(departmentsAdapter);

    }
}