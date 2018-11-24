package com.example.tianhao.seg2105project.Model;

public class ProvidedService {
    private Service service;
    private ServiceProvider serviceProvider;
    private HouseOwner houseOwner;

    public ProvidedService(Service service, ServiceProvider serviceProvider) {
        this.service = service;
        this.serviceProvider = serviceProvider;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public HouseOwner getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }
}
