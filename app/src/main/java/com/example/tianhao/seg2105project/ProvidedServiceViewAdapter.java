package com.example.tianhao.seg2105project;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.HomeOwner;
import com.example.tianhao.seg2105project.Model.ProvidedService;
import com.example.tianhao.seg2105project.Model.Service;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.tianhao.seg2105project.Model.Fragment;


public class ProvidedServiceViewAdapter extends  RecyclerView.Adapter<ProvidedServiceViewAdapter.ProvidedServiceViewHolder>{

    private static final String TAG = "ProvidedServiceViewAdapter";

    private Context mContext;

    private Dialog myDialog;
    private Button book,back,rate,cancel;
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
        //the rate of the service, showing differently at different fragment
        if(application.getFragment()==Fragment.FIRST){
            if(providedServiceArrayList.get(i).getRate()==0){
                providedServiceViewHolder.search_service_rate.setText("Not rated yet");
            }else{
                providedServiceViewHolder.search_service_rate.setText("Rate:"+String.valueOf(providedServiceArrayList.get(i).getRate()));
            }
        }else if(application.getFragment()==Fragment.SECOND){
            if(providedServiceArrayList.get(i).getIndividualRate()==0){
                providedServiceViewHolder.search_service_rate.setText("Not rated yet");
            }else{
                providedServiceViewHolder.search_service_rate.setText("Your rate is "+ String.valueOf(providedServiceArrayList.get(i).getIndividualRate()));
            }
        }
        providedServiceViewHolder.times_slots.setText(providedServiceArrayList.get(i).getTimeSlots());
        myDialog = new Dialog(mContext);

        if(application.getFragment()==Fragment.FIRST){//first fragment

            myDialog.setContentView(R.layout.dialog_booking);
            providedServiceViewHolder.serviceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                    ArrayList<String> timeSlots = new ArrayList<>();
                    //加一个spinner，算时间的，让pickedTime的值等于那个
                    String[] parts = providedServiceArrayList.get(i).getTimeSlots().split(",");
                    for(int i = 0; i<parts.length ; i++){
                        timeSlots.add(parts[i]);
                    }//把这个timeslots放到spinner里面

                    final Spinner mSpinner_time =(Spinner) myDialog.findViewById(R.id.spinner_time_slots);
                    ArrayAdapter<String> adapter_time=new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item,
                            timeSlots);
                    adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner_time.setAdapter(adapter_time);


                    book = myDialog.findViewById(R.id.Book);
                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //setvalue on firebase
                            pickedTime = mSpinner_time.getSelectedItem().toString();
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
        }else if(application.getFragment()==Fragment.SECOND){

            myDialog.setContentView(R.layout.dialog_rate_and_cencel_booking);
            providedServiceViewHolder.serviceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.show();
                    final Spinner mSpinner_rate =(Spinner) myDialog.findViewById(R.id.spinner_rate);
                    ArrayAdapter<String> adapter_rate=new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item,
                            mContext.getResources().getStringArray(R.array.rate));
                    adapter_rate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner_rate.setAdapter(adapter_rate);

                    final EditText comment = myDialog.findViewById(R.id.editTextComment);
                    comment.setText(providedServiceArrayList.get(i).getComment());

                    rate=myDialog.findViewById(R.id.rate);
                    rate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String rate = mSpinner_rate.getSelectedItem().toString();
                            if(!rate.equals("Please choose a rate...")){
                                String myComment = comment.getText().toString();
                                homeOwner.rateService(providedServiceArrayList.get(i),Integer.valueOf(rate),myComment);
                                providedServiceArrayList.get(i).setIndividualRate(Integer.valueOf(rate));
                                notifyDataSetChanged();
                                myDialog.dismiss();
                            }else{
                                Toast.makeText(mContext, "Please pick a valid rate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    cancel=myDialog.findViewById(R.id.cancel_booking);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeOwner.cancelService(providedServiceArrayList.get(i));
                            providedServiceArrayList.remove(i);
                            notifyDataSetChanged();
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
        TextView times_slots;
        ConstraintLayout serviceLayout;

        public ProvidedServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_search);
            serviceName = itemView.findViewById(R.id.providedServiceName);
            hourlyRate = itemView.findViewById(R.id.providedHourlyRate);
            serviceLayout = itemView.findViewById(R.id.search_service_layout);
            search_service_rate=itemView.findViewById(R.id.search_service_rate);
            search_service_provider=itemView.findViewById(R.id.search_service_provider);
            times_slots = itemView.findViewById(R.id.time_slots);
        }
    }

}
