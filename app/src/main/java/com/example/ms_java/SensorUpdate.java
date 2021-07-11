package com.example.ms_java;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SensorUpdate implements SensorEventListener {
    private Sensor sensor;
    private SensorManager sensorManager;
    private BehaviorSubject<SensorEvent> proxy = BehaviorSubject.create();
    private String valuePath;

    public SensorUpdate(SensorManager sensorManager, int sensorType, String dbPath) {
        this.sensorManager = sensorManager;
        this.sensor = sensorManager.getDefaultSensor(sensorType);
        this.valuePath = dbPath;
    }

    public void registerListener() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        sendUpdates(valuePath);
        Log.d("REGISTER", "Listener");
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proxy.onNext(event);
        Log.d(valuePath, "Update");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void sendUpdates(final String dbPath) {
        Observable
                .interval(10, TimeUnit.SECONDS)
                .map(new Function<Long, SensorEvent>() {
                    @Override
                    public SensorEvent apply(@NonNull Long aLong) throws Exception {
                        return proxy.getValue();
                    }
                })
                .subscribe(new DefaultObserver<SensorEvent>() {
                    @Override
                    public void onNext(@NonNull SensorEvent sensorEvent) {
                        Log.d("RXJAVA", "UPDATING");
                        DatabaseConnection.updateSensorValue(valuePath, sensorEvent.values);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
