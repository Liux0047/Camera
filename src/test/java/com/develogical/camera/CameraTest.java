package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);
        Camera camera = new Camera(sensor, null);

        camera.powerOn();

        verify(sensor).powerUp();
        // write your test here
    }


    @Test
    public void switchingOffCameraOffSensor () {
        Sensor sensor = mock(Sensor.class);
        Camera camera = new Camera(sensor, null);
        camera.powerOff();
        verify(sensor).powerDown();
    }

    @Test
    public void pressShutterCopyData () {
        Sensor sensor = mock(Sensor.class);
        MemoryCard mc = mock(MemoryCard.class);

        byte[] data = {1};
        when(sensor.readData()).thenReturn(data);
        Camera camera = new Camera(sensor, mc);
        camera.powerOn();
        camera.pressShutter();
        verify(sensor).readData();
        verify(mc).write(eq(data), any(WriteCompleteListener.class));

    }

    @Test
    public void pressShutterCopyDataOff () {
        Sensor sensor = mock(Sensor.class);
        MemoryCard mc = mock(MemoryCard.class);

        Camera camera = new Camera(sensor, mc);
        camera.pressShutter();

        verifyZeroInteractions(sensor);
        verifyZeroInteractions(mc);
    }

    @Test
    public void cameraPowerOffWhenWriting () {
        Sensor sensor = mock(Sensor.class);
        MemoryCard mc = mock(MemoryCard.class);

        Camera camera = new Camera(sensor, mc);
        camera.powerOn();
        camera.pressShutter();
        verify(sensor).powerUp();
        verify(sensor).readData();

        camera.powerOff();
        verifyNoMoreInteractions(sensor);
    }

}
