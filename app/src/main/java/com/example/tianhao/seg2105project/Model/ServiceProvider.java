package com.example.tianhao.seg2105project.Model;

public class ServiceProvider extends User {

    private String address, phone, companyName, description;
    private boolean hasLicensed;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasLicensed() {
        return hasLicensed;
    }

    public void setHasLicensed(boolean hasLicensed) {
        this.hasLicensed = hasLicensed;
    }
}