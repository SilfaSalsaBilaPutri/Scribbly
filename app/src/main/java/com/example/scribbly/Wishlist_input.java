package com.example.scribbly;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;

public class Wishlist_input extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView imagePreview;
    EditText etSavingsName, etTarget, etCurrency, etNominal;
    RadioGroup rgPlan;
    RadioButton rbDaily, rbWeekly, rbMonthly;
    TimePicker timePicker;
    ToggleButton sun, mon, tue, wed, thu;
    Button btnSave;
    ImageButton btnBack;
    TextView tvCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_input);
        imagePreview = findViewById(R.id.imagePreview);
        etSavingsName = findViewById(R.id.etSavingsName);
        etTarget = findViewById(R.id.etTarget);
        etCurrency = findViewById(R.id.etCurrency);
        etNominal = findViewById(R.id.etNominal);
        rgPlan = findViewById(R.id.rgPlan);
        rbDaily = findViewById(R.id.rbDaily);
        rbWeekly = findViewById(R.id.rbWeekly);
        rbMonthly = findViewById(R.id.rbMonthly);
        timePicker = findViewById(R.id.timePicker);
        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        tvCalculator = findViewById(R.id.tvCalculator);

        // Image picker
        imagePreview.setOnClickListener(v -> openImagePicker());

        // Save button
        btnSave.setOnClickListener(v -> saveData());

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Kalkulator target
        tvCalculator.setOnClickListener(v -> {
            // Buka activity lain atau kalkulator
            Toast.makeText(this, "Opening calculator...", Toast.LENGTH_SHORT).show();
        });

        // TimePicker default ke 12:00
        Calendar calendar = Calendar.getInstance();
        timePicker.setHour(12);
        timePicker.setMinute(0);
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        String name = etSavingsName.getText().toString();
        String target = etTarget.getText().toString();
        String currency = etCurrency.getText().toString();
        String nominal = etNominal.getText().toString();

        String plan = "";
        int selectedId = rgPlan.getCheckedRadioButtonId();
        if (selectedId == rbDaily.getId()) plan = "Daily";
        else if (selectedId == rbWeekly.getId()) plan = "Weekly";
        else if (selectedId == rbMonthly.getId()) plan = "Monthly";

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        StringBuilder selectedDays = new StringBuilder();
        if (sun.isChecked()) selectedDays.append("Sunday ");
        if (mon.isChecked()) selectedDays.append("Monday ");
        if (tue.isChecked()) selectedDays.append("Tuesday ");
        if (wed.isChecked()) selectedDays.append("Wednesday ");
        if (thu.isChecked()) selectedDays.append("Thursday ");

        // Simulasi penyimpanan atau tampilkan data
        Toast.makeText(this, "Saved:\n" +
                        "Name: " + name + "\n" +
                        "Target: " + target + "\n" +
                        "Currency: " + currency + "\n" +
                        "Nominal: " + nominal + "\n" +
                        "Plan: " + plan + "\n" +
                        "Time: " + hour + ":" + String.format("%02d", minute) + "\n" +
                        "Days: " + selectedDays.toString(),
                Toast.LENGTH_LONG).show();
    }
}