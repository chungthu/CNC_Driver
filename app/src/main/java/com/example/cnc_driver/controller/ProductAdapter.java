package com.example.cnc_driver.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.response.ProductResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {

    private Context context;
    private List<ProductResponse> item;

    public ProductAdapter(Context context, List<ProductResponse> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<ProductResponse> item){
        this.item = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        assert item.get(position).getImage() != null;
        Picasso.get().load(item.get(position).getImage()).into(holder.img);

        holder.tvName.setText(item.get(position).getName());
        holder.tvPrice1.setText(item.get(position).getPrice1());
        holder.tvPrice2.setText(item.get(position).getPrice2());
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private CardView llItem;
        private ImageView img;
        private TextView tvName;
        private TextView tvPrice1;
        private TextView tvPrice2;

        Viewholder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice1 = itemView.findViewById(R.id.tv_price1);
            tvPrice2 = itemView.findViewById(R.id.tv_price2);
        }
    }
}
