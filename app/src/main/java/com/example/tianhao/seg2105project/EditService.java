package com.example.tianhao.seg2105project;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;

public class EditService extends AppCompatActivity {

    private Application application = Application.getInstance(this);
    private TextInputLayout createHourlyRate;
    private TextInputLayout createServiceType;

    private String id;


    Button buttonDelete, buttonGOBACK, buttonSave;
    boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

//        final Service currentService;
        id = getIntent().getStringExtra("id");

        buttonDelete = (Button)findViewById(R.id.delete_service);
        if(id.equals("")){
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.removeService(id);
                finish();
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


        //for save button-two functions(edit and post new service)
        createHourlyRate = findViewById((R.id.save_hourlyRate));
        createServiceType = findViewById((R.id.save_serviceType));
        buttonSave = (Button) findViewById(R.id.save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //identify which function to be used
                if(id.equals("")) {
                    application.addService(createServiceType.getEditText().getText().toString(),
                            Double.parseDouble(createHourlyRate.getEditText().getText().toString()));
                }else {
                    application.editService(id,createServiceType.getEditText().getText().toString(),
                            Double.parseDouble(createHourlyRate.getEditText().getText().toString()));
                }
                finish();
            }
        });
    }
}



