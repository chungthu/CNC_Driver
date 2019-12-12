package com.example.cnc_driver.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.TableResponse;
import com.example.cnc_driver.printer.PrintDoneActivity;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    Context context;
    List<TableResponse> item;
    private FirebaseManager firebaseManager = new FirebaseManager();

    public TableAdapter(Context context, List<TableResponse> item) {
        this.context = context;
        this.item = item;
    }

    public void update(List<TableResponse> tableResponses) {
        this.item = tableResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (item.get(position).getStatus() == 0) {
            holder.tvNameTable.setText(item.get(position).getName());
            holder.tvNameTable.setTextColor(Color.BLACK);
            holder.cardTable.setBackgroundColor(Color.WHITE);
            holder.img.setImageResource(R.drawable.ic_shopping_basket_black_24dp);
            holder.cardTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, R.string.not_bill, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.tvNameTable.setText(item.get(position).getName());
            holder.tvNameTable.setTextColor(Color.WHITE);
            holder.cardTable.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.img.setImageResource(R.drawable.ic_shopping_basket_white_24dp);
            holder.cardTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseManager.bill(item.get(position).getId());
                    Intent intent = new Intent(context, PrintDoneActivity.class);
                    intent.putExtra("table", item.get(position).getName());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (item == null) {
            return 0;
        }
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardTable;
        private TextView tvNameTable;
        private ImageView img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTable = itemView.findViewById(R.id.card_Table);
            tvNameTable = itemView.findViewById(R.id.tv_name_table);
            img = itemView.findViewById(R.id.img);
        }
    }
}
