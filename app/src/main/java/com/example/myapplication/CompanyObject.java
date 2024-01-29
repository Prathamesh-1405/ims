package com.example.myapplication;

public class CompanyObject {
    // on below line creating a variable for message.
    private String name;
    private String address;
    private String city;
    private String pincode;
    private String state;
    private String gstNo;
    private String companyInSez;
    private String companyType;
    private String supplierType;
    private Float distanceFromAndheri;
    private Float distanceFromVasai;
    public CompanyObject(String name, String address, String city, String pincode, String state, String gstNo, String companyInSez, String companyType, String supplierType, Float distanceFromAndheri, Float distanceFromVasai) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.gstNo = gstNo;
        this.companyInSez = companyInSez;
        this.companyType = companyType;
        this.supplierType = supplierType;
        this.distanceFromAndheri = distanceFromAndheri;
        this.distanceFromVasai = distanceFromVasai;
    }

    // getter and setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getCompanyInSez() {
        return companyInSez;
    }

    public void setCompanyInSez(String companyInSez) {
        this.companyInSez = companyInSez;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public Float getDistanceFromAndheri() {
        return distanceFromAndheri;
    }

    public void setDistanceFromAndheri(Float distanceFromAndheri) {
        this.distanceFromAndheri = distanceFromAndheri;
    }

    public Float getDistanceFromVasai(){
        return distanceFromVasai;
    }
    public void setDistanceFromVasai(Float distanceFromVasai){
        this.distanceFromVasai = distanceFromVasai;
    }
}
