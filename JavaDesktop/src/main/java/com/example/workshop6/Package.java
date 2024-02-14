package com.example.workshop6;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Package {
    private SimpleIntegerProperty packageId;
    private SimpleStringProperty pkgName;
    private SimpleStringProperty pkgStartDate;
    private SimpleStringProperty pkgEndDate;
    private SimpleStringProperty pkgDesc;
    private SimpleDoubleProperty pkgBasePrice;
    private SimpleDoubleProperty pkgAgencyCommission;

    public Package(int packageId, String pkgName, String pkgStartDate, String pkgEndDate, String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission) {
        this.packageId = new SimpleIntegerProperty(packageId);
        this.pkgName = new SimpleStringProperty(pkgName);
        this.pkgStartDate = new SimpleStringProperty(pkgStartDate);
        this.pkgEndDate = new SimpleStringProperty(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public String getPkgName() {
        return pkgName.get();
    }

    public SimpleStringProperty pkgNameProperty() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName.set(pkgName);
    }

    public String getPkgStartDate() {
        return pkgStartDate.get();
    }

    public SimpleStringProperty pkgStartDateProperty() {
        return pkgStartDate;
    }

    public void setPkgStartDate(String pkgStartDate) {
        this.pkgStartDate.set(pkgStartDate);
    }

    public String getPkgEndDate() {
        return pkgEndDate.get();
    }

    public SimpleStringProperty pkgEndDateProperty() {
        return pkgEndDate;
    }

    public void setPkgEndDate(String pkgEndDate) {
        this.pkgEndDate.set(pkgEndDate);
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public SimpleStringProperty pkgDescProperty() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc.set(pkgDesc);
    }

    public double getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission.set(pkgAgencyCommission);
    }

    @Override
    public String toString() {
        return packageId + " : " + pkgName + " - " + pkgBasePrice;
    }
}
