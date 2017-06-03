package com.example.johnnie.mstorage;

import java.io.Serializable;

/**
 * Created by Masterace on 28/5/2017.
 */

//Simple class for Department. Not much to explain here.

public class Department implements Serializable{

    private int id;
    private int storageid;
    private String name;
    private boolean selected = false;

    public Department(int _id, int _storageid, String _name){
        setId(_id);
        setName(_name);
        setStorageid(_storageid);
        setSelected(false);
    }

    public void setId(int _id) {
        id = _id;
    }

    public int getId() {
        return id;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setStorageid(int _storageid) {
        storageid = _storageid;
    }

    public int getStorageid() {
        return storageid;
    }

    public void setSelected(boolean _selected){
        selected = _selected;
    }

    public boolean isSelected(){
        return selected;
    }

}
