package com.personalcounter.sqlite;

import android.os.Build;
import android.os.Bundle;
import android.os.SharedMemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewRecordActivity extends AppCompatActivity {
    private ArrayList<RecordModel> recordModelArrayList;
    private DBHandler dbHandler;
    private RecordsRVAdapter recordsRVAdapter;
    private RecyclerView recordRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        recordModelArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewRecordActivity.this);

        recordModelArrayList = dbHandler.fetchRecord();

        recordsRVAdapter = new RecordsRVAdapter(recordModelArrayList, ViewRecordActivity.this);
        recordRV = findViewById(R.id.idRVRecords);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewRecordActivity.this, RecyclerView.VERTICAL, false);
        recordRV.setLayoutManager(linearLayoutManager);
        recordRV.setAdapter(recordsRVAdapter);

    }
}
