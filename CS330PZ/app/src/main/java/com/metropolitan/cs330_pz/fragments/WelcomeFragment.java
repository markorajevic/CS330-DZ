package com.metropolitan.cs330_pz.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.metropolitan.cs330_pz.R;
import com.metropolitan.cs330_pz.db.DBHelper;

import java.io.ByteArrayInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeFragment extends Fragment {

    private AppCompatTextView name;
    private AppCompatTextView mail;

    private CircleImageView profileImageView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    static final int SELECT_PHOTO = 1;
    Bitmap thumbnail;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.welcome, container, false);
        dbHelper = new DBHelper(getContext());

        Bundle bundle = getArguments();

        String username = getActivity().getIntent().getStringExtra("NAME");
        final String email = getActivity().getIntent().getStringExtra("EMAIL");

        name = (AppCompatTextView) view.findViewById(R.id.welcomeName);
        name.setText(username);

        mail = (AppCompatTextView) view.findViewById(R.id.welcomeMail);
        mail.setText(email);

        profileImageView = (CircleImageView) view.findViewById(R.id.profile_image);

        byte[] photo = dbHelper.getImage(email);

        if(photo != null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            profileImageView.setImageBitmap(theImage);

        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
