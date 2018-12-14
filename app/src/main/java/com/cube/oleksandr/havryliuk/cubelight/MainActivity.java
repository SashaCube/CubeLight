package com.cube.oleksandr.havryliuk.cubelight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0;
    private static final boolean OFF = false;
    private static final boolean ON = true;

    private boolean lightState;
    private ImageView mainImage;
    private TextView textView;
    private Flash flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(checkCameraHardware()) {
            checkPermissions();

            initView();
            initFlash();
        }
        else{
            textView.setText("No camera on this device");
        }
    }

    public void initFlash(){
        lightState = OFF;
        flash = new Flash();
    }

    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    public void checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this,
                            "You declined to allow the app to access your camera",
                            Toast.LENGTH_SHORT);
                }
        }
    }

    public void initView() {
        textView = findViewById(R.id.text_view);
        mainImage = findViewById(R.id.main_image);
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lightState == OFF) {
                    lightOn();
                    return;
                }
                if (lightState == ON) {
                    lightOff();
                    return;
                }
            }
        });
    }

    public void lightOn() {

        mainImage.setImageDrawable((getResources().getDrawable(R.drawable.ic_light_bulb)));
        if(flash.isFlashReady()){
            flash.on();
        } else {
            textView.setText("Camera is unavailable");
        }
        lightState = ON;
    }

    public void lightOff() {
        mainImage.setImageDrawable((getResources().getDrawable(R.drawable.ic_light_bulb_off)));
        if(flash.isFlashReady()){
            flash.off();
        } else {
            textView.setText("Camera is unavailable");
        }
        lightState = OFF;
    }

    @Override
    protected void onResume() {
        super.onResume();
        flash.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lightOff();
        flash.close();
    }

}
