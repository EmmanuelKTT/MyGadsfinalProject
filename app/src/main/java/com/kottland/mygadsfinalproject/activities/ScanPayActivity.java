package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kottland.mygadsfinalproject.MainActivity;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.datadb.MyDatabaseHelper;
import com.kottland.mygadsfinalproject.model.product;
import com.kottland.mygadsfinalproject.utils.GenerateRandomString;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanPayActivity extends AppCompatActivity {

    //qr code scanner object
    private IntentIntegrator qrScan;
    MyDatabaseHelper myDB;
    String productName, productAmnt, transacCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pay);
        myDB = new MyDatabaseHelper(ScanPayActivity.this);

        //intializing scan object
        qrScan = new IntentIntegrator(this);


        //initiating the qr code scan
        qrScan.initiateScan();



    }

    public String Gen12GimacStatusCode(){
        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        Log.e("timeStamp", "Gen12GimacStatusCode: "+"-->"+" "+ts);
        return ts;
    }


    private void ProcessingPayment(String  code){

        Log.e( "ProcessingPayment: ","CODE: "+ code );
        ProgressDialog progressDialog = new ProgressDialog(ScanPayActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing Payment ...");
        progressDialog.show();

        MyDatabaseHelper mydb = new MyDatabaseHelper(this);
        product produuctItem = mydb.getSingleProductInfo(code);
        productName =produuctItem.getProductName();
        productAmnt = produuctItem.getProductAmount();

        transacCode =  Gen12GimacStatusCode()+ GenerateRandomString.randomString(5);
        myDB.addTrans(productName, productAmnt);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                showSuccessDialog();

            }


        }, 2500);



    }


    private void showSuccessDialog(){



        AlertDialog.Builder dialog = new AlertDialog.Builder(ScanPayActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_success, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ImageButton dialogBtnCloseDialog = dialogView.findViewById(R.id.btn_close_dialog);

        AlertDialog alertDialogSuccess = dialog.create();

        dialogBtnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogSuccess.dismiss();

                Intent intent = new Intent(ScanPayActivity.this, BuyerActivity.class);
                startActivity(intent);
                finish();

            }
        });



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ScanPayActivity.this, BuyerActivity.class));
                finish();
            }
        }, 4000);



        alertDialogSuccess.show();


    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //     textViewName.setText(obj.getString("name"));
                    //       textViewAddress.setText(obj.getString("address"));

                 //   message.setText(obj.toString());
                    Log.e("TAG_MESSAGE", "onActivityResult: " );
                    Log.e("TAG_MESSAGE", obj.toString() );
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast

                   // message.setText(result.getContents());
                    Log.e("TAG_RESULTS", result.getContents() );
                    Log.e("TAG_RESULTSCODE", result.getContents().substring(7) );
                    if (result.getContents().startsWith("XDGADS1")){
                        Log.e( "ORIGIN DB CODE: ",result.getContents().substring(7));

                        ProcessingPayment(result.getContents().substring(7));
                    }else {
                     //   ProcessingPayment(result.getContents().substring(7));

                        Toast.makeText(this, "NOT a VALID PRODUCT", Toast.LENGTH_LONG).show();

                    }
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);


        }
    }


}
