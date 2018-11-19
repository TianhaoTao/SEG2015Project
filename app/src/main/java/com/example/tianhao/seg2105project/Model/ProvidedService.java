package com.example.tianhao.seg2105project.Model;


import java.util.ArrayList;

public class ProvidedService {

    private String id;
    private String serviceProviderName;
    private String serviceId;
    private String serviceName;

    private String TimeSlots;

    public ProvidedService() {
    }

    public ProvidedService(String id, String serviceProviderName, String serviceId, String serviceName, String timeSlots) {
        this.id = id;
        this.serviceProviderName = serviceProviderName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        TimeSlots = timeSlots;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimeSlots() {
        return TimeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        TimeSlots = timeSlots;
    }
}
