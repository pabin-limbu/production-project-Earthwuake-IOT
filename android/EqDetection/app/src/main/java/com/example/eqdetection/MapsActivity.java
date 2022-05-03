package com.example.eqdetection;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.eqdetection.Models.User;
import com.example.eqdetection.WebClient.URL;
import com.example.eqdetection.WebClient.UserClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loadUsersOnMap();
    }

    private void loadUsersOnMap() {

        UserClient userClient = URL.getInstance().create(UserClient.class);
        Call<List<User>> call = userClient.getAllUsers();
        //   Toast.makeText(MapsActivity.this,"hello bro :",Toast.LENGTH_LONG).show();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Toast.makeText(MapsActivity.this, "hello  :", Toast.LENGTH_LONG).show();
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                if (!response.isSuccessful()) {
                    //  Toast.makeText(MapsActivity.this, "fail :", Toast.LENGTH_LONG).show();
                    return;
                } else if (response.isSuccessful()) {

                    allUsers = response.body();
                    LatLng userLocation;


                    for (User user : allUsers) {
                        Toast.makeText(MapsActivity.this, "hello :" + user.getLat(), Toast.LENGTH_LONG).show();
                        userLocation = new LatLng(Double.parseDouble(user.getLat()),
                                Double.parseDouble(user.getLng()));
                        mMap.addMarker(new MarkerOptions()
                                .position(userLocation)
                                .title(user.getFirstName() + " " + user.getLastName()));
                    }

                }


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "fail :", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //    Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        loadUsersOnMap();


    }
}
