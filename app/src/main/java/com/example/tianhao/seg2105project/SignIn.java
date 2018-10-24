package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.tianhao.seg2105project.Login.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class SignIn extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText editEmail, editPassword;
    Button buttonSubmit, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");

        editEmail = (EditText)findViewById((R.id.editEmail));
        editPassword = (EditText)findViewById(R.id.editPassword);

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), SignUp.class);
                startActivity(register);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(editEmail.getText().toString(),editPassword.getText().toString());
            }
        });

    }

    private void signIn(final String email, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(email).exists() && !email.isEmpty()){
                        User login = dataSnapshot.child(email).getValue(User.class);
                        if(login.getPassword().equals(password)){
                            Toast.makeText(SignIn.this, "Success Login", Toast.LENGTH_SHORT).show();
                           Intent register = new Intent(getApplicationContext(), WelcomePage.class);
                           startActivity(register);
                        }else{
                            Toast.makeText(SignIn.this, "Password is Wrong", Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Toast.makeText(SignIn.this, "Email is Not Registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //write code here
            }
        });
    }


}
