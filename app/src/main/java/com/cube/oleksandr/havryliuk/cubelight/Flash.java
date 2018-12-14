package com.cube.oleksandr.havryliuk.cubelight;

import android.hardware.Camera;

public class Flash {
    private static final String TAG = "FLASH";

    private Camera camera;
    private Camera.Parameters cameraParameters;

    public void open(){
        camera = getCameraInstance();
        cameraParameters = camera.getParameters();
    }

    public boolean isFlashReady(){
        if(camera == null){
            return false;
        }
        return true;
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){

        }
        // returns null if camera is unavailable
        return c;
    }

    public void close(){
        releaseCamera();
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();
            camera = null;
        }
    }

    public void on(){
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(cameraParameters);
    }

    public void off(){
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(cameraParameters);
    }
}