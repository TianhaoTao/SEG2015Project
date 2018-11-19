package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;
import com.example.tianhao.seg2105project.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddServiceToProfile extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener {

    //about firebase
    FirebaseDatabase database;
    DatabaseReference profile;
    private Application application;
    String user;


    //all initial
    int hour,hourFinal, minute,minuteFinal;
    String[] pickDate;//Monday to Sunday
    boolean[] checkPickedDate;
    ArrayList<String> availableTime=new ArrayList<>();
    Button buttonDelete, buttonGOBACK, buttonSave, dateTimePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_to_profile);

        //about firebase
        application = Application.getInstance(this);
        user = application.getUser().getUsername();
        database = FirebaseDatabase.getInstance();
        profile = database.getReference("Service_Provider_Profile");

        //assign the id for button
        dateTimePicker=(Button) findViewById(R.id.datePicker);
        buttonDelete = (Button)findViewById(R.id.delete_service);
        buttonSave = (Button) findViewById(R.id.save_button);
        buttonGOBACK = (Button) findViewById(R.id.cancel);
        pickDate = getResources().getStringArray(R.array.aWeek);
        checkPickedDate = new boolean[pickDate.length];


        //group of clickListener goes here
        dateTimePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddServiceToProfile.this);
                mBuilder.setTitle("Select Your Available day and time");
                View mview_day = getLayoutInflater().inflate(R.layout.dialog_day_spinner,null);
                final Spinner mSpinner_day =(Spinner) mview_day.findViewById(R.id.spinner_day);
                final Spinner mSpinner_time = (Spinner) mview_day.findViewById(R.id.spinner_time);
                ArrayAdapter<String> adapter_day=new ArrayAdapter<String>(AddServiceToProfile.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.aWeek));
                ArrayAdapter<String> adapter_time=new ArrayAdapter<String>(AddServiceToProfile.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.time_day));
                adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner_day.setAdapter(adapter_day);
                mSpinner_time.setAdapter(adapter_time);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        if(!mSpinner_day.getSelectedItem().toString().equalsIgnoreCase("")){
                            Toast.makeText(AddServiceToProfile.this,
                                    mSpinner_day.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                        if(!mSpinner_time.getSelectedItem().toString().equalsIgnoreCase("")){
                            Toast.makeText(AddServiceToProfile.this,
                                    mSpinner_time.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                        availableTime.add(
                                mSpinner_day.getSelectedItem().toString()+" "+mSpinner_time.getSelectedItem().toString());
                        profile.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                profile.child(user).child(getIntent().getStringExtra("name")).child("Available_Time").setValue(availableTime);
                                Toast.makeText(AddServiceToProfile.this, "Day set successfully", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });


                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
//                            profile.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    profile.child(user).child(getIntent().getStringExtra("name")).child("Available_Time").removeValue();
//                                    Toast.makeText(AddServiceToProfile.this, "Day cleared successfully", Toast.LENGTH_SHORT).show();
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                        }
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
    }

    //date and time setter goes here
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minute = i1;

    }
}
