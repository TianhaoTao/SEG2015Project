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
//
    private ArrayList<Service> serviceArrayList;
//    private ArrayList<String> imageArrayList = new ArrayList<>();

    private Application  application;

    Button buttonAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

//        application = Application.getInstance(this);
        buttonAdd =(Button)findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditService.class);
                intent.putExtra("id","");
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        application = Application.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new ServiceViewAdapter(this,application.getServiceArrayList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
//
////    private void initService(){
//        serviceArrayList.add(new Service("carpet cleaning",20));
//        serviceArrayList.add(new Service("pest control", 30.50));
//        serviceArrayList.add(new Service("moving",17.05));
////        imageArrayList.add("abc");
//        initRecyclerView();
//    }
//
//    private void initRecyclerView(){
//        Log.d(TAG, "initRecyclerView: init RecyclerView");
//        //recyclerView.setAdapter(new ServiceViewAdapter(this,serviceArrayList));
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setAdapter(application.getAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }

}
