package com.ozeksi.demogdg.network.interceptor;

import com.ozeksi.demogdg.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().removeHeader("Pragma").build();// To avoid 504 Unsatisfiable Request (only-if-cached)

        if (Utils.isInternetAvailable()) {
            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
        } else {
//            request = request.newBuilder().header("Cache-Control", "public, only-if-cached").build();
            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
        }
        return chain.proceed(request);
    }
}
