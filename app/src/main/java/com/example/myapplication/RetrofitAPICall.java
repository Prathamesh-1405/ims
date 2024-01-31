package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface RetrofitAPICall {
    String URL_BASE = "http://54.204.188.232:5000/";

    @Headers({"Content-Type: application/json","Accept: application/json","source-name: streamlining-inventory-management"})
    @POST("add-company")
    public Call<String> addCompany(@Body String body);

    @POST("add-material")
    public Call<String> addMaterialEntry(@Body String body);

    @POST("add-item")
    public Call<String> addItem(@Body String body);

    @POST("material-dispatch")
    public Call<String> materialDispatch(@Body String body);

    @GET("company")
    public Call<String> getCompanyNames();
}