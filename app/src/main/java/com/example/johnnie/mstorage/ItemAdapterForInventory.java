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

public class ItemAdapterForInventory extends BaseAdapter {

    private Context context;
    private ArrayList<Item> itemsList;

    public ItemAdapterForInventory(Context context, ArrayList<Item> list) {
        this.context = context;
        itemsList = list;
    }

    @Override
    public int getCount(){
        return itemsList.size();
    }

    @Override
    public Object getItem(int position){
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = itemsList.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_items, null);
        }

        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName); //Getting reference from the TextView for the name
        TextView ItemCode = (TextView) convertView.findViewById(R.id.ItemCode); //Getting reference from the TextView for the id
        TextView ItemMesUnit = (TextView) convertView.findViewById(R.id.ItemMeasurementUnit); //Getting reference from the TextView for the id
        TextView ItemQuantity = (TextView) convertView.findViewById(R.id.ItemQuantity); //Getting reference from the TextView for the id

        ItemName.setText("Item Name: " + item.getName()); //Setting the name
        ItemCode.setText("Item Code: " + item.getId()); //Setting the Code
        ItemMesUnit.setText("Measurement Unit: " + item.getMeasurement_unit()); //Setting the measurement unit
        ItemQuantity.setText("Quantity: " + item.getQuantity()); //Setting the measurement unit

        return convertView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}
