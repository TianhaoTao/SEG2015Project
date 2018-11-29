package com.example.tianhao.seg2105project;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.HomeOwner;
import com.example.tianhao.seg2105project.Model.ProvidedService;
import com.example.tianhao.seg2105project.Model.Service;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProvidedServiceViewAdapter extends  RecyclerView.Adapter<ProvidedServiceViewAdapter.ProvidedServiceViewHolder>{

    private static final String TAG = "ProvidedServiceViewAdapter";

    private Context mContext;

    private Dialog myDialog;
    private Button book,back;
    String pickedTime;// to store the selected time slot

    private ArrayList<ProvidedService> providedServiceArrayList;

    private Application application;
    private HomeOwner homeOwner;

    public ProvidedServiceViewAdapter(Context mContext,ArrayList<ProvidedService> providedServiceArrayList){
        this.mContext=mContext;
        this.providedServiceArrayList =providedServiceArrayList;
        application=Application.getInstance(mContext);
        homeOwner = (HomeOwner)application.getUser();
    }

    @NonNull
    @Override
    public ProvidedServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_search_service, viewGroup, false);
        ProvidedServiceViewHolder holder = new ProvidedServiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProvidedServiceViewHolder providedServiceViewHolder, final int i) {
        Service service = providedServiceArrayList.get(i).getService();

        providedServiceViewHolder.serviceName.setText(providedServiceArrayList.get(i).getService().getName());
        providedServiceViewHolder.hourlyRate.setText("hourly rate:"+String.valueOf(providedServiceArrayList.get(i).getService().getHourlyRate()));

        providedServiceViewHolder.search_service_provider.setText(providedServiceArrayList.get(i).
                getServiceProviderName());
        if(providedServiceArrayList.get(i).getRate()==-1){
            providedServiceViewHolder.search_service_rate.setText("Not rated yet");
        }else{
            providedServiceViewHolder.search_service_rate.setText(String.valueOf(providedServiceArrayList.get(i).getRate()));
        }
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_booking);

        providedServiceViewHolder.serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();
                ArrayList<String> timeSlots = new ArrayList<>();
                //加一个spinner，算时间的，让pickedTime的值等于那个
                String[] parts = providedServiceArrayList.get(i).getTimeslots().split(",");
                for(int i = 0; i<parts.length ; i++){
                    timeSlots.add(parts[i]);
                }
                

                book = myDialog.findViewById(R.id.Book);
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //setvalue on firebase
                        homeOwner.bookService(providedServiceArrayList.get(i),pickedTime);
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return providedServiceArrayList.size();
    }


    public class ProvidedServiceViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView serviceName;
        TextView hourlyRate;
        TextView search_service_rate;
        TextView search_service_provider;
        ConstraintLayout serviceLayout;

        public ProvidedServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_search);
            serviceName = itemView.findViewById(R.id.providedServiceName);
            hourlyRate = itemView.findViewById(R.id.providedHourlyRate);
            serviceLayout = itemView.findViewById(R.id.search_service_layout);
            search_service_rate=itemView.findViewById(R.id.search_service_rate);
            search_service_provider=itemView.findViewById(R.id.search_service_provider);
        }
    }

}
