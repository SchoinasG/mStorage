package com.example.johnnie.mstorage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DepartmentsSelection extends AppCompatActivity {

    private ListView DepartmentsListView;
    private ListAdapter DepartmentsList;
    private int Selected_Storage_ID;
    private String Selected_Storage_Name;

    private ArrayList<Department> StorageDepartments;

    private DBHandler dbHandler;

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
            }
        }

        Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();
    }


    public void CheckAll(View view){
        //Nothing yet
    }
}
