package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;

public class RotationalLoadDcMotorSim extends BaseDcMotorSimulator
{
    protected static final double sGRAVITY = 9.8;

    protected final RotationalLoadMotorSimulationConfig mConfig;

    protected final IPwmWrapper mSpeedController;
    protected final double mArmInertia;
    protected final double mGravityBasedTorqueFactor;

    //Helper springs
    protected final double mConstantAssistTorque;
    protected final double mOverCenterAssistTorque;

    /**
     *
     * @param aModel
     *            The motor model
     * @param aSpeedController
     *            The speed controller that is wrapped
     */
    public RotationalLoadDcMotorSim(
            DcMotorModel aModel,
            IPwmWrapper aSpeedController,
            RotationalLoadMotorSimulationConfig aConfig)
    {
        super(aModel);

        mConfig = aConfig;

        mSpeedController = aSpeedController;
        mArmInertia = mConfig.mArmMass * mConfig.mArmCenterOfMass * mConfig.mArmCenterOfMass;
        mGravityBasedTorqueFactor = mConfig.mArmMass * mConfig.mArmCenterOfMass * sGRAVITY;
        mConstantAssistTorque = mConfig.mConstantAssistTorque;
        mOverCenterAssistTorque = mConfig.mOverCenterAssistTorque;
    }


    @Override
    public void update(double aCycleTime)
    {
        double position = mSpeedController.getPosition();
        double gravityTorque = mGravityBasedTorqueFactor * Math.sin(position);
        gravityTorque += mConstantAssistTorque;
        gravityTorque += mOverCenterAssistTorque * Math.sin(position);

        double inVolts = mVoltagePercentage * 12;

        mMotorModel.step(inVolts, mArmInertia, gravityTorque, aCycleTime);
    }

    public RotationalLoadMotorSimulationConfig getConfig()
    {
        return mConfig;
    }
}
