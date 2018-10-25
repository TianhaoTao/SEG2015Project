package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tianhao.seg2105project.Login.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {

    User user;
    FirebaseDatabase database;
    DatabaseReference users;

    TextView welcome;
    TextView usernameList;

    Button buttonSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        user= new User(getIntent().getStringExtra("username"),
                getIntent().getStringExtra("email"),
                getIntent().getStringExtra("password"),
                getIntent().getStringExtra("userType"));
        welcome=(TextView)findViewById(R.id.textWelcome);
        usernameList=(TextView)findViewById(R.id.textViewUsers);
        welcome.setText("Hello, "+user.getUsername()+
        "！ You log in as a "+ user.getUserType());

        buttonSignOut=(Button) findViewById(R.id.buttonSignOut);

        if(user.getUserType().equals("Administrator")){
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userList="";
                    int counter=0;
                    for(DataSnapshot data: dataSnapshot.getChildren()) {
                        counter++;
                        userList+= counter+"."+data.child("username").getValue().toString()
                                +" "+data.child("userType").getValue().toString()
                                +"\n";
                    }
                    usernameList.setText(userList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            usernameList.setVisibility(View.INVISIBLE);

        }

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intentSignUp);
            }
        });


    }


}
