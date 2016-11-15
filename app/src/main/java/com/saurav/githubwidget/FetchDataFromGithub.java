package com.saurav.githubwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saurav.p on 3/1/2016.
 */
public class FetchDataFromGithub extends Service {
    public static ArrayList<String> restDataname = new ArrayList<String>();
    public static ArrayList<String> restDataurl = new ArrayList<String>();
    public static ArrayList<String> avatarurl = new ArrayList<String>();
    public static ArrayList<String> restLinkToOpen = new ArrayList<String>();
    private String FETCHDATA = "FETCHDATA";
    FetchGithubData asyncTaskInstance = new FetchGithubData();
    public static Boolean isDataFetched = false;

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            switch (intent.getAction()) {
                case "FETCHDATA":
                    Log.d("Saurav", "s@urav Actually Fetching Data from Service!");
                    if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
                        appWidgetId = intent.getIntExtra(
                                AppWidgetManager.EXTRA_APPWIDGET_ID,
                                AppWidgetManager.INVALID_APPWIDGET_ID);

                    Log.d("Saurav", "s@urav Inside onStartCommand of FetchGithubService!");
                    fetchGithubDataFromRest(intent);
                    break;


            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchGithubDataFromRest(Intent intent) {
        try{
        String fetchdata = "https://api.github.com/users/"+intent.getStringExtra("editText").toString()+"/starred";
        Log.d("Saurav", "s@urav Inside AsyncTask:"+fetchdata);
        asyncTaskInstance.execute(fetchdata);
        }
        catch(Exception e)
        {
            Log.d("Saurav","s@urav Null Pointer "+e);
        }
    }

    //class to fetch Github data
    class FetchGithubData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            Log.d("Saurav", "s@urav Inside onPreExecute of AsyncTask!");
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(String... values) {
            // TODO Auto-generated method stub
            Log.d("Saurav", "s@urav Inside onProgressUpdate of AsyncTask!");
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray jarray = null;
            if (result != null)
            {
                super.onPostExecute(result);
            try {
                jarray = new JSONArray(result);
                Log.d("Saurav", "s@urav jarray value is" + jarray.length());

                JSONArray json = jarray;

                for (int i = 0; i < 5; i++) {
                    try {
                        JSONObject c = json.getJSONObject(i);
                        //Log.d("Saurav", c.getString("name"));
                        //Log.d("Saurav", c.getString("full_name"));
                        Log.d("Saurav", c.getJSONObject("owner").getString("avatar_url"));
                        Log.d("Saurav", c.getJSONObject("owner").getString("html_url"));
                        restDataname.add(c.getString("name"));
                        restDataurl.add(c.getString("full_name"));
                        avatarurl.add(c.getJSONObject("owner").getString("avatar_url"));
                        restLinkToOpen.add(c.getJSONObject("owner").getString("html_url"));

                    } catch (Exception e) {
                        Log.d("####", "s@urav JSon Parsing Exception:" + e);
                    }
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "s@urav Error parsing data " + e.toString());
            }
            // Log.d("####",result);
            Log.d("Saurav", "AsyncTask Over");
            updateWidgetView();
        }
        }
    }
    public void updateWidgetView()
    {
        Log.d("Saurav", "Updating The widgetView!");
        Intent updateWidgetWithGitData = new Intent();
        updateWidgetWithGitData.setAction(myWidget.DATA_FETCHED);
        updateWidgetWithGitData.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        isDataFetched = true;
        sendBroadcast(updateWidgetWithGitData);
        this.stopSelf();


    }
}
