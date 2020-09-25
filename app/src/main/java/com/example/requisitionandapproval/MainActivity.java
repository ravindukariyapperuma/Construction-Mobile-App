package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = "http://10.0.2.2:3000";
    private TextView destxt,qty1,price, sef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        Button add_item = findViewById(R.id.add_item);
        destxt = findViewById(R.id.destxt);
        qty1 = findViewById(R.id.qty1);
        price = findViewById(R.id.price);


        add_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendPostRequest();
            }
        });
    }

    public void sendPostRequest(){

        Button add_item = findViewById(R.id.add_item);
        destxt = findViewById(R.id.destxt);
        qty1 = findViewById(R.id.qty1);
        price = findViewById(R.id.price);

        add_item.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                HashMap <String, String> map = new HashMap<>();
                map.put("ItemID", destxt.getText().toString());
                map.put("Item_Description", qty1.getText().toString());
                map.put("Item_Quantity", price.getText().toString());

                Call<Void> call = endpoints.saveItemList(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("pass");
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