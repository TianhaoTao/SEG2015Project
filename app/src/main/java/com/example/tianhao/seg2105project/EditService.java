package com.example.tianhao.seg2105project;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditService extends AppCompatActivity {

//    private Application application = Application.getInstance(this);
//    private TextInputLayout createHourlyRate;
//    private TextInputLayout createServiceType;
//    private TextInputLayout getIDinputLine;
//    private Service editService;
//
//
//    Button buttonDelete, buttonGOBACK, buttonSave,buttonEditCheck;
//    boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
//        getIDinputLine.setVisibility(View.INVISIBLE);

        //for Delete button
//        final Service currentService;
//        currentService = new Service(getIntent().getStringExtra("ServiceID"),
//                getIntent().getStringExtra("ServiceType"),
//                Double.parseDouble(getIntent().getStringExtra("HourlyRate")));
//
//        buttonDelete = (Button)findViewById(R.id.delete_service);
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                application.removeService(currentService.getId());
//            }
//        });
//
//        //for go back button
//        buttonGOBACK = (Button) findViewById(R.id.cancel);
//        buttonGOBACK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//        //for save button-two functions(edit and post new service)
//        createHourlyRate = findViewById((R.id.hourlyRate));
//        createServiceType = findViewById((R.id.serviceType));
//        editService = new Service(getIntent().getStringExtra("ServiceID"),
//                getIntent().getStringExtra("ServiceType"),
//                Double.parseDouble(getIntent().getStringExtra("HourlyRate")));
//        buttonEditCheck=(Button) findViewById(R.id.edit_service_check);
//        getIDinputLine=(TextInputLayout)findViewById(R.id.serviceID);
//        buttonEditCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                check=true;
//                getIDinputLine.setVisibility(View.VISIBLE);
//            }
//        });
//        buttonSave = (Button) findViewById(R.id.save_button);
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //identify which function to be used
//                if(check) {
//                    application.editService(editService.getId(),createHourlyRate.getEditText().getText().toString(),
//                            Double.parseDouble(createServiceType.getEditText().getText().toString()));
//                }else {
//                    application.addService(createHourlyRate.getEditText().getText().toString(),
//                            Double.parseDouble(createServiceType.getEditText().getText().toString()));
//                }
//            }
//        });
    }
}



