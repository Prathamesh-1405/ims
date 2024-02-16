package com.example.myapplication;

public class Item {
    private int id;
    private String itemName;
    private double rodDiameter;
    private double unitWeight;

    public void setId(int id) {
        this.id = id;
    }

    private double unitPrice;
    private int quantity;
    private double total;

    // getters and setters


    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getRodDiameter() {
        return rodDiameter;
    }

    public void setRodDiameter(double rodDiameter) {
        this.rodDiameter = rodDiameter;
    }

    public double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(double unitWeight) {
        this.unitWeight = unitWeight;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

