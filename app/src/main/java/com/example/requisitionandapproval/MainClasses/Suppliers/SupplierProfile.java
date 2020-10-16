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
import com.example.requisitionandapproval.MainClasses.Order.place_purchase_order_Item_List;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.MainClasses.SiteManager.goods_receipt;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.adapterClasses.ApproveAdapter;
import com.example.requisitionandapproval.adapterClasses.place_Item_listAdapter;
import com.example.requisitionandapproval.adapterClasses.supplierOrderAdaptor;
import com.example.requisitionandapproval.model.ApproveModel;
import com.example.requisitionandapproval.model.GetReqDetailsByID;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.ReqApprovalModel;
import com.example.requisitionandapproval.model.SupplierItemDetails;
import com.example.requisitionandapproval.model.SupplierOrderMdel;
import com.example.requisitionandapproval.model.getOrderedItemList;
import com.example.requisitionandapproval.model.orderItemcls;
import com.example.requisitionandapproval.model.reqIDbysupplier;
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

public class SupplierProfile extends AppCompatActivity {

    List<ApproveModel> approveModels;
    RecyclerView recyclerView;
    ReqApprovalModel[] rm;
    supplierOrderAdaptor adaptor;
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

        recyclerView = findViewById(R.id.suplyitm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add_item = findViewById(R.id.add_item);

        getAllsupplierReqNumbers();


                deliverItem();

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


        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderReqId");
        HashMap<String, String> map = new HashMap<>();

        map.put("reqID", reqID);

        Call<List<SupplierItemDetails>> call = endpoints.getItemsByReqIDSupplier(map);


        call.enqueue(new Callback<List<SupplierItemDetails>>() {
            @Override
            public void onResponse(Call<List<SupplierItemDetails>> call, final Response<List<SupplierItemDetails>> response) {

                TextView requreDate = findViewById(R.id.requreDate);
                TextView line1 = findViewById(R.id.line1);
                TextView line2 = findViewById(R.id.line2);
                TextView line3 = findViewById(R.id.linecity);
                System.out.println("passssed");
                if(response.code() ==200){
                }
                List<SupplierItemDetails> it = response.body();
                assert it != null;
                SupplierOrderMdel[] itemcls  =  new SupplierOrderMdel[it.size()];

                if (it.size()>0) {
                    requreDate.setText(it.get(0).getRequiredDate());
                    line1.setText(it.get(0).getAddressline1() + ", ");
                    line2.setText(it.get(0).getAddressline2() + ", ");
                    line3.setText(it.get(0).getOther());
                }



                for(int i =0 ; i<it.size(); i++){
                    itemcls[i] =new SupplierOrderMdel(it.get(i).getDes(), it.get(i).getQty(), it.get(i).getPrice());
                }

                 adaptor= new supplierOrderAdaptor(itemcls, SupplierProfile.this);
                recyclerView.setAdapter(adaptor);

            }

            @Override
            public void onFailure(Call<List<SupplierItemDetails>> call, Throwable t) {
                System.out.println("failed");

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

    public void deliverItem() {


        final Spinner orderreqIDS = findViewById(R.id.reqIDS);

            add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String[] itmarr = new String[adaptor.checkedItems.size()];
                    for (int i = 0 ; i< adaptor.checkedItems.size(); i++){

                        itmarr[i]= adaptor.checkedItems.get(i).toString();
                    }
                    System.out.println(itmarr);

                    String[] reqarr ={orderreqIDS.getSelectedItem().toString()};

                    HashMap <String, String[]> map = new HashMap<>();
                    map.put("reqID",reqarr );
                    map.put("itemDescription", itmarr);

                    Call<Void> call = endpoints.deliverItem(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println(response.body());

                            getdetails_from_reqIDSupplier(orderreqIDS.getSelectedItem().toString());

                            new SweetAlertDialog(SupplierProfile.this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Item deliver Successful")
                                    .show();

                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("fail");
                        }
                    });

                }
            });




    }
}