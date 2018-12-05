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

    @Test
    public void checkCount(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        service.setCount(1234);
        assertEquals("check the count of provided service", 1234, service.getCount());
    }

    @Test
    public void checkHomeOwnerName(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        service.setHomeOwnerName("Abc");
        assertEquals("check the name of home owner", "Abc", service.getHomeOwnerName());
    }

    @Test
    public void checkIndividualRate(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        service.setIndividualRate(12);
        assertEquals("check the individual of provided service", 12, service.getIndividualRate());
    }

    @Test
    public void checkBookedTimeSlots(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        service.setBookedTimeSlots("33");
        assertEquals("check the booked time slots of provided service", "33", service.getBookedTimeSlots());
    }

    @Test
    public void checkComment(){
        ProvidedService service = new ProvidedService("123456", service1, "hhh", "12", 5);
        service.setComment("GOOD.");
        assertEquals("check the comment of provided service", "GOOD.", service.getComment());
    }



}
