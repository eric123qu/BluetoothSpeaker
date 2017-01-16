package com.haier.ai.bluetoothspeaker.manager;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.net.AIApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: qu
 * date: 17-1-13
 * introduce:
 */

public class RetrofitApiManager {
    private final String TAG = "RetrofitApiManager";

    public static AIApiService getAiApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Const.BASE_URL)
                .client(genericClient())
                .build();

        return retrofit.create(AIApiService.class);
    }

    public static OkHttpClient genericClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("appId", "AI-LIAPI-0000")
                                .addHeader("appVersion", "2016060401")
                                .addHeader("clientId", "356877020056553-08002700DC94")
                                .addHeader("sequenceId", "08002700DC94-15110519074300001")
                                .addHeader("sign", "bd4495183b97e8133aeab2f1916fed41")
                                .addHeader("timestamp", "1446639090139")
                                .addHeader("language", "zh-cn")
                                .addHeader("timezone", "8")
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        return okHttpClient;
    }
}
