package com.saurav.githubwidget;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;


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
		Log.d("Saurav", "s@urav Inside updateListView! "+" Length of restDataname is:"+FetchDataFromGithub.restDataname.size()
		+" Avatar URL Length is:"+FetchDataFromGithub.avatarurl.size());
		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		ComponentName thisWidget = new ComponentName(context, myWidget.class );
		//remoteViews.setImageViewResource(R.id.imageView, R.drawable.github1);
		Log.d("saurav","s@urav AvatarURL[0]"+FetchDataFromGithub.avatarurl.get(0));
        String openuserrepo = "https://github.com/";
		//test code
		Intent linkToOpen = new Intent(Intent.ACTION_VIEW, Uri.parse("openuserrepo"+Configure.texttoset));
		PendingIntent pendingInt = PendingIntent.getActivity(context,0,linkToOpen,PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.rel1,pendingInt);
		Picasso.with(context)
				.load(Uri.parse(FetchDataFromGithub.avatarurl.get(0)))
				.resize(100,100)
		         .into(remoteViews,R.id.imageView,new int[] { appWidgetId });
		remoteViews.setTextViewText(R.id.heading, FetchDataFromGithub.restDataname.get(0));
		remoteViews.setTextViewText(R.id.content, FetchDataFromGithub.restDataurl.get(0));
		//remoteViews.setOnClickPendingIntent(R.id.rel2,pendingInt);

		//remoteViews.setImageViewResource(R.id.imageView1, R.drawable.github1);
		Log.d("saurav","s@urav AvatarURL[1]"+FetchDataFromGithub.avatarurl.get(1));
		Intent linkToOpen1 = new Intent(Intent.ACTION_VIEW, Uri.parse("openuserrepo"+Configure.texttoset));
		PendingIntent pendingInt1 = PendingIntent.getActivity(context,0,linkToOpen,PendingIntent.FLAG_UPDATE_CURRENT);
		Picasso.with(context)
				.load(Uri.parse(FetchDataFromGithub.avatarurl.get(1)))
				.resize(100,100)
				.into(remoteViews,R.id.imageView1,new int[] { appWidgetId });
		remoteViews.setTextViewText(R.id.heading1,FetchDataFromGithub.restDataname.get(1));
		remoteViews.setTextViewText(R.id.content1,FetchDataFromGithub.restDataurl.get(1));
		remoteViews.setOnClickPendingIntent(R.id.rel2,pendingInt1);

		//remoteViews.setImageViewResource(R.id.imageView2, R.drawable.github1);
		Log.d("saurav","s@urav AvatarURL[2]"+FetchDataFromGithub.avatarurl.get(2));
		Intent linkToOpen2 = new Intent(Intent.ACTION_VIEW, Uri.parse("openuserrepo"+Configure.texttoset));
		PendingIntent pendingInt2 = PendingIntent.getActivity(context,0,linkToOpen,PendingIntent.FLAG_UPDATE_CURRENT);
		Picasso.with(context)
				.load(Uri.parse(FetchDataFromGithub.avatarurl.get(2)))
				.resize(100,100)
				.into(remoteViews,R.id.imageView2,new int[] { appWidgetId });
		remoteViews.setTextViewText(R.id.heading2,FetchDataFromGithub.restDataname.get(2));
		remoteViews.setTextViewText(R.id.content2,FetchDataFromGithub.restDataurl.get(2));
		remoteViews.setOnClickPendingIntent(R.id.rel3,pendingInt2);

		//remoteViews.setImageViewResource(R.id.imageView3, R.drawable.github1);
		Log.d("saurav","s@urav AvatarURL[3]"+FetchDataFromGithub.avatarurl.get(3));
		Intent linkToOpen3 = new Intent(Intent.ACTION_VIEW, Uri.parse("openuserrepo"+Configure.texttoset));
		PendingIntent pendingInt3 = PendingIntent.getActivity(context,0,linkToOpen,PendingIntent.FLAG_UPDATE_CURRENT);
		Picasso.with(context)
				.load(Uri.parse(FetchDataFromGithub.avatarurl.get(3)))
				.resize(100,100)
				.into(remoteViews,R.id.imageView3,new int[] { appWidgetId });
		remoteViews.setTextViewText(R.id.heading3,FetchDataFromGithub.restDataname.get(3));
		remoteViews.setTextViewText(R.id.content3,FetchDataFromGithub.restDataurl.get(3));
		remoteViews.setOnClickPendingIntent(R.id.rel4,pendingInt3);
		//remoteViews.setImageViewResource(R.id.imageView4, R.drawable.github1);
		Log.d("saurav","s@urav AvatarURL[4]"+FetchDataFromGithub.avatarurl.get(4));
		Intent linkToOpen4 = new Intent(Intent.ACTION_VIEW, Uri.parse("openuserrepo"+Configure.texttoset));
		PendingIntent pendingInt4 = PendingIntent.getActivity(context,0,linkToOpen,PendingIntent.FLAG_UPDATE_CURRENT);
		Picasso.with(context)
				.load(Uri.parse(FetchDataFromGithub.avatarurl.get(4)))
				.resize(100,100)
				.into(remoteViews,R.id.imageView4,new int[] { appWidgetId });
		remoteViews.setTextViewText(R.id.heading4,FetchDataFromGithub.restDataname.get(4));
		remoteViews.setTextViewText(R.id.content4,FetchDataFromGithub.restDataurl.get(4));
		remoteViews.setOnClickPendingIntent(R.id.rel5,pendingInt4);
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
    }
}
