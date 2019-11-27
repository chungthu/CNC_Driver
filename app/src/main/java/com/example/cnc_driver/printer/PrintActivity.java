package com.example.cnc_driver.printer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnc_driver.R;
import com.example.cnc_driver.common.eventBus.ActionEvent;
import com.example.cnc_driver.common.eventBus.MessagesEvent;
import com.example.cnc_driver.controller.ProductBeanAdapter;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.response.BillResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PrintActivity extends AppCompatActivity implements Runnable {
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    int printstat;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProductBeanAdapter productBeanAdapter;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    private TextView txttotal, namebill;
    private Button btnCnt, btnprint, btnpay;
    private TextView txtsta;
    private BillResponse.BillBean billBean;
    private List<BillResponse.BillBean.ProductsBean> list;
    private FirebaseManager firebaseManager = new FirebaseManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txttotal = findViewById(R.id.totalbean);
        recyclerView = findViewById(R.id.recyclerv);
        txtsta = findViewById(R.id.txtstatus);
        namebill = findViewById(R.id.namebill);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btnCnt = findViewById(R.id.connect);
        btnpay = findViewById(R.id.pay);
        btnprint = findViewById(R.id.print);


        btnCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnCnt.getText().equals("Connect")) {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(PrintActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(
                                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent,
                                    REQUEST_ENABLE_BT);
                        } else {
                            ListPairedDevices();
                            Intent connectIntent = new Intent(PrintActivity.this,
                                    DeviceListActivity.class);
                            startActivityForResult(connectIntent,
                                    REQUEST_CONNECT_DEVICE);

                        }
                    }

                } else if (btnCnt.getText().equals("Disconnect")) {
                    if (mBluetoothAdapter != null)
                        mBluetoothAdapter.disable();
                    txtsta.setText("");
                    txtsta.setText("Disconnected");
                    txtsta.setTextColor(Color.rgb(199, 59, 59));
                    btnprint.setEnabled(false);
                    btnCnt.setEnabled(true);
                    btnCnt.setText("Connect");
                }
            }

        });

        btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1();

                int TIME = 10000; //5000 ms (5 Seconds)

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        p2(); //call function!

                        printstat = 1;
                    }
                }, TIME);

            }
        });

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PrintActivity.this);
                builder.setTitle("Thanh toán");
                builder.setMessage("Bạn có muốn thanh toán không ?");

                builder.setPositiveButton("Thanh toán", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        billBean.setStatus_pay(true);

                        firebaseManager.updateBill(billBean.getId(),billBean);

                        p1();

                        int TIME = 10000; //5000 ms (5 Seconds)

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                p2(); //call function!

                                printstat = 1;
                            }
                        }, TIME);

                    }
                });
                builder.setNegativeButton("Hủy", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        setup();
    }

    private void setup() {

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            txtsta.setText("");
            txtsta.setText("Connected");
            txtsta.setTextColor(Color.rgb(97, 170, 74));
            btnprint.setEnabled(true);
            btnCnt.setText("Disconnect");


        }
    };


    @Override
    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }


    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread();
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(PrintActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(PrintActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //lấy dữ liệu trên list
    public void p1() {

        Thread t = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket
                            .getOutputStream();
                    String header = "";
                    String he = "";
                    String header2 = "";
                    String BILL = "";
                    String vio = "";
                    String header3 = "";
                    String mvdtail = "";
                    String header4 = "";
                    String offname = "";
                    String copy = "";
                    String checktop_status = "";

                    he = "      CNC\n";
                    he = he + "********************************\n\n";

                    header = "Oder ";
//                    BILL = customer_dtl.getText().toString()+"\n";
                    BILL = BILL
                            + "          ";
                    header2 = "Số lượng";
//                    vio = order_detail.getText().toString()+"\n";
                    vio = vio
                            + "          ";
                    header3 = "Giá";
//                    mvdtail = total_price.getText().toString()+"\n";
                    mvdtail = mvdtail
                            + "================================\n";

                    header4 = "Tổng: ";
//                    offname = agent_detail.getText().toString()+"\n";
                    offname = offname
                            + "--------------------------------\n";
                    copy = "-Thanks \n\n\n\n\n";


                    os.write(he.getBytes());
                    os.write(header.getBytes());
                    os.write(BILL.getBytes());
                    os.write(header2.getBytes());
                    os.write(vio.getBytes());
                    os.write(header3.getBytes());
                    os.write(mvdtail.getBytes());
                    os.write(header4.getBytes());
                    os.write(offname.getBytes());
                    os.write(checktop_status.getBytes());
                    os.write(copy.getBytes());


                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public void p2() {

        Thread tt = new Thread() {
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket
                            .getOutputStream();
                    String header = "";
                    String he = "";
                    String header2 = "";
                    String BILL = "";
                    String vio = "";
                    String header3 = "";
                    String mvdtail = "";
                    String header4 = "";
                    String offname = "";
                    String copy = "";
                    String checktop_status = "";

                    he = "      SAMPLE PRINT\n";
                    he = he + "********************************\n\n";

                    header = "CUSTOMER DETAILS:\n";
//                    BILL = customer_dtl.getText().toString()+"\n";
                    BILL = BILL
                            + "================================\n";
                    header2 = "ORDER DETAILS:\n";
//                    vio = order_detail.getText().toString()+"\n";
                    vio = vio
                            + "================================\n";
                    header3 = "TOTAL PRICE:\n";
//                    mvdtail = total_price.getText().toString()+"\n";
                    mvdtail = mvdtail
                            + "================================\n";

                    header4 = "AGENT DETAILS:\n";
//                    offname = agent_detail.getText().toString()+"\n";
                    offname = offname
                            + "--------------------------------\n";
                    copy = "-Agents's Copy\n\n\n\n\n";


                    os.write(he.getBytes());
                    os.write(header.getBytes());
                    os.write(BILL.getBytes());
                    os.write(header2.getBytes());
                    os.write(vio.getBytes());
                    os.write(header3.getBytes());
                    os.write(mvdtail.getBytes());
                    os.write(header4.getBytes());
                    os.write(offname.getBytes());
                    os.write(checktop_status.getBytes());
                    os.write(copy.getBytes());


                    //This is printer specific code you can comment ==== > Start

                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 104;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        tt.start();
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActionEvent event) {
        switch (event.action) {
            case MessagesEvent.DATA_BILL:
                billBean = (BillResponse.BillBean) event.object;
                list = new ArrayList<>();
                list = billBean.getProducts();
                productBeanAdapter = new ProductBeanAdapter(this, list);
                recyclerView.setAdapter(productBeanAdapter);
                Intent intent = getIntent();
                txttotal.setText(intent.getStringExtra("total"));
                namebill.setText(intent.getStringExtra("name"));
                break;
            default:
                break;
        }
    }

}
