package com.snobot.simulator.motor_sim.motor_factory;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;

public final class MakeTransmission
{
    private MakeTransmission()
    {

    }

    public static DcMotorModelConfig makeTransmission(DcMotorModelConfig aMotor, int aNumMotors, double aGearReduction, double aEfficiency)
    {
        DcMotorModelConfig.FactoryParams factoryParams = new DcMotorModelConfig.FactoryParams(aMotor.mFactoryParams.mMotorType, aNumMotors,
                aGearReduction, aEfficiency, aMotor.mFactoryParams.ismInverted(), aMotor.mFactoryParams.ismHasBrake());
        DcMotorModelConfig.MotorParams modifiedMotorParams = new DcMotorModelConfig.MotorParams(
                aMotor.mMotorParams.mNominalVoltage,
                aMotor.mMotorParams.mFreeSpeedRpm / aGearReduction,
                aMotor.mMotorParams.mFreeCurrent  * aNumMotors,
                aMotor.mMotorParams.mStallTorque  * aNumMotors,
                aMotor.mMotorParams.mStallCurrent * aNumMotors,
                aMotor.mMotorParams.mMortorInertia * aNumMotors * aGearReduction * aGearReduction,
                aEfficiency * aNumMotors * aGearReduction);

        return new DcMotorModelConfig(factoryParams, modifiedMotorParams);
    }
}
