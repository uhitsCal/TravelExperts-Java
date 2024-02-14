package model;

import jakarta.persistence.*;

@Entity
public class Customers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CustomerId")
    private int customerId;
    @Basic
    @Column(name = "CustFirstName")
    private String custFirstName;
    @Basic
    @Column(name = "CustLastName")
    private String custLastName;
    @Basic
    @Column(name = "CustAddress")
    private String custAddress;
    @Basic
    @Column(name = "CustCity")
    private String custCity;
    @Basic
    @Column(name = "CustProv")
    private String custProv;
    @Basic
    @Column(name = "CustPostal")
    private String custPostal;
    @Basic
    @Column(name = "CustCountry")
    private String custCountry;
    @Basic
    @Column(name = "CustHomePhone")
    private String custHomePhone;
    @Basic
    @Column(name = "CustBusPhone")
    private String custBusPhone;
    @Basic
    @Column(name = "CustEmail")
    private String custEmail;
    @Basic
    @Column(name = "AgentId")
    private Integer agentId;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProv() {
        return custProv;
    }

    public void setCustProv(String custProv) {
        this.custProv = custProv;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomePhone() {
        return custHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone = custHomePhone;
    }

    public String getCustBusPhone() {
        return custBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone = custBusPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customers customers = (Customers) o;

        if (customerId != customers.customerId) return false;
        if (custFirstName != null ? !custFirstName.equals(customers.custFirstName) : customers.custFirstName != null)
            return false;
        if (custLastName != null ? !custLastName.equals(customers.custLastName) : customers.custLastName != null)
            return false;
        if (custAddress != null ? !custAddress.equals(customers.custAddress) : customers.custAddress != null)
            return false;
        if (custCity != null ? !custCity.equals(customers.custCity) : customers.custCity != null) return false;
        if (custProv != null ? !custProv.equals(customers.custProv) : customers.custProv != null) return false;
        if (custPostal != null ? !custPostal.equals(customers.custPostal) : customers.custPostal != null) return false;
        if (custCountry != null ? !custCountry.equals(customers.custCountry) : customers.custCountry != null)
            return false;
        if (custHomePhone != null ? !custHomePhone.equals(customers.custHomePhone) : customers.custHomePhone != null)
            return false;
        if (custBusPhone != null ? !custBusPhone.equals(customers.custBusPhone) : customers.custBusPhone != null)
            return false;
        if (custEmail != null ? !custEmail.equals(customers.custEmail) : customers.custEmail != null) return false;
        if (agentId != null ? !agentId.equals(customers.agentId) : customers.agentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (custFirstName != null ? custFirstName.hashCode() : 0);
        result = 31 * result + (custLastName != null ? custLastName.hashCode() : 0);
        result = 31 * result + (custAddress != null ? custAddress.hashCode() : 0);
        result = 31 * result + (custCity != null ? custCity.hashCode() : 0);
        result = 31 * result + (custProv != null ? custProv.hashCode() : 0);
        result = 31 * result + (custPostal != null ? custPostal.hashCode() : 0);
        result = 31 * result + (custCountry != null ? custCountry.hashCode() : 0);
        result = 31 * result + (custHomePhone != null ? custHomePhone.hashCode() : 0);
        result = 31 * result + (custBusPhone != null ? custBusPhone.hashCode() : 0);
        result = 31 * result + (custEmail != null ? custEmail.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        return result;
    }
}
