package com.example.johnnie.mstorage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Created a Custom adapter cause we are not gonna use a pre set one from the android libraries

class StorageAdapter extends ArrayAdapter<Storage> { //Custom adapter of type Storage because we will be storing Storage objects in this adapter

    public StorageAdapter( Context context, ArrayList<Storage> Storages) { //Default constructor stuff
        super(context, R.layout.list_item_storage, Storages);
    }

    @NonNull
    @Override //Here, our adapter will start to populate each row with the data we want. Each row contains 2 TextViews the Storage Name and Storage ID
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater storagesInflater = LayoutInflater.from(getContext()); //Initiating a LayoutInflater object so we can inflate our custom xml file for the rows of our listview
        View customView = storagesInflater.inflate(R.layout.list_item_storage, parent, false); //As you see, the view is in the list_item_storage xml file

        Storage storage = getItem(position); //Get the storage object from the respective position of the adapter and use it below to extract storage name and id

        TextView storageItemName = (TextView) customView.findViewById(R.id.storageItemName); //Getting reference from the TextView for the name
        TextView storageItemId = (TextView) customView.findViewById(R.id.storageItemID); //Getting reference from the TextView for the id

        storageItemName.setText("Name: " + storage.getName()); //Setting the name
        storageItemId.setText("Id: " + storage.getId()); //Setting the id

        return customView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}
