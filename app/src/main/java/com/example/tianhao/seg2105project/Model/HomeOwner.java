package com.example.tianhao.seg2105project.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeOwner extends User {

    FirebaseDatabase database;
    DatabaseReference ProvidedServices;

    private Context mContext;

    private ArrayList<ProvidedService> serviceArrayList;

    public HomeOwner(Context context,User user){
        this.mContext=context;
        if(user.getUserType().equals("Home Owner")){
            this.setUsername(user.getUsername());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setUserType(user.getUserType());
        }

        database=FirebaseDatabase.getInstance();
        ProvidedServices=database.getReference("ProvidedServices");

        ProvidedServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceArrayList = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.child("homeOwners").child(getUsername()).exists()){
                        ProvidedService providedService = new ProvidedService();
                        providedService.setId(data.child("id").getValue().toString());
                        providedService.setServiceProviderName(data.child("serviceProviderName").getValue().toString());
                        providedService.setService(data.child("service").getValue(Service.class));
                        if(!(data.child("rate").getValue()==null)){
                            providedService.setRate(Double.valueOf(data.child("rate").getValue().toString()));
                        }else{
                            providedService.setRate(0);
                        }
                        //homeOwner name
                        providedService.setHomeOwnerName(getUsername());
                        if(data.child("homeOwners").child(getUsername()).child("rate").getValue()==null){
                            providedService.setIndividualRate(0);
                        }else{
                            providedService.setIndividualRate(Integer.valueOf(data.child("homeOwners").
                                    child(getUsername()).child("rate").getValue().toString()));
                        }
                        //myTimeSlot
                        providedService.setTimeSlots(data.child("homeOwners").
                                child(getUsername()).child("timeSlot").getValue().toString());
                        //Count
                        try{
                            providedService.setCount(data.child("count").getValue(Long.class));
                        }catch(NullPointerException e){
                            providedService.setCount(0);
                        }
                        //comment
                        try{
                            providedService.setComment(data.child("homeOwners").
                                    child(getUsername()).child("comment").getValue().toString());
                        }catch(NullPointerException e){
                            providedService.setComment("");
                        }
                        serviceArrayList.add(providedService);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void bookService(ProvidedService providedService){
        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("timeSlot").setValue(providedService.getBookedTimeSlots());
        Toast.makeText(mContext, "Service Booked", Toast.LENGTH_SHORT).show();
    }
    public void bookService(ProvidedService providedService,String pickedTime){
        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("timeSlot").setValue(pickedTime);
        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("rate").setValue(0);
        Toast.makeText(mContext, "Service Booked", Toast.LENGTH_SHORT).show();
    }

    public void cancelService(ProvidedService providedService){
        ProvidedServices.child(providedService.getId()).child("homeOwners").removeValue();
        Toast.makeText(mContext, "Service Canceled", Toast.LENGTH_SHORT).show();
    }

    public void rateService(ProvidedService providedService, int rate){
        if(providedService.getRate()==0 || providedService.getCount()==0){
            providedService.setRate(rate);
            providedService.setCount(0);
        }
        double newRate=0;

        if(providedService.getIndividualRate()==0){
            newRate = (providedService.getCount()*providedService.getRate()+rate)
                    /(providedService.getCount()+1);
            providedService.setCount(providedService.getCount()+1);
        }else{
            newRate = (providedService.getCount()*providedService.getRate()+rate-providedService.getIndividualRate())
                    /(providedService.getCount());
        }

        providedService.setIndividualRate(rate);

        providedService.setRate(newRate);

        ProvidedServices.child(providedService.getId()).child("rate").setValue(newRate);

        ProvidedServices.child(providedService.getId()).child("count").setValue(providedService.getCount());

        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("rate").setValue(rate);

        Toast.makeText(mContext, "Service Rated", Toast.LENGTH_SHORT).show();
    }

    public void rateService(ProvidedService providedService, int rate,String comment){
        rateService(providedService, rate);
        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("comment").setValue(comment);
        providedService.setComment(comment);
    }


    public ArrayList<ProvidedService> getServiceArrayList() {
        return serviceArrayList;
    }

    public void setServiceArrayList(ArrayList<ProvidedService> serviceArrayList) {
        this.serviceArrayList = serviceArrayList;
    }
}
