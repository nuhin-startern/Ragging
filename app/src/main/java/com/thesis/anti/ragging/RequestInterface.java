package com.thesis.anti.ragging;

import com.thesis.anti.ragging.models.ServerRequest;
import com.thesis.anti.ragging.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("ragging/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
