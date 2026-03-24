package com.app.my_aplikasi_s16;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 01. Deklarasi sesuai XML terbaru
    private EditText etNama, etEmail, etPassword, etConfirmPassword;
    private RadioGroup rgGender;
    private CheckBox cbCooking, cbMusik, cbTravel, cbOlahraga, cbGame;
    private Spinner spinnerKota;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 02. Inisialisasi View
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        cbCooking = findViewById(R.id.cbCooking);
        cbMusik = findViewById(R.id.cbMusik);
        cbTravel = findViewById(R.id.cbTraveling);
        cbOlahraga = findViewById(R.id.cbOlahraga);
        cbGame = findViewById(R.id.cbGame);
        spinnerKota = findViewById(R.id.spinnerKota);
        btnLogin = findViewById(R.id.btnLogin);

        // 03. Setup Spinner Data
        String[] listKota = {"Jakarta", "Bandung", "Surabaya", "Medan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listKota);
        spinnerKota.setAdapter(adapter);

        // 04. AKTIFKAN REAL-TIME VALIDATION
        setupRealTimeValidation();

        // 05. Gesture Interaction (Long Press)
        btnLogin.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "Data disimpan sebagai Draft!", Toast.LENGTH_SHORT).show();
            return true;
        });

        // Tombol Daftar
        btnLogin.setOnClickListener(v -> {
            if (isDataValid()) {
                showConfirmDialog();
            }
        });
    }

    // Fungsi Poin 02: Real-time Validation
    private void setupRealTimeValidation() {
        // Cek Email saat mengetik
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    etEmail.setError("Format email salah!");
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Cek Konfirmasi Password saat mengetik
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = etPassword.getText().toString();
                if (!s.toString().equals(pass)) {
                    etConfirmPassword.setError("Password tidak cocok!");
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    // Fungsi Poin 02 & 03: Validasi Akhir
    private boolean isDataValid() {
        if (etNama.getText().toString().isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
            etEmail.setError("Email tidak valid");
            return false;
        }
        if (etPassword.getText().length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            return false;
        }
        if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
            etConfirmPassword.setError("Password tidak match");
            return false;
        }

        // Poin 03: Cek minimal 3 hobi
        int count = 0;
        if (cbCooking.isChecked()) count++;
        if (cbMusik.isChecked()) count++;
        if (cbTravel.isChecked()) count++;
        if (cbOlahraga.isChecked()) count++;
        if (cbGame.isChecked()) count++;

        if (count < 3) {
            Toast.makeText(this, "Pilih minimal 3 hobi!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah data sudah benar?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}