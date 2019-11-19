package com.example.cnc_driver.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.response.BillResponse;

import java.util.List;

public class ProductBeanAdapter extends RecyclerView.Adapter<ProductBeanAdapter.Viewholder> {
    private Context context;
    private List<BillResponse.BillBean.ProductsBean> listProduct;

    public ProductBeanAdapter(Context context, List<BillResponse.BillBean.ProductsBean> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    public void update(List<BillResponse.BillBean.ProductsBean> list) {
        this.listProduct = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductBeanAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bean, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBeanAdapter.Viewholder holder, int position) {
        holder.txtname.setText(listProduct.get(position).getName());
        holder.txtsl.setText(listProduct.get(position).getAmount());
        holder.txtprice.setText(listProduct.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (listProduct == null) {
            return 0;
        }
        return listProduct.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private CardView car;
        private TextView txtname, txtsl, txtprice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            car = itemView.findViewById(R.id.cardbean);
            txtname = itemView.findViewById(R.id.name_bean);
            txtsl = itemView.findViewById(R.id.numer);
            txtprice = itemView.findViewById(R.id.price_bean);
        }
    }
}
