package com.example.cnc_driver.modun.main.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.controller.UserAdapter;
import com.example.cnc_driver.interfaces.DataUserStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.UserRespones;
import com.example.cnc_driver.view.activity.AddUserActivity;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareFragment extends BaseFragment {

    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    @BindView(R.id.fab_addUser)
    FloatingActionButton fabAddUser;

    private List<UserRespones> list = new ArrayList<>();
    private FirebaseManager firebaseManager = new FirebaseManager();
    private UserAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        setUp();
    }

    @OnClick(R.id.fab_addUser)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), AddUserActivity.class));
    }

    private void setUp(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        rvUser.setHasFixedSize(true);
        rvUser.setLayoutManager(gridLayoutManager);
        firebaseManager.readAddUser();
        firebaseManager.setDataUserStatus(new DataUserStatus() {
            @Override
            public void getData(List<UserRespones> item) {
                list = item;
                if (adapter == null) {
                    adapter = new UserAdapter(getContext(), list);
                    rvUser.setAdapter(adapter);
                }else {
                    adapter.update(list);
                }
            }
        });
    }
}