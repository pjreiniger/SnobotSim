package com.snobot.simulator.jni;

public final class MotorConfigFactoryJni extends BaseSimulatorJni
{
    public static LocalDcMotorModelConfig createMotor(String aName)
    {
        return createMotor(aName, 1, 1, 1);
    }

    private MotorConfigFactoryJni()
    {

    }

    public static native LocalDcMotorModelConfig createMotor(String aName, int aNumMotors, double aGearReduction, double aTransmissionEfficiency);
}
