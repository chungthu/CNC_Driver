package com.example.cnc_driver.view.fragment.product;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.cnc_driver.R;
import com.example.cnc_driver.controller.TabProdcutAdapter;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class ProductFragment extends BaseFragment {

    @BindView(R.id.tab_product)
    TabLayout tabProduct;
    @BindView(R.id.vp_product)
    ViewPager vpProduct;
    private TabProdcutAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        setUpTabs();
    }

    private void setUpTabs() {
        this.adapter = new TabProdcutAdapter(getActivity().getSupportFragmentManager(), getContext());
        this.vpProduct.setOffscreenPageLimit(2);
        this.vpProduct.setAdapter(adapter);
        this.tabProduct.setupWithViewPager(vpProduct);
    }
}