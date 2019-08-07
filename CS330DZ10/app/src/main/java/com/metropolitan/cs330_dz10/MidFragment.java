package com.metropolitan.cs330_dz10;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class MidFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.mid_fragment, container, false);

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        TextView txtView = (TextView)view.findViewById(R.id.vreme);
        txtView.setText("Current Date and Time : "+ formattedDate);

        return view;
    }
}
