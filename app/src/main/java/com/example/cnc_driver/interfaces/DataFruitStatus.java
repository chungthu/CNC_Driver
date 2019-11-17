package com.example.cnc_driver.interfaces;

import com.example.cnc_driver.net.response.ProductResponse;

import java.util.List;

public interface DataFruitStatus {
    void getData(List<ProductResponse> item);
}
