package com.app.my_aplikasi_s16;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Inisialisasi TextView
        TextView tvNama = findViewById(R.id.tvResultNama);
        TextView tvEmail = findViewById(R.id.tvResultEmail);
        TextView tvPhone = findViewById(R.id.tvResultPhone);
        TextView tvGender = findViewById(R.id.tvResultGender);
        TextView tvSeminar = findViewById(R.id.tvResultSeminar);
        Button btnBack = findViewById(R.id.btnBackHome);

        // Ambil data dari Intent (Dikirim dari MainActivity)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvNama.setText("Nama: " + extras.getString("NAMA"));
            tvEmail.setText("Email: " + extras.getString("EMAIL"));
            tvPhone.setText("Nomor HP: " + extras.getString("PHONE"));
            tvGender.setText("Jenis Kelamin: " + extras.getString("GENDER"));
            tvSeminar.setText("Seminar yang dipilih: " + extras.getString("SEMINAR"));
        }

        // Fungsi Tombol Kembali ke Halaman Utama
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            // Membersihkan history stack agar tidak balik ke form lagi
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}