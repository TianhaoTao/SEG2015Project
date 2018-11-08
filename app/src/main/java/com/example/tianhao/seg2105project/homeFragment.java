package com.example.tianhao.seg2105project;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tianhao.seg2105project.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {


    User user;
    FirebaseDatabase database;
    DatabaseReference users;

    TextView welcome;
    TextView usernameList;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        user= new User(getIntent().getStringExtra("username"),
                getIntent().getStringExtra("email"),
                getIntent().getStringExtra("password"),
                getIntent().getStringExtra("userType"));
        welcome=(TextView)findViewById(R.id.textWelcome);
        usernameList=(TextView)findViewById(R.id.textViewUsers);
        welcome.setText("Hello, "+user.getUsername()+
                "！ You are logged as a "+ user.getUserType());


        if(user.getUserType().equals("Administrator")){//the admin can see the list of house owners and service providers
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
