package com.example.workshop6;

public class Booking {
    private int bookingId;
    private String bookingDate;
    private String bookingNo;
    private int travelerCount;
    private int customerId;
    private String tripTypeId;
    private int packageId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public int getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount = travelerCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Booking(int bookingId, String bookingDate, String bookingNo, int travelerCount, int customerId, String tripTypeId, int packageId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.customerId = customerId;
        this.tripTypeId = tripTypeId;
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "Booking: "+bookingId;
    }
}
