package com.kottland.mygadsfinalproject.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.activities.BuyerActivity;
import com.kottland.mygadsfinalproject.activities.GenQrActivity;
import com.kottland.mygadsfinalproject.activities.ScanPayActivity;
import com.kottland.mygadsfinalproject.adapters.AllProductAdapter;
import com.kottland.mygadsfinalproject.adapters.BuyerListAdapter;
import com.kottland.mygadsfinalproject.adapters.MerchantProductAdapter;
import com.kottland.mygadsfinalproject.datadb.MyDatabaseHelper;
import com.kottland.mygadsfinalproject.model.history_items;
import com.kottland.mygadsfinalproject.model.product;
import com.kottland.mygadsfinalproject.paymentproc.PaymentppActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllItemsFragment extends Fragment implements AllProductAdapter.AccountListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AllProductAdapter adapter;
    private Context context;
    MyDatabaseHelper myDB;
    RecyclerView recyclerView ;
    List<product> productList = new ArrayList<>();
    FloatingActionButton fabPay;

    public static AllItemsFragment newInstance() {
        return new AllItemsFragment();
    }



    public AllItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllItemsFragment newInstance(String param1, String param2) {
        AllItemsFragment fragment = new AllItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_items, container, false);


        myDB = new MyDatabaseHelper(getActivity());

        context = getActivity();
        fabPay  = (FloatingActionButton)root.findViewById(R.id.fab_pay);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        fabPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToPayDialog();

            }
        });
        connectView();
        return root;

    }

    public List<product> getAllproduct() {

        Collections.reverse(productList);
        return productList;
    }

    private void connectView() {
        productList = new ArrayList<>();
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        storeDataInArrays();

        adapter = new AllProductAdapter(context, getAllproduct());
        //  adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
//            empty_imageview.setVisibility(View.VISIBLE);
//            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                String idpd = cursor.getString(0);
                String NamePd = cursor.getString(1);
                String Amntpd = cursor.getString(2);
                String pagetd = cursor.getString(3);

                Log.e("POSIT", idpd );
                Log.e("NamePd", NamePd );
                Log.e("Amntpd", Amntpd );
                productList.add(new product(Integer.parseInt(idpd), NamePd, Amntpd, Amntpd, pagetd));

            }
//            empty_imageview.setVisibility(View.GONE);
//            no_data.setVisibility(View.GONE);
        }
    }



    void confirmDialog(final String idx, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                myDB.deleteOneRow(idx);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }




    private  void  connectToPayDialog(){

        final Dialog dialog_alert = new Dialog(getActivity(), R.style.displayx_dialog);
        dialog_alert.setContentView(R.layout.dialog_payment_product);
        dialog_alert.setCanceledOnTouchOutside(false);
        dialog_alert.setCancelable(false);

        final Button btnValidate = (Button) dialog_alert.findViewById(R.id.btn_validate);
        final Button btnCancel = (Button) dialog_alert.findViewById(R.id.btn_cancel);
        final TextView heardertxt = (TextView) dialog_alert.findViewById(R.id.txtheader);
        AppCompatSpinner spinnerTypes = (AppCompatSpinner)dialog_alert.findViewById(R.id.spinnerttype);

        heardertxt.setText("" + "" +
                "Select payment Method");

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(context,
                R.array.pay_types, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinnerTypes.setAdapter(arrayAdapter);


        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_alert.dismiss();

                startActivity(new Intent(getActivity(), ScanPayActivity.class));
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
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


    @Override
    public void edit(int position) {


        startActivity(new Intent(getActivity(), GenQrActivity.class)
                .putExtra("prdID", String.valueOf(productList.get(position).getProductId()))
                .putExtra("prodName", productList.get(position).getProductName())
                .putExtra("prodAmt", productList.get(position).getProductAmount())
        );
    }
}