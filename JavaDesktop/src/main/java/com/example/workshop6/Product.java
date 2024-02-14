package com.example.workshop6;

public class Product {
    private int prodID;
    private String prodName;

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Product(int prodID, String prodName) {
        this.prodID = prodID;
        this.prodName = prodName;
    }
}
