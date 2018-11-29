package com.example.tianhao.seg2105project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tianhao.seg2105project.Model.Application;
import com.example.tianhao.seg2105project.Model.HomeOwner;

public class bookingFragment extends Fragment {
    public bookingFragment() {
        // Required empty public constructor
    }

    private Application application = Application.getInstance(getActivity());

    private HomeOwner homeOwner;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeOwner = (HomeOwner)application.getUser();
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(new ProvidedServiceViewAdapter(getActivity(), homeOwner.getServiceArrayList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
