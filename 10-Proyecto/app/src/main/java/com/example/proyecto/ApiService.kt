package com.example.proyecto

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    fun fetchData(@Url url: String?): Call<ApiResponse>?
}
