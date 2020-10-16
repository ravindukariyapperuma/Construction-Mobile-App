package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.MainClasses.Suppliers.DeliverdItems;
import com.example.requisitionandapproval.MainClasses.Suppliers.SupplierProfile;
import com.example.requisitionandapproval.MainClasses.Suppliers.inprogressItemsInsupplier;
import com.example.requisitionandapproval.R;

public class SupplierDashboard extends AppCompatActivity {

    String EXTRA_SESSION_ID;
    ImageView supplierr,Btninprogress,BtnDelivered;
    AppCompatRadioButton rbAvailable, rbNotAvailable;
    RadioButton rdActive, rdNotActive;
    TextView txtUsername;
//    String username ="abc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_dashboard);
        rbAvailable = findViewById(R.id.rbAvailable);
        rbNotAvailable = findViewById(R.id.rbNotAvailable);
        Btninprogress = findViewById(R.id.btninprogress);
        BtnDelivered = findViewById(R.id.btnDelivered);

//        rdActive = findViewById(R.id.rdActive);
//        rdNotActive = findViewById(R.id.rdNotActive);
//        rdActive.setChecked(true);


        txtUsername = findViewById(R.id.txtUsername);
//        txtUsername.setText(username);



        supplierr = findViewById(R.id.supplierr);

        Intent intent = getIntent();
        EXTRA_SESSION_ID = intent.getStringExtra("EXTRA_SESSION_ID");

        supplierr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SupplierDashboard.this, SupplierProfile.class);
                intent1.putExtra("username",EXTRA_SESSION_ID);
                startActivity(intent1);
            }
        });


        Btninprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, inprogressItemsInsupplier.class);
                startActivity(intent);
            }
        });

        BtnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierDashboard.this, DeliverdItems.class);
                startActivity(intent);
            }
        });



    }

    public void onRadioButtonClick(View view){
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
//        switch (view.getId()){
//            case R.id.rbAvailable:
//                if(isSelected){
//                    rbAvailable.setTextColor(Color.WHITE);
//                    rbNotAvailable.setTextColor(Color.RED);
//                }
//                break;
//            case R.id.rbNotAvailable:
//                if(isSelected){
//                    rbNotAvailable.setTextColor(Color.WHITE);
//                    rbAvailable.setTextColor(Color.RED);
//                }
//                break;
//        }
//        if(rbAvailable.isChecked()){
//            rbAvailable.setTextColor(Color.WHITE);
//            rbNotAvailable.setTextColor(Color.RED);
//        }else{
//            rbNotAvailable.setTextColor(Color.WHITE);
//            rbAvailable.setTextColor(Color.RED);
//        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}