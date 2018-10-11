package com.bc.firstui.net;

import com.bc.firstui.net.response.LoginResponseEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author bc on 2018/10/11.
 */
public interface IApi {
    @GET("/UserAccount/Login")
    Call<LoginResponseEntity> login (@Query("userName") String userName, @Query("password") String password);
}
