package com.example.requisitionandapproval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.ApiClient.ApiClient;
import com.example.requisitionandapproval.ApiClient.Endpoints;
import com.example.requisitionandapproval.model.ItemResult;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ApiClient apiClient = new ApiClient();
    private Retrofit retrofit;
    private Endpoints endpoints;
    private String Base_URL = apiClient.getBASE_URL();
    private TextView destxt,qty1,price, sef;
    public String username, userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        endpoints = retrofit.create(Endpoints.class);

        Button add_item = findViewById(R.id.add_item);
        Button button = findViewById(R.id.showBtn);
        Button showBtn = findViewById(R.id.showBtn);

        destxt = findViewById(R.id.destxt);
        qty1 = findViewById(R.id.qty1);
        price = findViewById(R.id.price);
        Intent intent = getIntent();

        //get the username and userID when user login to the system
        username = intent.getStringExtra("username");
        userID = intent.getStringExtra("userID");

        add_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendPostRequest();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGETRequest();
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getBaseContext(), com.example.requisitionandapproval.MainClasses.Items.itemList.class);
                it.putExtra("username", username);
                startActivity(it);
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
                map.put("username", username);
                map.put("ItemID", userID);
                map.put("Item_Description", destxt.getText().toString());
                map.put("Item_Quantity", qty1.getText().toString());
                map.put("Item_AgreedPrice", price.getText().toString());

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

    public void sendGETRequest(){

        destxt = findViewById(R.id.destxt);
        HashMap <String, String> map = new HashMap<>();
        map.put("username", destxt.getText().toString());
        Call<List<ItemResult>> call = endpoints.getItemListByUser(map);
        call.enqueue(new Callback<List<ItemResult>>() {
            @Override
            public void onResponse(Call<List<ItemResult>> call, Response<List<ItemResult>> response) {
                System.out.println("passssed");
                if(response.code() ==200){
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
                List<ItemResult> it = response.body();
                for(int i=0;i<2;i++){
                    it.get(i);
                    System.out.println("product " + it.get(i).getItemID());
                }

            }

            @Override
            public void onFailure(Call<List<ItemResult>> call, Throwable t) {
                System.out.println("failed");
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });

    }
}