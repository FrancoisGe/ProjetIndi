package Client;


import Client.Listener.AttachListenerCo;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.Phidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.*;

import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by François on 05-11-16.
 */
public class NewPhidgetCo extends Phidget {

    private Socket socket;



    private LinkedList sensorChangeListeners = new LinkedList();
    private long nativeSensorChangeHandler = 0L;

    public NewPhidgetCo(Socket socket) throws PhidgetException {
        super(100000000);
        this.socket = socket;
        AttachListenerCo attachListener = new AttachListenerCo(socket);

        this.addAttachListener( attachListener);
        attachListener.getBufferReader();



    }
    private static native long create() throws PhidgetException;




    public native int getOutputCount() throws PhidgetException;

    public native int getInputCount() throws PhidgetException;

    public native int getSensorCount() throws PhidgetException;

    public native int getDataRateMin(int var1) throws PhidgetException;

    public native int getDataRateMax(int var1) throws PhidgetException;

    public native boolean getInputState(int var1) throws PhidgetException;

    public native boolean getOutputState(int var1) throws PhidgetException;

    public native int getSensorValue(int var1) throws PhidgetException;

    public native int getSensorRawValue(int var1) throws PhidgetException;

    public native int getSensorChangeTrigger(int var1) throws PhidgetException;

    public native int getDataRate(int var1) throws PhidgetException;

    public native boolean getRatiometric() throws PhidgetException;

    public native void setOutputState(int var1, boolean var2) throws PhidgetException;

    public native void setSensorChangeTrigger(int var1, int var2) throws PhidgetException;

    public native void setDataRate(int var1, int var2) throws PhidgetException;

    public native void setRatiometric(boolean var1) throws PhidgetException;

    private final void enableDeviceSpecificEvents(boolean var1) {

        this.enableSensorChangeEvents(var1 && this.sensorChangeListeners.size() > 0);
    }











    public final void addSensorChangeListener(SensorChangeListener var1) {
        LinkedList var2 = this.sensorChangeListeners;
        synchronized(this.sensorChangeListeners) {
            this.sensorChangeListeners.add(var1);
            this.enableSensorChangeEvents(true);
        }
    }

    public final void removeSensorChangeListener(SensorChangeListener var1) {
        LinkedList var2 = this.sensorChangeListeners;
        synchronized(this.sensorChangeListeners) {
            this.sensorChangeListeners.remove(var1);
            this.enableSensorChangeEvents(this.sensorChangeListeners.size() > 0);
        }
    }

    private void fireSensorChange(SensorChangeEvent var1) {
        LinkedList var2 = this.sensorChangeListeners;
        synchronized(this.sensorChangeListeners) {
            Iterator var3 = this.sensorChangeListeners.iterator();

            while(var3.hasNext()) {
                ((SensorChangeListener)var3.next()).sensorChanged(var1);
            }

        }
    }

    private native void enableSensorChangeEvents(boolean var1);


}
