package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;

//
//import com.example.tianhao.seg2105project.ServiceViewAdapter;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class AdminHomePage extends AppCompatActivity {

    private static final String TAG = "AdminHomePage";

//    private ArrayList<Service> serviceArrayList;

    private Application  application;

    private RecyclerView recyclerView;
    private Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        buttonAdd =(Button)findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditService.class);
                intent.putExtra("id","");
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);

    }


    @Override
    protected void onStart() {
        super.onStart();
        application = Application.getInstance(this);

        recyclerView.setAdapter(new ServiceViewAdapter(this,application.getServiceArrayList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
