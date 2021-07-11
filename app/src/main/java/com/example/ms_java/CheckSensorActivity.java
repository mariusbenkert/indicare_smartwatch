package com.example.ms_java;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckSensorActivity extends WearableActivity {

    private TextView loggedInEmailTextView;
    private Button beginListeningButton;

    private boolean heartRateCheck = false;
    private boolean locationCheck = false;
    private boolean stepCheck = false;
    private boolean accelCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sensor);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        String email = intent.getStringExtra("email");

        loggedInEmailTextView = findViewById(R.id.textView);
        loggedInEmailTextView.setText(email);

        beginListeningButton = findViewById(R.id.button_sensor);

        beginListeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckSensorActivity.this, UpdateActivity.class);

                intent.putExtra("heartRateCheck", heartRateCheck);
                intent.putExtra("locationCheck", locationCheck);
                intent.putExtra("stepCheck", stepCheck);
                intent.putExtra("accelCheck", accelCheck);

                startActivity(intent);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxHeartRate:
                if (checked) {
                    heartRateCheck = true;
                } else {
                    heartRateCheck = false;
                }
                Log.d("HeartRateCheck", String.valueOf(heartRateCheck));
                break;
            case R.id.checkBoxLocation:
                if (checked) {
                    locationCheck = true;
                } else {
                    locationCheck = false;
                }
                Log.d("LocationCheck", String.valueOf(locationCheck));
                break;
            case R.id.checkBoxStep:
                if (checked) {
                    stepCheck = true;
                } else {
                    stepCheck = false;
                }
                Log.d("StepCheck", String.valueOf(stepCheck));
                break;
            case R.id.checkBoxAccel:
                if (checked) {
                    accelCheck = true;
                } else {
                    accelCheck = false;
                }
                Log.d("AccelCheck", String.valueOf(accelCheck));
                break;
        }
    }
}