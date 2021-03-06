package com.example.ramesh_pc.madridista;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {
    Button b1;public static int SECONDS_IN_A_DAY = 24 * 60 * 60;
    Button b2; Button b3;
    CountDownTimer countDownTimer;
    private ProgressBar spinner;
    TextView textView;
    TextView textView1;
    TextView copyright;
   // static boolean shallI=true;
    static Calendar c= Calendar.getInstance();

    static  int currsec=c.get(Calendar.SECOND);

    static  int currmin=c.get(Calendar.MINUTE);

    static  int currhour=c.get(Calendar.HOUR);

    static  int currmon=c.get(Calendar.MONTH)+1;
    static  int curryear=c.get(Calendar.YEAR);
    static  int currday=c.get(Calendar.DATE);

    static int matchDay=0;
    static int matchMonth=0;
    static int matchYear=0;
    CountDownTimer mCountDownTimer;
     StringBuilder time = new StringBuilder();
 static  String URL="";
    static ImageView imageView;
    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        /*
        ezpuzzle:: This is the key to get the Team that is selected from the SelectActivity

         */
         URL = intent.getExtras().getString("epuzzle");
imageView=(ImageView)findViewById(R.id.imageView);
        if(URL.contains("barcelona")){
            imageView.setImageResource(R.drawable.barca);
        } if(URL.contains("liverpool")){
            imageView.setImageResource(R.drawable.liverpool);

        } if(URL.contains("atl%C3%A9tico-madrid")){
            imageView.setImageResource(R.drawable.atletico);

        } if(URL.contains("real-madrid")){
            imageView.setImageResource(R.drawable.real);

        }
            b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button1);
         b3=(Button) findViewById(R.id.button2);
        //spinner = (ProgressBar) findViewById(R.id.progressBar);
           // spinner.setVisibility(View.GONE);
         textView1= (TextView) findViewById(R.id.textview);
        textView=(TextView)findViewById(R.id.textView);

      if(isNetworkAvailable()) {
          new ProgressTask().execute();
      }else{
          Toast.makeText(getApplicationContext(),"There is no internet connection...Please check the connection!!",Toast.LENGTH_LONG).show();
      }

      copyright=(TextView)findViewById(R.id.copyright);

       copyright.setText( "Ramesh Gali"+"\u00a9" );
        copyright.setTextColor(Color.BLACK);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    startActivity(new Intent(MainActivity.this, Updates.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Please check your internet connection!!",Toast.LENGTH_LONG).show();
                }
               // startActivity(new Intent(MainActivity.this, Updates.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    Intent i = new Intent(getApplicationContext(), FixturesActivity.class);
                    i.putExtra("epuzzle", URL);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Please check your internet connection!!",Toast.LENGTH_LONG).show();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(isNetworkAvailable()) {
                     startActivity(new Intent(MainActivity.this, TableActivity.class));

                 }else{
                    Toast.makeText(getApplicationContext(),"Please check your internet connection!!",Toast.LENGTH_LONG).show();
                }

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        ArrayList<Integer> timelist= new ArrayList<>();

        @Override
        protected Object doInBackground(Void... arg0) {
            try{
            stringres =getAllOpponents().get(0); Log.i("new",stringres);
                 object.setName(stringres);
                if(getAllTimes().get(0).contains("PM")){
                    s=getAllTimes().get(0).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    //System.out.println(s);
                    Log.i("new",s);
                    l=Integer.valueOf(s)+1200;
                    Log.i("new",stringres);
                }else if(getAllTimes().get(0).contains("AM")){
                    s=getAllTimes().get(0).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    l=Integer.valueOf(s);
                }
               // ArrayList<Integer> timelist= new ArrayList<>(TimeLeft(getAllDates().get(0)));
                timelist.addAll(TimeLeft(getAllDates().get(0)));

                Calendar thatDay = Calendar.getInstance();
                thatDay.set(Calendar.MINUTE,l%100);
                thatDay.set(Calendar.HOUR_OF_DAY,l/100);
                thatDay.set(Calendar.DAY_OF_MONTH,timelist.get(0));
                thatDay.set(Calendar.MONTH,timelist.get(1)-1); // 0-11 so 1 less
                thatDay.set(Calendar.YEAR, timelist.get(2));

                Calendar today = Calendar.getInstance();
                long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
                long diffSec = diff / 1000;

                long days = diffSec / SECONDS_IN_A_DAY;
                long secondsDay = diffSec % SECONDS_IN_A_DAY;
                long seconds = secondsDay % 60;
                long minutes = (secondsDay / 60) % 60;
                long hours = (secondsDay / 3600); // % 24 not needed

            list.add((int)days);list.add((int)seconds);list.add((int) minutes);list.add((int)hours);
                object.setId(list);

                Log.i("logged",list.get(0)+" "+list.get(1)+" "+list.get(2)+" "+list.get(3)+" ");


            }
            catch (Exception e){
                e.printStackTrace();
            }
        return object;
        }

        @Override
        protected void onPostExecute(Object object) {
            //spinner.setVisibility(View.GONE);
            list.addAll(object.getTime());
            long mInitialTime = DateUtils.DAY_IN_MILLIS * list.get(0)+
                    DateUtils.HOUR_IN_MILLIS * list.get(3) +
                    DateUtils.MINUTE_IN_MILLIS * list.get(2)+
                    DateUtils.SECOND_IN_MILLIS * list.get(1);

            stringres=object.getName();
            mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
                @Override
                public void onFinish() {
                    textView.setText("Postponed!");
                 new ProgressTaskover().execute();
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
    /*
    *    DIRTY CODE!!!!
    *
    *
    * */
    private class ProgressTaskover extends AsyncTask<Void,Void,Object> {
        String stringres="";
        Object object= new Object();
        ArrayList<Integer> list= new ArrayList<>();
        int l=0;
        String s;
        int min;
        int hour;
        int result;
        ArrayList<Integer> timelist= new ArrayList<>();

        @Override
        protected Object doInBackground(Void... arg0) {
            try{
                stringres =getAllOpponents().get(1); Log.i("new",stringres);
                object.setName(stringres);
                if(getAllTimes().get(1).contains("PM")){
                    s=getAllTimes().get(1).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    //System.out.println(s);
                    Log.i("new",s);
                    l=Integer.valueOf(s)+1200;
                    Log.i("new",stringres);
                }else if(getAllTimes().get(1).contains("AM")){
                    s=getAllTimes().get(1).replaceAll("\\s", "").replaceAll(":", "").replaceAll("A", "").replaceAll("P", "").replaceAll("M", "");
                    l=Integer.valueOf(s);
                }
                // ArrayList<Integer> timelist= new ArrayList<>(TimeLeft(getAllDates().get(0)));
                timelist.addAll(TimeLeft(getAllDates().get(1)));

                Calendar thatDay = Calendar.getInstance();
                thatDay.set(Calendar.MINUTE,l%100);
                thatDay.set(Calendar.HOUR_OF_DAY,l/100);
                thatDay.set(Calendar.DAY_OF_MONTH,timelist.get(0));
                thatDay.set(Calendar.MONTH,timelist.get(1)-1); // 0-11 so 1 less
                thatDay.set(Calendar.YEAR, timelist.get(2));

                Calendar today = Calendar.getInstance();
                long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
                long diffSec = diff / 1000;

                long days = diffSec / SECONDS_IN_A_DAY;
                long secondsDay = diffSec % SECONDS_IN_A_DAY;
                long seconds = secondsDay % 60;
                long minutes = (secondsDay / 60) % 60;
                long hours = (secondsDay / 3600); // % 24 not needed

                list.add((int)days);list.add((int)seconds);list.add((int) minutes);list.add((int)hours);
                object.setId(list);

                Log.i("logged",list.get(0)+" "+list.get(1)+" "+list.get(2)+" "+list.get(3)+" ");


            }
            catch (Exception e){
                e.printStackTrace();
            }
            return object;
        }

        @Override
        protected void onPostExecute(Object object) {
            //spinner.setVisibility(View.GONE);
            list.addAll(object.getTime());
            long mInitialTime = DateUtils.DAY_IN_MILLIS * list.get(0)+
                    DateUtils.HOUR_IN_MILLIS * list.get(3) +
                    DateUtils.MINUTE_IN_MILLIS * list.get(2)+
                    DateUtils.SECOND_IN_MILLIS * list.get(1);

            stringres=object.getName();
            mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
                @Override
                public void onFinish() {
                    textView.setText("NOW PLAYING!!");

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

    public static ArrayList<String> getAllTimes() throws Exception{
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
    public static ArrayList<String> getAllOpponents() throws Exception{
        Document doc = Jsoup.connect(URL).get();
        ArrayList<String> dates= new ArrayList<>();
        Elements h11 = doc.body().getElementsByClass("team");
        for(int i=1;i<h11.size();i=i+2){
            if(h11.get(i).text().equals("Real Madrid")||h11.get(i).text().contains("Barcelona")||h11.get(i).text().contains("Madrid")||h11.get(i).text().contains("Liverpool")){
                String s=h11.get(i-1).text()+" (A)";
                dates.add(s);
            }else {
                String s=h11.get(i).text()+" (H)";
                dates.add(s);
            }
        }
        return dates;
    }

    public static ArrayList<String> getschedule(String URL) throws Exception{
        Document doc = Jsoup.connect("http://www.realmadrid.com/en").get();
        Elements h1 = doc.body().getElementsByClass("m_schecdule_header");
        // Elements h11 = doc.body().getElementsByClass("destacado");
        ArrayList<String> list= new ArrayList<>();
        for(Element el: h1){
            list.add(el.text());
        }
        return list;
    }
    public static ArrayList<Integer> TimeLeft(String s){
        s.replaceAll("\\s", "");

        String[] parts=s.split(",");
        //String part=parts[0];
        String part1=parts[1];
        String part2=parts[2];
        matchYear=Integer.parseInt(part2.replaceAll("\\s+",""));
        if(part1.contains("January")){
            matchMonth=1;
        }if(part1.contains("February")){
            matchMonth=2;
        }if(part1.contains("March")){
            matchMonth=3;
        }if(part1.contains("April")){
            matchMonth=4;
        }if(part1.contains("May")){
            matchMonth=5;
        }if(part1.contains("June")){
            matchMonth=6;
        }if(part1.contains("July")){
            matchMonth=7;
        }if(part1.contains("August")){
            matchMonth=8;
        }if(part1.contains("September")){
            matchMonth=9;
        }if(part1.contains("October")){
            matchMonth=10;
        }if(part1.contains("November")){
            matchMonth=11;
        }if(part1.contains("December")){
            matchMonth=12;
        }
        StringBuilder buf= new StringBuilder();
        for(int i=0;i<part1.length();i++){
            if(part1.charAt(i)=='0'||part1.charAt(i)=='2'||part1.charAt(i)=='3'||
                    part1.charAt(i)=='4'||part1.charAt(i)=='5'||part1.charAt(i)=='6'||
                    part1.charAt(i)=='7'||part1.charAt(i)=='8'||part1.charAt(i)=='9'||
                    part1.charAt(i)=='1'){
                buf.append(part1.charAt(i));
            }
        }
        matchDay=Integer.valueOf(buf.toString());
       ArrayList<Integer> list= new ArrayList<>();
        list.add(matchDay);list.add(matchMonth);list.add(matchYear);
         return list;
    }

}
