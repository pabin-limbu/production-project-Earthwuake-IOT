package com.example.eqdetection.WebClient;

import com.example.eqdetection.Models.LoginSignupResponse;
import com.example.eqdetection.Models.User;
import com.example.eqdetection.Models.UserCUD;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserClient {

    @POST("users/signup")
    Call<Void> registerUser(@Body UserCUD user);

    //for login
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginSignupResponse> checkuser(@Field("username") String userName, @Field("password") String password);

    @GET("users")
    Call<List<User>> getAllUsers();
}