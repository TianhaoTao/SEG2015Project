package com.example.tianhao.seg2105project;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;

import java.util.regex.Pattern;

public class EditService extends AppCompatActivity {
    private static final Pattern HOURLY_RATE =
            Pattern.compile(
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])"+ "(?=\\S+$)"+"(?=.*[@#$~`!*()_{}|?/>,<%^&+=])");
    private Application application = Application.getInstance(this);
    private TextInputLayout createHourlyRate;
    private TextInputLayout createServiceType;

    private String id;


    Button buttonDelete, buttonGOBACK, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

//      id of current service;
        id = getIntent().getStringExtra("id");
        createServiceType = findViewById((R.id.save_serviceType));
        createHourlyRate = findViewById((R.id.save_hourlyRate));


        buttonDelete = (Button)findViewById(R.id.delete_service);
        if(id.equals("")){
            buttonDelete.setVisibility(View.INVISIBLE);
        }else{
            createServiceType.getEditText().setText(getIntent().getStringExtra("name"));
            createHourlyRate.getEditText().setText(getIntent().getStringExtra("hourlyRate"));
        }
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.removeService(id);
                //finish();
            }
        });

        //for go back button
        buttonGOBACK = (Button) findViewById(R.id.cancel);
        buttonGOBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //for save button-two functions(edit and post new serv
        buttonSave = (Button) findViewById(R.id.save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //identify which function to be used
                if(id.equals("")) {
                    if(validationHourlyRate()&&validationServiceName()){
                        application.addService(createServiceType.getEditText().getText().toString(),
                                Double.parseDouble(createHourlyRate.getEditText().getText().toString()));
                        finish();
                    }else if(!validationHourlyRate()){
                        Toast.makeText(EditService.this, "invalid Hourly Rate", Toast.LENGTH_SHORT).show();
                    }else if(!validationServiceName()){
                        Toast.makeText(EditService.this, "invalid Service Name", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(validationHourlyRate()&&validationServiceName()) {
                        application.editService(id, createServiceType.getEditText().getText().toString(),
                                Double.parseDouble(createHourlyRate.getEditText().getText().toString()));
                        finish();
                    }else if(!validationHourlyRate()){
                        Toast.makeText(EditService.this, "invalid Hourly Rate", Toast.LENGTH_SHORT).show();
                    }else if(!validationServiceName()){
                        Toast.makeText(EditService.this, "invalid Service Name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean validationServiceName(){
        String ServiceName = createHourlyRate.getEditText().getText().toString().trim();
        if(ServiceName.isEmpty()){
            createHourlyRate.setError("Please Enter Service type");
            return false;
        }else{
            createHourlyRate.setError(null);
            return true;
        }
    }

    public boolean validationHourlyRate() {
        String hourlyRate = createHourlyRate.getEditText().getText().toString().trim();
        if (hourlyRate.isEmpty()) {
            createHourlyRate.setError("Please Enter Hourly rate here");
            return false;
        } else if (HOURLY_RATE.matcher(hourlyRate).matches()) {
            createHourlyRate.setError("HOURLY RATE NOT VALID,SHOULD ONLY NUMBER");
            return false;
        } else {
            createHourlyRate.setError(null);
            return true;
        }
    }
}



