package com.example.cnc_driver.interfaces;

import com.example.cnc_driver.net.response.BillResponse;

import java.util.List;

public interface DataBillStatus {
    void getData(List<BillResponse.BillBean> item);
}
