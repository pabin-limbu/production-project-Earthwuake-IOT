package com.example.eqdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eqdetection.Models.LoginSignupResponse;
import com.example.eqdetection.WebClient.URL;
import com.example.eqdetection.WebClient.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements OnClickListener {

    EditText et_username_l, et_password_l;
    Button btn_login, btn_goto_register;

    Intent intentTo_UserRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username_l = findViewById(R.id.et_username_l);
        et_password_l = findViewById(R.id.et_password_l);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_goto_register = findViewById(R.id.btn_goto_register);
        btn_goto_register.setOnClickListener(this);

        intentTo_UserRegistration = new Intent(Login.this, UserRegistration.class);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginUser();
                break;
            case R.id.btn_goto_register:
                startActivity(intentTo_UserRegistration);
                break;
        }

    }

    private void loginUser() {
//        Intent intent = new Intent(Login.this, MapsActivity.class );
//        startActivity(intent);

        checkUser();


    }

    private void checkUser() {
        UserClient userClient = URL.getInstance().create(UserClient.class);
        String username = et_username_l.getText().toString().trim();
        String password = et_password_l.getText().toString().trim();

        Call<LoginSignupResponse> userCall = userClient.checkuser(username, password);
        userCall.enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                if (!response.isSuccessful()) {
                    return;

                } else if (response.body().getSuccess()) {
                    Intent intent = new Intent(Login.this, MapsActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
             Toast.makeText(Login.this,"" + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
