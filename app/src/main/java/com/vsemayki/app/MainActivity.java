package com.vsemayki.app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.HttpMetric;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRequest();
            }
        });
    }

    private void launchRequest() {
        new Thread() {
            @Override
            public void run() {
                manualNetworkTrace();
            }
        }.start();
    }

    public void manualNetworkTrace() {
        // [START perf_manual_network_trace]
        HttpMetric metric =
                FirebasePerformance.getInstance().newHttpMetric("https://www.google_firebase_25_13_0.com",
                        FirebasePerformance.HttpMethod.GET);
        metric.start();

        try {
            Thread.sleep(500 + new Random().nextInt(5) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        metric.setRequestPayloadSize(new Random().nextInt(255) + 255);
        metric.setHttpResponseCode(200);
        metric.stop();
        // [END perf_manual_network_trace]
    }
}