package com.example.ramesh_pc.madridista;

import android.app.ProgressDialog;
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


/*
pulls the data from various web pages and updates the textviews dynamically.
Scrollview is enabled for the textviews
 */

public class TableActivity extends AppCompatActivity {
   static String URL="http://www.marca.com/en/stats/football/laliga/league-table.html?cid=MENUMIGA35903&s_kw=table";
    static String URL1="http://www.marca.com/en/stats/football/laliga/2016_17/ranking.html";
    static  String url="http://messivsronaldo.net/msn-vs-bbc/";
   static String URLpl="http://www.goal.com/en-us/tables/premier-league/8";

    private  static ProgressDialog pd;
    public static ArrayList<String> getTable(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        Elements h1 = doc.body().getElementsByClass("equipo");
        Elements h2 = doc.body().getElementsByClass("pts");
        ArrayList<String> list= new ArrayList<>();
        for(int i=0;i<h1.size();i++){

            list.add(h1.get(i).text()+"-----"+h2.get(i+1).text());
        }

        return list;
    }
    public static ArrayList<String> getScorers(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        Elements h1 = doc.body().getElementsByClass("nombre");
        Elements h11 = doc.body().getElementsByClass("destacado");

        ArrayList<String> list= new ArrayList<>();
        for(int i=1;i<10;i++){
            list.add(h1.get(i).text()+"----"+h11.get(i-1).text());
        }
        return list;
    }
    public static ArrayList<String> getPLtable(String URL) throws Exception{
        Document doc = Jsoup.connect(URL).get();
        Elements h1 = doc.body().getElementsByTag("tr");
        ArrayList<String> list= new ArrayList<>();
        for(Element e:h1){
            String s=e.text();
            list.add(e.text());
        }
        return list;
    }
    public static ArrayList<String> getValues(ArrayList<String> list){
        ArrayList<String> listres= new ArrayList<>();
        for(int i=2;i<22;i++){
            String s=list.get(i);
            String r="";
            String[] parts=s.split(" ");
            if(isNumeric(parts[3])){
                r=parts[0]+"----"+parts[1]+"-----"+parts[3];}
            else if(isNumeric(parts[4])){
                r=parts[0]+"----"+parts[1]+" "+parts[2]+"-----"+parts[4];
            }else{
                r=parts[0]+"----"+parts[1]+" "+parts[2]+" "+parts[3]+"-----"+parts[5];
            }
            listres.add(r);

        }
        return listres;

    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public static ArrayList<String> getMSNvsBBC(String URL) throws  Exception{
        Document doc = Jsoup.connect(URL).get();
        Element h=doc.body();
         ArrayList<String> list= new ArrayList<>();
         Elements h11 = doc.body().getElementsByTag("tr");
        for(Element el:h11)
        {

            if(el.text().contains("All Competitions"))
            {
                String[] parts=el.text().split("All Competitions");
                String s1=parts[0];
                String s2=parts[1];
                String[] parts1=parts[0].split(" ");
                String[] parts2=parts[1].split(" ");
                String resultBBCG="GOALS--"+"Cristiano--"+parts2[1]+" Benzema--"+parts2[4]+" Bale--"+parts2[7];list.add(resultBBCG);
                String resultBBCA="ASSISTS--"+"Cristiano--"+parts2[2]+" Benzema--"+parts2[5]+" Bale--"+parts2[8];list.add(resultBBCA);
                String resultMSNG="GOALS--"+"Messi--"+parts1[8]+" Suarez--"+parts1[5]+" Neymar--"+parts1[2];list.add(resultMSNG);
                String resultMSNA="ASSISTS--"+"Messi--"+parts1[7]+" Suarez--"+parts1[4]+" Neymar--"+parts1[1];list.add(resultMSNA);
            }
            //System.out.println(el.text());
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
      new tables().execute();
    }
    private class tables extends AsyncTask<Void,Void,ArrayList<ArrayList<String>>> {
        Object object = new Object();
        ArrayList<ArrayList<String>> list = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(TableActivity.this, "", "Please Wait...Getting results!!", false);
        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... arg0) {
            try {

                ArrayList<String> lgtable = new ArrayList<>(getTable(URL));
                ArrayList<String> scorers = new ArrayList<>(getScorers(URL1));
                ArrayList<String> MSNvsBBC = new ArrayList<>(getMSNvsBBC(url));
                ArrayList<String> PLTABLE = new ArrayList<>(getValues(getPLtable(URLpl)));
                list.add(lgtable);
                list.add(scorers);
                list.add(MSNvsBBC);
                list.add(PLTABLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> list) {
            pd.dismiss();
            final int N = list.get(0).size(); // total number of textviews to add
            final int M = 5;
            Log.i("hello logs", N + " is the size");
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.info);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;
            final TextView[] newTextViews = new TextView[M];
            TextView tit = new TextView(getApplicationContext());
            tit.setText("----BBC vs MSN----");
            tit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tit.setTextColor(Color.BLUE);
            linearLayout.addView(tit);
            final int Ram = 4;
            for (int g = 0; g < Ram; g++) {
                final TextView rowTextram = new TextView(getApplicationContext());// set some properties of rowTextView or something

                rowTextram.setText(list.get(2).get(g));
                rowTextram.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowTextram.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowTextram);

                // save a reference to the textview for later
                newTextViews[g] = rowTextram;
            }

            TextView title = new TextView(getApplicationContext());
            title.setText("----TOP SCORERS----");
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title.setTextColor(Color.BLUE);

            // add the textview to the linearlayout
            linearLayout.addView(title);

            for (int r = 0; r < M; r++) {
                final TextView rowText = new TextView(getApplicationContext());  // set some properties of rowTextView or something

                rowText.setText(r + 1 + " ---- " + list.get(1).get(r));

                rowText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowText.setTextColor(Color.BLACK);
                // add the textview to the linearlayout
                linearLayout.addView(rowText);

                // save a reference to the textview for later
                newTextViews[r] = rowText;
            }
            TextView title1 = new TextView(getApplicationContext());
            title1.setText("----La Liga table----");
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
            TextView title11 = new TextView(getApplicationContext());
            title11.setText("----Premier League table----");
            title11.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            title11.setTextColor(Color.BLUE);

            // add the textview to the linearlayout
            linearLayout.addView(title11);
            for (int f = 0; f < list.get(3).size(); f++) {
                // create a new textview
                final TextView rowTextView1 = new TextView(getApplicationContext());  // set some properties of rowTextView or something

                rowTextView1.setText(list.get(3).get(f));


                rowTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                rowTextView1.setTextColor(Color.BLACK);

                // add the textview to the linearlayout
                linearLayout.addView(rowTextView1);
                myTextViews[f] = rowTextView1;
            }
        }

        }
    }

