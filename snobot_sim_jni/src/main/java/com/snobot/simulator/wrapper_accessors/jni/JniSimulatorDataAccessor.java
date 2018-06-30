package com.snobot.simulator.wrapper_accessors.jni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.jni.JoystickJni;
import com.snobot.simulator.jni.LocalDcMotorModelConfig;
import com.snobot.simulator.jni.MotorConfigFactoryJni;
import com.snobot.simulator.jni.RobotStateSingletonJni;
import com.snobot.simulator.jni.SimulationConnectorJni;
import com.snobot.simulator.jni.SnobotSimulatorJni;
import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;

public class JniSimulatorDataAccessor implements SimulatorDataAccessor
{
    @Override
    public void setLogLevel(SnobotLogLevel aLogLevel)
    {
        SnobotSimulatorJni.initializeLogging(aLogLevel.ordinal());
    }

    @Override
    public String getNativeBuildVersion()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset()
    {
        SnobotSimulatorJni.reset();
    }

    @Override
    public boolean connectTankDriveSimulator(int aLeftEncHandle, int aRightEncHandle, int aGyroHandle, double aTurnKp)
    {
        return SimulationConnectorJni.connectTankDriveSimulator(aLeftEncHandle, aRightEncHandle, aGyroHandle, aTurnKp);
    }

    @Override
    public Collection<Object> getSimulatorComponentConfigs()
    {
        return new ArrayList<>();
    }

    @Override
    public DcMotorModelConfig createMotor(String aMotorType, int aNumMotors, double aGearReduction, double aEfficiency, boolean aInverted, boolean aBrake)
    {
        LocalDcMotorModelConfig config = MotorConfigFactoryJni.createMotor(aMotorType, aNumMotors, aGearReduction, aEfficiency);
        config.getConfig().mFactoryParams.setHasBrake(aBrake);
        config.getConfig().mFactoryParams.setInverted(aInverted);
        return config.getConfig();
    }

    @Override
    public DcMotorModelConfig createMotor(String aMotorType)
    {
        LocalDcMotorModelConfig config = MotorConfigFactoryJni.createMotor(aMotorType);
        return config.getConfig();
    }

    @Override
    public boolean setSpeedControllerModel_Simple(int aScHandle, SimpleMotorSimulationConfig aConfig)
    {
        return SimulationConnectorJni.setSpeedControllerModel_Simple(aScHandle, aConfig.mMaxSpeed);
    }

    @Override
    public boolean setSpeedControllerModel_Static(int aScHandle, DcMotorModelConfig aMotorConfig, StaticLoadMotorSimulationConfig aConfig)
    {
        LocalDcMotorModelConfig config = new LocalDcMotorModelConfig(aMotorConfig);
        return SimulationConnectorJni.setSpeedControllerModel_Static(aScHandle, config, aConfig.mLoad, aConfig.mConversionFactor);
    }

    @Override
    public boolean setSpeedControllerModel_Gravitational(int aScHandle, DcMotorModelConfig aMotorConfig, GravityLoadMotorSimulationConfig aConfig)
    {
        LocalDcMotorModelConfig config = new LocalDcMotorModelConfig(aMotorConfig);
        return SimulationConnectorJni.setSpeedControllerModel_Gravitational(aScHandle, config, aConfig.getLoad());
    }

    @Override
    public boolean setSpeedControllerModel_Rotational(int aScHandle, DcMotorModelConfig aMotorConfig, RotationalLoadMotorSimulationConfig aConfig)
    {
        LocalDcMotorModelConfig config = new LocalDcMotorModelConfig(aMotorConfig);
        return SimulationConnectorJni.setSpeedControllerModel_Rotational(aScHandle, config, aConfig.mArmCenterOfMass, aConfig.mArmMass,
                aConfig.mConstantAssistTorque, aConfig.mOverCenterAssistTorque);
    }

    @Override
    public void setDisabled(boolean aDisabled)
    {
        RobotStateSingletonJni.setDisabled(aDisabled);
    }

    @Override
    public void setAutonomous(boolean aAuton)
    {
        RobotStateSingletonJni.setAutonomous(aAuton);
    }

    @Override
    public double getMatchTime()
    {
        return RobotStateSingletonJni.getMatchTime();
    }

    @Override
    public void waitForProgramToStart()
    {
        RobotStateSingletonJni.waitForProgramToStart();
    }

    @Override
    public void updateSimulatorComponents(double aUpdatePeriod)
    {
        SpeedControllerWrapperJni.updateAllSpeedControllers(aUpdatePeriod);
        SimulationConnectorJni.updateLoop();
    }

    @Override
    public void waitForNextUpdateLoop(double aUpdatePeriod)
    {
        RobotStateSingletonJni.waitForNextUpdateLoop(aUpdatePeriod);
    }

    @Override
    public void setJoystickInformation(int aIndex, float[] aAxisValues, short[] aPovValues, int aButtonCount, int aButtonMask)
    {
        JoystickJni.setJoystickInformation(aIndex, aAxisValues, aPovValues, aButtonCount, aButtonMask);
    }

    @Override
    public Collection<String> getAvailableSpiSimulators()
    {
        return Arrays.asList("NavX", "ADXRS450", "ADXL345", "ADXL362");
    }

    @Override
    public Collection<String> getAvailableI2CSimulators()
    {
        return Arrays.asList("NavX", "ADXL345");
    }

    @Override
    public void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeSimulatorComponent(Object aComp)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public double getTimeSinceEnabled()
    {
        // TODO Auto-generated method stub
        return -1;
    }

    @Override
    public boolean createSpiSimulator(int aPort, String aType)
    {
        SimulationConnectorJni.setSpiDefault(aPort, aType);
        return true;
    }

    @Override
    public boolean createI2CSimulator(int aPort, String aType)
    {
        SimulationConnectorJni.setI2CDefault(aPort, aType);
        return true;
    }

    @Override
    public Map<Integer, String> getI2CWrapperTypes()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, String> getSpiWrapperTypes()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
