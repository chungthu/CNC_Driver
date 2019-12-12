package com.example.cnc_driver.net.response;

import java.util.List;

public class BillResponse {

    public static class BillBean {
        private String id;
        private String id_table;
        private String name;
        private String responsible;
        private boolean status_pay;
        private String time;
        private String total;
        private List<ProductsBean> products;

        public String getId_table() {
            return id_table;
        }

        public void setId_table(String id_table) {
            this.id_table = id_table;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getResponsible() {
            return responsible;
        }

        public void setResponsible(String responsible) {
            this.responsible = responsible;
        }

        public boolean isStatus_pay() {
            return status_pay;
        }

        public void setStatus_pay(boolean status_pay) {
            this.status_pay = status_pay;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            private String amount;
            private String id;
            private String image;
            private String name;
            private String price;
            private String total;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }
    }

}
