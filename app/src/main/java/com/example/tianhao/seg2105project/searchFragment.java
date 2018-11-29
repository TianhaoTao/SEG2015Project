package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;

public class searchFragment extends Fragment {
    public searchFragment() {
        // Required empty public constructor
    }
    private Application application = Application.getInstance(getActivity());

    private RecyclerView recyclerView;
    private Button buttonSearchByTime,buttonSearchByService,buttonSearchByRate,buttonRefresh;
    public String time,rate,service;
    public ArrayAdapter<String> servicesArrayAdapter;
    private ArrayList<String> serviceNames = new ArrayList<>();
    private ArrayList<ProvidedService> providedServiceArrayList;
    private ArrayList<ProvidedService> selectedServiceArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        //buttom and view for homeowner only
        providedServiceArrayList=application.getProvidedServiceArrayList();

        buttonSearchByTime = (Button)view.findViewById(R.id.buttonSearchByTime);
        buttonSearchByService = (Button)view.findViewById(R.id.buttonSearchByService);
        buttonSearchByRate = (Button)view.findViewById(R.id.buttonSearchByRate);
        buttonRefresh = view.findViewById(R.id.buttonRefresh);
        recyclerView = view.findViewById(R.id.recycler_view);
        for(ProvidedService providedService:providedServiceArrayList){
            String serviceName = providedService.getService().getName();
            if(!serviceNames.contains(serviceName)){
                serviceNames.add(serviceName);
            }
        }
        servicesArrayAdapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,
                serviceNames);
        service="";


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
                                selectedServiceArrayList=new ArrayList<>();
                                for(ProvidedService providedService:providedServiceArrayList){
                                    if(providedService.getTimeSlots().contains(time)){
                                        selectedServiceArrayList.add(providedService);
                                    }
                                }
                                initAdapter(selectedServiceArrayList);
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
                servicesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_searchbyservice.setAdapter(servicesArrayAdapter);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (!mSpinner_searchbyservice.getSelectedItem().toString().equalsIgnoreCase("")) {
                            service = mSpinner_searchbyservice.getSelectedItem().toString();
                            selectedServiceArrayList=new ArrayList<>();
                            for(ProvidedService providedService:providedServiceArrayList){
                                if(providedService.getService().getName().equals(service)){
                                    selectedServiceArrayList.add(providedService);
                                }
                            }
                            initAdapter(selectedServiceArrayList);
                            Toast.makeText(getContext(), service,
                                    Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
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
                                selectedServiceArrayList=new ArrayList<>();
                                for(ProvidedService providedService:providedServiceArrayList){
                                    if(providedService.getRate()>=Integer.valueOf(rate)){
                                        selectedServiceArrayList.add(providedService);
                                    }
                                }
                                initAdapter(selectedServiceArrayList);
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

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdapter(providedServiceArrayList);
            }
        });

        initAdapter(providedServiceArrayList);
        // Inflate the layout for this fragment
        return view;
    }

    public void initAdapter(ArrayList<ProvidedService> providedServices) {
        recyclerView.setAdapter(new ProvidedServiceViewAdapter(getActivity(), providedServices));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
