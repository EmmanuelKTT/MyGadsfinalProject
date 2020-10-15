package com.kottland.mygadsfinalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.kottland.mygadsfinalproject.R;
import com.kottland.mygadsfinalproject.model.history_items;

import java.util.List;


public class BuyerListAdapter extends RecyclerView.Adapter<BuyerListAdapter.RecyclerViewHolder> {

    private List<history_items> account_list;
    private Context context;



    public BuyerListAdapter(Context context, List<history_items> account_list ) {
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
        View itemView = inflater.inflate(R.layout.buyer_list_item, viewGroup, false);




        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {

        history_items prodct = account_list.get(position);
        //  viewHolder.tvImge.setText();
        viewHolder.tvTitle.setText(prodct.getItemsName());
        viewHolder.tvDesc.setText(prodct.getItemsPayType() + " "+"USD");


    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public ImageView tvImge;
        public TextView tvTitle;
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
                    listener.del(getAdapterPosition());

                }
            });
        }

    }


    public interface AccountListener {


        void del(int position);
    }

    private  AccountListener listener;

    public BuyerListAdapter setListener(AccountListener listener) {
        this.listener = listener;
        return this;
    }
}



