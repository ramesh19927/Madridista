package com.example.ramesh_pc.madridista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectActivity extends AppCompatActivity {

    public String easyPuzzle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ImageButton btn= (ImageButton) findViewById(R.id.imageView0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPuzzle = "http://www.goal.com/en-us/fixtures/team/atl%C3%A9tico-madrid/2020?ICID=TP_TN_177";
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        ImageButton btn1= (ImageButton) findViewById(R.id.imageView1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPuzzle = "http://www.goal.com/en-us/fixtures/team/barcelona/2017?ICID=TP_TN_177";
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        ImageButton btn2= (ImageButton) findViewById(R.id.imageView2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPuzzle = "http://www.goal.com/en-us/fixtures/team/real-madrid/2016?ICID=TP_NMW_FT_1";
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        ImageButton btn3= (ImageButton) findViewById(R.id.imageView3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyPuzzle="http://www.goal.com/en-us/fixtures/team/liverpool/663?ICID=TP_TN_119";
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
    }

}
