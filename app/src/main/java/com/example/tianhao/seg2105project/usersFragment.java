package com.example.tianhao.seg2105project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tianhao.seg2105project.Model.Application;


/**
 * A simple {@link Fragment} subclass.
 */
public class usersFragment extends Fragment {


    public usersFragment() {
        // Required empty public constructor
    }
//
//    private Application application;
//
//    private RecyclerView recyclerView;
//    private Button buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_num_account, container, false);
//
//        buttonAdd =(Button)view.findViewById(R.id.buttonAdd);
//
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EditService.class);
//                intent.putExtra("id","");
//                startActivity(intent);
//            }
//        });
//        recyclerView = view.findViewById(R.id.recycler_view);

        // Inflate the layout for this fragment
        return view;
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        application = Application.getInstance(getActivity());
//        recyclerView.setAdapter(new ServiceViewAdapter(getActivity(),application.getServiceArrayList()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//    }
}
