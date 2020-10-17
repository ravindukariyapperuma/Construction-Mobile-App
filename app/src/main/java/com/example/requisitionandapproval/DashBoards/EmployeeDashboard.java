package com.example.requisitionandapproval.DashBoards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.requisitionandapproval.Notification.MainActivity;
import com.example.requisitionandapproval.R;

public class EmployeeDashboard extends AppCompatActivity {
ImageButton addItem;
Context context = this;
TextView txtEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);

        final Intent intent = getIntent();
        final String un = intent.getStringExtra("username");
        final String uid = intent.getStringExtra("userID");

        addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("username",un);
                intent1.putExtra("userID",uid);
                startActivity(intent1);
            }
        });

        txtEmployee = findViewById(R.id.txtEmployee);
        txtEmployee.setText(un);
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