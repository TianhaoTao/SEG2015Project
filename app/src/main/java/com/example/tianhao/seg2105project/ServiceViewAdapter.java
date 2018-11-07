package com.example.tianhao.seg2105project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Service;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceViewAdapter extends RecyclerView.Adapter<ServiceViewAdapter.ServiceViewHolder>{

    private static final String TAG = "ServiceViewAdapter";

    private Context mContext;

    private ArrayList<Service> serviceArrayList = new ArrayList<>();
    private ArrayList<String> imageArrayList = new ArrayList<String>();

    public ServiceViewAdapter(Context mContext, ArrayList<Service> serviceArrayList, ArrayList<String> imageArrayList) {
        this.mContext = mContext;
        this.serviceArrayList = serviceArrayList;
        this.imageArrayList = imageArrayList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_service,viewGroup,false);
        ServiceViewHolder holder = new ServiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called..");

        serviceViewHolder.serviceName.setText(serviceArrayList.get(i).getName());
        serviceViewHolder.hourlyRate.setText(String.valueOf(serviceArrayList.get(i).getHourlyRate()));

        serviceViewHolder.serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+serviceArrayList.get(i));
                Toast.makeText(mContext, i+" is the current position.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView serviceName;
        TextView hourlyRate;
        ConstraintLayout serviceLayout;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            serviceName = itemView.findViewById(R.id.serviceName);
            hourlyRate = itemView.findViewById(R.id.hourlyRate);
            serviceLayout = itemView.findViewById(R.id.service_layout);
        }
    }
}
