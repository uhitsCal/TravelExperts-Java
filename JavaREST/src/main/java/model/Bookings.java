package model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Bookings {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BookingId")
    private int bookingId;
    @Basic
    @Column(name = "BookingDate")
    private Timestamp bookingDate;
    @Basic
    @Column(name = "BookingNo")
    private String bookingNo;
    @Basic
    @Column(name = "TravelerCount")
    private Double travelerCount;
    @Basic
    @Column(name = "CustomerId")
    private Integer customerId;
    @Basic
    @Column(name = "TripTypeId")
    private String tripTypeId;
    @Basic
    @Column(name = "PackageId")
    private Integer packageId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Double getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(Double travelerCount) {
        this.travelerCount = travelerCount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookings bookings = (Bookings) o;

        if (bookingId != bookings.bookingId) return false;
        if (bookingDate != null ? !bookingDate.equals(bookings.bookingDate) : bookings.bookingDate != null)
            return false;
        if (bookingNo != null ? !bookingNo.equals(bookings.bookingNo) : bookings.bookingNo != null) return false;
        if (travelerCount != null ? !travelerCount.equals(bookings.travelerCount) : bookings.travelerCount != null)
            return false;
        if (customerId != null ? !customerId.equals(bookings.customerId) : bookings.customerId != null) return false;
        if (tripTypeId != null ? !tripTypeId.equals(bookings.tripTypeId) : bookings.tripTypeId != null) return false;
        if (packageId != null ? !packageId.equals(bookings.packageId) : bookings.packageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookingId;
        result = 31 * result + (bookingDate != null ? bookingDate.hashCode() : 0);
        result = 31 * result + (bookingNo != null ? bookingNo.hashCode() : 0);
        result = 31 * result + (travelerCount != null ? travelerCount.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (tripTypeId != null ? tripTypeId.hashCode() : 0);
        result = 31 * result + (packageId != null ? packageId.hashCode() : 0);
        return result;
    }
}
