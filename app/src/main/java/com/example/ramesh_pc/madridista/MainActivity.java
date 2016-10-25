package com.example.ramesh_pc.madridista;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import  com.example.ramesh_pc.madridista.Object;
public class MainActivity extends AppCompatActivity {
    Button b1;
    CountDownTimer countDownTimer;
    private ProgressBar spinner;
    TextView textView;
    TextView textView1;
    CountDownTimer mCountDownTimer;
    long mInitialTime = DateUtils.DAY_IN_MILLIS * 0+
            DateUtils.HOUR_IN_MILLIS * 0 +
            DateUtils.MINUTE_IN_MILLIS * 0 +
            DateUtils.SECOND_IN_MILLIS * 20;
    StringBuilder time = new StringBuilder();
     static Calendar c=Calendar.getInstance();
     static int  currmin; static int  currsec;
    static int  currhour;

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            b1 = (Button) findViewById(R.id.button);
            spinner = (ProgressBar) findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);
         textView1= (TextView) findViewById(R.id.textview);
        textView=(TextView)findViewById(R.id.textView);
       new ProgressTask().execute();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    startTimer(23);
//                   try{
//                       textView1.setText(getAllOpponents());
//
//                   }catch (Exception e){
//                      e.printStackTrace();
//                       Log.i("LOGGED",e.toString());
//                  }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////
    private class ProgressTask extends AsyncTask<Void,Void,Object> {
        String stringres="";
        Object object= new Object();
      ArrayList<Integer> list= new ArrayList<>();
       int l=0;
        String s;
        int min;
        int hour;
        int result;
        @Override
        protected Object doInBackground(Void... arg0) {
            try{
            stringres =getAllOpponents();
                 object.setName(stringres);
                if(getAllTimes().get(0).contains("PM")){
                    s=getAllTimes().get(0).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    //System.out.println(s);
                    l=Integer.valueOf(s)+1200;
                }else if(getAllTimes().get(0).contains("AM")){
                    s=getAllTimes().get(0).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    l=Integer.valueOf(s);
                }
                Calendar c=Calendar.getInstance();
                min=c.get(Calendar.MINUTE);
                hour=c.get(Calendar.HOUR);
                result=hour*100+min;
                list.add(l-result);
                object.setId(list);


            }
            catch (Exception e){
                e.printStackTrace();
            }
        return object;
        }

        @Override
        protected void onPostExecute(Object object) {
            spinner.setVisibility(View.GONE);

            stringres=object.getName();
            mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
                @Override
                public void onFinish() {
                    //textView.setText(DateUtils.formatElapsedTime(0));
                    textView.setText("Now Playing!!");
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    time.setLength(0);
                    // Use days if appropriate
                    if(millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                        long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
                        if(count > 1)
                            time.append(count).append(" days ");
                        else
                            time.append(count).append(" day ");

                        millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
                    }

                    time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
                    textView.setText(time.toString());
                }
            }.start();

            textView1.setText(stringres);
        }
    }

    public static ArrayList<String> getAllDates() throws Exception{
        Document doc = Jsoup.connect("http://www.goal.com/en-us/fixtures/team/real-madrid/2016?ICID=TP_NMW_FT_1").get();
        //Element h1 = doc.body().getElementsByTag("th").get(0);
        ArrayList<String> dates= new ArrayList<>();
        Elements h111 = doc.body().getElementsByClass("comp-date");

        for(int j=0;j<h111.size();j++){
            //System.out.println(h111.get(j).text());
            dates.add(h111.get(j).text());
        }
        return dates;
    }

    public static ArrayList<String> getAllTimes() throws Exception{
        Document doc = Jsoup.connect("http://www.goal.com/en-us/fixtures/team/real-madrid/2016?ICID=TP_NMW_FT_1").get();
        //Element h1 = doc.body().getElementsByTag("th").get(0);
        ArrayList<String> dates= new ArrayList<>();
        Elements h111 = doc.body().getElementsByClass("status");

        for(int j=0;j<h111.size();j++){
            //System.out.println(h111.get(j).text());
            dates.add(h111.get(j).text());
        }
        currmin = c.get(Calendar.MINUTE);
        currhour=c.get(Calendar.HOUR);
        currsec=c.get(Calendar.SECOND);


        return dates;
    }
    public static String getAllOpponents() throws Exception{
        Document doc = Jsoup.connect("http://www.goal.com/en-us/fixtures/team/real-madrid/2016?ICID=TP_NMW_FT_1").get();
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
        return dates.get(0);
    }


}
