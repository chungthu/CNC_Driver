package com.example.cnc_driver.view.fragment.product;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.controller.ProductAdapter;
import com.example.cnc_driver.interfaces.DataMilkteaStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.ProductResponse;
import com.example.cnc_driver.view.activity.AddProductActivity;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MilkTeaFragment extends BaseFragment {


    @BindView(R.id.rv_milk_tea)
    RecyclerView rvMilkTea;
    @BindView(R.id.fab_addProduct)
    FloatingActionButton fabAddProduct;
    private ProductAdapter adapter;
    private List<ProductResponse> list;
    private FirebaseManager firebaseManager = new FirebaseManager();

    public static MilkTeaFragment newInstance() {
        MilkTeaFragment fragment = new MilkTeaFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_milk_tea;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        setup();
    }

    private void setup() {
        firebaseManager.reaAllDataTea();
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        rvMilkTea.setHasFixedSize(true);
        rvMilkTea.setLayoutManager(manager);
        list = new ArrayList<>();

        firebaseManager.setDataMilkteaStatus(new DataMilkteaStatus() {
            @Override
            public void getData(List<ProductResponse> item) {
                list = item;
                if (adapter == null) {
                    adapter = new ProductAdapter(getContext(), item);
                    rvMilkTea.setAdapter(adapter);
                } else {
                    adapter.update(item);
                }
            }
        });
    }

    @OnClick(R.id.fab_addProduct)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), AddProductActivity.class));
    }
}
