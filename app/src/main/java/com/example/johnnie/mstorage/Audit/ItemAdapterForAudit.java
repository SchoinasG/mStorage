package com.example.johnnie.mstorage.Audit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.johnnie.mstorage.Item;
import com.example.johnnie.mstorage.R;
import java.util.ArrayList;

/**
 * Created by Masterace on 10/6/2017.
 */

public class ItemAdapterForAudit extends BaseAdapter {

    private Context context;
    private ArrayList<Item> itemsList;

    public ItemAdapterForAudit(Context context, ArrayList<Item> list) {
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

        int quantity = item.getQuantity();
        int quantity_found = item.getQuantity_found();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_items_audit, null);
        }

        LinearLayout itemRowBackground = (LinearLayout) convertView.findViewById(R.id.item_row_background); //Getting reference from item's_row layout to change background color according to quantity found
        Drawable row_background = itemRowBackground.getBackground();

        GradientDrawable gradientDrawable_forItemRow = (GradientDrawable) row_background;

        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName); //Getting reference from the TextView for the name
        TextView ItemCode = (TextView) convertView.findViewById(R.id.ItemCode); //Getting reference from the TextView for the Code
        TextView ItemMesUnit = (TextView) convertView.findViewById(R.id.ItemMeasurementUnit); //Getting reference from the TextView for the mesUnit
        TextView ItemQuantity = (TextView) convertView.findViewById(R.id.ItemQuantity); //Getting reference from the TextView for the quantity
        TextView ItemQuantityFound = (TextView) convertView.findViewById(R.id.ItemQuantityFound); //Getting reference from the TextView for the quantityFound

        if(quantity_found>quantity){
            gradientDrawable_forItemRow.setColor(Color.parseColor("#ed0000"));
        } else if(quantity_found<quantity){
            if(quantity_found==0){
                gradientDrawable_forItemRow.setColor(Color.parseColor("#247087"));
            } else {
                gradientDrawable_forItemRow.setColor(Color.parseColor("#fd6721"));
            }
        } else if (quantity_found==quantity){
            gradientDrawable_forItemRow.setColor(Color.parseColor("#00aa00"));
        }

        ItemName.setText("Item Name: " + item.getName()); //Setting the name
        ItemCode.setText("Item Code: " + item.getId()); //Setting the Code
        ItemMesUnit.setText("Measurement Unit: " + item.getMeasurement_unit()); //Setting the measurement unit
        ItemQuantity.setText(" / " + item.getQuantity()); //Setting the measurement unit
        ItemQuantityFound.setText("Quantity: " + item.getQuantity_found());

        return convertView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}
