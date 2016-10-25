package com.example.ramesh_pc.madridista;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;

public class Updates extends AppCompatActivity {
 static String URL1="http://www.marca.com/en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        //View linearLayout =  findViewById(R.id.info);
        new news().execute();


    }


    private class news extends AsyncTask<Void,Void,ArrayList<String>> {
        Object object= new Object();
        ArrayList<String> list= new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(Void... arg0) {
          try {
              Document doc = Jsoup.connect(URL1).get();
              Elements h1 = doc.body().getElementsByClass("mod-title");
              for(int i=0;i<h1.size();i++){
                  list.add(h1.get(i).text());
              }


          }catch (Exception e){
              e.printStackTrace();
          }



            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
            final int N = list.size(); // total number of textviews to add
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.info);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;

            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(getApplicationContext());

                // set some properties of rowTextView or something
                rowTextView.setText(i+1+".  "+list.get(i));

                rowTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowTextView.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowTextView);

                // save a reference to the textview for later
                myTextViews[i] = rowTextView;
            }
        }
    }

}
