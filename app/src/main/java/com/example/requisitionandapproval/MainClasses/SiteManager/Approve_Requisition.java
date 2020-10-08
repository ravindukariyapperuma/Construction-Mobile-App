package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.Users.UserLogin;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.userLogin;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Approve_Requisition extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve__requisition);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        recyclerView = findViewById(R.id.approveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getAllReqNumbers();

//        ApproveModel[] approveModels = new ApproveModel[]{
//
//                new ApproveModel("Tokyo cement"),
//                new ApproveModel("Pipes")
//
//
//
//        };
//        ApproveAdapter adapter= new ApproveAdapter(approveModels,Approve_Requisition.this);
//        recyclerView.setAdapter(adapter);
       // getdetails_from_reqID(RequisitionId);
        final Spinner reqId = (Spinner) findViewById(R.id.reqIDS);

        reqId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String RequisitionId = reqId.getSelectedItem().toString();
                getdetails_from_reqID(RequisitionId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       // initdata();

    }
    private void initRecyclerView() {
        ApproveAdapter approveAdapter = new ApproveAdapter(approveModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(approveAdapter);
    }
    private void initdata(){
        approveModels = new ArrayList<>();
        approveModels.add(new ApproveModel("Tokya Cementt","20","20.00"));
        approveModels.add(new ApproveModel("Pipes","10","30.00"));
        approveModels.add(new ApproveModel("Brikes","50","35"));

    }

    public void getdetails_from_reqID(String reqID){

        HashMap<String, String> map = new HashMap<>();
        map.put("ItemID", reqID);


        Call<List<GetReqDetailsByID>> call = endpoints.getItemsByReqID(map);

        call.enqueue(new Callback<List<GetReqDetailsByID>>() {
            @Override
            public void onResponse(Call<List<GetReqDetailsByID>> call, Response<List<GetReqDetailsByID>> response) {
                if(response.code() ==200){
                    Toast.makeText(Approve_Requisition.this, response.message(), Toast.LENGTH_LONG).show();
                }
                approveModels = new ArrayList<>();
//                approveModels.add(new ApproveModel("Tokya Cementt","20","20.00"));
//                approveModels.add(new ApproveModel("Pipes","10","30.00"));
//                approveModels.add(new ApproveModel("Brikes","50","35"));
                try {
//                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                    String nme = jsonObject.getString("username");
//
//                    System.out.println(jsonObject.length()+"ycyctgcitvig");
                    List<GetReqDetailsByID> it = response.body();

                    String[] username = new String[it.size()];
                    for(int i=0; i<it.size(); i++){
                        System.out.println("HEEREEEEEE");
                        System.out.println("sefij"+it.get(0).getUserName());
                        approveModels.add(new ApproveModel(it.get(i).getDes(),it.get(i).getQty(),it.get(i).getPrice()));
                       // username[i]= it.get(i).getUserName();

                    }
                   // System.out.println(username[0]);
                    initRecyclerView();
                }catch (Exception e){

                    e.printStackTrace();
                    System.out.println("123123123123");

                }
            }

            @Override
            public void onFailure(Call<List<GetReqDetailsByID>> call, Throwable t) {
                System.out.println("failed" +t);
                Toast.makeText(Approve_Requisition.this, "Error", Toast.LENGTH_LONG).show();
            }
        });





    }

    public void getAllReqNumbers() {
        System.out.println("HEREE");

        Call<List<GetReqNumbers>> call = endpoints.getAllReqNumbers();
        System.out.println("HEREE12");
        call.enqueue(new Callback<List<GetReqNumbers>>() {
            @Override
            public void onResponse(Call <List<GetReqNumbers>> call, Response<List<GetReqNumbers>> response) {
                try {
                    List<GetReqNumbers> it = response.body();
                    String[] arraySpinner = new String[it.size()];
                    for(int i=0; i<it.size(); i++){

                        arraySpinner[i]= it.get(i).getItemIDs();
                    }
                    Spinner s = (Spinner) findViewById(R.id.reqIDS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Approve_Requisition.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<List<GetReqNumbers>> call, Throwable t) {
                System.out.println("ERROR::"+ t);
            }
        });
    }

}