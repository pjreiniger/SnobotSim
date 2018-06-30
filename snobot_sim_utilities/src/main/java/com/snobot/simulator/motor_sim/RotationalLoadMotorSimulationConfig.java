package com.snobot.simulator.motor_sim;

public class RotationalLoadMotorSimulationConfig implements IMotorSimulatorConfig
{
    public final double mArmCenterOfMass;
    public final double mArmMass;
    public final double mConstantAssistTorque;
    public final double mOverCenterAssistTorque;

    @SuppressWarnings("unused")
    private RotationalLoadMotorSimulationConfig()
    {
        this(0, 0);
    }

    /**
     *
     * @param aArmCenterOfMass
     *            The location of the center of mass, in meters
     * @param aArmMass
     *            The mass of the arm, in kg
     */
    public RotationalLoadMotorSimulationConfig(double aArmCenterOfMass, double aArmMass)
    {
        this(aArmCenterOfMass, aArmMass, 0, 0);
    }

    /**
     *
     * @param aArmCenterOfMass
     *            The location of the center of mass, in meters
     * @param aArmMass
     *            The mass of the arm, in kg
     * @param aConstantAssistTorque
     *            torque provided constantly over the entire range of motion, in
     *            N*m
     * @param aOverCenterAssistTorque
     *            torque provided varying with sin of angle (same effect as
     *            gravity) due to over-center, in N*m
     */
    public RotationalLoadMotorSimulationConfig(double aArmCenterOfMass, double aArmMass, double aConstantAssistTorque, double aOverCenterAssistTorque)
    {
        mArmCenterOfMass = aArmCenterOfMass;
        mArmMass = aArmMass;
        mConstantAssistTorque = aConstantAssistTorque;
        mOverCenterAssistTorque = aOverCenterAssistTorque;
    }
}
