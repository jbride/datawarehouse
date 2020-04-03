package com.redhat.cajun.navy.datawarehouse.model;

import java.math.BigDecimal;

public class Responder implements io.vertx.core.shareddata.Shareable {

    private Integer id;
    private String name;
    private String phoneNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer boatCapacity;
    private Boolean medicalKit;
    private Boolean available;
    private Boolean person;
    private Boolean enrolled;

    @Override
    public String toString() {
        return "Responder [available=" + available + ", boatCapacity=" + boatCapacity + ", enrolled=" + enrolled
                + ", id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", medicalKit=" + medicalKit
                + ", name=" + name + ", person=" + person + ", phoneNumber=" + phoneNumber + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getBoatCapacity() {
        return boatCapacity;
    }

    public void setBoatCapacity(Integer boatCapacity) {
        this.boatCapacity = boatCapacity;
    }

    public Boolean getMedicalKit() {
        return medicalKit;
    }

    public void setMedicalKit(Boolean medicalKit) {
        this.medicalKit = medicalKit;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getPerson() {
        return person;
    }

    public void setPerson(Boolean person) {
        this.person = person;
    }

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }
}
