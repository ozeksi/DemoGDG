package com.ozeksi.demogdg.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseRequestInterceptor implements Interceptor {

    private static final String X_ARC_ENV = "X-ARC-ENV";
    private static final String ENVIRONMENT = "android";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.addHeader(X_ARC_ENV, ENVIRONMENT);
        requestBuilder.addHeader("temp", "gdg-ankara");
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
