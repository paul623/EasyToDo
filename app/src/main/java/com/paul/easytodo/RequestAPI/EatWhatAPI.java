package com.paul.easytodo.RequestAPI;

import com.paul.easytodo.domain.EatWhatResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EatWhatAPI {
    @GET("eat/get")
    Call<EatWhatResult> getEatWhat();
}
