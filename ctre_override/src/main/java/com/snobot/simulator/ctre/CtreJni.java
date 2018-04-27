
package com.snobot.simulator.ctre;

public final class CtreJni
{
    private CtreJni()
    {

    }

    public static native int registerCanMotorCallback(CtreCallback aCallback);

    public static native void cancelCanMotorCallback(int aUid);

    public static native int registerCanPigeonImuCallback(CtreCallback aCallback);

    public static native void cancelCanPigeonImuCallback(int aUid);
}
