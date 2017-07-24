package com.example.manishacomputer.navigation.Nav_view_menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manishacomputer.navigation.R;



public class AboutusFragment extends Fragment {
private View view;

    public AboutusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_aboutus, container, false);
        return view;
    }
}

