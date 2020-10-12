package com.kottland.mygadsfinalproject.model;

public class history_items {

    private int itemsId;
    private String itemsName;
    private String itemsCtd;
    private String itemsPayType;

    public history_items(int itemsId, String itemsName, String itemsCtd, String itemsPayType) {
        this.itemsId = itemsId;
        this.itemsName = itemsName;
        this.itemsCtd = itemsCtd;
        this.itemsPayType = itemsPayType;
    }


    public history_items() {
    }

    public int getItemsId() {
        return itemsId;
    }

    public void setItemsId(int itemsId) {
        this.itemsId = itemsId;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemsCtd() {
        return itemsCtd;
    }

    public void setItemsCtd(String itemsCtd) {
        this.itemsCtd = itemsCtd;
    }

    public String getItemsPayType() {
        return itemsPayType;
    }

    public void setItemsPayType(String itemsPayType) {
        this.itemsPayType = itemsPayType;
    }
}
