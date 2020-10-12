package com.kottland.mygadsfinalproject.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.model.product;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MerchantProductAdapter extends RecyclerView.Adapter<MerchantProductAdapter.RecyclerViewHolder> {

    private List<product> account_list;
    private Context context;



    public MerchantProductAdapter(Context context, List<product> account_list ) {
        this.context = context;
        this.account_list = account_list;
    }

    @Override
    public int getItemCount() {
        return account_list.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.merchant_list_item, viewGroup, false);




        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {

        product prodct = account_list.get(position);
      //  viewHolder.tvImge.setText();
        viewHolder.tvTitle.setText(prodct.getProductName());
        viewHolder.tvDesc.setText(prodct.getProductAmount()+ " "+"USD");


    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public ImageView tvImge;
        public  TextView tvTitle;
        public  TextView tvDesc;
        public LinearLayout tvParent;

        // for show menu edit and delete
        private PopupMenu popupMenu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tvImge = (ImageView) itemView.findViewById(R.id.image_tv);
            tvTitle = (TextView) itemView.findViewById(R.id.title_tv);
            tvDesc = (TextView) itemView.findViewById(R.id.desc_tv);
            tvParent = (LinearLayout) itemView.findViewById(R.id.linerlayout_tv);



            itemView.findViewById(R.id.linerlayout_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

    }

    public void removeItem(int position) {
        account_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,account_list.size());
    }

    public interface AccountListener {


        void del(int position);
        void retrieve(int position);
    }

    private  AccountListener listener;

    public MerchantProductAdapter setListener(AccountListener listener) {
        this.listener = listener;
        return this;
    }
}

