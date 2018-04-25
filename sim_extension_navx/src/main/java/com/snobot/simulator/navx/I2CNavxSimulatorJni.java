package com.snobot.simulator.navx;

public class I2CNavxSimulatorJni
{
    public static native long createNavx(int aPort);
    public static native void deleteNavx(long aNativeAddress);

    public static native double getXAccel(long aNativeAddress);
    public static native double getYAccel(long aNativeAddress);
    public static native double getZAccel(long aNativeAddress);

    public static native void setXAccel(long aNativeAddress, double x);
    public static native void setYAccel(long aNativeAddress, double y);
    public static native void setZAccel(long aNativeAddress, double z);

    public static native double getYaw(long aNativeAddress);
    public static native double getPitch(long aNativeAddress);
    public static native double getRoll(long aNativeAddress);

    public static native void setYaw(long aNativeAddress, double yaw);
    public static native void setPitch(long aNativeAddress, double pitch);
    public static native void setRoll(long aNativeAddress, double roll);
}
