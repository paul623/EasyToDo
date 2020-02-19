package com.paul.easytodo.RequestAPI;

import com.paul.easytodo.DataSource.WordsBean;
import com.paul.easytodo.domain.WordsAllResult;
import com.paul.easytodo.domain.WordsResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WordsAPI {
    @GET("words/get")
    Call<WordsResult> getTodayWords();

    @GET("words/getAll")
    Call<WordsAllResult> getAllWords();
}
