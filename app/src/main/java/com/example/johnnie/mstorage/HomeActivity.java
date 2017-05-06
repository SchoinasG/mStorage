package com.example.johnnie.mstorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onClickImport(View view){
        Intent intent = new Intent(this, ImportData.class);
        startActivity(intent);
    }
    public void onClickView(View view){
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }
    public void onClickAudit(View view){
        Intent intent = new Intent(this, StartAudit.class);
        startActivity(intent);
    }
    public void onClickReport(View view){
        Intent intent = new Intent(this, MakeReport.class);
        startActivity(intent);
    }
}
