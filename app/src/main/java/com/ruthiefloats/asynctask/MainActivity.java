package com.ruthiefloats.asynctask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruthiefloats.asynctask.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {

	/*
    For learning purposes, Ruthie, there's a TextView
	the updatedisplay method adds text that View.
	 */

    ProgressBar pb;
    List<Flower> flowerList;
    @SuppressWarnings("unused")
    protected static final String PHOTOS_BASE_URL =
            "http://services.hanselandpetal.com/photos/";

    public static final String ENDPOINT =
            "http://services.hanselandpetal.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_do_task) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            } else {
                Toast.makeText(MainActivity.this, "Network is not working", Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    private void requestData(String uri) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        FlowersAPI api = adapter.create(FlowersAPI.class);

        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                flowerList = flowers;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
//        MyTask myTask = new MyTask();
//        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
    }

    protected void updateDisplay() {

        if (flowerList != null) {

            RecyclerView rvFlowers = (RecyclerView) findViewById(R.id.rvFlowers);
            RVAdapter adapter = new RVAdapter(MainActivity.this, flowerList);

            rvFlowers.setAdapter(adapter);

            rvFlowers.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
    }
//        output.append(message + "\n");


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}