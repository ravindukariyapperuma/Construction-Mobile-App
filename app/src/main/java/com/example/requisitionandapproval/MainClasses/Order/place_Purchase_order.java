package com.example.requisitionandapproval.MainClasses.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.MainClasses.SiteManager.place_purchase_Order_List;
import com.example.requisitionandapproval.R;
import com.example.requisitionandapproval.model.supplierModel;
import com.example.requisitionandapproval.Notification.progressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class place_Purchase_order extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
     EditText requireDate,cnum,holderName,cvv,expdate,mobileNumber;
     LinearLayout cOndelivery,cardpaymetlayout;
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    String name;
    TextView uname;
    RadioButton card,cOnd;
    RadioGroup paymentradiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__purchase_order);
        new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Order Approval Successful")
                .show();
        Intent intent = getIntent();
        name = intent.getStringExtra("name1");
        uname = findViewById(R.id.uname);

        uname.setText("Site Manager : "+name );

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        Button Addrequisition = findViewById(R.id.Addrequisition);

        requireDate= findViewById(R.id.requireDate);
        card= findViewById(R.id.card);
        cOnd= findViewById(R.id.cOnd);
        cnum= findViewById(R.id.cnum);
        holderName= findViewById(R.id.holderName);
        cvv= findViewById(R.id.cvv);
        expdate= findViewById(R.id.expdate);
        mobileNumber= findViewById(R.id.mobileNumber);
        cOndelivery= findViewById(R.id.cOndelivery);
        cardpaymetlayout= findViewById(R.id.cardpaymetlayout);
        paymentradiogroup= findViewById(R.id.paymentradiogroup);

        getAllSuppliers();

        cOndelivery.setVisibility(View.GONE);
        cardpaymetlayout.setVisibility(View.GONE);


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


        paymentradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (card.isChecked()) {

                    cardpaymetlayout.setVisibility(View.VISIBLE);
                    cOndelivery.setVisibility(View.GONE);

                }else if(cOnd.isChecked()){
                    cOndelivery.setVisibility(View.VISIBLE);
                    cardpaymetlayout.setVisibility(View.GONE);
                }







            }
        });





        Addrequisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addline1 = findViewById(R.id.addLine1);
                EditText addline2 = findViewById(R.id.addLine2);
                EditText requireDate = findViewById(R.id.requireDate);
                Spinner Suplier = findViewById(R.id.supplyspin);

                if (addline1.getText().toString().isEmpty() || addline2.getText().toString().isEmpty()|| requireDate.getText().toString().isEmpty()|| Suplier.getCount()==0){
                    //Toast.makeText(place_Purchase_order.this,"Please Fill All fields",Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please Fill All fields")
                            .show();
                }else{

                    if (card.isChecked()){
                        if (cnum.getText().toString().isEmpty() ||holderName.getText().toString().isEmpty() ||cvv.getText().toString().isEmpty() ||expdate.getText().toString().isEmpty()) {
                            new SweetAlertDialog(place_Purchase_order.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Please Fill Card Details ")
                                    .show();
                        }else{
                            placeOrder();
                            new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Order Placed Successful")
                                    .show();
                        }

                    }else if (cOnd.isChecked()){

                        if (mobileNumber.getText().toString().isEmpty() ) {
                            new SweetAlertDialog(place_Purchase_order.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Please Enter Contact Number ")
                                    .show();
                        }else{
                            placeOrder();
                            new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Order Placed Successful")
                                    .show();
                        }

                    }else{

                        new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Please Select Payment Methods")
                                .show();

                    }

//
//                    placeOrder();
//                    new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText("Order Placed Successful")
//                            .show();

                }




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


        Call<Void> call = endpoints.placeOrder(map);
        try {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, final Response<Void> response) {


                            if(response.code() == 404){
                                new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Error !")
                                        .show();
                                System.out.println("PAAAAAAAAS");

                            }else {
                                new SweetAlertDialog(place_Purchase_order.this,SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Order placed successful!")
                                        .show();
                                //Toast.makeText(place_Purchase_order.this, "Order placed successful!", Toast.LENGTH_LONG).show();
                                System.out.println("adawqq"+response.code() );
//
//                                Intent intent1 = new Intent(place_Purchase_order.this, place_purchase_Order_List.class);
//                                intent1.putExtra("name",name);
//                                startActivity(intent1);
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