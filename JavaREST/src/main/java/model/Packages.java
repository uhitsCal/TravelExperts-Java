package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Packages {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PackageId")
    private int packageId;
    @Basic
    @Column(name = "PkgName")
    private String pkgName;
    @Basic
    @Column(name = "PkgStartDate")
    private Timestamp pkgStartDate;
    @Basic
    @Column(name = "PkgEndDate")
    private Timestamp pkgEndDate;
    @Basic
    @Column(name = "PkgDesc")
    private String pkgDesc;
    @Basic
    @Column(name = "PkgBasePrice")
    private BigDecimal pkgBasePrice;
    @Basic
    @Column(name = "PkgAgencyCommission")
    private BigDecimal pkgAgencyCommission;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Timestamp getPkgStartDate() {
        return pkgStartDate;
    }

    public void setPkgStartDate(Timestamp pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }

    public Timestamp getPkgEndDate() {
        return pkgEndDate;
    }

    public void setPkgEndDate(Timestamp pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public BigDecimal getPkgBasePrice() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(BigDecimal pkgBasePrice) {
        this.pkgBasePrice = pkgBasePrice;
    }

    public BigDecimal getPkgAgencyCommission() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(BigDecimal pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Packages packages = (Packages) o;

        if (packageId != packages.packageId) return false;
        if (pkgName != null ? !pkgName.equals(packages.pkgName) : packages.pkgName != null) return false;
        if (pkgStartDate != null ? !pkgStartDate.equals(packages.pkgStartDate) : packages.pkgStartDate != null)
            return false;
        if (pkgEndDate != null ? !pkgEndDate.equals(packages.pkgEndDate) : packages.pkgEndDate != null) return false;
        if (pkgDesc != null ? !pkgDesc.equals(packages.pkgDesc) : packages.pkgDesc != null) return false;
        if (pkgBasePrice != null ? !pkgBasePrice.equals(packages.pkgBasePrice) : packages.pkgBasePrice != null)
            return false;
        if (pkgAgencyCommission != null ? !pkgAgencyCommission.equals(packages.pkgAgencyCommission) : packages.pkgAgencyCommission != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = packageId;
        result = 31 * result + (pkgName != null ? pkgName.hashCode() : 0);
        result = 31 * result + (pkgStartDate != null ? pkgStartDate.hashCode() : 0);
        result = 31 * result + (pkgEndDate != null ? pkgEndDate.hashCode() : 0);
        result = 31 * result + (pkgDesc != null ? pkgDesc.hashCode() : 0);
        result = 31 * result + (pkgBasePrice != null ? pkgBasePrice.hashCode() : 0);
        result = 31 * result + (pkgAgencyCommission != null ? pkgAgencyCommission.hashCode() : 0);
        return result;
    }
}
