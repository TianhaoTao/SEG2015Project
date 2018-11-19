package com.example.tianhao.seg2105project;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ServiceProviderProfile extends AppCompatActivity {

    TextInputLayout editAddress;
    TextInputLayout editPhone;
    TextInputLayout editCompany;
    TextInputLayout editDescription;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button ButtonSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);

        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        editCompany = findViewById(R.id.editCompany);
        editDescription = findViewById(R.id.editDescription);
        radioGroup = findViewById(R.id.radioGroup);

        ButtonSubmit = (Button)findViewById(R.id.buttonSubmit2);

        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioButtonId);

            }
        });

    }

    public void checkButton(View v){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioButtonId);
    }
}
