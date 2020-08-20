package com.in.ernb.pixabayApp.presenter;

import android.util.Log;

import com.in.ernb.pixabayApp.AppPreferences;
import com.in.ernb.pixabayApp.interfaces.splash.ModelToPresenter;
import com.in.ernb.pixabayApp.interfaces.splash.PresenterToModel;
import com.in.ernb.pixabayApp.interfaces.splash.SplashPresenterInterface;
import com.in.ernb.pixabayApp.model.SplashModel;

/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */


public class SplashPresenter implements ModelToPresenter {

    private PresenterToModel presenterInterface;
    private SplashPresenterInterface view;

    private SplashModel model;

    public SplashPresenter(PresenterToModel presenterInterface) {
        this.presenterInterface = presenterInterface;
    }

    public SplashPresenter(SplashPresenterInterface view) {
        this.view = view;
    }

    public void ini_(AppPreferences preferences) {
        model = new SplashModel(this);
        model.getwallpaperForSpalsh(preferences);

    }

    @Override
    public void transferDataToPresenter(String url) {
        Log.e("PRESENTER","\tURL IS:\t"+url);
        view.UpdateView(url);
    }

    @Override
    public void setErrorMessage() {
        view.ErrorMessage();
    }
}
