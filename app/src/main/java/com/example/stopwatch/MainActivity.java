package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvWaktu;
    private Button btnMulai, btnBerhenti, btnReset, btnLap;
    private long WaktuMilliSekon, WaktuMulai, WaktuBuff, WaktuUpdate = 0L;
    private Handler handler;
    private int Detik, Menit, MiliSekon;
    private ListView listWaktu;
    private String[] ListElements = new String[]{};
    private List<String> ListElementsArrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWaktu = findViewById(R.id.txt_waktu);
        btnMulai = findViewById(R.id.btn_mulai);
        btnBerhenti = findViewById(R.id.btn_berhenti);
        btnReset = findViewById(R.id.btn_reset);
        btnLap = findViewById(R.id.btn_simpan_putaran);
        listWaktu = findViewById(R.id.list_waktu);

        handler = new Handler();
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listWaktu.setAdapter(adapter);

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaktuMulai = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                btnReset.setEnabled(false);
            }
        });

        btnBerhenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaktuBuff += WaktuMilliSekon;

                handler.removeCallbacks(runnable);

                btnReset.setEnabled(true);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WaktuMilliSekon = 0L;
                WaktuMulai = 0L;
                WaktuBuff = 0L;
                WaktuUpdate = 0L;
                Detik = 0;
                Menit = 0;
                MiliSekon = 0;

                tvWaktu.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ListElementsArrayList.add(tvWaktu.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            WaktuMilliSekon = SystemClock.uptimeMillis() - WaktuMulai;
            WaktuUpdate = WaktuBuff + WaktuMilliSekon;

            Detik = (int) (WaktuUpdate / 1000);

            Menit = Detik / 60;

            Detik = Detik % 60;

            WaktuMilliSekon = (int) (WaktuUpdate % 1000);

            tvWaktu.setText("" + Menit + ":" + String.format("%02d", Detik) + ":" + String.format("%03d", WaktuMilliSekon));

            handler.postDelayed(this, 0);
        }
    };
}
