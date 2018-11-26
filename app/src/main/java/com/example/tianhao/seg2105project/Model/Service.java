package com.example.tianhao.seg2105project.Model;

public class Service {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name,time,id;
    private double hourlyRate;
    private int rate;

    public Service() {
    }

    public Service(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public  Service(String name, int rate, String time){
        this.name = name;
        this.rate = rate;
        this.time = time;
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

    public int getRate(){return rate;}

    public void setRate(int rate){this.rate=rate;}

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
