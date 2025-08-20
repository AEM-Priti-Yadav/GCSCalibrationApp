package com.aem.cgscalibrationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView settingsIcon;
    View knob;
    FrameLayout joystick;
    TextView valueText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsIcon = findViewById(R.id.settingsIcon);
        settingsIcon.setOnClickListener(v -> showSettingsPopup());

        knob = findViewById(R.id.leftKnob);
        joystick = findViewById(R.id.joystickLeft);
        valueText = findViewById(R.id.leftJoystickValue);

        joystick.post(() -> updateKnobPosition(20, 40)); // Example X=50, Y=70

    }
    private void updateKnobPosition(float xValue, float yValue) {
        int maxRange = joystick.getWidth() / 2; // or joystick.getHeight()/2
        float knobX = (xValue / 100f) * maxRange;
        float knobY = (yValue / 100f) * maxRange;

        knob.setTranslationX(knobX);
        knob.setTranslationY(knobY);

        valueText.setText("X:" + xValue + ", Y:" + yValue);
    }
    private void showSettingsPopup() {
        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_settings, null);

        Spinner spinnerPort = dialogView.findViewById(R.id.spinnerPort);
        Spinner spinnerBaud = dialogView.findViewById(R.id.spinnerBaud);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        // Sample data for dropdowns
        String[] ports = {"Select Com Port", "COM1", "COM2", "COM3"};
        String[] baudRates = {"Select Baud Rate", "9600", "115200", "230400"};

        ArrayAdapter<String> portAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, ports);
        spinnerPort.setAdapter(portAdapter);

        ArrayAdapter<String> baudAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, baudRates);
        spinnerBaud.setAdapter(baudAdapter);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        btnSubmit.setOnClickListener(view -> {
            String selectedPort = spinnerPort.getSelectedItem().toString();
            String selectedBaud = spinnerBaud.getSelectedItem().toString();

            Toast.makeText(this,
                    "Selected: " + selectedPort + " @ " + selectedBaud,
                    Toast.LENGTH_SHORT).show();

            dialog.dismiss();
        });

        dialog.show();
    }
}
