package com.example.ramesh_pc.madridista;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
   static String URL="http://www.marca.com/en/stats/football/laliga/league-table.html?cid=MENUMIGA35903&s_kw=table";
    static String URL1="http://www.marca.com/en/stats/football/laliga/2016_17/ranking.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
      new tables().execute();
    }
    private class tables extends AsyncTask<Void,Void,ArrayList<ArrayList<String>>> {
        Object object= new Object();
        ArrayList<ArrayList<String>> list= new ArrayList<>();

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... arg0) {
            try {

                ArrayList<String> lgtable= new ArrayList<>(getTable(URL));
                ArrayList<String >scorers= new ArrayList<>(getScorers(URL1));
                list.add(lgtable);
               list.add(scorers);

            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> list) {
            final int N = list.get(0).size(); // total number of textviews to add
            final int M=5;
            Log.i("hello logs", N + " is the size");
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.info);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;
            final TextView[] newTextViews = new TextView[M];
            TextView title= new TextView(getApplicationContext());
            title.setText("----TOP SCORERS----");
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title.setTextColor(Color.BLUE);

            // add the textview to the linearlayout
            linearLayout.addView(title);

            for(int r=0;r<M;r++){
                final TextView rowText = new TextView(getApplicationContext());  // set some properties of rowTextView or something

                rowText.setText(r + 1 + " ---- " + list.get(1).get(r));


                rowText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowText.setTextColor(Color.RED);

                // add the textview to the linearlayout
                linearLayout.addView(rowText);

                // save a reference to the textview for later
                newTextViews[r] = rowText;
            }
            TextView title1= new TextView(getApplicationContext());
            title1.setText("----LEAGUE TABLE----");
            title1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title1.setTextColor(Color.BLUE);

            // add the textview to the linearlayout
            linearLayout.addView(title1);
            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(getApplicationContext());  // set some properties of rowTextView or something

                    rowTextView.setText(i + 1 + " ---- " + list.get(0).get(i));


                rowTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowTextView.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowTextView);

                // save a reference to the textview for later
                myTextViews[i] = rowTextView;
            }
        }
    }
    public static ArrayList<String> getTable(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        Elements h1 = doc.body().getElementsByClass("equipo");
        ArrayList<String> list= new ArrayList<>();
        for(int i=0;i<h1.size();i++){
            list.add(h1.get(i).text());
        }
        return list;
    }
    public static ArrayList<String> getScorers(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        Elements h1 = doc.body().getElementsByClass("nombre");
        ArrayList<String> list= new ArrayList<>();
        for(int i=1;i<10;i++){
            list.add(h1.get(i).text());
        }
        return list;
    }

}
