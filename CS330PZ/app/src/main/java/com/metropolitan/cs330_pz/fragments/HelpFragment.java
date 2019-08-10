package com.metropolitan.cs330_pz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.metropolitan.cs330_pz.R;

public class HelpFragment extends Fragment {

    public HelpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.help, container, false);

        return view;
    }
}