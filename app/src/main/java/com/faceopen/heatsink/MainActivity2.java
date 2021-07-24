package com.faceopen.heatsink;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.thermal.seekware.SeekCamera;
import com.thermal.seekware.SeekCameraManager;
import com.thermal.seekware.SeekImage;
import com.thermal.seekware.SeekImageReader;
import com.thermal.seekware.SeekUtility;
import com.thermal.seekware.Thermography;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements SeekImageReader.OnImageAvailableListener {

    private TextView thermographyInfo;
    private SeekCamera seekCamera;
    private SeekCameraManager seekCameraManager;
    private SeekImageReader seekImageReader;
    private SeekCamera.StateCallback stateCallback = new SeekCamera.StateCallbackAdapter() {
        @Override
        public void onOpened(SeekCamera sc) {
            seekCamera = sc;
            seekCamera.createSeekCameraCaptureSession(seekImageReader);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        thermographyInfo = findViewById(R.id.thermography_info);
        seekImageReader = new SeekImageReader();
        seekImageReader.setOnImageAvailableListener(this);
        seekCameraManager = new SeekCameraManager(this, null, stateCallback);
    }
String TAG = "sdsdsdsds";
    @Override
    public void onImageAvailable(final SeekImage seekImage) {
        runOnUiThread(() -> {
            seekImage.getThermography().setTemperatureUnit(SeekUtility.Temperature.Unit.FAHRENHEIT);
            Thermography.Data r = seekImage.getThermography().getThermalData();
            Log.i("testing_data",r.getTemperature(100,100).toString()+" ");
            String text = seekCamera.toString() +
                    "\nMin: " + seekImage.getThermography().getMinSpot().getTemperature().toString() +
                    "\nSpot: " + seekImage.getThermography().getCenterSpot().getTemperature().toString() +
                    "\nMax: " + seekImage.getThermography().getMaxSpot().getTemperature().toString();
            thermographyInfo.setText(text);
        });
    }


}