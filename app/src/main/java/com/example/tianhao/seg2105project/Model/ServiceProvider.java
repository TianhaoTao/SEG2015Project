package com.example.tianhao.seg2105project.Model;

public class ServiceProvider extends User {

    private ServiceProviderProfile serviceProviderProfile;

    public ServiceProvider(String username, String email, String password, String userType) {
        super(username, email, password, userType);
    }

    public ServiceProviderProfile getServiceProviderProfile() {
        return serviceProviderProfile;
    }

    public void setServiceProviderProfile(ServiceProviderProfile serviceProviderProfile) {
        this.serviceProviderProfile = serviceProviderProfile;
    }
}
