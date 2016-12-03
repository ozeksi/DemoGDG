package com.ozeksi.demogdg.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: ozkaneksi
 * Date: 29/09/16
 */

public class HttpResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Response originalResponse = chain.proceed(original);
        if (originalResponse.isSuccessful()) {
            return originalResponse;
        } else {
            return originalResponse;
        }
    }
}
