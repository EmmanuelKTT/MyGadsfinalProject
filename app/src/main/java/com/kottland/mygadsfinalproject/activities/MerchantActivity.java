package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.adapters.MerchantProductAdapter;
import com.kottland.mygadsfinalproject.model.product;

import java.util.ArrayList;
import java.util.List;

public class MerchantActivity extends AppCompatActivity {

    private MerchantProductAdapter adapter;
    private Context context;
    FloatingActionButton fabcreatePD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);


        context = this;
        setTitle(context.getText(R.string.merchant_act_title));

        connectView();


    }

    public List<product> getAllproduct() {

        List<product> productList = new ArrayList<>();

        productList.add(new product(1, "MackBook PRo", "adoascscscscvvdvddiwifjfej", "1550", "12/10/2020"));
        productList.add(new product(2, "Cloths", "adoascscscscvvdvddiwifjfej", "150", "12/10/2020"));
        productList.add(new product(3, "iphone", "adoascscscscvvdvddiwifjfej", "150", "12/10/2020"));
        productList.add(new product(4, "tablet s5", "adoascscscscvvdvddiwifjfej", "650", "12/10/2020"));
        productList.add(new product(5, "Keyboard ", "adoascscscscvvdvddiwifjfej", "30", "12/10/2020"));
        productList.add(new product(6, "27' Samsung screen", "adoascscscscvvdvddiwifjfej", "210", "12/10/2020"));
        productList.add(new product(7, "Samsung s9", "adoascscscscvvdvddiwifjfej", "310", "12/10/2020"));


        return productList;
    }

    private void connectView() {

        fabcreatePD = (FloatingActionButton) findViewById(R.id.fabproduct);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MerchantProductAdapter(context, getAllproduct());
        //  adapter.setListener(this);
        recyclerView.setAdapter(adapter);


        fabcreatePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createProductAlert();
            }
        });
    }


    public void createProductAlert() {

        final Dialog dialog_alert = new Dialog(MerchantActivity.this, R.style.displayx_dialog);
        dialog_alert.setContentView(R.layout.dialog_create_product);
        dialog_alert.setCanceledOnTouchOutside(false);
        dialog_alert.setCancelable(false);

        final Button btnValidate = (Button) dialog_alert.findViewById(R.id.btn_validate);
        final Button btnCancel = (Button) dialog_alert.findViewById(R.id.btn_cancel);
        final TextView heardertxt = (TextView) dialog_alert.findViewById(R.id.txtheader);
        final TextView txttitle = (TextView) dialog_alert.findViewById(R.id.txttitle);

        txttitle.setText("" + "" +
                "\n\n" +
                "Product Content");


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_alert.dismiss();

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_alert.dismiss();

            }
        });


        dialog_alert.show();
        //dialog_alert.setCanceledOnTouchOutside(false);
        Window window = dialog_alert.getWindow();
      //  window.getAttributes().windowAnimations = R.style.DialogAnimation;
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }





}
