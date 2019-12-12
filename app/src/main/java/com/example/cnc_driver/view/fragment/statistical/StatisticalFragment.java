package com.example.cnc_driver.view.fragment.statistical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.response.BillResponse;
import com.example.cnc_driver.view.activity.StatisticalMonthActivity;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StatisticalFragment extends BaseFragment {
    private Button  btnmonth;
    private TextView txtend;

    int total =0;
    ArrayList<Integer> integers = new ArrayList<>();
    private List<BillResponse.BillBean> list;
    private List<BillResponse.BillBean> list2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        init();
        setup();
        btnmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StatisticalMonthActivity.class));
            }
        });
    }

    private void setup() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd");
        String date = dateFormat.format(calendar.getTime());
        Query query = FirebaseDatabase.getInstance().getReference("Bill")
                .orderByChild("time")
                .startAt(date)
                .endAt(date + "\uf88f");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BillResponse.BillBean billResponse = snapshot.getValue(BillResponse.BillBean.class);
                    list.add(billResponse);
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isStatus_pay()) {
                        list2.add(list.get(i));
                    }

                }
                for (int j=0; j < list2.size(); j++){
                    int a=Integer.parseInt(list2.get(j).getTotal());
                    integers.add(a);
                }
                for (Integer element : integers){
                    total += element;
                }
                Locale locale = new Locale("vi","VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                double tt= Double.parseDouble(String.valueOf(total));
                txtend.setText(String.format(format.format(tt)));

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void init() {

        btnmonth = getView().findViewById(R.id.btnmonth);

        txtend = getView().findViewById(R.id.txtend);


    }


}