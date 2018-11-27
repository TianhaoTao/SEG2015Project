package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.ProvidedService;
import com.example.tianhao.seg2105project.Model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class searchFragment extends Fragment {
    public searchFragment() {
        // Required empty public constructor
    }
    private Application application = Application.getInstance(getActivity());

    private RecyclerView recyclerView;
    private Button buttonSearchByTime,buttonSearchByService,buttonSearchByRate;
    public String time,rate,service;
    public ArrayAdapter<String> servicesArray;
    private ArrayList<Service> servicesView;
    DatabaseReference providedService;
    DatabaseReference allServices;//including not provided services
    FirebaseDatabase database;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //buttom and view for homeowner only
        buttonSearchByTime = (Button)view.findViewById(R.id.buttonSearchByTime);
        buttonSearchByService = (Button)view.findViewById(R.id.buttonSearchByService);
        buttonSearchByRate = (Button)view.findViewById(R.id.buttonSearchByRate);
        recyclerView = view.findViewById(R.id.recycler_view);
        servicesArray=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);
        servicesView=new ArrayList<Service>();
        service="";

        //about firebase
        database=FirebaseDatabase.getInstance();
        allServices=database.getReference("Services");
        providedService=database.getReference("ProvidedServices");

        //collect all service that set by admin
        allServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    servicesArray.add(data.child("name").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        buttonSearchByTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("SearchByTime");
                View mview_day = getLayoutInflater().inflate(R.layout.dialog_day_spinner,null);
                final Spinner mSpinner_searchbyday =(Spinner) mview_day.findViewById(R.id.spinner_day);
                final Spinner mSpinner_searchbytime =(Spinner) mview_day.findViewById(R.id.spinner_time);
                ArrayAdapter<String> adapter_day=new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.aWeek));
                ArrayAdapter<String> adapter_time=new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.time_day));

                adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_searchbyday.setAdapter(adapter_day);
                mSpinner_searchbytime.setAdapter(adapter_time);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        if(mSpinner_searchbyday.getSelectedItem().toString().equals("Please choose a day...")||mSpinner_searchbytime.getSelectedItem().toString().equals("Please choose a time...")){
                            //do nothing
                            Toast.makeText(getContext(),"Invalid day",Toast.LENGTH_SHORT).show();
                        }else{
                            if(!mSpinner_searchbyday.getSelectedItem().toString().equalsIgnoreCase("")
                                    &&!mSpinner_searchbytime.getSelectedItem().toString().equalsIgnoreCase("")){
                                time =mSpinner_searchbyday.getSelectedItem().toString()+" "+mSpinner_searchbytime.getSelectedItem().toString();
                                Toast.makeText(getContext(),time,
                                        Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mview_day);
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
            }
        });


        buttonSearchByService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("SearchByService");
                View mview_day = getLayoutInflater().inflate(R.layout.dialog_searchbyone_spinner,null);
                final Spinner mSpinner_searchbyservice =(Spinner) mview_day.findViewById(R.id.spinner_searchByOne);
                servicesArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_searchbyservice.setAdapter(servicesArray);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        if(!mSpinner_searchbyservice.getSelectedItem().toString().equalsIgnoreCase("")){
                            service =mSpinner_searchbyservice.getSelectedItem().toString();
                            Toast.makeText(getContext(),service,
                                    Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                        providedService.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(servicesView.isEmpty()) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.child("service").child("name").getValue().toString().equals(service)) {
                                            int rating;
                                            if(data.child("rate").getValue()==null){ rating=0;}else{
                                                rating=Integer.parseInt(data.child("rate").getValue().toString());
                                            }
                                            String time = data.child("timeSlots").getValue().toString();
                                            servicesView.add(new Service(service, rating, time));
                                            Toast.makeText(getContext(),"isempt,added",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    servicesView.clear();
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.child("service").child("name").getValue().toString().equals(service)) {
                                            int rating;
                                            if(data.child("rate").getValue()==null){ rating=0;}else{
                                                rating=Integer.parseInt(data.child("rate").getValue().toString());
                                            }
                                            String time = data.child("timeSlots").getValue().toString();
                                            servicesView.add(new Service(service, rating, time));
                                            Toast.makeText(getContext(),"isnotempt,added",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });

                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mview_day);
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
                initAdapter();
            }
        });


        buttonSearchByRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("SearchByRate");
                View mview_day = getLayoutInflater().inflate(R.layout.dialog_searchbyone_spinner,null);
                final Spinner mSpinner_searchbyrate =(Spinner) mview_day.findViewById(R.id.spinner_searchByOne);
                ArrayAdapter<String> adapter_rate=new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.rate));
                adapter_rate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_searchbyrate.setAdapter(adapter_rate);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){

                        if(mSpinner_searchbyrate.getSelectedItem().toString().equals("Please choose a rate...")){
                            //do nothing
                            Toast.makeText(getContext(),"Invalid day",Toast.LENGTH_SHORT).show();
                        }else{
                            if(!mSpinner_searchbyrate.getSelectedItem().toString().equalsIgnoreCase("")){
                                rate =mSpinner_searchbyrate.getSelectedItem().toString();
                                Toast.makeText(getContext(),rate,
                                        Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mview_day);
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void initAdapter() {
        application = Application.getInstance(getActivity());
        for(int i=0; i<servicesView.size();i++){
            Toast.makeText(getContext(),servicesView.get(i).getName()+" "+servicesView.get(i).getRate() +
                    " "+servicesView.get(i).getTime(),Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(new ServiceViewAdapter(getActivity(),servicesView));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
