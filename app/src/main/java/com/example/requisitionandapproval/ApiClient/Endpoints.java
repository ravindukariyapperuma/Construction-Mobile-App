package com.example.requisitionandapproval.ApiClient;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.InprogressResponseModel;
import com.example.requisitionandapproval.model.ItemResult;

import com.example.requisitionandapproval.model.ManagerReqNumbers;
import com.example.requisitionandapproval.model.OrderDoneModel;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.SupplierAvailability;
import com.example.requisitionandapproval.model.SupplierItemDetails;
import com.example.requisitionandapproval.model.deliverItemModel;
import com.example.requisitionandapproval.model.getDetaislByManagerReqID;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.inprogressItemIDModel;
import com.example.requisitionandapproval.model.placedorderReqId;
import com.example.requisitionandapproval.model.reqIDbysupplier;
import com.example.requisitionandapproval.model.supplierModel;
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
    @POST("/sitemanager/siteManagergetAllApprovalItems")
    Call<placedorderReqId> getApprovedOrderIDs();

    @POST("/sitemanager/SiteManagergetAllDetails")
    Call<List<getOrderedItemList>> getOrderedItems(@Body HashMap<String, String> map);

    //Site Manager clicked received Items
    @POST("/sitemanager/deleteReceivedItems")
    Call<Void> orderDone(@Body HashMap<String, String[]> map);

    //Manager's ENDPOINTS
    @GET("/items/getAllPendingReqNumberList")
    Call<List<ManagerReqNumbers>> getAllManagerReqNumbers();

    @POST("/items/getPendingItemByReqID")
    Call<List<getDetaislByManagerReqID>> getItemsByManagerReqID(@Body HashMap<String, String> map);

    @POST("/manager/approval")
    Call<Void> placeManagerOrder(@Body HashMap<String, String> map);

    @POST("/sitemanager/declineRequest")
    Call<Void> declinesitemanagerRequsition(@Body HashMap<String, String> map);

    @POST("/manager/declineRequest")
    Call<Void> declinemanagerRequsition(@Body HashMap<String, String> map);

    @GET("/supplier/getSuppliers")
    Call<List<supplierModel>> getAllsuppliers();

    @POST("/supplier/getAllItemsBySupplierName")
    Call<reqIDbysupplier> reqIDbysupplierName(@Body HashMap<String, String> map);

    //get all the items by request number
    @POST("/supplier/getInfo")
    Call<List<SupplierItemDetails>> getItemsByReqIDSupplier(@Body HashMap<String, String> map);

    @POST("/supplier/getSelectedInfo")
    Call<Void> deliverItem(@Body HashMap<String, String[]> map);

    @GET("/supplier/getPendingOrderListIds")
    Call<inprogressItemIDModel> getallInprogressIDs();

    @GET("/supplier/getAllDeliveredItemsId")
    Call<deliverItemModel> getallDeliverIDs();

    @POST("/supplier/getPendingItems")
    Call<List<InprogressResponseModel>> getItemsDetailsByReqIDSupplier(@Body HashMap<String, String> map);

    @POST("/supplier/getDeliveredItems")
    Call<List<InprogressResponseModel>> getDeliveredItemsDetailsByReqIDSupplier(@Body HashMap<String, String> map);

    @POST("/supplier/Supplieravalability")
    Call<Void> chechAvailable(@Body HashMap<String, String> map);

    @POST("/supplier/checkTheSupplierStatus")
    Call<SupplierAvailability> checkInitialAvailability(@Body HashMap<String, String> map);

}