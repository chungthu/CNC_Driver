package com.example.cnc_driver.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.common.Constacts;
import com.example.cnc_driver.common.eventBus.ActionEvent;
import com.example.cnc_driver.common.eventBus.MessagesEvent;
import com.example.cnc_driver.net.response.BillResponse;
import com.example.cnc_driver.printer.PrintActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.Viewholder> {
    private Context context;

    private List<BillResponse.BillBean> listbill;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dt1 = new SimpleDateFormat(Constacts.DATE_FORMAT);

    public BillAdapter(Context context, List<BillResponse.BillBean> listbill) {
        this.context = context;
        this.listbill = listbill;
    }

    public void update(List<BillResponse.BillBean> list) {
        this.listbill = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.Viewholder holder, int position) {

        holder.namebill.setText(listbill.get(position).getId());
        holder.total.setText(listbill.get(position).getTotal());

        //Date da dc format
        String date = dt1.format(new Date(listbill.get(position).getTime()));

        String cva = listbill.get(position).getTotal();
        if (listbill.get(position).isStatus_pay()) {
            holder.status.setText("Đã thanh toán");
        } else if (!listbill.get(position).isStatus_pay()) {
            holder.status.setText("Chưa thanh toán");
        }
        holder.carview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new ActionEvent(MessagesEvent.DATA_BILL, listbill.get(position)));
                Intent intent = new Intent(context, PrintActivity.class);
                intent.putExtra("total", cva);
                intent.putExtra("name", listbill.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listbill == null) {
            return 0;
        }
        return listbill.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private CardView carview;
        private TextView namebill;
        private TextView total, date, status;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            carview = itemView.findViewById(R.id.cardview);
            namebill = itemView.findViewById(R.id.name_bill);
            total = itemView.findViewById(R.id.total);
            date = itemView.findViewById(R.id.name_date);
            status = itemView.findViewById(R.id.status);

        }
    }
}
