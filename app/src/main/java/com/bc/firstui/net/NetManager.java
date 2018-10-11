package com.bc.firstui.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络管理
 *
 * @author bc on 2018/10/11.
 */
public class NetManager {

    public static final String BASE_URL = "http://192.168.5.248:30090/siropenapi/";
    private static volatile NetManager mInstance;
    private Retrofit mRetrofit;

    private NetManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetManager getInstance() {
        if (mInstance == null) {
            synchronized (NetManager.class) {
                if (mInstance == null) {
                    mInstance = new NetManager();
                }
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
