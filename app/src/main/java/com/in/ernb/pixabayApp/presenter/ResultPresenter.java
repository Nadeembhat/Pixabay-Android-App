package com.in.ernb.pixabayApp.presenter;

import android.util.Log;

import com.in.ernb.pixabayApp.interfaces.result.ModelToPresenter;
import com.in.ernb.pixabayApp.interfaces.result.PresenterToModel;
import com.in.ernb.pixabayApp.interfaces.result.PresenterToView;
import com.in.ernb.pixabayApp.model.ResultModel;
import com.in.ernb.pixabayApp.utils.entity.ItemEntity;

import java.util.ArrayList;

/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */


public class ResultPresenter implements ModelToPresenter {

    PresenterToView  presenterToView;
    PresenterToModel presenterToModel;
    private ResultModel model;


    public ResultPresenter(PresenterToView presenterToView) {
        this.presenterToView = presenterToView;
    }

    public void ini_(String query, String type, String category, String orientation) {
        Log.e("Result ini_()","\t");
        model = new ResultModel(this);
        model.getApiCall(query,type,category,orientation);
    }

    @Override
    public void getApiData(ArrayList<ItemEntity> items) {
        presenterToView.updateView(items);
    }

    @Override
    public void responseNotFound(boolean status) {
        presenterToView.setRecyclerViewVisibility(true);
    }

    @Override
    public void showerror() {
        presenterToView.showError();
    }
}
