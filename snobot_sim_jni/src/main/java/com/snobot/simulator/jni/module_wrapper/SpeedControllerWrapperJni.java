
package com.snobot.simulator.jni.module_wrapper;

import com.snobot.simulator.jni.BaseSimulatorJni;
import com.snobot.simulator.jni.LocalDcMotorModelConfig;

public final class SpeedControllerWrapperJni extends BaseSimulatorJni
{
    private SpeedControllerWrapperJni()
    {

    }

    public static native boolean isInitialized(int aPort);

    public static native void setName(int aPort, String aName);

    public static native String getName(int aPort);

    public static native boolean getWantsHidden(int aPort);

    public static native boolean createSimulator(int aPort, String aType);

    public static native void removeSimluator(int aPort);

    public static native double getVoltagePercentage(int aPort);

    public static native double getPosition(int aHandle);

    public static native double getVelocity(int aHandle);

    public static native double getAcceleration(int aHandle);

    public static native double getCurrent(int aHandle);

    public static native void updateAllSpeedControllers(double aUpdatePeriod);

    public static native int getMotorSimTypeNative(int aPort);

    public static native int[] getPortList();

    public static native void reset(int aHandle, double aPosition, double aVelocity, double aCurrent);

    public static native LocalDcMotorModelConfig getMotorConfig(int aPort);

    public static native double getMotorSimSimpleModelConfig(int aPort);

    public static native double getMotorSimStaticModelConfig_load(int aPort);

    public static native double getMotorSimStaticModelConfig_conversionFactor(int aPort);

    public static native double getMotorSimGravitationalModelConfig(int aPort);

    public static native double getMotorSimRotationalModelConfig_armCenterOfMass(int aPort);

    public static native double getMotorSimRotationalModelConfig_armMass(int aPort);

}
