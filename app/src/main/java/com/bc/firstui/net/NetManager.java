package com.bc.firstui.net;

import com.bc.firstui.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络管理
 *
 * @author bc on 2018/10/11.
 */
public class NetManager {

    public static final String BASE_URL = "http://192.168.5.248:30090/";
    private static volatile NetManager mInstance;
    private Retrofit mRetrofit;

    private NetManager() {

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

    public void init() {
        mRetrofit = new Retrofit.Builder()
                //baseUrl一定要以/结尾
                .baseUrl(BASE_URL)
                //这个是用来决定你的返回值是Observable还是Call。如果返回为Call那么可以不添加这个配置。如果使用Observable那就必须添加这个配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initHttpClient())
                .build();
    }

    private OkHttpClient initHttpClient() {
        OkHttpClient httpClient = new OkHttpClient()
                .newBuilder()
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("token", MyApplication.Token);
                        return chain.proceed(builder.build());
                    }
                })*/
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        return httpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
