package com.ruthiefloats.asynctask;

import android.net.http.AndroidHttpClient;
import android.net.http.HttpResponseCache;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpManager {

    /**
     * @param uri the location of the feed on web
     * @return
     */
    public static String getData(String uri) {

        /*
        you had to back down to level 22 to follow along with the
        deprecated methods.  as of 23 you just plain can't use them
        the lesson has utility because now i'll understand legacy
        code and can more sensibly update deprecated code.
         */
        AndroidHttpClient client = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet request = new HttpGet(uri);
        HttpResponse response;

        try {
            /*
            response is whatever the client gets back after
            executing the request
             */
            response = client.execute(request);
            //turn response into a String
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //prevent leaks
            client.close();
        }
    }
}
