package com.example.assignment3sqldatabasecp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class listviewactivity extends AppCompatActivity {
    ListView listView;
    SimpleCursorAdapter adapter;
    PokeDBProvider dbProvider;

    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getApplication(),listView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewactivity2);
        listView = findViewById(R.id.list);
        dbProvider = new PokeDBProvider();

        Cursor cursor = dbProvider.query(PokeDBProvider.contentURI, null, null, null, null);


    }




}