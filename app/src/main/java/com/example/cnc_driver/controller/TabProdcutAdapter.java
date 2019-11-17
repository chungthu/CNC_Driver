package com.example.cnc_driver.controller;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cnc_driver.R;
import com.example.cnc_driver.modun.main.ui.product.BreadFragment;
import com.example.cnc_driver.modun.main.ui.product.FruitFragment;
import com.example.cnc_driver.modun.main.ui.product.MilkTeaFragment;

public class TabProdcutAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.title_milk_tea, R.string.title_fruit, R.string.title_bread};
    private final Context mContext;

    public TabProdcutAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = MilkTeaFragment.newInstance();
                break;
            case 1:
                fragment = FruitFragment.newInstance();
                break;
            case 2:
                fragment = BreadFragment.newInstance();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
