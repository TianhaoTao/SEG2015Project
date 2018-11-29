package com.example.tianhao.seg2105project.Model;


public class ProvidedService {

    private String id;
    private Service service;
    private String serviceProviderName;
    private String timeSlots;
    private double rate;
    private long count;


    private String homeOwnerName;
    private int individualRate;
    private String bookedTimeSlots;



    public ProvidedService() {
    }

    public ProvidedService(String id, Service service, String serviceProviderName, String timeSlots) {
        this.id = id;
        this.service = service;
        this.serviceProviderName = serviceProviderName;
        this.timeSlots = timeSlots;
    }

    public ProvidedService(String id, Service service, String serviceProviderName, String timeSlots, int rate) {
        this.id = id;
        this.service = service;
        this.serviceProviderName = serviceProviderName;
        this.timeSlots = timeSlots;
        this.rate = rate;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getHomeOwnerName() {
        return homeOwnerName;
    }

    public void setHomeOwnerName(String homeOwnerName) {
        this.homeOwnerName = homeOwnerName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getIndividualRate() {
        return individualRate;
    }

    public void setIndividualRate(int individualRate) {
        this.individualRate = individualRate;
    }

    public String getBookedTimeSlots() {
        return bookedTimeSlots;
    }

    public void setBookedTimeSlots(String bookedTimeSlots) {
        this.bookedTimeSlots = bookedTimeSlots;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        this.timeSlots = timeSlots;
    }
}
