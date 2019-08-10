package com.metropolitan.cs330_pz.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.metropolitan.cs330_pz.db.DBHelper;
import com.metropolitan.cs330_pz.R;
import com.metropolitan.cs330_pz.db.DBHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class MovieFragment extends Fragment {

    EditText edtName, edtGenre, edtDescription, edtRating;
    Button btnAdd, btnList;
    ImageView movieImageView;

    public static DBHelper DBHelper;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Novi film");

        edtName = getView().findViewById(R.id.edtName);
        edtGenre = getView().findViewById(R.id.edtGenre);
        edtDescription = getView().findViewById(R.id.edtDescription);
        edtRating = getView().findViewById(R.id.edtRating);
        btnAdd = getView().findViewById(R.id.btnAdd);
        btnList = getView().findViewById(R.id.btnList);
        movieImageView = getView().findViewById(R.id.edtImg);

        DBHelper = new DBHelper(getActivity());

        movieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DBHelper.insertMovie(
                            edtName.getText().toString().trim(),
                            edtGenre.getText().toString().trim(),
                            edtDescription.getText().toString().trim(),
                            Integer.parseInt(edtRating.getText().toString()),
                            imageViewToByte(movieImageView)
                    );
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtGenre.setText("");
                    edtDescription.setText("");
                    edtRating.setText("");
                    movieImageView.setImageResource(R.drawable.ic_insert_image);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MoviesListActivity.class));
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getContext(), "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getContext(), MovieFragment.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK){
                Uri resultUri = result.getUri();
                movieImageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
