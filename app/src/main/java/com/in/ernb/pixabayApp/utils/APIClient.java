package com.in.ernb.pixabayApp.utils;


import com.in.ernb.pixabayApp.utils.SearchApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public class APIClient {

    private static final String BASE_URL = "http://pixabay.com/api/";

    public static SearchApi getSearchApi(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(SearchApi.class);
    }
}
