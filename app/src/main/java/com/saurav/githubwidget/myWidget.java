package com.saurav.githubwidget;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


public class myWidget extends AppWidgetProvider  {

	Context myContext;
	public static final String DATA_FETCHED = "com.saurav.githubwidget.DATA_FETCHED";
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		myContext = context;
		Log.d("Saurav", "s@urav Starting AsyncTask!");
		//new FetchGithubData().execute("https://api.github.com/users/"+Configure.etx1+"/starred");
	}

	@Override
	public void onDisabled(Context context) {
		FetchDataFromGithub.isDataFetched = false;
		super.onDisabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		FetchDataFromGithub.isDataFetched = false;
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Log.d("Saurav", "s@urav Inside OnUpdate of the Widget!");
		if(FetchDataFromGithub.isDataFetched) {
			final int N = appWidgetIds.length;
			for (int i = 0; i < N; i++) {
				Intent serviceIntent = new Intent(context, FetchDataFromGithub.class);
				serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						appWidgetIds[i]);
				context.startService(serviceIntent);
			}
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);


	}

	private RemoteViews updateListView(Context context, int appWidgetId) {
		Log.d("Saurav", "s@urav Inside updateListView! "+" Length of restDataname is:"+FetchDataFromGithub.restDataname.size());

		// which layout to show on widget
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		ComponentName thisWidget = new ComponentName(context, myWidget.class );
		//contents of first tab
//		Glide.with(context).load(FetchDataFromGithub.restDataurl.get(0), new );
/*		Glide.with(context)
				.load("https://www.google.es/images/srpr/logo11w.png")
				.asBitmap()
				.into(new SimpleTarget<Bitmap>(100,100) {
					@Override
					public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
						//remoteViews.setImageViewBitmap(R.id.imageView, resource); // Possibly runOnUiThread()
					}
				});*/
		remoteViews.setImageViewResource(R.id.imageView, R.drawable.github1);
		remoteViews.setTextViewText(R.id.heading, FetchDataFromGithub.restDataname.get(0));
		remoteViews.setTextViewText(R.id.content, FetchDataFromGithub.restDataurl.get(0));

		//contents of first tab
		remoteViews.setImageViewResource(R.id.imageView1, R.drawable.github1);
		remoteViews.setTextViewText(R.id.heading1,FetchDataFromGithub.restDataname.get(1));
		remoteViews.setTextViewText(R.id.content1,FetchDataFromGithub.restDataurl.get(1));

		//contents of first tab
		remoteViews.setImageViewResource(R.id.imageView2, R.drawable.github1);
		remoteViews.setTextViewText(R.id.heading2,FetchDataFromGithub.restDataname.get(2));
		remoteViews.setTextViewText(R.id.content2,FetchDataFromGithub.restDataurl.get(2));

		//contents of first tab
		remoteViews.setImageViewResource(R.id.imageView3, R.drawable.github1);
		remoteViews.setTextViewText(R.id.heading3,FetchDataFromGithub.restDataname.get(3));
		remoteViews.setTextViewText(R.id.content3,FetchDataFromGithub.restDataurl.get(3));

		//contents of first tab
		remoteViews.setImageViewResource(R.id.imageView4, R.drawable.github1);
		remoteViews.setTextViewText(R.id.heading4,FetchDataFromGithub.restDataname.get(4));
		remoteViews.setTextViewText(R.id.content4,FetchDataFromGithub.restDataurl.get(4));
		return remoteViews;
	}
	public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
		Log.d("Saurav", "s@urav Inside onReceive!"+intent.getAction());
        if (intent.getAction().equals(DATA_FETCHED)) {
			Log.d("Saurav", "s@urav Sending Manual Callback for Data Fetched!");
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            RemoteViews remoteViews = updateListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        //class to fetch Github data
/*	class FetchGithubData extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
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
			super.onPostExecute(result);
			try {
				jarray = new JSONArray(result);

				JSONArray json = jarray;
				
				for(int i=0;i<10;i++)
				{
					try{
						JSONObject c = json.getJSONObject(i);
						Log.d("Saurav",c.getString("name"));
						Log.d("Saurav",c.getString("full_name"));
						restDataname.add(c.getString("name"));
						restDataurl.add(c.getString("full_name"));
						
					}
					catch(Exception e)
					{
						Log.d("####","JSon Parsing Exception:"+e);
					}
				}

			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			// Log.d("####",result);
			Log.d("Saurav", "AsyncTask Over");
			updateWidgetView();
		}
	}*/
    }
}
