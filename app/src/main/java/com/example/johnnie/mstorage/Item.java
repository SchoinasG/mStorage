package com.example.johnnie.mstorage;

import java.io.Serializable;

/**
 * Created by Johnnie on 6/6/2017.
 */

public class Item implements Serializable{
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
    private int quantity_found;
    private String notes;
    private String date_modified;
    private int is_checked;


    public Item(int id, String name, String description, String category, String position, String measurement_unit,
                String SKU, String barcode, int quantity, int items_department_id )
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

    public void setId(int _id){
        id = _id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String _name){
        name = _name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String _desc){
        description = _desc;
    }

    public String getDescription(){
        return this.description;
    }

    public void setCategory(String _category){
        category = _category;
    }

    public String getCategory(){
        return this.category;
    }

    public void setPosition(String _position){
        position = _position;
    }

    public String getPosition(){
        return this.position;
    }

    public void setMeasurement_unit(String _measurement_unit){
        measurement_unit = _measurement_unit;
    }

    public String getMeasurement_unit(){
        return this.measurement_unit;
    }

    public void setSKU(String _SKU){
        SKU = _SKU;
    }

    public String getSKU(){
        return this.SKU;
    }

    public void setBarcode(String _barcode){
        barcode = _barcode;
    }

    public String getBarcode(){
        return this.barcode;
    }

    public void setQuantity(int _quantity){
        quantity = _quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setItems_department_id(int _items_department_id){
        items_department_id = _items_department_id;
    }

    public int getItems_department_id() {
        return this.items_department_id;
    }

    public void setQuantity_found(int quantity_found) {
        this.quantity_found = quantity_found;
    }

    public int getQuantity_found() {
        return quantity_found;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public String getNotes(){
        return this.notes;
    }

    public void setDate_modified(String date){
        this.date_modified = date;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setIs_checked(int checked){
        this.is_checked = checked;
    }

    public int getIs_checked() {
        return is_checked;
    }


}
