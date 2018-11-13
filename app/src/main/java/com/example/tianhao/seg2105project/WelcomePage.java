package com.example.tianhao.seg2105project;

import android.content.Intent;
import android.media.MediaPlayer;
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


import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {

    User user;

    TextView welcome;

    Button buttonSignOut;
    MediaPlayer buttonSound;


    private Application application;

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
        buttonSound=MediaPlayer.create(WelcomePage.this,R.raw.niu);

        application = Application.getInstance(this);
        user = application.getUser();

        //title
        welcome=(TextView)findViewById(R.id.textWelcome);
        welcome.setText("Hello, "+user.getUsername()+
        "！ You are logged as a "+ user.getUserType());

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
        setFragment(homeFragment);

        mMainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_users:
                        setFragment(usersFragment);
                        return true;
                        default:
                            return false;
                }
            }
        });

        //signOut
        buttonSignOut=(Button) findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
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
