package com.example.johnnie.mstorage;

import java.util.ArrayList;

//Simple class for Storage. Not much to explain here.

public class Storage {

    private int id;
    private String name;
    private ArrayList<Department> StorageDepartments;

    public Storage(int _id, String _name, ArrayList<Department> Deps){
        setId(_id);
        setName(_name);
        setStorageDepartments(Deps);
    }

    public void setId(int _id) {
        id = _id;
    }

    public int getId() {
        return id;
    }

    public void setName(String _name){
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setStorageDepartments(ArrayList<Department> _storageDepartments) {
        StorageDepartments = _storageDepartments;
    }

    public ArrayList<Department> getStorageDepartments() {
        return StorageDepartments;
    }

}
