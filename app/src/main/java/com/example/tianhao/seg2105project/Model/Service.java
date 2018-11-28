package com.example.tianhao.seg2105project.Model;

public class Service {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name,id;
    private double hourlyRate;

    public Service() {
    }

    public Service(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Service(String id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null){
            return false;
        }
        if(other == this){
            return true;
        }
        if(!(other instanceof Service)){
            return false;
        }
        Service otherService = (Service)other;
        return(this.toString().equals(otherService.toString()));
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
