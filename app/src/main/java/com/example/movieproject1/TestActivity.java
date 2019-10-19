package com.example.movieproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.movieproject1.utilities.ImageAdapter;
import com.example.movieproject1.utilities.MovieViewModel;

public class TestActivity extends AppCompatActivity implements ImageAdapter.ListItemClickListener {
    MovieViewModel model;
    TextView tx;
    RecyclerView mRV;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tx = findViewById(R.id.tv_test_livedata);
        mRV = findViewById(R.id.test_recyclerview);
        mRV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRV.setLayoutManager(layoutManager);


        model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getData().observe(this, data -> {
            tx.setText(model.getData().getValue().get(0).getTitle());
            mAdapter = new ImageAdapter(getApplicationContext(), data.size(), data, TestActivity.this);
            mRV.setAdapter(mAdapter);
        });


    }


    /**
     * //TODO the putExtra has a problem
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Class destinationActivity = DetailActivity.class;
        Intent intent = new Intent(TestActivity.this, destinationActivity);
        intent.putExtra("Movie", model.getData().getValue().get(clickedItemIndex));
        startActivity(intent);

    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_test, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {

            /*         * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)*/

            case R.id.popular_test:
                model.setData(1);
                mAdapter = new ImageAdapter(getApplicationContext(), model.getData().getValue().size(), model.getData().getValue(), TestActivity.this);
                mRV.setAdapter(mAdapter);
                return true;
            case R.id.top_rated_test:
                model.setData(2);
                mAdapter = new ImageAdapter(getApplicationContext(), model.getData().getValue().size(), model.getData().getValue(), TestActivity.this);
                mRV.setAdapter(mAdapter);
                return true;


        }

        return super.onOptionsItemSelected(item);//TODO why return?
    }

}
