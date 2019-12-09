package com.example.cnc_driver.view.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.FirebaseManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserActivity extends BaseActivity {

    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    private FirebaseManager firebaseManager = new FirebaseManager();

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_add_user;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        addUser();
    }

    private void addUser(){
        String user = edtUser.getText().toString();
        String password = edtPassword.getText().toString().trim();
        firebaseManager.insertUser(user,password,2);
        finish();
    }
}
