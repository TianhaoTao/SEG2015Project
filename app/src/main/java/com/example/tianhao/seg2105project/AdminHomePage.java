package com.example.tianhao.seg2105project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;

import java.util.ArrayList;

public class AdminHomePage extends AppCompatActivity {

    private static final String TAG = "AdminHomePage";
//
//    private ArrayList<Service> serviceArrayList = new ArrayList<>();
//    private ArrayList<String> imageArrayList = new ArrayList<>();

    private Application application = Application.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        initService();
    }

    private void initService(){
//        serviceArrayList.add(new Service("carpet cleaning",20));
//        serviceArrayList.add(new Service("pest control", 30.50));
//        serviceArrayList.add(new Service("moving",17.05));
//        imageArrayList.add("abc");
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setAdapter(new ServiceViewAdapter(this,serviceArrayList));
        recyclerView.setAdapter(application.getAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
