package com.ozeksi.demogdg.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ozeksi.demogdg.AppConfig;
import com.ozeksi.demogdg.network.api.ApiInterface;
import com.ozeksi.demogdg.network.api.ApiService;
import com.ozeksi.demogdg.network.interceptor.BaseRequestInterceptor;
import com.ozeksi.demogdg.network.interceptor.HttpResponseInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceProvider {
    private ApiService apiService;


    private ObjectMapper provideJacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new BaseRequestInterceptor())
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(new HttpResponseInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private Retrofit provideRetrofitClient() {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(provideJacksonObjectMapper()))
                .baseUrl(AppConfig.ENDPOINT_CONFIG_BASE_URL)
                .client(provideOkHttpClient())
                .build();
    }

    public ApiService provideApiService() {
        if (apiService == null) {
            apiService = new ApiService(this.provideRetrofitClient().create(ApiInterface.class));
        }
        return apiService;
    }

    @NonNull
    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
