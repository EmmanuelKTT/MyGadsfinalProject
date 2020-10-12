package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.adapters.BuyerListAdapter;
import com.kottland.mygadsfinalproject.model.history_items;

import java.util.ArrayList;
import java.util.List;

public class BuyerActivity extends AppCompatActivity {

    private BuyerListAdapter adapter;
    private Context context;
    FloatingActionButton fabPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        context = this;
        setTitle(context.getText(R.string.buyer_act_title));

        connectView();

    }

    public List<history_items> getAll_soldItem(){

        List<history_items> productList = new ArrayList<>();

        productList.add(new history_items(1, "MackBook PRo",  "1550", "12/10/2020"));
        productList.add(new history_items(2, "Cloths", "150", "12/10/2020"));
        productList.add(new history_items(3, "iphone",  "150", "12/10/2020"));
        productList.add(new history_items(4, "tablet s5",  "650", "12/10/2020"));
        return productList;
    }

    private void connectView() {

        fabPay  = (FloatingActionButton)findViewById(R.id.fab_pay);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BuyerListAdapter(context, getAll_soldItem());
        //  adapter.setListener(this);
        recyclerView.setAdapter(adapter);


        fabPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectToPayDialog();
            }
        });

    }


    private  void  connectToPayDialog(){

        final Dialog dialog_alert = new Dialog(BuyerActivity.this, R.style.displayx_dialog);
        dialog_alert.setContentView(R.layout.dialog_payment_product);
        dialog_alert.setCanceledOnTouchOutside(false);
        dialog_alert.setCancelable(false);

        final Button btnValidate = (Button) dialog_alert.findViewById(R.id.btn_validate);
        final Button btnCancel = (Button) dialog_alert.findViewById(R.id.btn_cancel);
        final TextView heardertxt = (TextView) dialog_alert.findViewById(R.id.txtheader);
        final TextView txttitle = (TextView) dialog_alert.findViewById(R.id.txttitle);

        txttitle.setText("" + "" +
                "\n\n" +
                "Select Means of Payment And pay");


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
