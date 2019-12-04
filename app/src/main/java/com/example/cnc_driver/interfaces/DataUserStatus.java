package com.example.cnc_driver.interfaces;

import com.example.cnc_driver.net.response.UserRespones;

import java.util.List;

public interface DataUserStatus {
    void getData(List<UserRespones> item);
}
