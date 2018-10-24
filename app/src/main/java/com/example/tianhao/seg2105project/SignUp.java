package com.example.tianhao.seg2105project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Login.User;
import com.example.tianhao.seg2105project.Login.UserType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//import android.widget.EditText;


public class SignUp extends AppCompatActivity {
    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+"\\@"+"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"+"("+
                    "\\."+"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"+")+"
    );

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private TextInputLayout editEmail;
    private TextInputLayout editUsername;
    private TextInputLayout editPassword;

    FirebaseDatabase database;
    DatabaseReference users;

    Spinner dropdownmenu;

//    EditText editUsername, editEmail, editPassword;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUsername = findViewById((R.id.editUsername));
        editPassword = findViewById((R.id.editPassword));
        editEmail = findViewById((R.id.editEmail));

        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    if(validateUser()&&validateEmail()){
                        //Toast.makeText(SignUp.this, "It is true", Toast.LENGTH_SHORT).show();
                        final User user = new User(editUsername.getEditText().getText().toString(),
                                editEmail.getEditText().getText().toString(),
                                editPassword.getEditText().getText().toString(),
                                UserType.ADMIN);

                       // Toast.makeText(SignUp.this, "It is true1", Toast.LENGTH_SHORT).show();

                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(user.getUsername()).exists()) {
                                    Toast.makeText(SignUp.this, "This Username Exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUp.this, "It is true", Toast.LENGTH_SHORT).show();
                                    users.child(user.getUsername()).setValue(user);
                                    Toast.makeText(SignUp.this, "Success Register", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
        });

        dropdownmenu = findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Home Owner");
        list.add("Administrator");
        list.add("Service Provider");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownmenu.setAdapter(adapter);
        dropdownmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(SignUp.this, "Selected:" + itemvalue, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean validateEmail(){
        String emailInput = editEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()){
            editEmail.setError("Please Enter Email here");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            editEmail.setError("Invalid email address");
            return false;
        }else{
            editEmail.setError(null);
            return true;
        }
    }

    public boolean validateUser(){
        String usernameInput = editUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            editUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            editUsername.setError("Username too long");
            return false;
        } else {
            editUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = editPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            editPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editPassword.setError("Password too weak");
            return false;
        } else {
            editPassword.setError(null);
            return true;
        }
    }


}
