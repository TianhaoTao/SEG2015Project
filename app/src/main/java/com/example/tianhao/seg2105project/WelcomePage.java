package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.tianhao.seg2105project.Model.User;
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

    //for navigation bar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private homeFragment homeFreg;
    private NumAccountFragment numAccountFragment;
    private categotyFragment cateFreg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //for navigation bar
        mMainFrame=(FrameLayout) findViewById(R.id.main_frame);
        mMainNav=(BottomNavigationView) findViewById(R.id.main_nav);
        homeFreg=new homeFragment();
        numAccountFragment=new NumAccountFragment();
        cateFreg=new categotyFragment();

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFreg);
                        return true;
                    case R.id.nav_accNum:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(numAccountFragment);
                        return true;
                    case R.id.nav_cate:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(cateFreg);
                        return true;

                        default:
                            return false;
                }
            }
        });

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

        buttonSignOut=(Button) findViewById(R.id.buttonSignOut);

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

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intentSignUp);
                finish();
            }
        });


    }

    //setNavigation Fragment
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


}
