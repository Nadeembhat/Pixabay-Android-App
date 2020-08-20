package com.in.ernb.pixabayApp.model;

import android.util.Log;
import android.view.View;

import com.in.ernb.pixabayApp.interfaces.result.ModelToPresenter;
import com.in.ernb.pixabayApp.interfaces.result.PresenterToModel;
import com.in.ernb.pixabayApp.utils.APIClient;
import com.in.ernb.pixabayApp.utils.ItemAdapter;
import com.in.ernb.pixabayApp.utils.SearchApi;
import com.in.ernb.pixabayApp.utils.entity.ItemEntity;
import com.in.ernb.pixabayApp.utils.entity.SearchEntity;

import java.util.ArrayList;

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


public class ResultModel implements PresenterToModel {

    private ModelToPresenter modelToPresenter;
    private ArrayList<ItemEntity> items;

    public ResultModel(ModelToPresenter modelToPresenter) {
        this.modelToPresenter = modelToPresenter;
    }



    @Override
    public void getApiCall(String query, String type, String category, String orientation) {
        Log.e("Result getApiCall()","\tquery"+query+" , type"+type+" , Category:"+category+" , Orientation :"+orientation);
        SearchApi searchApi = APIClient.getSearchApi();
        Call<SearchEntity> searchEntities = searchApi
                .getSearchResult(query, type, category, orientation);
        searchEntities.enqueue(callback);
    }

    private final Callback<SearchEntity> callback = new Callback<SearchEntity>() {
        @Override
        public void onResponse(Call<SearchEntity> call, Response<SearchEntity> response) {
            if (response.isSuccessful()) {
                Log.e("Result getApiCall()","\t Response is Successfull");
                SearchEntity result = response.body();
                if (result != null) {
                    items = result.getItems();
                    if (items != null && items.size() > 0) {
                        modelToPresenter.getApiData(items);
                    } else {
                        modelToPresenter.responseNotFound(true);
                    }
                } else {
                    modelToPresenter.showerror();
                }
            } else {
                modelToPresenter.showerror();
            }
        }

        @Override
        public void onFailure(Call<SearchEntity> call, Throwable t) {
            t.printStackTrace();
            modelToPresenter.showerror();
        }
    };
}
