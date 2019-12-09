package com.example.cnc_driver.modun.main.ui.tools;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.view.activity.StatisticalDateActivity;
import com.example.cnc_driver.view.activity.StatisticalMonthActivity;
import com.example.cnc_driver.view.fragment.BaseFragment;

public class ToolsFragment extends BaseFragment {
    private Button btnca,btndate,btnmonth;
    private TextView txtfirt,txtend;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        init();
        btnca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                View mview= getLayoutInflater().inflate(R.layout.dialog_end,null);
                EditText editText=(EditText) mview.findViewById(R.id.edtnumber);
                Button btn=(Button) mview.findViewById(R.id.btnkethuc);

                mbuilder.setView(mview);
                AlertDialog alertDialog= mbuilder.create();
                alertDialog.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtfirt.setText(editText.getText());
                        alertDialog.dismiss();
                    }
                });
            }
        });
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StatisticalDateActivity.class));
            }
        });
        btnmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StatisticalMonthActivity.class));
            }
        });
    }
    private void init() {
        btnca= getView().findViewById(R.id.btnend);
        btndate=getView().findViewById(R.id.btndate);
        btnmonth= getView().findViewById(R.id.btnmonth);
        txtfirt= getView().findViewById(R.id.txtfirt);
        txtend = getView().findViewById(R.id.txtend);


    }
}