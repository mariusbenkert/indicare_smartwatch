package com.example.ms_java;

import androidx.annotation.NonNull;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.Toast;

public class UpdateActivity extends WearableActivity implements IBaseGpsListener {

    SensorManager sensorManager;
    private static final int PERMISSION_LOCATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        boolean heartRateCheck = intent.getBooleanExtra("heartRateCheck", false);
        boolean locationCheck = intent.getBooleanExtra("locationCheck", false);
        boolean stepCheck = intent.getBooleanExtra("stepCheck", false);
        boolean accelCheck = intent.getBooleanExtra("accelCheck", false);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (heartRateCheck) {
            final SensorUpdate sensorHeartRate = new SensorUpdate(sensorManager, Sensor.TYPE_HEART_RATE, "heartRate");
            sensorHeartRate.registerListener();
        }

        if (locationCheck) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            } else {
                showLocation();
            }
        }

//        TODO: Implement new functionalities
//        if (stepCheck) {
//            final SensorUpdate sensorStep = new SensorUpdate(sensorManager, Sensor.TYPE_STEP_COUNTER, "stepCount");
//            sensorStep.registerListener();
//        }
//
//        if (accelCheck) {
//            final SensorUpdate sensorStep = new SensorUpdate(sensorManager, Sensor.TYPE_ACCELEROMETER, "stepCount");
//            sensorStep.registerListener();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        } else {
            Toast.makeText(this, "Enable GPS!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOC", "Location Update!");
        DatabaseConnection.updateLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onGpsStatusChanged(int event) {

    }
}