package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    //for navigation bar
    private NavigationView mMainNav;
    private FrameLayout mMainFrame;
    private homeFragment homeFragment;//home page fragment linking to fragment_home.xml
    private usersFragment usersFragment;//users fragment linking to fragment_num_account.xml
    private servicesFragment servicesFragment;//services category fragment linking to fragment_category.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for navigation draw
        mMainFrame=(FrameLayout) findViewById(R.id.main_fragment);
        mMainNav=(NavigationView) findViewById(R.id.nav_draw);
        homeFragment=new homeFragment();
        usersFragment=new usersFragment();
        servicesFragment=new servicesFragment();

        mMainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_users:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(usersFragment);
                        return true;
                    case R.id.nav_post_services:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(servicesFragment);
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


    //menuItem for draw in admin
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //setNavigation Fragment
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment);
        fragmentTransaction.commit();
    }


}
