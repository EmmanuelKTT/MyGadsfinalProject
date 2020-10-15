package com.kottland.mygadsfinalproject.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.adapters.BuyerListAdapter;
import com.kottland.mygadsfinalproject.adapters.MerchantProductAdapter;
import com.kottland.mygadsfinalproject.adapters.TabsPagerAdapter;
import com.kottland.mygadsfinalproject.datadb.MyDatabaseHelper;
import com.kottland.mygadsfinalproject.model.history_items;
import com.kottland.mygadsfinalproject.model.product;
import com.kottland.mygadsfinalproject.paymentproc.PaymentppActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuyerActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);


        setTitle(getText(R.string.buyer_act_title));
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }





}
