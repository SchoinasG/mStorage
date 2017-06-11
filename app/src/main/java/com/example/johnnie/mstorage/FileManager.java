package com.example.johnnie.mstorage;

import android.content.Context;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Johnnie on 10/6/2017.
 */

public class FileManager {

    public FileManager(){

    }

    public boolean writeFileonSd(Context context, Department department, JSONArray JaBody) {
        boolean flag = false;
        String storageState = Environment.getExternalStorageState();
        //Get The date
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy-k-mm");
        String Date = format.format(curDate);

        String sFileName = "Sid="+String.valueOf(department.getStorageid())+"#Did="+String.valueOf(department.getId())+"#Date="+Date+"#.txt";
        if(Environment.MEDIA_MOUNTED.equals(storageState)) {
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File root = new File(sdCard.getAbsolutePath()+"/audits");

                try{
                    if(root.mkdirs()) {
                        Log.d("====>","Directory created");
                    } else {
                        Log.d("====>","Directory is not created");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                //ACCESS DENIED TO CREATE FILE IN EXTERNAL STORAGE
                File gpxfile = new File(root, sFileName);
                if (!gpxfile.exists()) {
                    gpxfile.createNewFile();
                }
                FileWriter writer = new FileWriter(gpxfile);
                String sBody = JaBody.toString();
                writer.append(sBody);
                writer.flush();
                writer.close();
                Log.d("WRITERCLOSED","==================");
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                flag = true;
            } catch (IOException e) {
                Log.d("EXCEPTWRITER","==================");
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
