package com.adproject.android.inventory.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adproject.android.inventory.LoginActivity;
import com.adproject.android.inventory.R;

public class HomeFragment extends Fragment {


        public static final String TAG = "Home";
        private View view;
        private TextView textView;
        private String content;
        String email;
        FragmentManager fm ;

        public static HomeFragment newInstance(String content) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putString(TAG, content);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            content = bundle != null ? bundle.getString(TAG) : "";

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            getActivity().setTitle("Home");
            init();
            return view;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fm = getFragmentManager();
        Button btnRequest = getActivity().findViewById(R.id.buttonRequest);
        Button btnRep = getActivity().findViewById(R.id.buttonDeptRep);
        Button btnHead = getActivity().findViewById(R.id.buttonDeptHead);
        try{
            email = getActivity().getIntent().getExtras().getString("email");
        }
        catch (Exception e){
            email =null;
        }
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestFragment requestlist = RequestFragment.newInstance("Request");
                Bundle args = new Bundle();
                String userid = getActivity().getIntent().getExtras().getString("userid");
                args.putSerializable("userid", userid);
                requestlist.setArguments(args);
                fm.beginTransaction().replace(R.id.conten_frame, requestlist,"Request").addToBackStack("Request").commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(1).setChecked(true);
            }
        });
        btnRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeptRepFragment deptRepFragment = new DeptRepFragment();
                Bundle args = new Bundle();
                String userid = getActivity().getIntent().getExtras().getString("userid");
                args.putSerializable("userid", userid);
                deptRepFragment.setArguments(args);
                fm.beginTransaction().replace(R.id.conten_frame, deptRepFragment,"Rep").addToBackStack("Rep").commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(2).setChecked(true);
            }
        });
        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeptHeadFragment depthead = new DeptHeadFragment();
                Bundle args = new Bundle();
                String userid = getActivity().getIntent().getExtras().getString("userid");
                args.putSerializable("userid",userid);
                depthead.setArguments(args);
                fm.beginTransaction().replace(R.id.conten_frame,depthead).addToBackStack(null).commit();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(3).setChecked(true);
            }
        });
    }

    private void init() {

        }



}
