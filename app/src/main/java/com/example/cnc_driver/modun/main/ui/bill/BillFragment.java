package com.example.cnc_driver.modun.main.ui.bill;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.common.Constacts;

import com.example.cnc_driver.controller.BillAdapter;
import com.example.cnc_driver.controller.BillDoneAdapter;
import com.example.cnc_driver.interfaces.DataBillStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.BillResponse;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends BaseFragment {

    private List<BillResponse.BillBean> list;
    private List<BillResponse.BillBean> list2;
    private FirebaseManager firebaseManager = new FirebaseManager();
    private BillAdapter billAdapter;
    private BillDoneAdapter doneAdapter;
    private RecyclerView recyclerView;
    private  RecyclerView recyclerView1;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private DataBillStatus dataBillStatus;
    DatabaseReference db;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        recyclerView= view.findViewById(R.id.recycler);
        recyclerView1=view.findViewById(R.id.recycler1);
        setup();
    }

    private void setup(){
        firebaseManager.readAllBill();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager1= new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        billAdapter = new BillAdapter(getContext(), list2);
        doneAdapter= new BillDoneAdapter(getContext(),list);
        recyclerView.setAdapter(billAdapter);
        recyclerView1.setAdapter(doneAdapter);
         billAdapter.notifyDataSetChanged();
         doneAdapter.notifyDataSetChanged();
        Query query = FirebaseDatabase.getInstance().getReference("Bill")
                .orderByChild("status_pay")
                .equalTo(true);
        query.addListenerForSingleValueEvent(valueEventListener);

        Query query1= FirebaseDatabase.getInstance().getReference("Bill")
                .orderByChild("status_pay")
                .equalTo(false);
        query1.addListenerForSingleValueEvent(valueEventListener1);



        firebaseManager.setDataBillStatus(new DataBillStatus() {
            @Override
            public void getData(List<BillResponse.BillBean> item) {
                //Đây là dữ liệu đã đc lấy               list = item;về và chuyển thành list
//

                if (billAdapter == null) {
//                    billAdapter = new BillAdapter(getContext(), item);
//                    recyclerView.setAdapter(billAdapter);
                }else {
//                    billAdapter.update(item);
                }
            }
        });
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list2.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BillResponse.BillBean billResponse = snapshot.getValue(BillResponse.BillBean.class);
                    list2.add(billResponse);
                }
                billAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener valueEventListener1= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BillResponse.BillBean billResponse = snapshot.getValue(BillResponse.BillBean.class);
                    list.add(billResponse);
                }
                doneAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}