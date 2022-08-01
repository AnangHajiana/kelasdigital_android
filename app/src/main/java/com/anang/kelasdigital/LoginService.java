package com.anang.kelasdigital;

import com.anang.kelasdigital.entity.Login;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface LoginService {
    @POST("api/user/login")
    Call<Login> login(@Body HashMap<String,Object> request);
}
