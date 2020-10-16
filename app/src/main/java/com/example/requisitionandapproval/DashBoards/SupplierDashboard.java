package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.requisitionandapproval.MainClasses.Suppliers.SupplierProfile;
import com.example.requisitionandapproval.R;

public class SupplierDashboard extends AppCompatActivity {

    String EXTRA_SESSION_ID;
    ImageView supplierr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_dashboard);

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