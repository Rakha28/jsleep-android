package com.RakhaArgyaZahranJSleepDN.jsleep_android.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * tempat untuk menyimpan data RetrofitClient yang akan diambil dari database
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    /**
     * untuk get RetrofitClient
     * @param baseUrl url dari database
     * @return retrofit
     */
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();   
        }
        return retrofit;
    }

}
