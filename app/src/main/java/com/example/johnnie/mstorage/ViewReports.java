package com.example.johnnie.mstorage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ViewReports extends AppCompatActivity {

    private ListView ReportsListView;


    ArrayList<Report> Reportfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Reportfiles = new ArrayList<>();

        setContentView(R.layout.activity_view_reports);



        File sdCard = Environment.getExternalStorageDirectory();
        String path = sdCard+"/audits";

        File directory = new File(path);
        try{
            directory.mkdirs();
        }catch(Exception e){
            e.printStackTrace();
        }

        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(files[i]));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close();
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }

            Report Reportfile = new Report(files[i].getName(), text.toString());
            Reportfiles.add(Reportfile);

//            Toast.makeText(ViewReports.this, "File Array"+text.toString(), Toast.LENGTH_SHORT).show();



        }

        ReportsAdapter ReportsList = new ReportsAdapter(this,Reportfiles);
        ReportsListView = (ListView) findViewById(R.id.ReportsList);
        ReportsListView.setAdapter(ReportsList);

        ReportsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Report ReportClicked = (Report) parent.getItemAtPosition(position); //get the Report object that the user clicked on and store it in a local object of type Department to manipulate it as we want

                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewReports.this);
                        final EditText input = new EditText(ViewReports.this);
                        builder.setView(input);
                        builder.setTitle("Upload ore Preview Report");
                        builder.setPositiveButton("Preview", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(getApplicationContext(), ViewAReport.class); //Initiate intent object
                                i.putExtra("Report", ReportClicked);
                                startActivity(i); //Start the new activity
                            }
                        });
                        builder.setNegativeButton("Upload", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                                uploadImage(ReportClicked);
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    }
                }
        );

    }
    private void uploadImage(Report report){
        //Showing the progress dialog

        final Report UpReport = report;

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.7/mServ/takefile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(ViewReports.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ViewReports.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Getting File Name
                String filename = UpReport.getReport_name();
                String[] split = filename.split("#");
                for (int i = 0; i < (split.length-1); i++) {

                    String[] keyval = split[i].split("=");
                    params.put(keyval[0], keyval[1]);
                }
                String JsonArray = UpReport.getJsonArray();

                //Adding parameters
                params.put("Results", JsonArray);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
