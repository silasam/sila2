package com.example.sila;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        final EditText etEnglishWord = findViewById(R.id.editText);
        Button btnTranslate = findViewById(R.id.Translate_button);
        Button btnExit = findViewById(R.id.Exit_button);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Setup recyclerView
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Button exit
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Button translate
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String englishWord = etEnglishWord.getText().toString();
                startSearching(englishWord, adapter);
            }
        });
    }

    private void startSearching(final String englishWord, final RecyclerViewAdapter adapter) {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Read file in background thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Get file content to arrayList
                ArrayList<DataItem> dictionary = parseFileToList();

                // ArrayList to hold translations for word
                final ArrayList<DataItem> translations = new ArrayList<>();

                // Filter dictionary for swahili word equivalents for english word
                for (DataItem dataItem : dictionary) {
                    // Filter 10 items
                    if (translations.size() <= 10) {
                        if (dataItem.getEngWord().equals(englishWord)) translations.add(dataItem);
                    } else break;
                }

                // Show filtered translations in recyclerView
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setTranslations(translations);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


            }
        });


    }

    private ArrayList<DataItem> parseFileToList() {
        ArrayList<DataItem> data = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.db);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        try {
            while (br.readLine() != null) {
                String line = br.readLine();

                String[] words = line.split(",");

                DataItem dataItem = new DataItem();
                dataItem.setEngWord(words[1]);
                dataItem.setSwaWord(words[0]);

                data.add(dataItem);
            }

            inputStream.close();
        } catch (Exception ignored) {
        }

        return data;
    }


}
