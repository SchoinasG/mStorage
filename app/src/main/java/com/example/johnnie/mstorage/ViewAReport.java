package com.example.johnnie.mstorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.johnnie.mstorage.Audit.ItemAdapterForAudit;
import com.example.johnnie.mstorage.Audit.ItemAudit;
import com.example.johnnie.mstorage.Audit.ItemDetailsAudit;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ViewAReport extends AppCompatActivity {

    private ListView ItemsListView;

    private ArrayList<Item> ItemsList = new ArrayList<>();

    private Report ClickedReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_areport);
        //Get Departments data from extra
        Intent i = getIntent();
        ClickedReport = (Report) i.getSerializableExtra("Report");

        ItemsListView = (ListView) findViewById(R.id.ReportItemsList);

        ItemsList.clear();

        Log.d("","HERE WAS THE ERROR"+ClickedReport.getJsonArray());

        try {
            JSONArray jsonarray = new JSONArray(ClickedReport.getJsonArray());
            ItemsList = JasonParser.parseReportItems(jsonarray);

        }
        catch (JSONException e) {
            Toast.makeText(ViewAReport.this, "Corrupted file", Toast.LENGTH_SHORT).show();
        }

        Log.d("HERE IS JSON",""+ItemsList);

        ItemAdapterForAudit itemsAdapter = new ItemAdapterForAudit(ViewAReport.this, ItemsList);
        ItemsListView.setAdapter(itemsAdapter);

        ItemsListView.setOnItemClickListener( //Item listener for our listview
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //on item clicked

                        Item ItemClicked = (Item) parent.getItemAtPosition(position); //get the department object that the user clicked on and store it in a local object of type Department to manipulate it as we want

                        Intent i = new Intent(getApplicationContext(), ViewReportItem.class); //Initiate intent object
                        i.putExtra("ClickedItem",ItemClicked);
                        startActivityForResult(i,0); //Start the new activity
                    }
                }
        );


    }
}
