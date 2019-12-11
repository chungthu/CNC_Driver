package com.example.cnc_driver.interfaces;

import com.example.cnc_driver.net.response.TableResponse;

import java.util.List;

public interface DataTableStatus {
    void getData(List<TableResponse> item);
}
