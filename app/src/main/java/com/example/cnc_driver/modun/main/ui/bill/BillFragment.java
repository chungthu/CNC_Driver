package com.example.cnc_driver.modun.main.ui.bill;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.controller.BillAdapter;
import com.example.cnc_driver.interfaces.DataBillStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.BillResponse;
import com.example.cnc_driver.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends BaseFragment {

    private List<BillResponse.BillBean> list;
    private FirebaseManager firebaseManager = new FirebaseManager();
    private BillAdapter billAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        recyclerView= view.findViewById(R.id.recycler);
        setup();
    }

    private void setup(){
        firebaseManager.readAllBill();
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        firebaseManager.setDataBillStatus(new DataBillStatus() {
            @Override
            public void getData(List<BillResponse.BillBean> item) {
                //Đây là dữ liệu đã đc lấy về và chuyển thành list
                list = item;
                if (billAdapter == null) {
                    billAdapter = new BillAdapter(getContext(), item);
                    recyclerView.setAdapter(billAdapter);
                }else {
                    billAdapter.update(item);
                }
            }
        });
    }
}