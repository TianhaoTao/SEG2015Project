package com.example.tianhao.seg2105project;


import com.example.tianhao.seg2105project.Model.ProvidedService;
import com.example.tianhao.seg2105project.Model.Service;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProvidedServiceTest {

    Service service1 = new Service("hhh", 12);

    @Test
    public void checkID(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        assertEquals("check the ID of provided service", "123456", service.getId());
    }

    @Test
    public void checkService(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        assertEquals("check the service of provided service", service1, service.getService());
    }

    @Test
    public void checkServiceProviderName(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        assertEquals("check the name of service provider", "hhh", service.getServiceProviderName());
    }

    @Test
    public void checkTimeSlots(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        assertEquals("check the time slots of provided service", "12", service.getTimeSlots());
    }

    @Test
    public void checkRate(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        assertEquals("check the rate of provided service", 5, service.getRate(), 0.001);
    }





}
