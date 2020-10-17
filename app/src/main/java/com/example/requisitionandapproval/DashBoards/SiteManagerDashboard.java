package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.MainClasses.SiteManager.Approve_Requisition;
import com.example.requisitionandapproval.MainClasses.SiteManager.goods_receipt;
import com.example.requisitionandapproval.MainClasses.SiteManager.place_purchase_Order_List;
import com.example.requisitionandapproval.R;

public class SiteManagerDashboard extends AppCompatActivity {
    ImageButton approve,goods,purchaseorder;
    TextView txtSitemager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manager_dashboard);

        final Intent intent = getIntent();
        final String un = intent.getStringExtra("EXTRA_SESSION_ID");

        approve = findViewById(R.id.approve);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SiteManagerDashboard.this, Approve_Requisition.class);
                intent.putExtra("name",un);
                startActivity(intent);
            }
        });
        goods = findViewById(R.id.goods);
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent(SiteManagerDashboard.this, goods_receipt.class);
                intent1.putExtra("name",un);
                startActivity(intent1);
            }
        });

        purchaseorder = findViewById(R.id.purchaseorder);
        purchaseorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent(SiteManagerDashboard.this, place_purchase_Order_List.class);
                intent1.putExtra("name",un);
                startActivity(intent1);
            }
        });
        txtSitemager = findViewById(R.id.txtSitemager);
        txtSitemager.setText(un);

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