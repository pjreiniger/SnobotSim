package com.snobot.simulator.wrapper_accessors;

import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;

public interface SimulatorDataAccessor
{
    enum SnobotLogLevel
    {
        DEBUG, INFO, WARNING, ERROR
    }

    public static final double sDEFAULT_LOOP_PERIOD = .02;

    void setLogLevel(SnobotLogLevel logLevel);

    String getNativeBuildVersion();

    void reset();

    boolean connectTankDriveSimulator(int leftEncHandle, int rightEncHandle, int gyroHandle, double turnKp);

    Collection<Object> getSimulatorComponentConfigs();

    default DcMotorModelConfig createMotor(String selectedMotor, int numMotors, double gearReduction, double efficiency)
    {
        return createMotor(selectedMotor, numMotors, gearReduction, efficiency, false, false);
    }

    DcMotorModelConfig createMotor(String selectedMotor, int numMotors, double gearReduction, double efficiency, boolean inverted, boolean brake);

    DcMotorModelConfig createMotor(String motorType);

    boolean setSpeedControllerModel_Simple(int aScHandle, SimpleMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Static(int aScHandle, DcMotorModelConfig aMotorConfig, StaticLoadMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Gravitational(int aScHandle, DcMotorModelConfig aMotorConfig, GravityLoadMotorSimulationConfig aConfig);

    boolean setSpeedControllerModel_Rotational(int aScHandle, DcMotorModelConfig aMotorConfig, RotationalLoadMotorSimulationConfig aConfig);

    void setDisabled(boolean b);

    void setAutonomous(boolean b);

    double getMatchTime();

    void waitForProgramToStart();

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

    /**
     * Simulates waiting for a DS packet to come in. Actually waits the amount
     * of time. Uses default tactical timing of 20ms/50hz
     */
    default void waitForNextUpdateLoop()
    {
        waitForNextUpdateLoop(sDEFAULT_LOOP_PERIOD);
    }

    /**
     * Simulates waiting for a DS packet to come in. Actually waits the amount
     * of time
     * 
     * @param aUpdatePeriod
     *            The time, in seconds, to pause before notifying the DS it has
     *            received data
     */
    void waitForNextUpdateLoop(double aUpdatePeriod);

    void setJoystickInformation(int aJoystick, float[] axisValues, short[] povValues, int buttonCount, int buttonMask);

    void setDefaultSpiSimulator(int aPort, String aType);

    void setDefaultI2CSimulator(int aPort, String aType);

    Collection<String> getAvailableSpiSimulators();

    Collection<String> getAvailableI2CSimulators();

    Map<Integer, String> getDefaultI2CWrappers();

    Map<Integer, String> getDefaultSpiWrappers();

    enum MatchType
    {
        None, Practice, Qualification, Elimination
    }

    void setMatchInfo(String eventName, MatchType matchType, int matchNumber, int replayNumber, String gameSpecificMessage);

    void removeSimulatorComponent(Object comp);

    double getTimeSinceEnabled();

}
