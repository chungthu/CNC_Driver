package com.example.cnc_driver.modun.main.ui.store;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.controller.TableAdapter;
import com.example.cnc_driver.interfaces.DataTableStatus;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.TableResponse;
import com.example.cnc_driver.view.fragment.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class StoreFragment extends BaseFragment {


    @BindView(R.id.rv_table)
    RecyclerView rvTable;
    @BindView(R.id.fb_table)
    FloatingActionButton fbTable;
    private TableAdapter adapter;
    private FirebaseManager firebaseManager = new FirebaseManager();
    private List<TableResponse> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;

    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        firebaseManager.readAllTable();
        setup();
    }

    @OnClick(R.id.fb_table)
    public void onViewClicked() {
        insertTable();
    }

    private void setup(){
        rvTable.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvTable.setHasFixedSize(true);

        firebaseManager.setDataTableStatus(new DataTableStatus() {
            @Override
            public void getData(List<TableResponse> item) {
                list = item;
                if (adapter == null){
                    adapter = new TableAdapter(getContext(),list);
                    rvTable.setAdapter(adapter);
                }else {
                    adapter.update(list);
                }
            }
        });
    }

    private void insertTable(){
        final EditText nameTable;

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_insert_table, null);
        nameTable = alertLayout.findViewById(R.id.edt_name_table);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.key_inset_Table));
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (nameTable.getText().toString().equals("")) {
                    AlertDialog.Builder dialog_builder = new AlertDialog.Builder(getContext());
                    dialog_builder.setMessage(getString(R.string.key_emtry));
                    dialog_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog_builder.show();
                } else {
                    String name  = nameTable.getText().toString();
                    firebaseManager.insertTable(name);
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}