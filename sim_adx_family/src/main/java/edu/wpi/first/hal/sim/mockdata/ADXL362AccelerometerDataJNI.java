package edu.wpi.first.hal.sim.mockdata;

public class ADXL362AccelerometerDataJNI {
    public static native long createAccelerometer(int aPort);

    public static native void deleteAccelerometer(long aNativePointer);

    public static native double getX(long aNativePointer);
    public static native double getY(long aNativePointer);
    public static native double getZ(long aNativePointer);

    public static native void setX(long aNativePointer, double x);
    public static native void setY(long aNativePointer, double y);
    public static native void setZ(long aNativePointer, double z);
}
