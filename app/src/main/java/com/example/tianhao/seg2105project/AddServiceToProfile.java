package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    ArrayList<Integer> pickedDate = new ArrayList<>();
    ArrayList<String> day=new ArrayList<>();
    TextView tv_result;
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
        tv_result=(TextView) findViewById(R.id.tv_result);
        pickDate = getResources().getStringArray(R.array.aWeek);
        checkPickedDate = new boolean[pickDate.length];


        //group of clickListener goes here
        dateTimePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddServiceToProfile.this);
                mBuilder.setTitle("Select Your Available day");
                mBuilder.setMultiChoiceItems(pickDate, checkPickedDate, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int position, boolean isChecked) {
                        if(isChecked){
                            if(!pickedDate.contains(position)){
                                pickedDate.add(position);
                            }else{
                                pickedDate.remove(position);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        for(int i=0; i<pickedDate.size(); i++){
                            day.add(pickDate[pickedDate.get(i)]);
                        }
                        profile.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                profile.child(user).child("Available_Time").setValue(day);
                                Toast.makeText(AddServiceToProfile.this, "Day set successfully", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        tv_result.setText(day);
                    }
                });


                mBuilder.setNegativeButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i=0; i<checkPickedDate.length; i++){
                            checkPickedDate[i]=false;
                            day.clear();
                            pickedDate.clear();
                            profile.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    profile.child(user).child("Available_Time").removeValue();
                                    Toast.makeText(AddServiceToProfile.this, "Day cleared successfully", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            tv_result.setText("");
                        }
                    }
                });
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

        tv_result.setText(
                " hour: "+hourFinal+"/"+
                " minute: "+minuteFinal
        );
    }
}
