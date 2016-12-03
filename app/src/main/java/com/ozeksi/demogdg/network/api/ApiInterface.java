package com.ozeksi.demogdg.network.api;

import com.ozeksi.demogdg.network.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("login")
    Call<ResponseBody> login();

    @GET("register")
    Call<ResponseBody> register();

    @GET("forgot-password")
    Call<ResponseBody> forgotPassword();

    @GET("test")
    Call<ResponseBody> test();

    @GET("user")
    Call<User> getUser();
}
