package com.example.tianhao.seg2105project.Model;


import java.util.ArrayList;

public class ProvidedService {

    private String id;
    private Service service;
    private String serviceProviderName;
    private String timeslots;
    private int rate;
    private long count;


    private String homeOwnerName;
    private int individualRate;
    private String bookedTimeSlots;



    public ProvidedService() {
    }

    public ProvidedService(String id, Service service, String serviceProviderName, String timeslots) {
        this.id = id;
        this.service = service;
        this.serviceProviderName = serviceProviderName;
        this.timeslots = timeslots;
    }

    public ProvidedService(String id, Service service, String serviceProviderName, String timeslots, int rate, long count) {
        this.id = id;
        this.service = service;
        this.serviceProviderName = serviceProviderName;
        this.timeslots = timeslots;
        this.rate = rate;
        this.count = count;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
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

    public String getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(String timeslots) {
        this.timeslots = timeslots;
    }
}
