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

import com.example.tianhao.seg2105project.Model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddServiceToProfile extends AppCompatActivity{

    //about firebase
    FirebaseDatabase database;
    DatabaseReference profile;
    DatabaseReference providedServices;
    private Application application=Application.getInstance(this);;
    private String username;
    private ServiceProvider serviceProvider = (ServiceProvider)application.getUser();
    private Service service;


    //all initial
    String[] pickDate;//Monday to Sunday
    boolean[] checkPickedDate;
    ArrayList<String> availableTime = new ArrayList<>();
    Button buttonDelete, buttonGOBACK, buttonSave, dateTimePicker;
    private RecyclerView recyclerView;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_to_profile);
        service = new Service(getIntent().getStringExtra("id"),
                getIntent().getStringExtra("name"),
                Double.valueOf(getIntent().getStringExtra("hourlyRate")));

        //about firebase
        username = application.getUser().getUsername();
        database = FirebaseDatabase.getInstance();
        profile = database.getReference("Service_Provider_Profile");
        providedServices = database.getReference("ProvidedServices");

        //assign the id for button
        dateTimePicker=(Button) findViewById(R.id.datePicker);
        buttonDelete = (Button)findViewById(R.id.delete_service);
        buttonSave = (Button) findViewById(R.id.save_button);
        buttonGOBACK = (Button) findViewById(R.id.cancel);
        pickDate = getResources().getStringArray(R.array.aWeek);
        checkPickedDate = new boolean[pickDate.length];
        title = findViewById(R.id.textTitle);

        switch (application.getFragment()){
            case FIRST:buttonDelete.setVisibility(View.INVISIBLE);
                break;
            case THIRD:buttonSave.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }


        //the title of the activity
        title.setText("Service:"+getIntent().getStringExtra("name")+
                "\n"+"Hourly Rate:"+getIntent().getStringExtra("hourlyRate"));


        buttonGOBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceProvider.saveServiceToProfile(service);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceProvider.removeServiceFromProfile(service);
                finish();
            }
        });

    }

    private void initAdapter() {//refresh adapter
        AvailableTimeViewAdapter adapter = new AvailableTimeViewAdapter(availableTime,AddServiceToProfile.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}