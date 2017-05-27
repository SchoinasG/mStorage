package com.example.johnnie.mstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johnnie on 20/5/2017.
 */

public class StoragesAdapter extends ArrayAdapter<Storage> {

    public StoragesAdapter(Context context, ArrayList<Storage> Storages) {
            super(context, 0, Storages);
            }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Storage storage = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_storage, parent, false);
            }
            // Lookup view for data population
            TextView storageID = (TextView) convertView.findViewById(R.id.storage_id);
            TextView storageName = (TextView) convertView.findViewById(R.id.storage_name);
            // Populate the data into the template view using the data object
            storageID.setText("Storage ID : "+storage.getId());
            storageName.setText("Storage Name : "+storage.getName());
            // Return the completed view to render on screen
            return convertView;
    }
}
