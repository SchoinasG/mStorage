package com.example.johnnie.mstorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Masterace on 28/5/2017.
 */

public class JasonParser {

    public static ArrayList<Storage> parseResponse(JSONObject jsonresponse) throws JSONException {

        //Bunch of attributes we're gonna need
        int loops = 0;
        String StorageName, DepartmentName;
        int StorageID, DepartmentID, BelongsToStorage;

        ArrayList<Storage> StoragesArray = new ArrayList<>();

        JSONObject Storages = jsonresponse; //Get Jason object and store it in Storages
        loops = Storages.getInt("numberOfResults"); //Get number of storages the above object contains

        JSONArray storageArray = Storages.getJSONArray("wrappedResponse"); //Get the data for each storage

        for(int i=0; i<loops; i++){ //Loop through each storage and extract the data we want

            ArrayList<Department> DepartmentsArray = new ArrayList<>();

            JSONObject storageData = storageArray.getJSONObject(i); //Extracting data for the i storage found in storageArray array

            StorageName = storageData.getString("name"); //Extracting the name
            StorageID = storageData.getInt("id"); //Extracting the id

            JSONArray departmentArray = storageData.getJSONArray("departments"); //Extracting the whole array for the departments of the current (i) storage
            int loopsForDepartments = departmentArray.length(); //Counting how many times we are going to loop for the departments data to extract them

            if(loopsForDepartments > 0){ //If there are no departments at all in the storage, don't enter
                for(int j=0; j<loopsForDepartments; j++){ //If there's at least one department then start looping
                    JSONObject departmentData = departmentArray.getJSONObject(j); //Extracting data for the i department found in departmentArray array

                    DepartmentID = departmentData.getInt("id"); //Extracting the id
                    DepartmentName = departmentData.getString("name"); //Extracting the name
                    BelongsToStorage = departmentData.getInt("storageId"); //Extracting the id of the storage that the department belongs to

                    DepartmentsArray.add(new Department(DepartmentID, BelongsToStorage, DepartmentName)); //Store the i department's data in the DepartmentsArray (which is an ObjectArray containing objects of type Department) using the Department's constructor (See Department class)
                }
            }

            StoragesArray.add(new Storage(StorageID, StorageName, DepartmentsArray)); //Store the i storage's data in the StoragesArray (which is an ObjectArray objects of type Storage) using the Storage's constructor (See Storage class)
        }

        return StoragesArray; //After the loop has finished, return the ObjectArray which is named StoragesArray
    }

}