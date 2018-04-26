
package com.snobot.simulator.ctre;

public class CtreJni
{

    public static native int registerCanMotorCallback(CtreCallback callback);

    public static native void cancelCanMotorCallback(int uid);

    public static native int registerCanPigeonImuCallback(CtreCallback callback);

    public static native void cancelCanPigeonImuCallback(int uid);
}