
package com.snobot.simulator.jni;

public final class SimulationConnectorJni extends BaseSimulatorJni
{
    private SimulationConnectorJni()
    {

    }

    public static native void updateLoop();

    public static native boolean setSpeedControllerModel_Simple(
            int aSpeedControllerHandle,
            double aMaxSpeed);

    public static native boolean setSpeedControllerModel_Static(
            int aSpeedControllerHandle,
            LocalDcMotorModelConfig aConfig,
            double aLoad,
            double aConversionFactor);

    public static native boolean setSpeedControllerModel_Gravitational(
            int aSpeedControllerHandle,
            LocalDcMotorModelConfig aConfig,
            double aLoad);

    public static native boolean setSpeedControllerModel_Rotational(
            int aSpeedControllerHandle,
            LocalDcMotorModelConfig aConfig,
            double aArmCenterOfMass,
            double aArmMass,
            double aConstantAssistTorque,
            double aOverCenterAssistTorque);

    public static native boolean connectTankDriveSimulator(
            int aLeftEncoderHandle,
            int aRightEncoderHandle,
            int aGyroHandle,
            double aTurnKp);

    public static native boolean setI2CDefault(int aPort, String aDefaultType);

    public static native boolean setSpiDefault(int aPort, String aDefaultType);
}
