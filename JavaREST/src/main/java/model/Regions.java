package model;

import jakarta.persistence.*;

@Entity
public class Regions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RegionId")
    private String regionId;
    @Basic
    @Column(name = "RegionName")
    private String regionName;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Regions regions = (Regions) o;

        if (regionId != null ? !regionId.equals(regions.regionId) : regions.regionId != null) return false;
        if (regionName != null ? !regionName.equals(regions.regionName) : regions.regionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = regionId != null ? regionId.hashCode() : 0;
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        return result;
    }
}
