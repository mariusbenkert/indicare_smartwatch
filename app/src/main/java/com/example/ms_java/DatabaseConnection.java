package com.example.ms_java;

import android.location.Location;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConnection {

    private final static String databaseURL = "https://ms-project-88b21-default-rtdb.europe-west1.firebasedatabase.app/";
    private static DatabaseReference ref = FirebaseDatabase.getInstance(databaseURL).getReference();

    private static FirebaseUser signedInUser = FirebaseAuth.getInstance().getCurrentUser();

    public DatabaseConnection() {}

    public static void updateSensorValue(String valueType, float[] sensorValue) {
        ref.child("patients").child(signedInUser.getUid()).child("realtimeValues").child(valueType).setValue(sensorValue[0]);
    }

    public static void updateLocation(Location location) {
        Log.d("Loc", String.valueOf(location.getLatitude()));
        ref.child("patients").child(signedInUser.getUid()).child("realtimeValues").child("location").child("lat").setValue(location.getLatitude());
        ref.child("patients").child(signedInUser.getUid()).child("realtimeValues").child("location").child("lng").setValue(location.getLongitude());
    }

    public static void setWatchActive () {
        ref.child("patients").child(signedInUser.getUid()).child("isActive").setValue(true);
    }
}
