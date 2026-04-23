package com.app.my_aplikasi_s16;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNama, etEmail, etPhone;
    private RadioGroup rgGender;
    private Spinner spinnerSeminar;
    private CheckBox cbAgreement;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi ID
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        rgGender = findViewById(R.id.rgGender);
        spinnerSeminar = findViewById(R.id.spinnerSeminar);
        cbAgreement = findViewById(R.id.cbAgreement);
        btnDaftar = findViewById(R.id.btnDaftar);

        etPhone.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (!input.startsWith("08")) {
                    etPhone.setError("Harus diawali 08");
                } else if (input.length() < 10 || input.length() > 13) {
                    etPhone.setError("Harus 10-13 digit");
                } else {
                    etPhone.setError(null); // Hilangkan error jika sudah benar
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // Setup Spinner (Poin 2.5 - Minimal 5 pilihan)
        String[] daftarSeminar = {
                "Web Development Dasar",
                "Android Expert with Java",
                "Cyber Security Awareness",
                "Data Science Intro",
                "UI/UX Design Masterclass"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, daftarSeminar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeminar.setAdapter(adapter);

        // Klik Tombol Daftar
        btnDaftar.setOnClickListener(v -> {
            // Poin 3: Jalankan validasi input
            if (isDataValid()) {
                // Poin 4: Munculkan dialog konfirmasi
                showConfirmDialog();
            }
        });
    }

    private boolean isDataValid() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // 3.1 & 3.2: Semua field wajib diisi & tampilkan error
        if (nama.isEmpty()) {
            etNama.setError("Nama wajib diisi!");
            return false;
        }

        // 3.3: Validasi Email harus ada "@"
        if (email.isEmpty() || !email.contains("@")) {
            etEmail.setError("Email harus valid (mengandung @)!");
            return false;
        }

        // 3.4: Validasi Nomor HP (08, Angka, 10-13 digit)
        if (phone.isEmpty()) {
            etPhone.setError("Nomor HP wajib diisi!");
            return false;
        } else if (!phone.startsWith("08")) {
            etPhone.setError("Harus diawali 08");
            return false;
        } else if (phone.length() < 10 || phone.length() > 13) {
            etPhone.setError("Panjang harus 10-13 digit");
            return false;
        }

        // 3.6: Validasi Checkbox belum dicentang
        if (!cbAgreement.isChecked()) {
            Toast.makeText(this, "Centang persetujuan dulu!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi")
                .setMessage("Apakah data sudah benar?")
                // Tombol "Ya" -> lanjut ke halaman hasil
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Ambil data dari inputan
                    String nama = etNama.getText().toString();
                    String email = etEmail.getText().toString();
                    String phone = etPhone.getText().toString();

                    // Ambil nilai gender yang dipilih
                    int selectedId = rgGender.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(selectedId);
                    String gender = (rb != null) ? rb.getText().toString() : "-";

                    // Ambil nilai seminar dari spinner
                    String seminar = spinnerSeminar.getSelectedItem().toString();

                    // Pindah ke ResultActivity (Poin 5)
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("NAMA", nama);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("PHONE", phone);
                    intent.putExtra("GENDER", gender);
                    intent.putExtra("SEMINAR", seminar);
                    startActivity(intent);
                });

        // Tombol "Tidak" -> tetap di halaman form
        builder.setNegativeButton("Tidak", (dialog, which) -> {
            dialog.dismiss(); // Menutup dialog tanpa pindah halaman
        });

        // Tampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}