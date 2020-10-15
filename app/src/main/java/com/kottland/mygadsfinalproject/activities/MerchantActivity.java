package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.adapters.MerchantProductAdapter;
import com.kottland.mygadsfinalproject.datadb.MyDatabaseHelper;
import com.kottland.mygadsfinalproject.model.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MerchantActivity extends AppCompatActivity implements MerchantProductAdapter.AccountListener {

    private MerchantProductAdapter adapter;
    private Context context;
    FloatingActionButton fabcreatePD;
    MyDatabaseHelper myDB;
    RecyclerView recyclerView ;
    List<product> productList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        myDB = new MyDatabaseHelper(MerchantActivity.this);


        context = this;
        setTitle(context.getText(R.string.merchant_act_title));

        connectView();

    }

    public List<product> getAllproduct() {

        Collections.reverse(productList);
        return productList;
    }

    private void connectView() {
        productList = new ArrayList<>();
        fabcreatePD = (FloatingActionButton) findViewById(R.id.fabproduct);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        storeDataInArrays();

        adapter = new MerchantProductAdapter(context, getAllproduct());
        //  adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        fabcreatePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createProductAlert();
            }
        });
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
//            empty_imageview.setVisibility(View.VISIBLE);
//            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                /*book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));*/
                 String idpd = cursor.getString(0);
                 String NamePd = cursor.getString(1);
                 String Amntpd = cursor.getString(2);
                 String pagetd = cursor.getString(3);

                productList.add(new product(Integer.valueOf(idpd), NamePd, Amntpd, Amntpd, pagetd));

            }
//            empty_imageview.setVisibility(View.GONE);
//            no_data.setVisibility(View.GONE);
        }
    }


    public void createProductAlert() {

        final Dialog dialog_alert = new Dialog(MerchantActivity.this, R.style.displayx_dialog);
        dialog_alert.setContentView(R.layout.dialog_create_product);
        dialog_alert.setCanceledOnTouchOutside(false);
        dialog_alert.setCancelable(false);

        final Button btnValidate = (Button) dialog_alert.findViewById(R.id.btn_validate);
        final Button btnCancel = (Button) dialog_alert.findViewById(R.id.btn_cancel);
        final ImageView btnclose = (ImageView) dialog_alert.findViewById(R.id.action_close);
        final TextView heardertxt = (TextView) dialog_alert.findViewById(R.id.txtheader);
        final EditText productName = (EditText) dialog_alert.findViewById(R.id.productName);
        final EditText productAmount = (EditText) dialog_alert.findViewById(R.id.productAmount);

        heardertxt.setText(
                "Create a new Product");


        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_alert.dismiss();
            }
        });
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdName = productName.getText().toString();
                String pdAmt =  productAmount.getText().toString();

                myDB.addProduct(pdName, pdAmt);
                storeDataInArrays();
                connectView();


//                recyclerView.notify();

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



    private void UpdateProduct(final int position, String pdName, String pdAmnt){

        final Dialog dialog_alert = new Dialog(MerchantActivity.this, R.style.displayx_dialog);
        dialog_alert.setContentView(R.layout.dialog_create_product);
        dialog_alert.setCanceledOnTouchOutside(false);
        dialog_alert.setCancelable(false);

        final Button btnValidate = (Button) dialog_alert.findViewById(R.id.btn_validate);
        final Button btnCancel = (Button) dialog_alert.findViewById(R.id.btn_cancel);
        final ImageView btnclose = (ImageView) dialog_alert.findViewById(R.id.action_close);
        btnValidate.setText("Update");
        btnCancel.setText("Delete");

        final TextView heardertxt = (TextView) dialog_alert.findViewById(R.id.txtheader);
        final EditText productName = (EditText) dialog_alert.findViewById(R.id.productName);
        final EditText productAmount = (EditText) dialog_alert.findViewById(R.id.productAmount);
        productName.setText(pdName);
        productAmount.setText(pdAmnt);
        heardertxt.setText("Update This Product");


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdName = productName.getText().toString();
                String pdAmt =  productAmount.getText().toString();

                myDB.updateData(String.valueOf(position), pdName, pdAmt);
                storeDataInArrays();
                connectView();


//                recyclerView.notify();

                dialog_alert.dismiss();

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_alert.dismiss();
                myDB.deleteOneRow(String.valueOf(position));
                storeDataInArrays();
                connectView();
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_alert.dismiss();
            }
        });
        dialog_alert.show();
        //dialog_alert.setCanceledOnTouchOutside(false);
        Window window = dialog_alert.getWindow();
        //  window.getAttributes().windowAnimations = R.style.DialogAnimation;
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    void confirmDialog(final String idx, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MerchantActivity.this);
                myDB.deleteOneRow(idx);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MerchantActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MerchantActivity.this, MerchantActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    @Override
    public void edit(int position) {
        UpdateProduct(productList.get(position).getProductId(), productList.get(position).getProductName(),
                productList.get(position).getProductAmount());
    }

}
