package com.example.johnnie.mstorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

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

    public static ArrayList<Item> parseItems(JSONObject jsonresponse) throws JSONException{
        ArrayList<Item> ItemsList = new ArrayList<>();
        int loops = 0;
        JSONObject Items = jsonresponse;
        loops = Items.getInt("numberOfResults");
        JSONArray itemsArray = Items.getJSONArray("wrappedResponse");
        for (int i=0; i<loops; i++){
            JSONObject tempitem = itemsArray.getJSONObject(i);
            int Item_ID = tempitem.getInt("id");
            String Item_Name = tempitem.getString("name");
            String Item_Desc = tempitem.getString("description");
            String Item_SKU = tempitem.getString("SKU");
            String Item_Barcode = tempitem.getString("barcode");
            String Item_Category = tempitem.getString("category");
            String Item_Position = tempitem.getString("position");
            String Item_Measurement_Unit = tempitem.getString("measurementUnit");
            int Item_Quantity = tempitem.getInt("quantity");
            JSONObject belongs_to = tempitem.getJSONObject("placedAtDepartment");
            int Item_department_id = belongs_to.getInt("id");

            ItemsList.add(new Item(Item_ID, Item_Name, Item_Desc, Item_Category,
                    Item_Position, Item_Measurement_Unit, Item_SKU, Item_Barcode,
                    Item_Quantity, Item_department_id ));
        }

        return  ItemsList;
    }

    public static ArrayList<Department> parseDepartments(JSONObject jsonresponse) throws JSONException{
        ArrayList<Department> Departments = new ArrayList<>();

        //Bunch of attributes we're gonna need
        int loops = 0;
        String DepartmentName;
        int DepartmentID, BelongsToStorage;

        JSONObject Storages = jsonresponse; //Get Jason object and store it in Storages
        loops = Storages.getInt("numberOfResults"); //Get number of storages the above object contains
        JSONArray storageArray = Storages.getJSONArray("wrappedResponse"); //Get the data for each storage

        for(int i=0; i<loops; i++){ //Loop through each storage and extract the data we want
            JSONObject storageData = storageArray.getJSONObject(i); //Extracting data for the i storage found in storageArray array

            JSONArray departmentArray = storageData.getJSONArray("departments"); //Extracting the whole array for the departments of the current (i) storage
            int loopsForDepartments = departmentArray.length(); //Counting how many times we are going to loop for the departments data to extract them

            if(loopsForDepartments > 0){ //If there are no departments at all in the storage, don't enter
                for(int j=0; j<loopsForDepartments; j++){ //If there's at least one department then start looping
                    JSONObject departmentData = departmentArray.getJSONObject(j); //Extracting data for the i department found in departmentArray array

                    DepartmentID = departmentData.getInt("id"); //Extracting the id
                    DepartmentName = departmentData.getString("name"); //Extracting the name
                    BelongsToStorage = departmentData.getInt("storageId"); //Extracting the id of the storage that the department belongs to

                    Departments.add(new Department(DepartmentID, BelongsToStorage, DepartmentName)); //Store the i department's data in the DepartmentsArray (which is an ObjectArray containing objects of type Department) using the Department's constructor (See Department class)
                }
            }
        }
        return Departments; //After the loop has finished, return the ObjectArray which is named StoragesArray
    }

    public static ArrayList<Item> parseReportItems(JSONArray jsonresponse) throws JSONException{
        ArrayList<Item> ItemsList = new ArrayList<>();
        JSONArray Items = jsonresponse;
        for (int i=0; i < Items.length(); i++){
            JSONObject tempitem = Items.getJSONObject(i);
            int Item_ID = tempitem.getInt("item_id");
            String Item_Name = tempitem.getString("item_name");
            String Item_Desc = tempitem.getString("item_desc");
            String Item_SKU = tempitem.getString("item_sku");
            String Item_Barcode = tempitem.getString("item_barcode");
            String Item_Category = tempitem.getString("item_category");
            String Item_Position = tempitem.getString("item_position");
            String Item_Measurement_Unit = tempitem.getString("item_measurement_unit");
            int Item_Quantity = tempitem.getInt("item_quantity");
            int Item_department_id = tempitem.getInt("item_department_id");
            int Item_checked = tempitem.getInt("item_checked");
            int Item_quantity_found = tempitem.getInt("item_quantity_found");
            String Item_notes = tempitem.getString("item_notes");
            String Item_date = tempitem.getString("date_modified");

            Item TItem = new Item(Item_ID, Item_Name, Item_Desc, Item_Category,
                    Item_Position, Item_Measurement_Unit, Item_SKU, Item_Barcode,
                    Item_Quantity, Item_department_id );
            TItem.setIs_checked(Item_checked);
            TItem.setNotes(Item_notes);
            TItem.setQuantity_found(Item_quantity_found);
            TItem.setDate_modified(Item_date);

            ItemsList.add(TItem);
        }

        return  ItemsList;
    }

}