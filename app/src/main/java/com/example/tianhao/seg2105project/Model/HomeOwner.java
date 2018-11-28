package com.example.tianhao.seg2105project.Model;

import android.content.Context;
import android.support.annotation.NonNull;

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

    private ArrayList<ProvidedService> serviceArrayList = new ArrayList<>();

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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<ProvidedService> getServiceArrayList() {
        return serviceArrayList;
    }

    public void setServiceArrayList(ArrayList<ProvidedService> serviceArrayList) {
        this.serviceArrayList = serviceArrayList;
    }
}
