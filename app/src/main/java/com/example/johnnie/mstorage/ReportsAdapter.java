package com.example.johnnie.mstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johnnie on 11/6/2017.
 */

public class ReportsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Report> ReportList;

    public ReportsAdapter(Context context, ArrayList<Report> list) {
        this.context = context;
        ReportList = list;
    }

    @Override
    public int getCount(){
        return ReportList.size();
    }

    @Override
    public Object getItem(int position){
        return ReportList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Report report = ReportList.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_reports, null);
        }

        TextView ReportName = (TextView) convertView.findViewById(R.id.ReportName);
        ReportName.setText("" + report.getReport_name()); //Setting the name

        return convertView; //Return the view for the row so the ListView can use to adapt it to its rows
    }
}