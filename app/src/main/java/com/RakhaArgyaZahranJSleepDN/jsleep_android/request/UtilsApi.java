package com.RakhaArgyaZahranJSleepDN.jsleep_android.request;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * tempat untuk menyimpan data API yang akan diambil dari database
 */
public class UtilsApi {
    public static final String BASE_URL_API = "http://10.0.2.2:8080/";

    /**
     * method untuk mengambil data API
     * @return data API
     */
    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
