package com.example.johnnie.mstorage;

/**
 * Created by Johnnie on 19/5/2017.
 */

public class Department {
    private int id;
    private int storageid;
    private String name;

    public Department(int id, int storageid, String name){
        this.id = id;
        this.storageid = storageid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStorageid() {
        return storageid;
    }
}
