package com.example.tianhao.seg2105project;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvailableTimeViewAdapter extends  RecyclerView.Adapter<AvailableTimeViewAdapter.TimeViewHolder>{
    private ArrayList<String> mAvailableTime;
    private Context mContext;
    Dialog myDialog;


    public AvailableTimeViewAdapter(ArrayList<String> mavailableTime, Context context){
        mAvailableTime=mavailableTime;
        mContext = context;
    }
    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_available_time,viewGroup, false);
        TimeViewHolder holder =new TimeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder timeViewHolder, final int position) {

        Intent intent = new Intent(mContext,AddServiceToProfile.class);
        intent.putExtra("TimeSlot",mAvailableTime.get(position));
        mContext.startActivity(intent);
        Toast.makeText(mContext, mAvailableTime.get(position) + " is selected.", Toast.LENGTH_SHORT).show();
        //Dialog initial
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_delete_timeslot);
        timeViewHolder.AvailableTime_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mAvailableTime.get(position), Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAvailableTime.size();
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image_availableTime;
        TextView day;
        ConstraintLayout AvailableTime_layout;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            image_availableTime = itemView.findViewById(R.id.image_time);
            day = itemView.findViewById(R.id.TextView_day);
            AvailableTime_layout = itemView.findViewById(R.id.AvailableTime_layout);
        }
    }
}
