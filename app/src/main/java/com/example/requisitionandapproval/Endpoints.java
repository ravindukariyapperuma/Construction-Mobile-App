package com.example.requisitionandapproval;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Endpoints {
    @POST("/items/addItems")
    Call<Void> saveItemList(@Body HashMap<String, String> map);

}
