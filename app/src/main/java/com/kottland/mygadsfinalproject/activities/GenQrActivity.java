package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kottland.mygadsfinalproject.R;

public class GenQrActivity extends AppCompatActivity {

    Bitmap bmp;
    String prodID, prodName, prodAmt;

    TextView tvProductCode, tvProductName, tvPRodAmnt;
    FloatingActionButton fabShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qr);

        setTitle("Scan And PAY ");
        prodID = getIntent().getStringExtra("prdID");
        prodName = getIntent().getStringExtra("prodName");
        prodAmt = getIntent().getStringExtra("prodAmt");

        tvProductCode = (TextView)findViewById(R.id.text_view_content);
        tvProductName = (TextView)findViewById(R.id.text_view_type);
        tvPRodAmnt = (TextView)findViewById(R.id.text_view_time);
        fabShare = (FloatingActionButton)findViewById(R.id.fab_share);

        tvProductCode.setText("Product Code: "+ prodID);
        tvProductName.setText("Product Name: "+  prodName);
        tvPRodAmnt.setText("Product Cost: "+  prodAmt);


        genQRCode();


        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bmp,"qr_image", null);
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }


    private void genQRCode() {

        String message_rq = "XDGADS1"+ prodID;

        String content = message_rq;


        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((AppCompatImageView) findViewById(R.id.image_qrcode)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }



    }



}
