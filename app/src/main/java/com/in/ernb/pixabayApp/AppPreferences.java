package com.in.ernb.pixabayApp;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */


public class AppPreferences {

   private SharedPreferences sharedPreferences;

    public AppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setFavImage(Long  id,boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id",id);
        editor.putBoolean("liked", status); // value to store
        editor.apply();

    }

    public Map<Long, Boolean> getId() {
        Map<Long ,Boolean> map = new HashMap<>();
        Long id = sharedPreferences.getLong("id",0);
        boolean status = sharedPreferences.getBoolean("liked",false);
        map.put(id,status);
        return map;
    }


    public void incrementId(int id){
        sharedPreferences.edit().putInt("increment",id).apply();
    }
    public int getIdCount(){
       return sharedPreferences.getInt("increment",0);
    }


}
