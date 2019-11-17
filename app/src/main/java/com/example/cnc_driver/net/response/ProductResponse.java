package com.example.cnc_driver.net.response;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProductResponse {
    private String id;
    private String name;
    private String category_id;
    private String image;
    private String price1;
    private String price2;
    private String description;

    public ProductResponse() {
    }

    public ProductResponse(String id, String name, String category_id, String image, String price1, String price2, String description) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.image = image;
        this.price1 = price1;
        this.price2 = price2;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
