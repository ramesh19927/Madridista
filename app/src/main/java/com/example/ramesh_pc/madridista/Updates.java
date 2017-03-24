package com.example.ramesh_pc.madridista;

import android.app.ProgressDialog;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Handler;

public class Updates extends AppCompatActivity {
    static String URL1 = "http://www.marca.com/en";
    private static ProgressDialog pdi;
    public static LinearLayout linearLayout;
    public static TextView rowTextView;
    public static ArrayList<String> list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        //View linearLayout =  findViewById(R.id.info);
        new news().execute();
    }


    private void runThread(final ArrayList<String> list) {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                linearLayout = (LinearLayout) findViewById(R.id.info);
                                linearLayout.removeAllViews();
                                rowTextView = new TextView(getApplicationContext());
                                int randomNum = ThreadLocalRandom.current().nextInt(0, list.size());


                                rowTextView.setText(list.get(randomNum));

                                rowTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                rowTextView.setTextColor(Color.BLACK);

                                // add the textview to the linearlayout
                                linearLayout.addView(rowTextView);


                            }
                        });
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        runThread(list);
                    }
                }
            }
        }.start();
    }


    private class news extends AsyncTask<Void, Void, ArrayList<String>> {
        Object object = new Object();
        ArrayList<String> list = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            pdi = ProgressDialog.show(Updates.this, "", "Please Wait...Getting results!!", false);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... arg0) {
            try {
                Document doc = Jsoup.connect("http://www.marca.com/en").get();
                ArrayList<String> realmadrid = new ArrayList<>();

                for (Element link : doc.select("a")) {
                    if (link.attr("href").contains("barcelona"))
                        realmadrid.add(link.attr("href"));
                    if (link.attr("href").contains("real-madrid"))
                        realmadrid.add(link.attr("href"));
                }
                HashSet<String> finalreal = new HashSet<>();
                for (int i = 3; i < realmadrid.size(); i++) {
                    Document realdoc = Jsoup.connect(realmadrid.get(i)).get();
                    Elements realstrings = realdoc.body().getElementsByTag("p");
                    String realstring = "";
                    for (Element el : realstrings) {
                        if (!el.text().contains("Unidad Editorial Información Deportiva") && !el.text().contains("Follow us"))
                            realstring = realstring + System.lineSeparator() + el.text();
                    }
                    //System.out.println(realstring);
                    finalreal.add(realstring);
                }
                list.addAll(finalreal);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return list;
        }


        @Override
        protected void onPostExecute(ArrayList<String> list) {
            pdi.dismiss();
            // runThread(list);


            final int N = list.size(); // total number of textviews to add
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.info);
            final TextView[] myTextViews = new TextView[N]; // create an empty array;

            for (int i = 0; i < N; i++) {
                // create a new textview
                final TextView rowTextView = new TextView(getApplicationContext());

                // set some properties of rowTextView or something
                rowTextView.setText(" [ " + (i + 1) + " ] " + System.lineSeparator() + list.get(i) + System.lineSeparator());

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


