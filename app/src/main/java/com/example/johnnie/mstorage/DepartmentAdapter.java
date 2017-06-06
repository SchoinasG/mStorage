package com.example.johnnie.mstorage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Masterace on 28/5/2017.
 */

//Created a Custom adapter cause we are not gonna use a pre set one from the android libraries

public class DepartmentAdapter extends ArrayAdapter<Department> { //Custom adapter of type Department because we will be storing Department objects in this adapter

    private Department department;
    private Context context;

    ArrayList<Department> selectedDepartments = new ArrayList<Department>();

    public DepartmentAdapter(Context context, ArrayList<Department> Departments) { //Default constructor stuff
        super(context, R.layout.list_item_department, Departments);
        this.context = context;
    }

    //The structure of this adapter is different from the storage adapter and this happens because we have checkboxes we need to use in this adapter
    //We will need to create a ViewHolder class and store our Views from the xml file in here as attributes
    private class ViewHolder {
        TextView departmentItemName, departmentItemId;
        CheckBox cb;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customView = convertView;
        ViewHolder holder = null;

        if(customView == null){
            LayoutInflater departmentsInflater = LayoutInflater.from(getContext()); //Initiating a LayoutInflater object so we can inflate our custom xml file for the rows of our listview
            customView = departmentsInflater.inflate(R.layout.list_item_department, parent, false); //As you see, the view is in the list_item_department xml file

            holder = new ViewHolder(); //Creating object to use our views

            //Getting our views and storing them in appropriate objects
            holder.departmentItemName = (TextView) customView.findViewById(R.id.departmentItemName);
            holder.departmentItemId = (TextView) customView.findViewById(R.id.departmentItemID);
            holder.cb = (CheckBox) customView.findViewById(R.id.departmentCheckbox);

            //Preparing a click listener for each row's checkbox
            holder.cb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) { //Here, View v actually contains the CheckBox view so
                    CheckBox cb = (CheckBox) v ; //We get the CheckBox view from v and store it in a CheckBox object named cb
                    Department dep = (Department) cb.getTag(); //Get the tag for the repsective department

                    dep.setSelected(cb.isChecked()); //Set checkbox value to the deparment object
                }
            });
        } else {
            holder = (ViewHolder) customView.getTag();
        }

        //Item from adapter gets stored in department object
        department = getItem(position);

        holder.departmentItemName.setText("Department Name: " + department.getName()); //Set department name to TextView
        holder.departmentItemId.setText("Department Id: " + department.getId()); //Set department id
        holder.cb.setChecked(department.isSelected()); //Set checkbox boolean value from value stored in object's attribute
        holder.cb.setTag(department); //Set the object's tag to the checkbox because we are going to need it for the checkbox's click listener above


        return customView;
    }
}
