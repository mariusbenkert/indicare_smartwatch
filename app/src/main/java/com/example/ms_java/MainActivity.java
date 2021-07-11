package com.example.ms_java;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;


public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
