package com.in.ernb.pixabayApp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.ernb.pixabayApp.AppPreferences;
import com.in.ernb.pixabayApp.R;
import com.in.ernb.pixabayApp.interfaces.splash.SplashPresenterInterface;
import com.in.ernb.pixabayApp.presenter.SplashPresenter;
import com.squareup.picasso.Picasso;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public class SplashActivity extends AppCompatActivity implements SplashPresenterInterface {


    private SplashPresenter presenter;
    private ImageView imageView;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private SharedPreferences sharedPreferences;
    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences = new AppPreferences(sharedPreferences);
        imageView =findViewById(R.id.wallpaper);
        checkForPermissions();
        presenter = new SplashPresenter(this);
        //Transfering control to presenter
        presenter.ini_(preferences);
    }


    @Override
    public void ErrorMessage() {
        Toast.makeText(getApplicationContext(),"Image Not Found",Toast.LENGTH_LONG).show();
    }

    @Override
    public void UpdateView(final String url) {
        int id = preferences.getIdCount();
        if(id <195899)
        preferences.incrementId(id+1);
        else{
            preferences.incrementId(195890);
        }
        Log.e("VIEW","\tURL :"+url);
        Glide.with(this).load(url).into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, SearchInputActivity.class);
                startActivity(i);
                finish();
            }
        }, 10000);
    }


    private void checkForPermissions() {
        if (!isPermissionsGranted()) {
            preferences.incrementId(195890);
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        }
    }

    private boolean isPermissionsGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate();
            }
        }
    }


}