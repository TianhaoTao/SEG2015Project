package com.example.tianhao.seg2105project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        "！ You log in as a "+ getIntent().getStringExtra("userType"));

    }


}
