package com.example.manishacomputer.navigation.Nav_view_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manishacomputer.navigation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends Fragment {

private View view;
    public WithdrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_withdraw, container, false);
        return view;
    }

}
