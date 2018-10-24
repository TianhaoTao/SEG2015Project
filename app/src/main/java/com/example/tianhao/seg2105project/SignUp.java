package com.example.tianhao.seg2105project;

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




public class SignUp extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText editUsername, editEmail, editPassword;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUsername = (EditText)findViewById((R.id.editUsername));
        editPassword = (EditText)findViewById((R.id.editPassword));
        editEmail = (EditText)findViewById((R.id.editEmail));

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    final User user = new User(editUsername.getText().toString(),
                            editEmail.getText().toString(),
                            editPassword.getText().toString());

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getEmail()).exists()) {
                                Toast.makeText(SignUp.this, "This Email is Already Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                users.child(user.getEmail()).setValue(user);
                                Toast.makeText(SignUp.this, "Success Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
        });

    }

    public boolean validateEmail(String email){

        return false;
    }

    public boolean validateUser(String username){

        return false;
    }

}
