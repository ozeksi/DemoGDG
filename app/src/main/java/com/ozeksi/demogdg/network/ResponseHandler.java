package com.ozeksi.demogdg.network;

public interface ResponseHandler<T> {
    void success(T data);

    void fail(String code, String description);
}
