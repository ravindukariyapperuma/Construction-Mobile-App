package com.example.requisitionandapproval.ApiClient;
import com.example.requisitionandapproval.model.ItemResult;
import com.example.requisitionandapproval.model.userLogin;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Endpoints {
    @POST("/items/addItems")
    Call<Void> saveItemList(@Body HashMap<String, String> map);

//    @HTTP(method = "GET",  path = "/items/getItemsByUser", hasBody = true)
//    List<Repo> getItemListByUser(@Body HashMap<String, String> map);

    @POST("/items/getItemsByUser")
//    Call<JSONResponse> getItemListByUser(@Body HashMap<String, String> map);
    Call<List<ItemResult>> getItemListByUser(@Body HashMap<String, String> map);

    @POST("/users/userLogin")
//    Call<JSONResponse> getItemListByUser(@Body HashMap<String, String> map);
    Call<userLogin> userLogin(@Body HashMap<String, String> map);

    @POST("/users/registerUsers")
//    Call<JSONResponse> getItemListByUser(@Body HashMap<String, String> map);
    Call<Void> userRegister(@Body HashMap<String, String> map);
}
