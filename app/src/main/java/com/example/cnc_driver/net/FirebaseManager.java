package com.example.cnc_driver.net;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cnc_driver.common.Constacts;
import com.example.cnc_driver.interfaces.DataBillStatus;
import com.example.cnc_driver.interfaces.DataBreadStatus;
import com.example.cnc_driver.interfaces.DataFruitStatus;
import com.example.cnc_driver.interfaces.DataMilkteaStatus;
import com.example.cnc_driver.net.response.BillResponse;
import com.example.cnc_driver.net.response.ProductResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product");
    static DatabaseReference mDatabaseBill = FirebaseDatabase.getInstance().getReference("Bill");
    private List<ProductResponse> item = new ArrayList<>();
    private List<ProductResponse> itemFruit = new ArrayList<>();
    private List<ProductResponse> itemBr = new ArrayList<>();
    private List<BillResponse.BillBean> itemBill = new ArrayList<>();
    private DataMilkteaStatus dataMilkteaStatus;
    private DataFruitStatus dataFruitStatus;
    private DataBreadStatus dataBreadStatus;
    private DataBillStatus dataBillStatus;

    public void setDataMilkteaStatus(DataMilkteaStatus dataMilkteaStatus) {
        this.dataMilkteaStatus = dataMilkteaStatus;
    }

    public void setDataFruitStatus(DataFruitStatus dataFruitStatus) {
        this.dataFruitStatus = dataFruitStatus;
    }

    public void setDataBreadStatus(DataBreadStatus dataBreadStatus) {
        this.dataBreadStatus = dataBreadStatus;
    }

    public void setDataBillStatus(DataBillStatus dataBillStatus) {
        this.dataBillStatus = dataBillStatus;
    }


    //Insert
    public static void insertProduct(String name, String id_category, String name_category, String image,
                                     String priceM, String priceL, String description) {
        String key = mDatabase.push().getKey();

        ProductResponse item = new ProductResponse(key, name, id_category, image, priceM, priceL, description);

        assert key != null;
        mDatabase.child(key).setValue(item);
    }

    //Update
    public void updateproduct(String id_product, ProductResponse item) {
        mDatabase.child(id_product).setValue(item);
    }

    //Delete
    public void deleteProduct(String id_product) {
        assert id_product != null;
        mDatabase.child(id_product).removeValue();
    }

    //ReadAll
    public void reaAllDataTea() {
        mDatabase.orderByChild("category_id").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    ProductResponse productResponse = childDataSnapshot.getValue(ProductResponse.class);
                    item.add(productResponse);
                }
                dataMilkteaStatus.getData(item);
                Log.e(Constacts.TAG, "onDataChange: " + item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constacts.TAG, "onCancelled: " + databaseError);
            }
        });
    }


    public void reaAllFruit() {
        mDatabase.orderByChild("category_id").equalTo("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemFruit.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    ProductResponse productResponse = childDataSnapshot.getValue(ProductResponse.class);
                    itemFruit.add(productResponse);
                }
                dataFruitStatus.getData(itemFruit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constacts.TAG, "onCancelled: " + databaseError);
            }
        });
    }

    public void readAllBread() {
        mDatabase.orderByChild("category_id").equalTo("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemBr.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    ProductResponse productResponse = childDataSnapshot.getValue(ProductResponse.class);
                    itemBr.add(productResponse);
                }
                dataBreadStatus.getData(itemBr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constacts.TAG, "onCancelled: " + databaseError);
            }
        });
    }

    public void readAllBill() {
        mDatabaseBill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemBill.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    BillResponse.BillBean billResponse = childDataSnapshot.getValue(BillResponse.BillBean.class);
                    itemBill.add(billResponse);
                }
                dataBillStatus.getData(itemBill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constacts.TAG, "onCancelled: " + databaseError);
            }
        });
    }

}


