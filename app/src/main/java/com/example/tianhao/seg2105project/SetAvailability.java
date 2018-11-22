package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Profile;
import com.example.tianhao.seg2105project.Model.Service;
import com.example.tianhao.seg2105project.Model.ServiceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetAvailability extends AppCompatActivity {

    //about firebase
    FirebaseDatabase database;
    DatabaseReference profile;
    DatabaseReference providedServices;
    private Application application=Application.getInstance(this);;
    private String username;
    private ServiceProvider serviceProvider = (ServiceProvider)application.getUser();


    //all initial
    int hour,hourFinal, minute,minuteFinal;
    String[] pickDate;//Monday to Sunday
    boolean[] checkPickedDate;
    ArrayList<String> availableTime = new ArrayList<>();
    ArrayList<String> otherAvailableTime = new ArrayList<>();
    Button buttonGOBACK, buttonSave, dateTimePicker;
    private RecyclerView recyclerView;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_to_profile);

        //about firebase
        username = application.getUser().getUsername();
        database = FirebaseDatabase.getInstance();
        profile = database.getReference("Service_Provider_Profile");
        providedServices = database.getReference("ProvidedServicesNew");

        //assign the id for button
        dateTimePicker=(Button) findViewById(R.id.datePicker);
        buttonSave = (Button) findViewById(R.id.save_button);
        buttonGOBACK = (Button) findViewById(R.id.cancel);
        pickDate = getResources().getStringArray(R.array.aWeek);
        checkPickedDate = new boolean[pickDate.length];
        title = findViewById(R.id.textTitle);

        recyclerView=findViewById(R.id.recycler_view_available_time);

        try{
            String[] parts = serviceProvider.getProfile().getAvailableTime().split(",");
            for(int i = 0; i<parts.length ; i++){
                availableTime.add(parts[i]);
                initAdapter();
            }
        }catch(NullPointerException e){

        }


//        //get the available time from database
//
////
////        providedServices.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                int count =0;
////                for(DataSnapshot data : dataSnapshot.getChildren()){
////                    if(data.child("serviceProviderName").getValue().toString().equals(username)){
////                        String[] timeslots = data.child("timeSlots").getValue().toString().split(",");
////                        if(data.child("service").child("id").getValue().toString()
////                                .equals(getIntent().getStringExtra("id"))){
////                            //Toast.makeText(AddServiceToProfile.this, "why", Toast.LENGTH_SHORT).show();
////                            String[] parts = data.child("timeSlots").getValue().toString().split(",");
////                            for(int i = 0; i<parts.length ; i++){
////                                availableTime.add(parts[i]);
////                                initAdapter();
////                            }
////                        }else{
////                            for(int i = 0; i<timeslots.length ; i++){
////                                otherAvailableTime.add(timeslots[i]);
////                            }
////                        }
////                    }
////                }
////            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        //the title of the activity
//        title.setText("Service:"+getIntent().getStringExtra("name")+
//                "\n"+"Hourly Rate:"+getIntent().getStringExtra("hourlyRate"));


        //group of clickListener goes here
        dateTimePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SetAvailability.this);
                mBuilder.setTitle("Select Your Available day and time");
                View mview_day = getLayoutInflater().inflate(R.layout.dialog_day_spinner,null);
                final Spinner mSpinner_day =(Spinner) mview_day.findViewById(R.id.spinner_day);
                final Spinner mSpinner_time = (Spinner) mview_day.findViewById(R.id.spinner_time);
                ArrayAdapter<String> adapter_day=new ArrayAdapter<String>(SetAvailability.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.aWeek));
                ArrayAdapter<String> adapter_time=new ArrayAdapter<String>(SetAvailability.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.time_day));
                adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_day.setAdapter(adapter_day);
                mSpinner_time.setAdapter(adapter_time);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){

                        if(mSpinner_day.getSelectedItem().toString().equals("Please choose a day...")||mSpinner_time.getSelectedItem().toString().equals("Please choose a time...")){
                            //do nothing
                            Toast.makeText(SetAvailability.this,"Invalid day",Toast.LENGTH_SHORT).show();
                        }else{
                            if(!mSpinner_day.getSelectedItem().toString().equalsIgnoreCase("")
                                    &&!mSpinner_time.getSelectedItem().toString().equalsIgnoreCase("")){
                                String time =mSpinner_day.getSelectedItem().toString()+" "+mSpinner_time.getSelectedItem().toString();
                                if(otherAvailableTime.contains(time) || availableTime.contains(time)){
                                    Toast.makeText(SetAvailability.this,
                                            "This time slot is selected", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SetAvailability.this,time,
                                            Toast.LENGTH_SHORT).show();
                                    availableTime.add(time);
                                    initAdapter();
                                    dialogInterface.dismiss();
                                }
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

        buttonGOBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!availableTime.isEmpty()) {
                    String timeslots = new String();
                    for (int i = 0; i < availableTime.size(); i++) {
                        if (i == 0) {
                            timeslots = availableTime.get(i);
                        } else {

                            timeslots = timeslots + "," + availableTime.get(i);
                        }
                    }
                    serviceProvider.getProfile().setAvailableTime(timeslots);
                    finish();
                } else {
                    Toast.makeText(SetAvailability.this, "Please input at least one time slot", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initAdapter() {//refresh adapter
        AvailableTimeViewAdapter adapter = new AvailableTimeViewAdapter(availableTime,SetAvailability.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
