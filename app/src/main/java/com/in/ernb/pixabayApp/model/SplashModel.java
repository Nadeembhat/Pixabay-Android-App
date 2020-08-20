package com.in.ernb.pixabayApp.model;

import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.in.ernb.pixabayApp.AppPreferences;
import com.in.ernb.pixabayApp.interfaces.splash.ModelToPresenter;
import com.in.ernb.pixabayApp.interfaces.splash.PresenterToModel;
import com.in.ernb.pixabayApp.presenter.SplashPresenter;
import com.in.ernb.pixabayApp.utils.APIClient;
import com.in.ernb.pixabayApp.utils.ItemAdapter;
import com.in.ernb.pixabayApp.utils.SearchApi;
import com.in.ernb.pixabayApp.utils.entity.SearchEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */


public class SplashModel implements PresenterToModel {
    private ModelToPresenter modelToPresenter;
    /*private SplashPresenter presenter;
    private PresenterToModel presenterToModel;*/
    private AppPreferences mPref;


    public SplashModel(ModelToPresenter model) {
        this.modelToPresenter = model;
    }


    @Override
    public void getwallpaperForSpalsh(AppPreferences preferences) {
        //presenter = new SplashPresenter(this);
        this.mPref = preferences;
        Log.e("MODEL", "\t");
        int id = mPref.getIdCount();
        SearchApi searchApi = APIClient.getSearchApi();
        Call<JsonElement> wallpaperImage = searchApi
                .getWallpaperImage("yellow+flowers", "photo", id);

        wallpaperImage.enqueue(callback);

    }

    private final Callback<JsonElement> callback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            JSONObject jsonObject = null;
            if (response.isSuccessful()) {
                Log.e("MODEL", "\t Response Sucess");
                JsonElement result = response.body();
                Log.e("MODEL", "\t Result \t" + result);
                String str = result.toString();
                Log.e("MODEL", "\t String \t" + str);

                try {
                    jsonObject = new JSONObject(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonObject != null) {
                    try {
                        int id = mPref.getIdCount();
                        if (id < 195899)
                            mPref.incrementId(id + 1);
                        else
                            mPref.incrementId(195890);
                        String imageUrl = parseresult(jsonObject);
                        Log.e("MODEL", "\t Image Url\t" + imageUrl);
                        modelToPresenter.transferDataToPresenter(imageUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    modelToPresenter.setErrorMessage();
                }
            } else {
                modelToPresenter.setErrorMessage();
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            t.printStackTrace();
            modelToPresenter.setErrorMessage();
        }
    };

    private String parseresult(JSONObject result) throws JSONException {
        JSONArray array = result.getJSONArray("hits");
        Log.e("MODEL", "\tARRAY is:\t" + array.toString());
        Log.e("MODEL", "\tString is:\t" + result.getJSONArray("hits").getJSONObject(0).getString("largeImageURL"));
        return result.getJSONArray("hits").getJSONObject(0).getString("largeImageURL");
    }

}
