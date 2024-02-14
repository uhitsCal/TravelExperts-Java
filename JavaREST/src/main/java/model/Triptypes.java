package model;

import jakarta.persistence.*;

@Entity
public class Triptypes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TripTypeId")
    private String tripTypeId;
    @Basic
    @Column(name = "TTName")
    private String ttName;

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public String getTtName() {
        return ttName;
    }

    public void setTtName(String ttName) {
        this.ttName = ttName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triptypes triptypes = (Triptypes) o;

        if (tripTypeId != null ? !tripTypeId.equals(triptypes.tripTypeId) : triptypes.tripTypeId != null) return false;
        if (ttName != null ? !ttName.equals(triptypes.ttName) : triptypes.ttName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tripTypeId != null ? tripTypeId.hashCode() : 0;
        result = 31 * result + (ttName != null ? ttName.hashCode() : 0);
        return result;
    }
}
