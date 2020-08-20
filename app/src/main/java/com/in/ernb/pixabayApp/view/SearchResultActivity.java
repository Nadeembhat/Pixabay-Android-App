package com.in.ernb.pixabayApp.view;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.ernb.pixabayApp.AppPreferences;
import com.in.ernb.pixabayApp.interfaces.result.PresenterToView;
import com.in.ernb.pixabayApp.presenter.ResultPresenter;
import com.in.ernb.pixabayApp.utils.APIClient;
import com.in.ernb.pixabayApp.R;
import com.in.ernb.pixabayApp.utils.SearchApi;
import com.in.ernb.pixabayApp.utils.ItemAdapter;
import com.in.ernb.pixabayApp.utils.entity.ItemEntity;
import com.in.ernb.pixabayApp.utils.entity.SearchEntity;
import com.in.ernb.pixabayApp.interfaces.AdapterClickListener;
import com.in.ernb.pixabayApp.utils.StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */

public class SearchResultActivity extends AppCompatActivity implements PresenterToView {

    private TextView nothingFoundView;
    private RecyclerView recyclerView;
    private ArrayList<ItemEntity> items;
    private ImageButton favButton;
    private SharedPreferences preferences;
    private AppPreferences appPreferences;
    private ResultPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        preferences  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        appPreferences = new AppPreferences(preferences);
        favButton = findViewById(R.id.favbutton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        nothingFoundView = findViewById(R.id.txt_view_nothing_found);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra(SearchInputActivity.TAG_INPUT);
        query = StringConverter.getQueryString(query);

        String type = getIntent().getStringExtra(SearchInputActivity.TAG_TYPE);
        type = StringConverter.getImageTypeQuery(type);

        String category = getIntent().getStringExtra(SearchInputActivity.TAG_CATEGORY);
        category = StringConverter.getImageCategoryQuery(category);

        String orientation = getIntent().getStringExtra(SearchInputActivity.TAG_ORIENTATION);
        orientation = StringConverter.getImageOrientationQuery(orientation);

        presenter = new ResultPresenter(this);
        presenter.ini_( query, type, category, orientation);
    }

    private final AdapterClickListener adapterClickListener = new AdapterClickListener() {
        @Override
        public void onClick(int position, View view) {
            switch (view.getId()) {
                case R.id.thumbnail_view:
                    openPageInBrowser(position);
                    break;
                case R.id.action_download:
                    downloadImage(position);
                    break;
            }
        }
    };

    private void openPageInBrowser(int position) {
        String url = items.get(position).getPageURL();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        List<ResolveInfo> activities = getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities != null && activities.size() != 0) {
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.no_applications), Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadImage(int position) {
        String url = items.get(position).getWebformatURL();
        String name = StringConverter.getImageNameFromUrl(url);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);

        if (downloadManager != null) {
            downloadManager.enqueue(request);
        } else {
           // showErrorToast();
            showError();
        }
    }

    @Override
    public void updateView(ArrayList<ItemEntity> items) {
        ItemAdapter adapter = new ItemAdapter(items, adapterClickListener,appPreferences);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRecyclerViewVisibility(boolean status) {
        if(status){
            recyclerView.setVisibility(View.GONE);
            nothingFoundView.setVisibility(View.VISIBLE);
        }
    }
}
