package com.example.tianhao.seg2105project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tianhao.seg2105project.Login.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class WelcomePage extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference users;

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        welcome=(TextView)findViewById(R.id.textWelcome);
        welcome.setText("Hello, "+getIntent().getStringExtra("username")+
        "！ it is very glad to see you.");

    }


}
