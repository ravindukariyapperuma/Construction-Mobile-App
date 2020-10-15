package com.example.requisitionandapproval.MainClasses.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.MainClasses.SiteManager.place_purchase_Order_List;
import com.example.requisitionandapproval.MainClasses.Users.UserLogin;
import com.example.requisitionandapproval.MainClasses.Users.registerUsers;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.GetReqNumbers;
import com.example.requisitionandapproval.model.supplierModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class place_Purchase_order extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
     EditText requireDate;
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__purchase_order);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        Button Addrequisition = findViewById(R.id.Addrequisition);

        requireDate= findViewById(R.id.requireDate);
        getAllSuppliers();

        String []city = {"Jaffna","Kilinochchi","Mannar","Mullaitivu","Vavuniya","Puttalam","Kurunegala","Gampaha","Colombo","Kalutara","Anuradhapura","Polonnaruwa","Matale","Kandy","Nuwara Eliya","Kegalle","Ratnapura","Trincomalee","Batticaloa","Ampara","Badulla","Monaragala","Hambantota","Matara","Galle"};

        Spinner selectcity = findViewById(R.id.selectcity);
        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(place_Purchase_order.this, android.R.layout.simple_spinner_item, city);
        cityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectcity.setAdapter(cityadapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        requireDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(place_Purchase_order.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Addrequisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                placeOrder();

            }
        });






    }

    public void getAllSuppliers() {

        Call<List<supplierModel>> call = endpoints.getAllsuppliers();
        call.enqueue(new Callback<List<supplierModel>>() {
            @Override
            public void onResponse(Call<List<supplierModel>> call, Response<List<supplierModel>> response) {
                try {
                    List<supplierModel> it = response.body();
                    String[] arraySpinner = new String[it.size()];
                    for (int i = 0; i < it.size(); i++) {

                        arraySpinner[i] = it.get(i).getUsername();
                    }
                    Spinner supplyspin = findViewById(R.id.supplyspin);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(place_Purchase_order.this, android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    supplyspin.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<supplierModel>> call, Throwable t) {
                System.out.println("ERROR::" + t);
            }
        });
    }

    public void placeOrder(){

        Spinner Suplier = findViewById(R.id.supplyspin);
        Spinner city = findViewById(R.id.selectcity);
        EditText addline1 = findViewById(R.id.addLine1);
        EditText addline2 = findViewById(R.id.addLine2);
        EditText requireDate = findViewById(R.id.requireDate);

        final Intent intent = getIntent();
        String reqId =intent.getStringExtra("reqid");


        HashMap<String, String> map = new HashMap<>();
        map.put("reqID", reqId);
        map.put("supplier", Suplier.getSelectedItem().toString());
        map.put("addressline1", addline1.getText().toString());
        map.put("addressline2", addline2.getText().toString());
        map.put("other", city.getSelectedItem().toString());
        map.put("requiredDate", requireDate.getText().toString());

        //System.out.println("awd" + sp.getSelectedItem().toString());

        Call<Void> call = endpoints.placeOrder(map);
        try {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {


                    //Toast.makeText(place_Purchase_order.this, response.message(), Toast.LENGTH_LONG).show();



                    if(response.code() == 404){
                        Toast.makeText(place_Purchase_order.this, "Please fill all required fields!", Toast.LENGTH_LONG).show();
                        System.out.println("PAAAAAAAAS");

                    }else {
                        Toast.makeText(place_Purchase_order.this, "Order placed successful!", Toast.LENGTH_LONG).show();
                        System.out.println("adawqq"+response.code() );

                        Intent intent1 = new Intent(place_Purchase_order.this, place_purchase_Order_List.class);
                        startActivity(intent1);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("failed" + t);
                    Toast.makeText(place_Purchase_order.this, "Requistion ID already added", Toast.LENGTH_LONG).show();

                }

            });
        } catch (Exception e) {
            Toast.makeText(place_Purchase_order.this, "Please enter All Fields", Toast.LENGTH_LONG).show();

        }




    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        requireDate.setText(sdf.format(myCalendar.getTime()));
    }
}