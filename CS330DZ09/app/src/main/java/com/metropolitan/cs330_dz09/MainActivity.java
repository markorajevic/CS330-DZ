package com.metropolitan.cs330_dz09;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    private GoogleMap mMap;
    public static double h = 50;
    public static double w = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng kg = new LatLng(h,  w);
        Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());


        mMap.addMarker(new MarkerOptions().position(kg).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kg));

        System.out.println("Sirina:"+h);
        System.out.println("Duzina"+w);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.getUiSettings().setCompassEnabled(true);



        try {
            List<Address> adr;
            adr = gk.getFromLocation(h, w, 1);
            Address adresa = adr.get(0);

            String lokalitet = adresa.getLocality();
            String grad = adresa.getCountryName();
            String region = adresa.getCountryCode();
            String ulica = adresa.getFeatureName();

            StringBuilder ad = new StringBuilder();
            ad.append(lokalitet + " ");
            ad.append(grad + " ");
            ad.append(region + " ");
            ad.append(ulica + " ");

            Toast.makeText(getBaseContext(),ad.toString(),Toast.LENGTH_LONG).show();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {

            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);
        }



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.addMarker(new MarkerOptions().position(position));
                Geocoder gk = new Geocoder(getBaseContext(), Locale.getDefault());

                try {
                    List<Address> adr;
                    adr = gk.getFromLocation(position.latitude, position.longitude, 1);
                    Address adresa = adr.get(0);

                    String lokalitet = adresa.getLocality();
                    String grad = adresa.getCountryName();
                    String region = adresa.getCountryCode();
                    String ulica = adresa.getFeatureName();

                    StringBuilder ad = new StringBuilder();
                    ad.append(lokalitet + " ");
                    ad.append(grad + " ");
                    ad.append(region + " ");
                    ad.append(ulica + " ");

                    Toast.makeText(getBaseContext(),ad.toString(),Toast.LENGTH_LONG).show();

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClickBtn(View v) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText txtname = (EditText)findViewById(R.id.editText1);
        h      =  Double.parseDouble(txtname.getText().toString());

        EditText txtname2 = (EditText)findViewById(R.id.editText2);
        w      =  Double.parseDouble(txtname2.getText().toString());


    }


}

