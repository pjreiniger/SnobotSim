
package com.snobot.simulator.jni;

public final class JoystickJni extends BaseSimulatorJni
{
    private JoystickJni()
    {

    }

    public static native void setJoystickInformation(int aJoystickHandle, float[] aAxesArray, short[] aPovsArray, int aButtonCount, int aButtonMask);
}
