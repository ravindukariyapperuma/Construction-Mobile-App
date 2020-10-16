package com.example.requisitionandapproval.MainClasses.Suppliers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.SupplierItemDetails;
import com.example.requisitionandapproval.model.reqIDbysupplier;
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

public class SupplierProfile extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    ReqApprovalModel[] rm;
    ApiClient apiClient = new ApiClient();
    Button add_item;
    static int val;
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        TextView user = findViewById(R.id.supplier);
        user.setText("Supplier : "+username);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        recyclerView = findViewById(R.id.approveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_item = findViewById(R.id.add_item);

        getAllsupplierReqNumbers();

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestingApproval(rm);
            }
        });

        final Spinner reqId = (Spinner) findViewById(R.id.reqIDS);

        reqId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String RequisitionId = reqId.getSelectedItem().toString();
                getdetails_from_reqIDSupplier(RequisitionId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initRecyclerView() {
        ApproveAdapter approveAdapter = new ApproveAdapter(approveModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(approveAdapter);
    }

    private void initdata() {
        approveModels = new ArrayList<>();
        approveModels.add(new ApproveModel("Tokya Cementt", "20", "20.00"));
        approveModels.add(new ApproveModel("Pipes", "10", "30.00"));
        approveModels.add(new ApproveModel("Brikes", "50", "35"));

    }

    public void getdetails_from_reqIDSupplier(final String reqID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", reqID);


        Call<List<SupplierItemDetails>> call = endpoints.getItemsByReqIDSupplier(map);

        call.enqueue(new Callback<List<SupplierItemDetails>>() {
            @Override
            public void onResponse(Call<List<SupplierItemDetails>> call, Response<List<SupplierItemDetails>> response) {
                if (response.code() == 200) {
                    Toast.makeText(SupplierProfile.this, response.message(), Toast.LENGTH_LONG).show();
                }
                approveModels = new ArrayList<>();

                try {
                    List<SupplierItemDetails> it = response.body();
                    rm = new ReqApprovalModel[it.size()];
                    for (int i = 0; i < it.size(); i++) {
                        approveModels.add(new ApproveModel(it.get(i).getDes(), it.get(i).getQty(), it.get(i).getPrice()));
                        String nm = it.get(i).getPrice();
                        for (int j = 0; j < it.size(); j++) {
                            if(i == 0){
                                int itmprive = Integer.parseInt(it.get(j).getPrice());
                                int quantity = Integer.parseInt(it.get(j).getPrice());
                                val = itmprive * quantity;
                            }
                        }
                        rm[i] = new ReqApprovalModel(reqID, username,it.get(i).getDes(), it.get(i).getPrice(),it.get(i).getQty() );
                    }

                    initRecyclerView();
                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("123123123123");

                }
            }

            @Override
            public void onFailure(Call<List<SupplierItemDetails>> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(SupplierProfile.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getAllsupplierReqNumbers() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);

        Call<reqIDbysupplier> call = endpoints.reqIDbysupplierName(map);
        call.enqueue(new Callback<reqIDbysupplier>() {
            @Override
            public void onResponse(Call<reqIDbysupplier> call, Response<reqIDbysupplier> response) {
                try {
                    reqIDbysupplier it = response.body();

                    Spinner s = (Spinner) findViewById(R.id.reqIDS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SupplierProfile.this, android.R.layout.simple_spinner_item, it.getItemID());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<reqIDbysupplier> call, Throwable t) {
                System.out.println("ERROR::" + t);
            }
        });
    }

    public void requestingApproval(ReqApprovalModel[] rm) {

        Call<ReqApprovalModel> call = endpoints.requestApproval(rm);
        call.enqueue(new Callback<ReqApprovalModel>() {
            @Override
            public void onResponse(Call<ReqApprovalModel> call, Response<ReqApprovalModel> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String nme = jsonObject.getString("status");
                    System.out.println("nme"+nme);
                    if(nme.equals("PENDING")){

                        System.out.println("Navigate to manager port");

                    }else{
                        Intent it = new Intent(getBaseContext(), com.example.requisitionandapproval.MainClasses.Order.place_Purchase_order.class);
                        startActivity(it);
                        System.out.println("Navigate to sitemanager payment");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ReqApprovalModel> call, Throwable t) {
                System.out.println("ERROR::"+t);

            }
        });
    }
}