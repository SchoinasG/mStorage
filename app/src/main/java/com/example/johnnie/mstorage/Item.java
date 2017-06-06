package com.example.johnnie.mstorage;

/**
 * Created by Johnnie on 6/6/2017.
 */

public class Item {
    private int id;
    private String name;
    private String description;
    private String category;
    private String position;
    private String measurement_unit;
    private String SKU;
    private String barcode;
    private int quantity;
    private int items_department_id;

    public Item(int id, String name, String description, String category,
                String position, String measurement_unit, String SKU,
                String barcode, int quantity, int items_department_id )
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.position = position;
        this.measurement_unit = measurement_unit;
        this.SKU = SKU;
        this.barcode = barcode;
        this.quantity = quantity;
        this.items_department_id = items_department_id;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        return this.description;
    }
    public String getCategory(){
        return this.category;
    }
    public String getPosition(){
        return this.position;
    }
    public String getMeasurement_unit(){
        return this.measurement_unit;
    }
    public String getSKU(){
        return this.SKU;
    }
    public String getBarcode(){
        return this.barcode;
    }
    public int getQuantity(){
        return this.quantity;
    }

    public int getItems_department_id() {
        return this.items_department_id;
    }
}
