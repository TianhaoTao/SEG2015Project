package com.example.tianhao.seg2105project.Model;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServiceProvider extends User {

    private Profile profile;

    FirebaseDatabase database;
    DatabaseReference ProvidedServices;

    private Context mContext;


    private ArrayList<ProvidedService> serviceArrayList = new ArrayList<>();

    private ArrayList<Service> serviceArrayList1 = new ArrayList<>();

    public ServiceProvider(Context context, User user) {
        mContext=context;
        if(user.getUserType().equals("Service Provider")){
            this.setUsername(user.getUsername());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setUserType(user.getUserType());
        }

        database=FirebaseDatabase.getInstance();
        ProvidedServices=database.getReference("ProvidedServicesNew");

        //get profile from database if it exist
        database.getReference("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ServiceProvider.this.getUsername()).exists()){
                    profile = dataSnapshot.child(ServiceProvider.this.getUsername()).getValue(Profile.class);
                }else{
                    profile = new Profile();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //get all the provided services from database
        ProvidedServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceArrayList1 = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals(ServiceProvider.this.getUsername())) {
                        for(DataSnapshot service : data.getChildren()){
                            serviceArrayList1.add(service.getValue(Service.class));
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveServiceToProfile(Service service,String timeSlots){
        String id = service.getId()+this.getUsername();
        ProvidedServices.child(id).setValue(new ProvidedService(id,service,this.getUsername(),timeSlots));
        Toast.makeText(mContext, "The Service is saved to the profile", Toast.LENGTH_SHORT).show();
    }


    public void removeServiceFromProfile(String serviceId){
        String id = serviceId+this.getUsername();
        ProvidedServices.child(id).removeValue();
        Toast.makeText(mContext, "Service Removed from profile", Toast.LENGTH_SHORT).show();
    }

    public void saveServiceToProfile(Service service){
        boolean flag = true;
        for(Service service1 : serviceArrayList1){
            if(service1.getId().equals(service)){
                flag = false;
                break;
            }
        }
        if(flag){
            serviceArrayList1.add(service);
            ProvidedServices.child(getUsername()).child(service.getId()).setValue(service);
            Toast.makeText(mContext, "The Service is saved to the profile", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeServiceFromProfile(Service service){
        serviceArrayList1.remove(service);
        ProvidedServices.child(getUsername()).child(service.getId()).removeValue();
        Toast.makeText(mContext, "Service Removed from profile", Toast.LENGTH_SHORT).show();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        database.getReference("Profile").child(getUsername()).setValue(profile);
    }

    public ArrayList<ProvidedService> getServiceArrayList() {
        return serviceArrayList;
    }

    public ArrayList<Service> getServiceArrayList1() {
        return serviceArrayList1;
    }
}
