package com.example.eqdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eqdetection.Models.UserCUD;
import com.example.eqdetection.WebClient.URL;
import com.example.eqdetection.WebClient.UserClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {

    private EditText et_firstName, et_lastName, et_userName, et_password, et_lat, et_lng;
    private Button btn_register, btn_goto_login;
    private Intent intentTo_Login;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_userName = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_password);
        et_lat = findViewById(R.id.et_lat);
        et_lng = findViewById(R.id.et_lng);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        btn_goto_login = findViewById(R.id.btn_goto_login);
        btn_goto_login.setOnClickListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        intentTo_Login = new Intent(UserRegistration.this, Login.class);
        getLastKnownLocation();
    }

    private void getLastKnownLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                et_lat.setText(""+lat);
                et_lng.setText(""+lng);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.btn_goto_login:
                startActivity(intentTo_Login);
                break;
        }
    }

    public void registerUser() {
        UserCUD user = new UserCUD(
                et_firstName.getText().toString(),
                et_lastName.getText().toString(),
                et_userName.getText().toString(),
                et_password.getText().toString(),
                et_lat.getText().toString(),
                et_lng.getText().toString(),
                false);

        Retrofit retrofit = URL.getInstance();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Void> call = userClient.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UserRegistration.this, "" + response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserRegistration.this, "" + t.getLocalizedMessage(), Toast.LENGTH_LONG).hashCode();
            }
        });
    }
}
