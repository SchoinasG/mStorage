package com.example.johnnie.mstorage;

import java.util.ArrayList;

/**
 * Created by Johnnie on 20/5/2017.
 */

public class Storage {
    private int id;
    private String name;
    private ArrayList<Department> StorageDepartments;

    public Storage(int id, String name, ArrayList<Department> Deps){
        this.id = id;
        this.name = name;
        this.StorageDepartments = Deps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Department> getStorageDepartments() {
        return StorageDepartments;
    }
}

