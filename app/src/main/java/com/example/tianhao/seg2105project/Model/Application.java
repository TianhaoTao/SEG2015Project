package com.example.tianhao.seg2105project.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class Application {

    private Context mContext;

    private Fragment fragment;

    private ArrayList<Service> serviceArrayList = new ArrayList<>();
    private ArrayList<ProvidedService> providedServiceArrayList = new ArrayList<>();

    private static Application instance;

    private User user;

    FirebaseDatabase database;
    DatabaseReference services;

    private Application(Context context) {
        mContext=context;
        database=FirebaseDatabase.getInstance();
        fragment=Fragment.FIRST;
        services=database.getReference("Services");
        services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceArrayList = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    serviceArrayList.add(data.getValue(Service.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference("ProvidedServices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providedServiceArrayList=new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Service service = new Service(data.child("service").child("id").getValue().toString(),
                            data.child("service").child("name").getValue().toString(),
                            Double.valueOf(data.child("service").child("hourlyRate").getValue().toString()));
//                    Service service = new Service("FAQ",0.101);
                    ProvidedService providedService = new ProvidedService();
                    providedService.setId(data.child("id").getValue().toString());
                    providedService.setService(service);
                    providedService.setServiceProviderName(data.child("serviceProviderName").getValue().toString());
                    providedService.setTimeSlots(data.child("timeSlots").getValue().toString());
                    if(!(data.child("rate").getValue()==null)){
                        providedService.setRate(Double.valueOf(data.child("rate").getValue().toString()));
                    }else{
                        providedService.setRate(0);
                    }
                    providedService.setCount(data.child("homeOwners").getChildrenCount());
                    providedServiceArrayList.add(providedService);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static Application getInstance(Context context) {
        if(instance == null){
            instance = new Application(context);
        }
        return instance;
    }

    public void addService(String name, double hourlyRate){
        if(!user.getUserType().equals("Administrator")) {return;}
        String id = services.push().getKey();
        services.child(id).setValue(new Service(id,name,hourlyRate));
        Toast.makeText(mContext, "New Service Added", Toast.LENGTH_SHORT).show();
    }

    public void editService(String id, String name, double hourlyRate){
        if(!user.getUserType().equals("Administrator")) {return;}
        DatabaseReference originalService = services.child(id);
        originalService.setValue(new Service(id, name, hourlyRate));
        Toast.makeText(mContext, "Service Updated", Toast.LENGTH_SHORT).show();
    }


    public void removeService(String id){
        if(!user.getUserType().equals("Administrator")) {return;}
        if(id.equals(""))return;
        DatabaseReference originalService = services.child(id);
        originalService.removeValue();
        Toast.makeText(mContext, "Service Removed", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<Service> getServiceArrayList() {
        return serviceArrayList;
    }

    public ArrayList<ProvidedService> getProvidedServiceArrayList() {
        return providedServiceArrayList;
    }

    public void setProvidedServiceArrayList(ArrayList<ProvidedService> providedServiceArrayList) {
        this.providedServiceArrayList = providedServiceArrayList;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
