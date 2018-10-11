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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;




public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText editUsername, editEmail, editPassword;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                            if (dataSnapshot.child(user.getUsername()).exists()) {
                                Toast.makeText(MainActivity.this, "The Username Already Exist", Toast.LENGTH_SHORT).show();
                            } else {
                                users.child(user.getUsername()).setValue(user);
                                Toast.makeText(MainActivity.this, "Success Register", Toast.LENGTH_SHORT).show();

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
        });

    }
}
