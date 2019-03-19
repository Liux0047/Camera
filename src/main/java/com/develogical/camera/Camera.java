package com.develogical.camera;

public class Camera implements WriteCompleteListener {

    private final Sensor sensor;
    private final MemoryCard mc;
    private boolean cameraOn=false;
    private boolean writingData=false;


    public Camera(Sensor sensor, MemoryCard mc) {

        this.sensor = sensor;
        this.mc = mc;
    }

    public void pressShutter() {

        // not implemented
        if (this.cameraOn) {

            byte[] data = this.sensor.readData();
            writingData = true;
            this.mc.write(data, this);

        }

    }

    public void powerOn() {
        this.sensor.powerUp();
        cameraOn = true;
        // not implemented
    }

    public void powerOff() {
        // not implemented
        cameraOn = false;

        if (!this.writingData) {
            this.sensor.powerDown();
        }



    }




    @Override
    public void writeComplete() {
        writingData = false;
        if (!cameraOn) {
            this.sensor.powerDown();
        }

    }
}

