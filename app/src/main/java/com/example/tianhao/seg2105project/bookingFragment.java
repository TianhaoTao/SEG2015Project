package com.example.tianhao.seg2105project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tianhao.seg2105project.Model.Application;

public class bookingFragment extends Fragment {
    public bookingFragment() {
        // Required empty public constructor
    }

    private Application application = Application.getInstance(getActivity());

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        // Inflate the layout for this fragment
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();//refresh the recylerview every time it is brought to the front
//        application = Application.getInstance(getActivity());
//        recyclerView.setAdapter(new ServiceViewAdapter(getActivity(),application.getServiceArrayList()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }
}
