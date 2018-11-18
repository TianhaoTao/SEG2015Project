package com.example.tianhao.seg2105project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tianhao.seg2105project.Model.Application;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {


    public profileFragment() {
        // Required empty public constructor
    }
    private Application application = Application.getInstance(getActivity());

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }
}
