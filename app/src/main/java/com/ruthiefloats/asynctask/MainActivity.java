package com.ruthiefloats.asynctask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ruthiefloats.asynctask.model.Flower;
import com.ruthiefloats.asynctask.parsers.JSONParser;
import com.ruthiefloats.asynctask.parsers.XMLParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	/*
    For learning purposes, Ruthie, there's a TextView
	the updatedisplay method adds text that View.
	 */

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//		Initialize the TextView for vertical scrolling
        output = (TextView) findViewById(R.id.textView);
        output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<MyTask>();
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
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");
            } else {
                Toast.makeText(MainActivity.this, "Network is not working", Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    private void requestData(String uri) {
        MyTask myTask = new MyTask();
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
    }

    protected void updateDisplay() {
        if (flowerList != null){
            for (Flower flower : flowerList) {
                output.append(flower.getName() + "\n");

            }
        }
//        output.append(message + "\n");
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private class MyTask extends AsyncTask<String, String, String> {

        //This method has access to main thread.
        //Executed before doinbackground.
        @Override
        protected void onPreExecute() {

//            updateDisplay("Starting task");
            /*
            if the list of tasks is empty, make bar visible
            (otherwise, if the list isn't empty, the bar is already visible)
             */
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            /*
            add the current task to the list
             */
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... strings) {

            String content = HttpManager.getData(strings[0]);
            return content;
        }

        //Has access to main thread.
        @Override
        protected void onPostExecute(String s) {
            flowerList = JSONParser.parseFeed(s);


            updateDisplay();
            /*
            remove this task from the list
             */
            tasks.remove(this);
            /*
            if the list is now empty, disappear the bar
             */
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}