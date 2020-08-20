package com.in.ernb.pixabayApp.interfaces.result;

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


public interface PresenterToView {
    void updateView(ArrayList<ItemEntity> items);
    void showError();
    void setRecyclerViewVisibility(boolean status);
}
