package com.kottland.mygadsfinalproject.paymentproc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kottland.mygadsfinalproject.R;


import org.json.JSONException;

import java.math.BigDecimal;

public class PaymentppActivity extends AppCompatActivity {


    Button btnPayNow;
    EditText edtAmount;

    String amount = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentpp);


        btnPayNow = findViewById(R.id.btnPayNow);
        edtAmount = findViewById(R.id.edtAmount);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPayment();
            }
        });


    }



    private void processPayment() {
        amount = edtAmount.getText().toString();

    }



}
