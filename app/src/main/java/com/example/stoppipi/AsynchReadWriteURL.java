package com.byanhor.stoppipi;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AsynchReadWriteURL extends AsyncTask<String,String,String> {

    String result = "";
    boolean dateAlreadyInTheDatabase = false;
    private Context mContext;
    private View mRootView;
    private String mSelectedDate;

    public AsynchReadWriteURL(Context context, View rootView, String selectedDate){
        this.mContext = context;
        this.mRootView = rootView;
        this.mSelectedDate = selectedDate;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... URLArr) {
        String link = "https://raw.githubusercontent.com/byAnhor/StopPipi/master/database.csv";
        try {

            URL linkURL = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(linkURL.openStream()));
            String inputLine;
            dateAlreadyInTheDatabase = false;
            result = mSelectedDate;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
                if (inputLine.startsWith(mSelectedDate)) dateAlreadyInTheDatabase = true;
            }
            in.close();
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        TextView textViewDebug = (TextView)this.mRootView.findViewById(R.id.textViewDebug);
        textViewDebug.setText(result);
        LinearLayout linearLayoutIcons = (LinearLayout)this.mRootView.findViewById(R.id.linearLayoutIcons);
        linearLayoutIcons.setEnabled(!dateAlreadyInTheDatabase);
        linearLayoutIcons.setAlpha(!dateAlreadyInTheDatabase ? 1.0f : 0.1f);
        linearLayoutIcons.setClickable(!dateAlreadyInTheDatabase);
    }

    /*@Override
    protected void onProgressUpdate(Integer... a) {
    }*/
}
