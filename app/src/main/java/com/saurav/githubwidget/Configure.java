package com.saurav.githubwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by saurav.p on 2/9/2016.
 */
public class Configure extends Activity {
    Button btn1;
    public static EditText etx1;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;;
    public static String texttoset = null;
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d("Saurav", "s@urav Inside onCreate of Config Activity!");
        setResult(RESULT_CANCELED);
        setContentView(R.layout.config);
        //code to cancel if some AsyncTask is already running
        //Intent serviceIntent = new Intent(getApplicationContext(), FetchDataFromGithub.class);
        //serviceIntent.setAction("CLEARASYNC");
        //getApplicationContext().startService(serviceIntent);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        etx1 = (EditText) findViewById(R.id.editText);
        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = etx1.getText().toString();
                Log.d("Saurav", "s@urav Inside clickListener!"+value);
                AppWidgetProviderInfo appWidgetManager = AppWidgetManager.getInstance(getBaseContext()).getAppWidgetInfo(mAppWidgetId);/*AppWidgetManager.getInstance(getApplicationContext());*/
 /*              *//* RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(),
                        R.layout.example_appwidget);
                views.setTextViewText(R.id.text1,etx1.getText());*//*
                //WidActivity.updateAppWidget(getApplicationContext(),appWidgetManager, mAppWidgetId);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();*/
                // this intent is essential to show the widget
                // if this intent is not included,you can't show
                // widget on homescreen
                if (!value.isEmpty()) {
                    Log.d("Saurav", "s@urav Value is not null");
                    Intent intent = new Intent();
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(Activity.RESULT_OK, intent);

                    // start your service
                    // to fetch data from web
                    Intent serviceIntent = new Intent(getApplicationContext(), FetchDataFromGithub.class);
                    serviceIntent
                            .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    Log.d("Saurav", "s@urav TextToSetis:" + value);
                    serviceIntent.putExtra("editText", value);
                    serviceIntent.setAction("FETCHDATA");
                    Log.d("Saurav", "s@urav Starting Service to fetch Github Data!");
                    getApplicationContext().startService(serviceIntent);

                    // finish this activity
                    finish();

                }
                else{
                    Log.d("Saurav", "s@urav Value is null");
                    Toast.makeText(getApplicationContext(),"Enter Some Username!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
