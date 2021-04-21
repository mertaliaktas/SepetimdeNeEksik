package com.example.sepetimdeneeksik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Calculated extends Activity {

    Button reset;
    TextView textView;

    ListView callistView;
    static ArrayAdapter<String> caladapter;
    static ArrayList<String> callist;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calculated);

        callistView = (ListView) findViewById(R.id.datum);
        callist = new ArrayList<>();

        textView = findViewById(R.id.recommendedfruit);

        caladapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, callist);
        callistView.setAdapter(caladapter);

        for (int pos = 0; pos < MainActivity.getCount(); pos++) {
            callist.add(MainActivity.listView.getItemAtPosition(pos).toString());
        }
        caladapter.notifyDataSetChanged();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("items.txt")));
            String mLine = reader.readLine();
            String[] sutun;
            int deger;
            int okunan;
            while (mLine != null) {
                sutun = mLine.split("=");
                okunan = Integer.parseInt(MainActivity.x.get(0));
                deger = Integer.parseInt(sutun[1]);
                if (!mLine.isEmpty() && okunan == deger) {
                    textView.setText(sutun[2]);
                    mLine = null;
                } else if (!mLine.isEmpty()) {
                    mLine = reader.readLine();
                } else {
                    mLine = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callist.clear();
                caladapter.notifyDataSetChanged();
                MainActivity.myList.clear();
                Intent intent = new Intent(Calculated.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}