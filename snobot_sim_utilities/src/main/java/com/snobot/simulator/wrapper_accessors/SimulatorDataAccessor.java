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

    void setDisabled(boolean aDisabled);

    void setAutonomous(boolean aAutonomous);

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

    void setJoystickInformation(int aJoystick, float[] aAxisValues, short[] aPovValues, int aButtonCount, int aButtonMask);

    boolean createSpiSimulator(int aPort, String aType);

    boolean createI2CSimulator(int aPort, String aType);

    Collection<String> getAvailableSpiSimulators();

    Collection<String> getAvailableI2CSimulators();

    enum MatchType
    {
        None, Practice, Qualification, Elimination
    }

    void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage);

    void removeSimulatorComponent(Object aComp);

    double getTimeSinceEnabled();

    Map<Integer, String> getI2CWrapperTypes();

    Map<Integer, String> getSpiWrapperTypes();

}
