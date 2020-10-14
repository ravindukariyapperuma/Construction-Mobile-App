package com.example.requisitionandapproval.ApiClient;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.ItemResult;

import com.example.requisitionandapproval.model.OrderDoneModel;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.placedorderReqId;
import com.example.requisitionandapproval.model.userLogin;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
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


    //sitemanager's ENDPOINTS
    @GET("/items/getRequestingNumberList")
    Call<List<GetReqNumbers>> getAllReqNumbers();

    //get all the items by request number
    @POST("/items/getItemsByReqNumbers")
    Call<List<GetReqDetailsByID>> getItemsByReqID(@Body HashMap<String, String> map);

    //Site manager approve/reject endpoint
    @POST("/sitemanager/approveReq")
    Call<ReqApprovalModel> requestApproval(@Body ReqApprovalModel[] rm);

    //Site Manager place purchase order
    @POST("/sitemanager/placeorders")
    Call<Void> placeOrder(@Body HashMap<String, String> map);

    //Site Manager get req IDs of place purchase order
    @POST("/sitemanager/getAllOrdersReqIds")
    Call<placedorderReqId> getApprovedOrderIDs();

    @POST("/sitemanager/getPlaceOrders")
    Call<List<getOrderedItemList>> getOrderedItems(@Body HashMap<String, String> map);

    //Site Manager clicked received Items
    @POST("/sitemanager/deleteReceivedItems")
    Call<Void> orderDone(@Body HashMap<String, String[]> map);
}