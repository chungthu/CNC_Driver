package com.example.cnc_driver.modun.main.ui.bill;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cnc_driver.R;
import com.example.cnc_driver.common.base.BaseFragment;
import com.example.cnc_driver.interfaces.DataBillStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.BillResponse;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends BaseFragment {

    private List<BillResponse.BillBean> list;
    private FirebaseManager firebaseManager = new FirebaseManager();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        setup();
    }

    private void setup(){
        firebaseManager.readAllBill();
        list = new ArrayList<>();

        firebaseManager.setDataBillStatus(new DataBillStatus() {
            @Override
            public void getData(List<BillResponse.BillBean> item) {
                list = item;
            }
        });
    }
}