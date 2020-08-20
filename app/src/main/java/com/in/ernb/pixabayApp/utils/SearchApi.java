package com.in.ernb.pixabayApp.utils;


import com.google.gson.JsonElement;
import com.in.ernb.pixabayApp.utils.entity.SearchEntity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public interface SearchApi {

    @GET("?key=7446566-3a89da866024fd9f5d4ae9572&per_page=200")
    Call<SearchEntity> getSearchResult(@Query("q") String query,
                                       @Query("image_type") String type,
                                       @Query("category") String category,
                                       @Query("orientation") String orientation);

    @GET("?key=7446566-3a89da866024fd9f5d4ae9572")
    Call<JsonElement> getWallpaperImage(@Query("q") String query,
                                        @Query("image_type") String type,
                                        @Query("id") int id);

}
