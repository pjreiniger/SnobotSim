package com.snobot.simulator.wrapper_accessors;

import java.util.Collection;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;

public interface SimulatorDataAccessor
{
    public static final double sDEFAULT_LOOP_PERIOD = .02;

    enum SnobotLogLevel
    {
        DEBUG, INFO, WARNING, ERROR
    }

    void setLogLevel(SnobotLogLevel aLogLevel);

    String getNativeBuildVersion();

    void reset();

    boolean connectTankDriveSimulator(int aLeftEncHandle, int aRightEncHandle, int aGyroHandle, double aTurnKp);

    Collection<Object> getSimulatorComponentConfigs();

    default DcMotorModelConfig createMotor(String aSelectedMotor, int aNumMotors, double aGearReduction, double aEfficiency)
    {
        return createMotor(aSelectedMotor, aNumMotors, aGearReduction, aEfficiency, false, false);
    }

    DcMotorModelConfig createMotor(String aSelectedMotor, int aNumMotors, double aGearReduction, double aEfficiency, boolean aInverted, boolean aBrake);

    DcMotorModelConfig createMotor(String aMotorType);

    boolean setSpeedControllerModel_Simple(int aScHandle, SimpleMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Static(int aScHandle, DcMotorModelConfig aMotorConfig, StaticLoadMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Gravitational(int aScHandle, DcMotorModelConfig aMotorConfig, GravityLoadMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Rotational(int aScHandle, DcMotorModelConfig aMotorConfig, RotationalLoadMotorSimulationConfig aConfig);

    /**
     * Updates the simulator components and speed controllers, moving them
     * forward aUpdateTime seconds in time. This function does not delay any
     * time
     */
    default void updateSimulatorComponents()
    {
        updateSimulatorComponents(sDEFAULT_LOOP_PERIOD);
    }

    /**
     * Updates the simulator components and speed controllers, moving them
     * forward aUpdateTime seconds in time. This function does not delay any
     * time
     *
     * @param aUpdatePeriod
     *            The time to move the components forward
     */
    void updateSimulatorComponents(double aUpdatePeriod);

    void removeSimulatorComponent(Object aComp);
}
