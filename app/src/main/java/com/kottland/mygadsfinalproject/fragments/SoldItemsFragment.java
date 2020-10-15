package com.kottland.mygadsfinalproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.activities.BuyerActivity;
import com.kottland.mygadsfinalproject.adapters.BuyerListAdapter;
import com.kottland.mygadsfinalproject.datadb.MyDatabaseHelper;
import com.kottland.mygadsfinalproject.model.history_items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoldItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoldItemsFragment extends Fragment  implements BuyerListAdapter.AccountListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyDatabaseHelper myDB;

    private BuyerListAdapter adapter;
    private Context context;
    RecyclerView recyclerView;
    List<history_items> productList = new ArrayList<>();


    public static SoldItemsFragment newInstance() {
        return new SoldItemsFragment();
    }


    public SoldItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoldItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoldItemsFragment newInstance(String param1, String param2) {
        SoldItemsFragment fragment = new SoldItemsFragment();
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
        View root = inflater.inflate(R.layout.fragment_sold_items, container, false);


        myDB = new MyDatabaseHelper(getActivity());

        context = getActivity();
         recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        connectView();
        return root;

    }

    public List<history_items> getAll_soldItem(){
        Collections.reverse(productList);
        return productList;
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllTrans();
        if(cursor.getCount() == 0){
//            empty_imageview.setVisibility(View.VISIBLE);
//            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){

                String idpd = cursor.getString(0);
                String NamePd = cursor.getString(1);
                String Amntpd = cursor.getString(2);
                String pagetd = cursor.getString(3);
                productList.add(new history_items(Integer.parseInt(idpd), NamePd, Amntpd, Amntpd));

            }
//            empty_imageview.setVisibility(View.GONE);
//            no_data.setVisibility(View.GONE);
        }
    }



    private void connectView() {
        productList = new ArrayList<>();
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        storeDataInArrays();

        adapter = new BuyerListAdapter(context, getAll_soldItem());
        //  adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);



    }


    @Override
    public void del(int position) {

        confirmDialog(String.valueOf(productList.get(position).getItemsId()), productList.get(position).getItemsName());

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

}