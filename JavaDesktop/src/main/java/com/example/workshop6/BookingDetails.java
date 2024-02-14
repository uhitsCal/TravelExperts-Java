package com.example.workshop6;

import java.util.Date;

public class BookingDetails {
    private int bookingDetailId;
    private int itineraryNo;
    private String tripStart;
    private String tripEnd;
    private String description;
    private String destination;
    private int basePrice;
    private double agencyCommission;
    private int bookId;
    private String regionId;
    private String classId;
    private String feeId;
    private int productSupplierId;

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public int getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(int itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public String getTripStart() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public double getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public BookingDetails(int bookingDetailId, int itineraryNo, String tripStart, String tripEnd,
                          String description, String destination, int basePrice, double agencyCommission,
                          int bookId, String regionId, String classId, String feeId, int productSupplierId) {
        this.bookingDetailId = bookingDetailId;
        this.itineraryNo = itineraryNo;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.description = description;
        this.destination = destination;
        this.basePrice = basePrice;
        this.agencyCommission = agencyCommission;
        this.bookId = bookId;
        this.regionId = regionId;
        this.classId = classId;
        this.feeId = feeId;
        this.productSupplierId = productSupplierId;
    }
}
