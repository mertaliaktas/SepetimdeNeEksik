package com.example.sepetimdeneeksik;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    BufferedReader reader = null;
    FileOutputStream outputStreamWriter = null;
    static int threshold; // Min Destek sayisi
    static String file = "items.txt";
    static String file3 = "dataset.txt";// Kullanilacak veriler. Dosya, proje dosyasinin direk icinde olmalidir.
    static String specialFile = "ozelKayitListesi.txt";
    static FPGrowth fpGrowth;

    private static int count;
    Button scan;
    Button calculate;
    Button delete;
    static ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    static ArrayList<String> x;

    TextView textView;
    EditText editText;

    static List<String> myList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        listView = (ListView) findViewById(R.id.items);
        list = new ArrayList<>();

        textView = findViewById(R.id.uruncount);
        editText = findViewById(R.id.threshold);
        registerForContextMenu(listView);

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);


        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCount(listView.getAdapter().getCount());
                try {
                    if (getCount() != 0) {

                        progressDialog.setMessage("Devam eden işleminiz bulunmaktadır. Lütfen bekleyiniz...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.getMax();
                        progressDialog.getProgress();
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                    String[] input;
                                    String line = "";
                                    for (int i = 0; i < myList.size(); i++) {
                                        line += myList.get(i) + " ";
                                    }

                                    input = line.split(" ");

                                    System.out.println("satir " + line);
                                    //System.out.println("Minimum Destek Değerini giriniz (Örn. 8000)");

                                    File file2 = new File("/data/data/com.example.sepetimdeneeksik/files/" + specialFile);

                                    createSpecialDatabase("/data/data/com.example.sepetimdeneeksik/files/" + specialFile, input);

                                    int countt = 0;
                                    try {
                                        reader = new BufferedReader(
                                                new InputStreamReader(openFileInput(specialFile)));

                                        String line11 = reader.readLine();

                                        while (line11 != null) {
                                            countt++;
                                            line11 = reader.readLine();
                                        }
                                        System.out.println(countt);
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

                                    threshold = Integer.parseInt(editText.getText().toString());
                                    System.out.println(threshold);

                                    try {
                                        fpGrowth = new FPGrowth(file2, threshold);
                                    } catch (Exception e) {
                                        System.out.println("Ozel Dosya Hatasi :" + e.getMessage());
                                    }

                                    System.out.println("header " + fpGrowth.headerTable.size());
                                    System.out.println("fayda " + fpGrowth.frequentPatterns.entrySet());

                                    ArrayList<String> inputList = new ArrayList<String>(Arrays.asList(input));
                                    printHeaderTable();
                                    String best = findConfidence(inputList, line);

                                    Intent intent = new Intent(MainActivity.this, Calculated.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                    } else {
                        Toast.makeText(MainActivity.this, "Ürün Listeniz Boş", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println("main " + e.getMessage());
                }
            }

            private void createSpecialDatabase(String file2, String[] input) {

                try {
                    reader = new BufferedReader(
                            new InputStreamReader(getAssets().open("dataset.txt")));
                    outputStreamWriter = openFileOutput(specialFile, Context.MODE_PRIVATE);

                    String line = reader.readLine();
                    String line1 = "\n";
                    while (line != null) {
                        if (isContain(line, input)) {
                            outputStreamWriter.write(line.getBytes());
                            outputStreamWriter.write(line1.getBytes());
                        }
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            outputStreamWriter.close();
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            /*public void printFrequentPatterns() throws IOException {
                //File outputFile = new File("output.txt");// Faydali itemsetlerin bulunacagi dosya. Dosya, proje dosyasinin direk icinde olmalidir.
                //FileOutputStream fos = new FileOutputStream(outputFile);
                String newLine = "\n";
                String frequentPatternsLength = "The Count of Frequent Patterns: " + fpGrowth.frequentPatterns.size();
                byte[] bytes_frequentPatternLength = frequentPatternsLength.getBytes();

                for (Map.Entry<String, Integer> pairs : fpGrowth.frequentPatterns.entrySet()) {
                    System.out.println(pairs);
                    byte[] bytes = pairs.toString().getBytes();
                    byte[] bytes_newLine = newLine.getBytes();
                    //fos.write(bytes);
                    //fos.write(bytes_newLine);
                }
                //fos.write(bytes_frequentPatternLength);
                //fos.close();
            }*/

            private void printHeaderTable() {
                for (int i = 0; i < fpGrowth.headerTable.size(); i++) {
                    System.out.println(fpGrowth.headerTable.get(i).item + " supp:" + fpGrowth.headerTable.get(i).count);
                }
            }

            private String findConfidence(ArrayList<String> inputList, String input) {
                //int inputSupp = fpGrowth.frequentPatterns.get(myList.);

                String bestPattern = "";
                int best = 0;

                ArrayList<Integer> bestPatternsID = new ArrayList<>();

                for (Map.Entry<String, Integer> pairs : fpGrowth.frequentPatterns.entrySet()) {
                    //System.out.print(pairs.getKey());
                    ArrayList<String> originalList = new ArrayList<String>(Arrays.asList(pairs.getKey().split(" ")));
                    ArrayList<String> commonList = new ArrayList<String>(Arrays.asList(pairs.getKey().split(" ")));
                    commonList.retainAll(inputList);

                    if (commonList.size() == inputList.size()) {
                        System.out.print("\nGüvenli Liste: ");
                        for (int i = 0; i < originalList.size(); i++) {

                            System.out.print(originalList.get(i) + " ");
                        }
				/*if(inputSupp>0) {
					float conf=(float)pairs.getValue()/(float)inputSupp;
					System.out.println("Confidence: % "+conf*100);
				}
				*/
                        System.out.print("  Supp: " + pairs.getValue() + "\n");

                        if (originalList.size() > myList.size()) {
                            bestPatternsID.add(pairs.getValue());

                        }
                    }
                }
                Collections.sort(bestPatternsID);
                Collections.reverse(bestPatternsID);

                for (Map.Entry<String, Integer> pairs : fpGrowth.frequentPatterns.entrySet()) {

                    if (pairs.getValue().equals(bestPatternsID.get(0)))
                        bestPattern = pairs.getKey();

                }

                System.out.println("\nEn iyi Pattern: " + bestPattern);
                x = new ArrayList<String>(Arrays.asList(bestPattern.split(" ")));
                x.removeAll(myList);

                System.out.println("En iyi ITEM_ID: " + x.get(0));
                return x.get(0);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCount(listView.getAdapter().getCount());

                if (getCount() != 0) {
                    myList.clear();
                    list.clear();
                    textView.setText(listView.getAdapter().getCount() + " ürün");
                } else {
                    Toast.makeText(MainActivity.this, "Ürün Listeniz Boş", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView tv = (TextView) menuInfo.targetView;

        switch (item.getItemId()) {
            case R.id.delete:
                myList.remove(menuInfo.position);
                list.remove(menuInfo.position);
                adapter.notifyDataSetChanged();
                textView.setText(listView.getAdapter().getCount() + " ürün");
                Toast.makeText(this, tv.getText().toString() + " ürün silindi!!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.giveup:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void scanCode() {

        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Tarama Yapılıyor");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {
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
                        okunan = Integer.parseInt(result.getContents());
                        deger = Integer.parseInt(sutun[1]);
                        if (!mLine.isEmpty() && okunan == deger) {
                            myList.add(sutun[1]);
                            list.add(sutun[2]);
                            adapter.notifyDataSetChanged();
                            textView.setText(listView.getAdapter().getCount() + " ürün");
                            mLine = null;
                        } else if (!mLine.isEmpty()) {
                            mLine = reader.readLine();
                        } else {
                            mLine = null;
                            Toast.makeText(this, "Taratılan ürün veritabanında yok !!!", Toast.LENGTH_LONG).show();
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
            } else {
                Toast.makeText(this, "Tarama Yapmadınız !!!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private static boolean isContain(String line, String[] input) {
        boolean flag = true;
        for (int i = 0; i < input.length; i++) {
            if (!line.contains(input[i])) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}