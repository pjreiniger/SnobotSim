package edu.wpi.first.hal.sim.mockdata;

public class ADXRS450_GyroDataJNI {
    public static native long createGyro(int aPort);

    public static native void deleteGyro(long aNativePointer);

    public static native double getAngle(long aNativePointer);
    public static native void setAngle(long aNativePointer, double x);
}
