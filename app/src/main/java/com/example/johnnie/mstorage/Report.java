package com.example.johnnie.mstorage;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Johnnie on 11/6/2017.
 */

public class Report implements Serializable {
    private String report_name;
    private String JsonArray;

    public Report(String filename, String JsonArray){
        this.report_name = filename;
        this.JsonArray = JsonArray;
    }

    public String getReport_name() {
        return report_name;
    }

    public String getJsonArray() {
        return JsonArray;
    }
}
