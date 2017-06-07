package com.example.johnnie.mstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Masterace on 7/6/2017.
 */

public class DepartmentAdapterForInventory extends BaseAdapter {

    private Context context;
    private ArrayList<Department> departmentsList;

    public DepartmentAdapterForInventory(Context context, ArrayList<Department> list) {
        this.context = context;
        departmentsList = list;
    }

    @Override
    public int getCount(){
        return departmentsList.size();
    }

    @Override
    public Object getItem(int position){
        return departmentsList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Department department = departmentsList.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_department_inventory, null);
        }

        TextView departmentItemName = (TextView) convertView.findViewById(R.id.departmentItemName); //Getting reference from the TextView for the name
        TextView departmentItemId = (TextView) convertView.findViewById(R.id.departmentItemID); //Getting reference from the TextView for the id
        TextView departmentStorageId = (TextView) convertView.findViewById(R.id.departmentStorageID); //Getting reference from the TextView for the id

        departmentItemName.setText("Department Name: " + department.getName()); //Setting the name
        departmentItemId.setText("Department Id: " + department.getId()); //Setting the id
        departmentStorageId.setText("In Storage: " + department.getStorageid()); //Setting the id

        return convertView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}
