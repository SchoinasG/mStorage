package com.example.johnnie.mstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Masterace on 6/6/2017.
 */

public class StorageAdapterForInventory extends BaseAdapter {

    Context context;
    ArrayList<Storage> storagesList;

    public StorageAdapterForInventory(Context context, ArrayList<Storage> list){
        this.context = context;
        storagesList = list;
    }

    @Override
    public int getCount(){
        return storagesList.size();
    }

    @Override
    public Object getItem(int position){
        return storagesList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Storage storage = storagesList.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_storage, null);
        }

        TextView storageItemName = (TextView) convertView.findViewById(R.id.storageItemName); //Getting reference from the TextView for the name
        TextView storageItemId = (TextView) convertView.findViewById(R.id.storageItemID); //Getting reference from the TextView for the id

        storageItemName.setText("Name: " + storage.getName()); //Setting the name
        storageItemId.setText("Id: " + storage.getId()); //Setting the id

        return convertView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}
