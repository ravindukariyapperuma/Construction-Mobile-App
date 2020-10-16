package com.example.requisitionandapproval.MainClasses.SiteManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainActivity;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.Itemcls;
import com.example.requisitionandapproval.model.ItemsDetails;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.MainClasses.Order.place_Purchase_order;
import com.example.requisitionandapproval.progressBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Approve_Requisition extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    ReqApprovalModel[] rm;
    ApiClient apiClient = new ApiClient();
    Button add_item,decline_item;
    static int val;
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve__requisition);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        TextView uname = findViewById(R.id.name);
        uname.setText("Site Manager : "+name );


        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        recyclerView = findViewById(R.id.approveRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_item = findViewById(R.id.add_item);
        decline_item = findViewById(R.id.decline_item);

        getAllReqNumbers();

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestingApproval(rm);
            }
        });
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


        decline_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decline_Request();
            }
        });
        // initdata();

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

    public void getdetails_from_reqID(final String reqID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ItemID", reqID);


        Call<List<GetReqDetailsByID>> call = endpoints.getItemsByReqID(map);

        call.enqueue(new Callback<List<GetReqDetailsByID>>() {
            @Override
            public void onResponse(Call<List<GetReqDetailsByID>> call, Response<List<GetReqDetailsByID>> response) {
                if (response.code() == 200) {
                    Toast.makeText(Approve_Requisition.this, response.message(), Toast.LENGTH_LONG).show();
                }
                approveModels = new ArrayList<>();

                try {
                    List<GetReqDetailsByID> it = response.body();
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
                        rm[i] = new ReqApprovalModel(reqID, name,it.get(i).getDes(), it.get(i).getPrice(),it.get(i).getQty() );
                    }

                    initRecyclerView();
                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("123123123123");

                }
            }

            @Override
            public void onFailure(Call<List<GetReqDetailsByID>> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(Approve_Requisition.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getAllReqNumbers() {

        Call<List<GetReqNumbers>> call = endpoints.getAllReqNumbers();
        call.enqueue(new Callback<List<GetReqNumbers>>() {
            @Override
            public void onResponse(Call<List<GetReqNumbers>> call, Response<List<GetReqNumbers>> response) {
                try {
                    List<GetReqNumbers> it = response.body();
                    String[] arraySpinner = new String[it.size()];
                    for (int i = 0; i < it.size(); i++) {

                        arraySpinner[i] = it.get(i).getItemIDs();
                    }
                    Spinner s = (Spinner) findViewById(R.id.reqIDS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Approve_Requisition.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);

                    if (arraySpinner.length==0){

                        decline_item.setClickable(false);
                        add_item.setClickable(false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<GetReqNumbers>> call, Throwable t) {
                System.out.println("ERROR::" + t);
            }
        });
    }

    public void requestingApproval(ReqApprovalModel[] rm) {

        Call<ReqApprovalModel> call = endpoints.requestApproval(rm);
        call.enqueue(new Callback<ReqApprovalModel>() {
            @Override
            public void onResponse(Call<ReqApprovalModel> call, final Response<ReqApprovalModel> response) {

                final progressBar pbar = new progressBar(Approve_Requisition.this);
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        pbar.dismissProgress();
                        // my whole code



                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String nme = jsonObject.getString("status");
                            System.out.println("nme"+nme);
                            if(nme.equals("PENDING")){
                                System.out.println("Navigate to manager port");
                                final Spinner reqId = (Spinner) findViewById(R.id.reqIDS);
                                String RequisitionId = reqId.getSelectedItem().toString();
                                getdetails_from_reqID(RequisitionId);
                                new SweetAlertDialog(Approve_Requisition.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Order Approval Navigates to Manager")
                                        .show();
                                //Toast.makeText(Approve_Requisition.this,"Order Approval Navigates to Manager",Toast.LENGTH_SHORT).show();

                            }else{
                                //Toast.makeText(Approve_Requisition.this,"Order Approval Successful",Toast.LENGTH_SHORT).show();

                                Intent intent = getIntent();
                                String name = intent.getStringExtra("name");
                                Spinner reqId = (Spinner) findViewById(R.id.reqIDS);
                                String RequisitionId = reqId.getSelectedItem().toString();
                                Intent it = new Intent(getBaseContext(), com.example.requisitionandapproval.MainClasses.Order.place_Purchase_order.class);
                                it.putExtra("reqid",RequisitionId);
                                it.putExtra("name1",name);
                                startActivity(it);

                                System.out.println("Navigate to sitemanager payment");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    public void onTick(long millisUntilFinished) {
                        pbar.StartLoading();

                    }
                }.start();





            }

            @Override
            public void onFailure(Call<ReqApprovalModel> call, Throwable t) {
                System.out.println("ERROR::"+t);
                new SweetAlertDialog(Approve_Requisition.this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Order Approval Unsuccessful")
                        .show();
                //Toast.makeText(Approve_Requisition.this,"Order Approval Unsuccessful",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void decline_Request(){


        Spinner decSpin = findViewById(R.id.reqIDS);

        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", decSpin.getSelectedItem().toString());


        Call<Void> call = endpoints.declinesitemanagerRequsition(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {





                final progressBar pbar = new progressBar(Approve_Requisition.this);
                new CountDownTimer(2000, 1000) {
                    public void onFinish() {
                        pbar.dismissProgress();
                        // my whole code


                        final Spinner reqId = (Spinner) findViewById(R.id.reqIDS);
                        String RequisitionId = reqId.getSelectedItem().toString();
                        getdetails_from_reqID(RequisitionId);
                        //Toast.makeText(Approve_Requisition.this,"Decline Successful",Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(Approve_Requisition.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Order Decline Successful")
                                .show();


                    }

                    public void onTick(long millisUntilFinished) {
                        pbar.StartLoading();

                    }
                }.start();






            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("failed" + t);
                Toast.makeText(Approve_Requisition.this,"Decline Faild",Toast.LENGTH_SHORT).show();
            }
        });


    }

}