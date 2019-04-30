package com.snobot.simulator.wrapper_accessors.jni;

import java.util.ArrayList;
import java.util.Collection;

import com.snobot.simulator.jni.LocalDcMotorModelConfig;
import com.snobot.simulator.jni.MotorConfigFactoryJni;
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
    public void updateSimulatorComponents(double aUpdatePeriod)
    {
        SpeedControllerWrapperJni.updateAllSpeedControllers(aUpdatePeriod);
        SimulationConnectorJni.updateLoop();
    }

    @Override
    public void removeSimulatorComponent(Object aComp)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean loadConfigFile(String aConfigFile)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean saveConfigFile(String aConfigFile)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
