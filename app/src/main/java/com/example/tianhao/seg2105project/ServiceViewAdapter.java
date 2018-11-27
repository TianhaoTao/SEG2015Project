package com.example.tianhao.seg2105project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.Service;
import com.example.tianhao.seg2105project.Model.ServiceProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceViewAdapter extends RecyclerView.Adapter<ServiceViewAdapter.ServiceViewHolder>{

    private static final String TAG = "ServiceViewAdapter";

    private Context mContext;

    private ArrayList<Service> serviceArrayList;

    private Application application;

    Dialog myDialog;
    Button book,back;
    //about firebase
    FirebaseDatabase database;
    DatabaseReference bookedServices;

    public ServiceViewAdapter(Context mContext, ArrayList<Service> serviceArrayList) {
        this.mContext = mContext;
        this.serviceArrayList = serviceArrayList;
         application = Application.getInstance(mContext);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(!application.getUser().getUserType().equals("Home Owner")) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_service, viewGroup, false);
            ServiceViewHolder holder = new ServiceViewHolder(view);
            return holder;
        }else{//D4
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_search_service, viewGroup, false);
            ServiceViewHolder holder = new ServiceViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called..");

        if(!application.getUser().getUserType().equals("Home Owner")) {
            serviceViewHolder.serviceName.setText(serviceArrayList.get(i).getName());
            serviceViewHolder.hourlyRate.setText(String.valueOf(serviceArrayList.get(i).getHourlyRate()));
        }else{//D4
            serviceViewHolder.serviceName.setText(serviceArrayList.get(i).getName());
            serviceViewHolder.search_service_rate.setText(String.valueOf(serviceArrayList.get(i).getRate()));
            serviceViewHolder.search_service_time.setText(String.valueOf(serviceArrayList.get(i).getTime()));
        }

        //about firebase D4
        database=FirebaseDatabase.getInstance();
        bookedServices=database.getReference("ProvidedServices");
        //Dialog D4 initial
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_booking);

        serviceViewHolder.serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + serviceArrayList.get(i));
                if (application.getUser().getUserType().equals("Administrator")) {
                    Intent intent = new Intent(mContext, EditService.class);
                    intent.putExtra("id", serviceArrayList.get(i).getId());
                    intent.putExtra("name", serviceArrayList.get(i).getName());
                    intent.putExtra("hourlyRate", Double.toString(serviceArrayList.get(i).getHourlyRate()));
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, serviceArrayList.get(i).getName() + " is selected.", Toast.LENGTH_SHORT).show();

                }else if (application.getUser().getUserType().equals("Service Provider")){
                    ServiceProvider serviceProvider = (ServiceProvider)application.getUser();
                    if(serviceProvider.getProfile()==null){
                        Toast.makeText(mContext, "Please complete your information for profile before adding service", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(mContext, AddServiceToProfile.class);
                        intent.putExtra("id", serviceArrayList.get(i).getId());
                        intent.putExtra("name", serviceArrayList.get(i).getName());
                        intent.putExtra("hourlyRate", Double.toString(serviceArrayList.get(i).getHourlyRate()));
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, serviceArrayList.get(i).getName() + " is selected.", Toast.LENGTH_SHORT).show();

                    }
                }else{//D4
                    myDialog.show();
                    book = myDialog.findViewById(R.id.delete_time_slot);
                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //setvalue on firebase
                            myDialog.dismiss();
                        }
                    });
                    back = myDialog.findViewById(R.id.go_back);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    Intent intent = new Intent(mContext, EditService.class);
                    intent.putExtra("name", serviceArrayList.get(i).getName());
                    intent.putExtra("rate", serviceArrayList.get(i).getRate());
                    intent.putExtra("time", serviceArrayList.get(i).getTime());
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, serviceArrayList.get(i).getName() + " is selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceArrayList == null ? 0 : serviceArrayList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView serviceName;
        TextView hourlyRate;
        TextView search_service_rate;
        TextView search_service_time;
        ConstraintLayout serviceLayout;


        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            serviceName = itemView.findViewById(R.id.serviceName);
            if(!application.getUser().getUserType().equals("Home owner")) {
                serviceLayout = itemView.findViewById(R.id.service_layout);
                hourlyRate = itemView.findViewById(R.id.hourlyRate);
            }else{serviceLayout = itemView.findViewById(R.id.search_service_layout);
                search_service_rate=itemView.findViewById(R.id.search_service_rate);
                search_service_time=itemView.findViewById(R.id.search_service_time);}
        }
    }
}
