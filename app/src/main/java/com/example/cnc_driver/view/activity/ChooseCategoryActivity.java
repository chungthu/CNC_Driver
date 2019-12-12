package com.example.cnc_driver.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.cnc_driver.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseCategoryActivity extends BaseActivity {

    @BindView(R.id.tv_milk_tea)
    TextView tvMilkTea;
    @BindView(R.id.img_check_miiltea)
    ImageView imgCheckMiiltea;
    @BindView(R.id.ll_milk_tea)
    LinearLayout llMilkTea;
    @BindView(R.id.tv_fruit)
    TextView tvFruit;
    @BindView(R.id.img_check_fruit)
    ImageView imgCheckFruit;
    @BindView(R.id.ll_fruit)
    LinearLayout llFruit;
    @BindView(R.id.tv_bread)
    TextView tvBread;
    @BindView(R.id.img_check_bread)
    ImageView imgCheckBread;
    @BindView(R.id.ll_bread)
    LinearLayout llBread;
    private Timer timer = new Timer();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_choose_category;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_milk_tea, R.id.ll_fruit, R.id.ll_bread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_milk_tea:
                imgCheckMiiltea.setVisibility(View.VISIBLE);
                imgCheckFruit.setVisibility(View.GONE);
                imgCheckBread.setVisibility(View.GONE);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","1");
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                },100);
                break;
            case R.id.ll_fruit:
                imgCheckMiiltea.setVisibility(View.GONE);
                imgCheckFruit.setVisibility(View.VISIBLE);
                imgCheckBread.setVisibility(View.GONE);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","2");
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                },100);
                break;
            case R.id.ll_bread:
                imgCheckMiiltea.setVisibility(View.GONE);
                imgCheckFruit.setVisibility(View.GONE);
                imgCheckBread.setVisibility(View.VISIBLE);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","3");
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                },100);
                break;
        }
    }
}
