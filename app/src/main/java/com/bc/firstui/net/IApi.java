package com.bc.firstui.net;

import com.bc.firstui.net.response.LoginResponseEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author bc on 2018/10/11.
 */
public interface IApi {

    @GET("siropenapi/UserAccount/Login")
    Call<LoginResponseEntity> loginGet (@Query("userName") String userName, @Query("password") String password);

    @FormUrlEncoded
    @POST("siropenapi/UserAccount/Login/")
    Call<LoginResponseEntity> loginPost (@Field("userName") String userName, @Field("password") String password);
}

