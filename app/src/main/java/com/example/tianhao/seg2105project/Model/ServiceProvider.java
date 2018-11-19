package com.example.tianhao.seg2105project.Model;

public class ServiceProvider extends User {

    private Profile profile;

    public ServiceProvider(String username, String email, String password, String userType) {
        super(username, email, password, userType);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
