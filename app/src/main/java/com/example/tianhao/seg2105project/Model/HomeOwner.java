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
                        ProvidedService providedService = new ProvidedService(
                                data.child("id").getValue().toString(),
                                data.child("service").getValue(Service.class),
                                data.child("serviceProviderName").getValue().toString(),
                                data.child("timeSlots").getValue().toString(),
                                Integer.valueOf(data.child("rate").getValue().toString()),
                                Long.valueOf(data.child("homeOwners").getChildrenCount()));
                        providedService.setHomeOwnerName(getUsername());
                        providedService.setIndividualRate(Integer.valueOf(data.child("homeOwners").
                                child(getUsername()).child("rate").getValue().toString()));
                        providedService.setTimeslots(data.child("homeOwners").
                                child(getUsername()).child("timeSlot").getValue().toString());
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
        Toast.makeText(mContext, "Service Booked", Toast.LENGTH_SHORT).show();
    }

    public void cancelService(ProvidedService providedService){
        ProvidedServices.child(providedService.getId()).child("homeOwners").removeValue();
        Toast.makeText(mContext, "Service Canceled", Toast.LENGTH_SHORT).show();
    }

    public void rateService(ProvidedService providedService,int rate){
        ProvidedServices.child(providedService.getId()).child("homeOwners").
                child(getUsername()).child("rate").setValue(rate);
        Toast.makeText(mContext, "Service Rated", Toast.LENGTH_SHORT).show();
    }



    public ArrayList<ProvidedService> getServiceArrayList() {
        return serviceArrayList;
    }

    public void setServiceArrayList(ArrayList<ProvidedService> serviceArrayList) {
        this.serviceArrayList = serviceArrayList;
    }
}
