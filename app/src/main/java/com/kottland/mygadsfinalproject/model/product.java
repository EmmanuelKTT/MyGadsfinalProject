package com.kottland.mygadsfinalproject.model;

public class product {

    private int productId;
    private String productName;
    private String productImage;
    private String productAmount;
    private String productCtd;

    public product() {
    }

    public product(int productId, String productName, String productImage, String productAmount, String productCtd) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productAmount = productAmount;
        this.productCtd = productCtd;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductCtd() {
        return productCtd;
    }

    public void setProductCtd(String productCtd) {
        this.productCtd = productCtd;
    }
}
