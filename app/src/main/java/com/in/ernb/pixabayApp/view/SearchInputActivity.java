package com.in.ernb.pixabayApp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.in.ernb.pixabayApp.R;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public class SearchInputActivity extends AppCompatActivity {


    public static final String TAG_INPUT = "input";
    public static final String TAG_TYPE = "type";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_ORIENTATION = "orientation";
    private Spinner spinnerType;
    private Spinner spinnerCategory;
    private Spinner spinnerOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_input);


        spinnerType = findViewById(R.id.spinner_type);
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerOrientation = findViewById(R.id.spinner_orientation);

        findViewById(R.id.layout_spinner_type).setOnClickListener(listener);
        findViewById(R.id.layout_spinner_category).setOnClickListener(listener);
        findViewById(R.id.layout_spinner_orientation).setOnClickListener(listener);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setActivated(true);
        searchView.setOnQueryTextListener(queryTextListener);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_spinner_type:
                    spinnerType.performClick();
                    break;
                case R.id.layout_spinner_category:
                    spinnerCategory.performClick();
                    break;
                case R.id.layout_spinner_orientation:
                    spinnerOrientation.performClick();
                    break;
            }
        }
    };

    private final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            startSearchResultActivity(s);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    private void startSearchResultActivity(String input) {
        Intent intent = new Intent(SearchInputActivity.this, SearchResultActivity.class);
        intent.putExtra(TAG_INPUT, input);
        intent.putExtra(TAG_TYPE, spinnerType.getSelectedItem().toString());
        intent.putExtra(TAG_CATEGORY, spinnerCategory.getSelectedItem().toString());
        intent.putExtra(TAG_ORIENTATION, spinnerOrientation.getSelectedItem().toString());
        startActivity(intent);
    }

}
