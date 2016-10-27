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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FixturesActivity extends AppCompatActivity {
  static  String URL="http://www.goal.com/en-us/fixtures/team/real-madrid/2016?ICID=TP_NMW_FT_1";
  static  String url="http://www.soccer24.com/team/real-madrid/W8mj7MDD/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
      new fixtures().execute();
    }


    private class fixtures extends AsyncTask<Void,Void,ArrayList<ArrayList<String>>> {
        Object object= new Object();
        ArrayList<ArrayList<String>> list= new ArrayList<>();

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... arg0) {
            try {

           ArrayList<String> opponents= new ArrayList<>(getAllOpponents(URL));
           ArrayList<String >dates= new ArrayList<>(getAllDates(URL));
           ArrayList<String> times = new ArrayList<>(getAllTimes(URL));
                ArrayList<String> players= new ArrayList<>(InjuredPlayersList(url));
                list.add(opponents);
                list.add(dates);
                list.add(times);
                list.add(players);
            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> list) {
            final int N = list.get(0).size(); // total number of textviews to add
            Log.i("hello logs",N+" is the size");
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.info);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;
            final int M= list.get(3).size();
            final TextView[] newTextViews= new TextView[M];
            TextView rowText1 = new TextView(getApplicationContext());
            rowText1.setText("----PLAYERS NOT AVAILABLE---- ");
            rowText1.setTextColor(Color.RED);
            linearLayout.addView(rowText1);


            for (int i = 0; i < M; i++) {
                // create a new textview
                final TextView rowText = new TextView(getApplicationContext());

                // set some properties of rowTextView or something

                rowText.setText(i + 1 + " :: " + list.get(3).get(i));

                rowText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowText.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowText);

                // save a reference to the textview for later
                myTextViews[i] = rowText;
            }
            TextView rowText2 = new TextView(getApplicationContext());
            rowText2.setText("-------FIXRURES------ ");
            rowText2.setTextColor(Color.RED);
            linearLayout.addView(rowText2);


            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(getApplicationContext());

                // set some properties of rowTextView or something
                rowTextView.setText(i+1 +" :: "+list.get(1).get(i)+" : "+list.get(2).get(i)+" : "+list.get(0).get(i));

                rowTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowTextView.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowTextView);

                // save a reference to the textview for later
                myTextViews[i] = rowTextView;
            }
        }
    }

    public static  ArrayList<String> InjuredPlayersList(String URL) throws  Exception{
        Document doc = Jsoup.connect(URL).get();
        Element h=doc.body();
        //Elements h1 = doc.body().getElementsByClass("").tagName("goles");
        ArrayList<String> list= new ArrayList<>();
        //for(int i=0;i<h1.size();i++){
        //list.add(h1.get(i).text());
        //}
        Elements h11 = doc.body().getElementsByClass("player-name");//.get(0).getElementsByClass("player");
        for(Element el:h11)
            if(el.getElementsByTag("span").size()>1)
                list.add(el.text());
        return  list;
    }
    public static ArrayList<String> getAllOpponents(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        ArrayList<String> dates= new ArrayList<>();
        Elements h11 = doc.body().getElementsByClass("team");
        for(int i=1;i<h11.size();i=i+2){
            if(h11.get(i).text().equals("Real Madrid")){
                String s=h11.get(i-1).text()+" (A)";
                dates.add(s);
            }else {
                String s=h11.get(i).text()+" (H)";
                dates.add(s);
            }
        }
        return dates;
    }
    public static ArrayList<String> getAllDates(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        //Element h1 = doc.body().getElementsByTag("th").get(0);
        ArrayList<String> dates= new ArrayList<>();
        Elements h111 = doc.body().getElementsByClass("comp-date");

        for(int j=0;j<h111.size();j++){
            //System.out.println(h111.get(j).text());
            dates.add(h111.get(j).text());
        }
        return dates;
    }

    public static ArrayList<String> getAllTimes(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        //Element h1 = doc.body().getElementsByTag("th").get(0);
        ArrayList<String> dates= new ArrayList<>();
        Elements h111 = doc.body().getElementsByClass("status");

        for(int j=0;j<h111.size();j++){
            //System.out.println(h111.get(j).text());
            dates.add(h111.get(j).text());
        }
        return dates;
    }

}
