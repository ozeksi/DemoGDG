package com.ozeksi.demogdg.network.api;

import com.ozeksi.demogdg.network.ResponseHandler;
import com.ozeksi.demogdg.network.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {
    ApiInterface apiInterface;

    public ApiService(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void getUser(final ResponseHandler<User> responseHandler) {
        apiInterface.getUser().enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            responseHandler.success(response.body());
                        } else {
                            responseHandler.fail("-1", "Fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        responseHandler.fail("-1", "Fail");
                    }

                }
        );
    }

    public void test(final ResponseHandler<ResponseBody> responseHandler) {
        apiInterface.test().enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            responseHandler.success(response.body());
                        } else {
                            responseHandler.fail("-1", "Fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        responseHandler.fail("-1", "Fail");
                    }

                }
        );
    }
}
