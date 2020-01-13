package com.snobot.simulator.wrapper_accessors.java;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.config.SimulatorConfigWriter;
import com.snobot.simulator.config.v1.SimulatorConfigReaderV1;
import com.snobot.simulator.jni.RegisterCallbacksJni;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.module_wrapper.interfaces.ISimulatorUpdater;
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
import com.snobot.simulator.simulator_components.TankDriveGyroSimulator;
import com.snobot.simulator.simulator_components.config.TankDriveConfig;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor;

public class JavaSimulatorDataAccessor implements SimulatorDataAccessor
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaSimulatorDataAccessor.class);
    private static final String sUNKNOWN_SPEED_CONTROLLER_TEXT = "Unknown speed controller ";

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
        SensorActuatorRegistry.get().reset();
        RegisterCallbacksJni.reset();
    }

    @Override
    public boolean connectTankDriveSimulator(int aLeftEncHandle, int aRightEncHandle, int aGyroHandle, double aTurnKp)
    {
        TankDriveGyroSimulator simulator = new TankDriveGyroSimulator(
                new TankDriveConfig(aLeftEncHandle, aRightEncHandle, aGyroHandle, aTurnKp));

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

        IPwmWrapper speedController = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
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
        IPwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
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
        IPwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
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
        IPwmWrapper wrapper = SensorActuatorRegistry.get().getSpeedControllers().get(aScHandle);
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
    public void updateSimulatorComponents(double aUpdatePeriod)
    {
        for (IPwmWrapper wrapper : SensorActuatorRegistry.get().getSpeedControllers().values())
        {
            wrapper.update(aUpdatePeriod);
        }

        for (ISimulatorUpdater updater : SensorActuatorRegistry.get().getSimulatorComponents())
        {
            updater.update();
        }
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
    public boolean loadConfigFile(String aConfigFile)
    {
        return new SimulatorConfigReaderV1().loadConfig(aConfigFile);
    }

    @Override
    public boolean saveConfigFile(String aConfigFile)
    {
        return new SimulatorConfigWriter().writeConfig(aConfigFile);
    }

}
