package com.personalcounter.sqlite;

import static com.personalcounter.sqlite.R.layout.activity_main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView textview0;
    TextView textview1;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button btnreset;
    Button btnreadrecord;
    private DBHandler dbHandler;
    String date = null;
    int count0 = 0;
    int count1 = 0;
    public ArrayList<RecordModel> arrRecord;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textview0 = findViewById(R.id.textview0);
        textview1 = findViewById(R.id.textview1);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        btnreset = findViewById(R.id.reset);
        btnreadrecord = findViewById(R.id.btnReadRecord);

        /*recupero della scelta salvata,
         * questa viene eseguita leggendo una chiave dal nome "Counter0" con valore di default uguale a zero*/
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        /*impostazione della variabile counter0 in base al valore salvato all'interno
         * della chiave "Counter0"*/
        loadData(sharedPref);

        dbHandler = new DBHandler(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        loadData(sharedPref);

        button0.setOnClickListener(v -> {
            count0++;
            storeData(sharedPref);
            textview0.setText(String.valueOf(count0));
        });

        button1.setOnClickListener(v -> {
            count1++;
            storeData(sharedPref);
            textview1.setText(String.valueOf(count1));
        });

        button2.setOnClickListener(v -> {
            count0--;
            storeData(sharedPref);
            textview0.setText(String.valueOf(count0));
        });

        button3.setOnClickListener(v -> {
            count1--;
            storeData(sharedPref);
            textview1.setText(String.valueOf(count1));
        });

        btnreset.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date = String.valueOf(LocalDate.now());
            }
            String result;
            if (count0 > count1){
                result = "Player 1";
            } else if (count0 < count1) {
                result = "Player 2";
            } else {
                result = "Draw";
            }
            String player1 = textview0.getText().toString();
            String player2 = textview1.getText().toString();
            if (Objects.equals(date, sharedPref.getString("Date", null))){
                dbHandler.updateRecord(date, player1, player2, result);
            } else{
                dbHandler.addRecord(date, player1, player2, result);
            }

            count0 = 0;
            count1 = 0;
            textview0.setText(String.valueOf(count0));
            textview1.setText(String.valueOf(count1));
            storeData(sharedPref);

        });

        btnreadrecord.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewRecordActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        storeData(sharedPref);
        super.onDestroy();
    }
    private void loadData(SharedPreferences sharedPref) {
        count0 = sharedPref.getInt("Counter0", 0);
        count1 = sharedPref.getInt("Counter1", 0);
        textview0.setText(String.valueOf(count0));
        textview1.setText(String.valueOf(count1));
        date = sharedPref.getString("Date", null);
        String tmp = date + ", " + count0 + ", " + count1;
        Log.d("FIRST_INFO", tmp);

    }

    private void storeData(SharedPreferences sharedPref) {
        //dichiarazione di SharedPreferences in modalita privata (solo l'app può accedervi)
        //dichiarazione di voler modificare le SharedPreferences
        SharedPreferences.Editor editor = sharedPref.edit();
        //modifica della chiave "NOME_CHIAVE" con il valore della variabile nome_variabile
        editor.putInt("Counter0", count0);
        editor.putInt("Counter1", count1);
        editor.putString("Date", date);
        //applicazione (salvataggio) delle modifiche
        editor.apply();
    }
}