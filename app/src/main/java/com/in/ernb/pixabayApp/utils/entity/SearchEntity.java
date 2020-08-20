package com.in.ernb.pixabayApp.utils.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public class SearchEntity {

    @SerializedName("total")
    @Expose
    private Long total;

    @SerializedName("totalHits")
    @Expose
    private Long totalHits;

    @SerializedName("hits")
    @Expose
    private ArrayList<ItemEntity> items = new ArrayList<>();

    public Long getTotal() {
        return total;
    }

    public Long getTotalHits() {
        return totalHits;
    }

    public ArrayList<ItemEntity> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "total=" + total +
                ", totalHits=" + totalHits +
                ", items=" + items +
                '}';
    }
}
