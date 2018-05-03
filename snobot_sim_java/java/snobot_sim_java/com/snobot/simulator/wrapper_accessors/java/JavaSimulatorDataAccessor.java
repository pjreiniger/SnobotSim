package com.snobot.simulator.wrapper_accessors.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.jni.RegisterCallbacksJni;
import com.snobot.simulator.jni.standard_components.I2CCallbackJni;
import com.snobot.simulator.jni.standard_components.SpiCallbackJni;
import com.snobot.simulator.module_wrapper.PwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModel;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadDcMotorSim;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.IMotorSimulator;
import com.snobot.simulator.motor_sim.RotationalLoadDcMotorSim;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulator;
import com.snobot.simulator.motor_sim.StaticLoadDcMotorSim;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.motor_factory.MakeTransmission;
import com.snobot.simulator.motor_sim.motor_factory.PublishedMotorFactory;
import com.snobot.simulator.motor_sim.motor_factory.VexMotorFactory;
import com.snobot.simulator.simulator_components.ISimulatorUpdater;
import com.snobot.simulator.simulator_components.TankDriveGyroSimulator;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;

public class JavaSimulatorDataAccessor implements SimulatorDataAccessor
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaSimulatorDataAccessor.class);
    private static final String sUNKNOWN_SPEED_CONTROLLER_TEXT = "Unknown speed controller ";

    private double mEnabledTime = -1;

    @Override
    public void setLogLevel(SnobotLogLevel aLogLevel)
    {
        // Nothing to do
    }

    @Override
    public String getNativeBuildVersion()
    {
        return JaveSimulatorVersion.Version;
    }

    @Override
    public void reset()
    {
        getDefaultI2CWrappers().clear();
        getDefaultSpiWrappers().clear();
        SensorActuatorRegistry.get().reset();
        RegisterCallbacksJni.reset();
    }

    @Override
    public boolean connectTankDriveSimulator(int aLeftEncHandle, int aRightEncHandle, int aGyroHandle, double aTurnKp)
    {
        TankDriveGyroSimulator simulator = new TankDriveGyroSimulator(
                new TankDriveGyroSimulator.TankDriveConfig(aLeftEncHandle, aRightEncHandle, aGyroHandle, aTurnKp));

        return simulator.isSetup();
    }

    @Override
    public Collection<Object> getSimulatorComponentConfigs()
    {
        Collection<Object> output = new ArrayList<>();

        for (ISimulatorUpdater sim : SensorActuatorRegistry.get().getSimulatorComponents())
        {
            output.add(sim.getConfig());
        }

        return output;
    }

    @Override
    public DcMotorModelConfig createMotor(String aMotorType)
    {
        if ("rs775".equals(aMotorType))
        {
            return PublishedMotorFactory.makeRS775();
        }
        else
        {
            return VexMotorFactory.createMotor(aMotorType);
        }
    }

    @Override
    public DcMotorModelConfig createMotor(String aMotorType, int aNumMotors, double aGearReduction, double aEfficiency, boolean aInverted, boolean aBrake)
    {
        DcMotorModelConfig config = createMotor(aMotorType);
        config.mFactoryParams.setHasBrake(aBrake);
        config.mFactoryParams.setInverted(aInverted);
        return MakeTransmission.makeTransmission(config, aNumMotors, aGearReduction, aEfficiency);
    }

    @Override
    public boolean setSpeedControllerModel_Simple(int aScHandle, SimpleMotorSimulationConfig aConfig)
    {
        boolean success = false;

        PwmWrapper speedController = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
        if (speedController == null)
        {
            sLOGGER.log(Level.ERROR, sUNKNOWN_SPEED_CONTROLLER_TEXT + aScHandle);
        }
        else
        {
            speedController.setMotorSimulator(new SimpleMotorSimulator(aConfig));
            success = true;
        }

        return success;
    }

    @Override
    public boolean setSpeedControllerModel_Static(int aScHandle, DcMotorModelConfig aMotorConfig, StaticLoadMotorSimulationConfig aConfig)
    {
        boolean success = false;
        PwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
        IMotorSimulator motorModel = new StaticLoadDcMotorSim(new DcMotorModel(aMotorConfig), aConfig);
        if (wrapper == null)
        {
            sLOGGER.log(Level.ERROR, sUNKNOWN_SPEED_CONTROLLER_TEXT + aScHandle);
        }
        else
        {
            wrapper.setMotorSimulator(motorModel);
            success = true;
        }

        return success;
    }

    @Override
    public boolean setSpeedControllerModel_Gravitational(int aScHandle, DcMotorModelConfig aMotorConfig, GravityLoadMotorSimulationConfig aConfig)
    {
        boolean success = false;
        PwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
        IMotorSimulator motorModel = new GravityLoadDcMotorSim(new DcMotorModel(aMotorConfig), aConfig);
        if (wrapper == null)
        {
            sLOGGER.log(Level.ERROR, sUNKNOWN_SPEED_CONTROLLER_TEXT + aScHandle);
        }
        else
        {
            wrapper.setMotorSimulator(motorModel);
            success = true;
        }

        return success;
    }

    @Override
    public boolean setSpeedControllerModel_Rotational(int aScHandle, DcMotorModelConfig aMotorConfig, RotationalLoadMotorSimulationConfig aConfig)
    {
        boolean success = false;
        PwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
        IMotorSimulator motorModel = new RotationalLoadDcMotorSim(new DcMotorModel(aMotorConfig), wrapper, aConfig);
        if (wrapper == null)
        {
            sLOGGER.log(Level.ERROR, sUNKNOWN_SPEED_CONTROLLER_TEXT + aScHandle);
        }
        else
        {
            wrapper.setMotorSimulator(motorModel);
            success = true;
        }

        return success;
    }

    @Override
    public void setDisabled(boolean aDisabled)
    {
        DriverStationDataJNI.setEnabled(!aDisabled);
        DriverStationDataJNI.setDsAttached(!aDisabled);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public void setAutonomous(boolean aAuton)
    {
        DriverStationDataJNI.setAutonomous(aAuton);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public double getMatchTime()
    {
        return DriverStationDataJNI.getMatchTime();
    }

    @Override
    public void waitForProgramToStart()
    {
        SimulatorJNI.waitForProgramStart();
    }

    @Override
    public void updateSimulatorComponents(double aUpdatePeriod)
    {
        for (PwmWrapper wrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            wrapper.update(aUpdatePeriod);
        }

        for (ISimulatorUpdater updater : SensorActuatorRegistry.get().getSimulatorComponents())
        {
            updater.update();
        }
    }

    private double mNextExpectedTime = System.nanoTime() * 1e-9;

    @Override
    public void waitForNextUpdateLoop(double aUpdatePeriod)
    {
        double currentTime = System.nanoTime() * 1e-9;
        double diff = currentTime - mNextExpectedTime;
        double timeToWait = aUpdatePeriod - diff;

        mNextExpectedTime += aUpdatePeriod;

        try
        {
            if (timeToWait > 0)
            {
                Thread.sleep((long) (timeToWait * 1000));
            }

        }
        catch (Exception e)
        {
            sLOGGER.log(Level.ERROR, e);
        }

        DriverStationDataJNI.notifyNewData();
        DriverStationDataJNI.setMatchTime(DriverStationDataJNI.getMatchTime() + aUpdatePeriod);
        // DriverStationSimulatorJni.delayForNextUpdateLoop(aUpdatePeriod);
    }

    @Override
    public void setJoystickInformation(int aJoystickHandle, float[] aAxesArray, short[] aPovsArray, int aButtonCount, int aButtonMask)
    {
        DriverStationDataJNI.setJoystickAxes((byte) aJoystickHandle, aAxesArray);
        DriverStationDataJNI.setJoystickPOVs((byte) aJoystickHandle, aPovsArray);
        DriverStationDataJNI.setJoystickButtons((byte) aJoystickHandle, aButtonMask, aButtonCount);
    }

    @Override
    public void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage)
    {
        DriverStationDataJNI.setMatchInfo(aEventName, aMatchType.ordinal(), aMatchNumber, aReplayNumber, aGameSpecificMessage);
    }

    @Override
    public void setDefaultSpiSimulator(int aPort, String aType)
    {
        SpiCallbackJni.setDefaultWrapper(aPort, aType);
    }

    @Override
    public void setDefaultI2CSimulator(int aPort, String aType)
    {
        I2CCallbackJni.setDefaultWrapper(aPort, aType);
    }

    @Override
    public Collection<String> getAvailableSpiSimulators()
    {
        return SpiCallbackJni.getAvailableTypes();
    }

    @Override
    public Collection<String> getAvailableI2CSimulators()
    {
        return I2CCallbackJni.getAvailableTypes();
    }

    @Override
    public Map<Integer, String> getDefaultI2CWrappers()
    {
        return I2CCallbackJni.getDefaults();
    }

    @Override
    public Map<Integer, String> getDefaultSpiWrappers()
    {
        return SpiCallbackJni.getDefaults();
    }

    @Override
    public void removeSimulatorComponent(Object aComponent)
    {
        for (ISimulatorUpdater sim : SensorActuatorRegistry.get().getSimulatorComponents())
        {
            if (sim.getConfig().equals(aComponent))
            {
                SensorActuatorRegistry.get().getSimulatorComponents().remove(sim);
                break;
            }
        }
    }

    @Override
    public double getTimeSinceEnabled()
    {
        double currentTime = System.currentTimeMillis() * 1e-3;
        return currentTime - mEnabledTime;
    }

}
