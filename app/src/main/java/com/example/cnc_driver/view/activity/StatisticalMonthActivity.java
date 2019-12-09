package com.example.cnc_driver.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cnc_driver.R;
import com.example.cnc_driver.net.response.BillResponse;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticalMonthActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private CombinedChart mChart;
    private Spinner spinner;
    int sttmonth = 0;
    int total = 0;
    ArrayList<Integer> integers = new ArrayList<>();
    private List<BillResponse.BillBean> list;
    private List<BillResponse.BillBean> list2;
    private List<Integer> listda;
    String wek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_month);
        spinner = findViewById(R.id.spiner);
        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);


        listda = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("M");


        String date = dateFormat.format(calendar.getTime());



        ArrayList<Integer> month = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            month.add(i);
        }
//        listda = new ArrayList<>();
//        listda.add("Jan");
//        listda.add("Feb");
//        listda.add("Mar");
//        listda.add("Apr");
//        listda.add("May");
//        listda.add("Jun");
//        listda.add("Jul");
//        listda.add("Aug");
//        listda.add("Sep");
//        listda.add("Oct");
//        listda.add("Nov");
//        listda.add("Dec");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, month);

        spinner.setAdapter(arrayAdapter);
        int mm = Integer.parseInt(date);
        spinner.setSelection(mm - 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sttmonth = month.get(i);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("1");
        xLabel.add("2");
        xLabel.add("3");
        xLabel.add("4");
        xLabel.add("5");
        xLabel.add("6");
        xLabel.add("7");
        xLabel.add("8");
        xLabel.add("9");
        xLabel.add("10");
        xLabel.add("11");
        xLabel.add("12");
        xLabel.add("13");
        xLabel.add("14");
        xLabel.add("15");
        xLabel.add("16");
        xLabel.add("17");
        xLabel.add("18");
        xLabel.add("19");
        xLabel.add("20");
        xLabel.add("21");
        xLabel.add("22");
        xLabel.add("23");
        xLabel.add("24");
        xLabel.add("25");
        xLabel.add("26");
        xLabel.add("27");
        xLabel.add("28");
        xLabel.add("29");
        xLabel.add("30");
        xLabel.add("31");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();

        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
        query();
    }

    private void query() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        listda= new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("Bill")
                .orderByChild("time")
                .startAt("Mon Dec 09")
                .endAt("Mon Dec 09\uf88f");
        query.addListenerForSingleValueEvent(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BillResponse.BillBean billResponse = snapshot.getValue(BillResponse.BillBean.class);
                    list.add(billResponse);
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isStatus_pay()) {
                        list2.add(list.get(i));
                    }

                }
                for (int j = 0; j < list2.size(); j++) {
                    int a = Integer.parseInt(list2.get(j).getTotal());
                    integers.add(a);
                }
                for (Integer element : integers) {
                    total += element;
                }
               listda.add(total);


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Ngày: "
                + h.getX()
                + " Bán được: "
                + e.getY() + " đ" +
                +h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private static DataSet dataChart() {

        LineData d = new LineData();

        int[] data = new int[]{100, 200, 200, 100, 100, 100, 200, 100, 100, 200, 100, 900, 400, 500, 600, 100, 800, 500, 300, 200, 600, 700, 500, 800, 200, 800, 400, 900, 400, 100, 800};

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 31; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Doanh thu hàng ngày");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.RED);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLACK);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;


    }


}
