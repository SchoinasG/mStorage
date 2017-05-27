package com.example.johnnie.mstorage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Johnnie on 19/5/2017.
 */

public class JsonParser {

    private static final String LOG_TAG = JsonParser.class.getName();


    public static ArrayList<Storage> parseResponse(JSONObject jsonresponse) throws JSONException {

        ArrayList<Storage> StoragesList = new ArrayList<>();


        String WrappedResponse = "wrappedResponse";
        String StorageID = "id";
        String StorageName = "name";
        String StorageDepartments = "departments";

        JSONObject Response = jsonresponse;
        JSONArray wrappedResponse = Response.getJSONArray(WrappedResponse);
        int length = wrappedResponse.length();

        for(int i=0; i < length; i++)
        {
            ArrayList<Department> DepartmentsList = new ArrayList<>();

            JSONObject tempStorage = wrappedResponse.getJSONObject(i);
            String name = tempStorage.getString(StorageName);
            int id = tempStorage.getInt(StorageID);
            JSONArray Dep = tempStorage.getJSONArray(StorageDepartments);
            int DepLength = Dep.length();
            if(DepLength > 0){
                for (int j=0; j < DepLength; j++){
                    JSONObject tempDep = Dep.getJSONObject(j);
                    int tempID = tempDep.getInt("id");
                    int tempSTID = tempDep.getInt("storageId");
                    String tempNAME = tempDep.getString("name");
                    DepartmentsList.add(new Department(tempID, tempSTID, tempNAME));
                }
            }
            StoragesList.add(new Storage(id,name,DepartmentsList));

            Log.i(LOG_TAG ,"THE NAME : "+name);
            Log.i(LOG_TAG ,"THE ID : "+id);
            Log.i(LOG_TAG ,"THE DEP : "+Dep);



        }
        return StoragesList;
    }

    // DEN EIMAI SIGOUROS AN TO XREIAZOMASTE
//    public static ArrayList<Department> parseResponse(JSONObject jsonresponse) throws JSONException {
//
//        ArrayList<Department> DepartmentsList = new ArrayList<>();
//
//
//        String WrappedResponse = "wrappedResponse";
//        String DepartnentID = "id";
//        String DepartmentName = "name";
//        String DepartmentsStorageId = "storageid";
//
//        JSONObject Response = jsonresponse;
//        JSONArray wrappedResponse = Response.getJSONArray(WrappedResponse);
//        int length = wrappedResponse.length();
//
//        for(int i=0; i < length; i++)
//        {
//            /* ANTIGRAPSAME PARSER APO PANW NA VROUME THN LOGIKH GIA NA PAROUME DEP */
//            /* EDW THA EXOUME KAI KSEKATHARISMA ME VASH TO STORAGE ID APO TO INENT */
//            JSONObject tempDepartment = wrappedResponse.getJSONObject(i);
//            String name = tempDepartment.getString(DepartmentName);
//            int id = tempDepartment.getInt(DepartnentID);
//            int DepLength = Dep.length();
//            if(DepLength > 0){
//                for (int j=0; j < DepLength; j++){
//                    JSONObject tempDep = Dep.getJSONObject(j);
//                    int tempID = tempDep.getInt("id");
//                    int tempSTID = tempDep.getInt("storageId");
//                    String tempNAME = tempDep.getString("name");
//                    DepartmentsList.add(new Department(tempID, tempSTID, tempNAME));
//                }
//            }
//            DepartmentsList.add(new Storage(id,name,DepartmentsList));
//
//        }
//        return DepartmentsList;
//    }
}
