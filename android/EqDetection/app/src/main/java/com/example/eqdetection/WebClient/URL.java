package com.example.eqdetection.WebClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URL {
   private final static String BASE_URI  = "http://10.0.2.2:3000/";
   // private final static String BASE_URI  = "http://127.0.0.1:3000/";


    public final static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
