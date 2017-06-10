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

/**
 * Created by Johnnie on 10/6/2017.
 */

public class FileManager {

    public FileManager(){

    }

    public void writeFileonSd(Context context, int StorId, int DepId, String DepName, String Date, JSONArray JaBody) {
        String storageState = Environment.getExternalStorageState();
        String sFileName = "Sid="+String.valueOf(StorId)+"-Did="+String.valueOf(DepId)+"-Date="+Date+".txt";
        if(Environment.MEDIA_MOUNTED.equals(storageState)) {
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File root = new File(sdCard.getAbsolutePath()+"/");

//                try{
//                    if(root.mkdirs()) {
//                        Log.d("====>","Directory created");
//                    } else {
//                        Log.d("====>","Directory is not created");
//                    }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
                Log.d("MY ROOT",""+root);
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
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
