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

public class ServiceProviderProfile {

    private ServiceProvider serviceProvider;
    private String address, phone, companyName, description;
    private boolean hasLicensed;

    private ArrayList<ProvidedService> serviceArrayList;

    private Context mContext;

    FirebaseDatabase database;
    DatabaseReference ProvidedServices;

    public ServiceProviderProfile(Context mContext, final String username) {

        database=FirebaseDatabase.getInstance();
        ProvidedServices=database.getReference("ProvidedServices");

        this.mContext = mContext;
        database.getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(username).getValue(User.class);
                serviceProvider = new ServiceProvider(user.getUsername(),user.getEmail(),
                        user.getPassword(),user.getUserType());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        serviceProvider.setServiceProviderProfile(this);

        ProvidedServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("serviceProviderName").getValue().toString().equals(username)) {
                        serviceArrayList.add(data.getValue(ProvidedService.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addServiceToProfile(String serviceId,String serviceName,ArrayList<String> timeSlots){
        String id = ProvidedServices.push().getKey();
        ProvidedServices.child(id).setValue(new ProvidedService(id,serviceProvider.getUsername(),
                serviceId,serviceName,timeSlots));
        Toast.makeText(mContext, "The Service is added to the profile", Toast.LENGTH_SHORT).show();
    }

    public void editServiceInProfile(String id, String serviceId,String serviceName,ArrayList<String> timeSlots){
        DatabaseReference originalService = ProvidedServices.child(id);
        originalService.setValue(new ProvidedService(id,serviceProvider.getUsername(),
                serviceId,serviceName,timeSlots));
        Toast.makeText(mContext, "Service Updated", Toast.LENGTH_SHORT).show();
    }


    public void removeServiceFromProfile(String id){
        DatabaseReference originalService = ProvidedServices.child(id);
        originalService.removeValue();
        Toast.makeText(mContext, "Service Removed from profile", Toast.LENGTH_SHORT).show();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasLicensed() {
        return hasLicensed;
    }

    public void setHasLicensed(boolean hasLicensed) {
        this.hasLicensed = hasLicensed;
    }
}
