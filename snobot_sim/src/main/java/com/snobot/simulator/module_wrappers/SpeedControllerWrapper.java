package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.LocalDcMotorModelConfig;
import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class SpeedControllerWrapper
{
    private final int mHandle;

    public SpeedControllerWrapper(int aPort, String aType)
    {
        mHandle = aPort;
        SpeedControllerWrapperJni.createSimulator(aPort, aType);
    }

    public SpeedControllerWrapper(int aHandle)
    {
        mHandle = aHandle;
    }

    public boolean isInitialized()
    {
        return SpeedControllerWrapperJni.isInitialized(mHandle);
    }

    public void removeSimulator()
    {
        SpeedControllerWrapperJni.removeSimluator(mHandle);
    }

    public void setName(String aName)
    {
        SpeedControllerWrapperJni.setName(mHandle, aName);
    }

    public String getName()
    {
        return SpeedControllerWrapperJni.getName(mHandle);
    }

    public boolean getWantsHidden()
    {
        return SpeedControllerWrapperJni.getWantsHidden(mHandle);
    }

    public double getVoltagePercentage()
    {
        return SpeedControllerWrapperJni.getVoltagePercentage(mHandle);
    }

    public DcMotorModelConfig getMotorConfig()
    {
        LocalDcMotorModelConfig config = SpeedControllerWrapperJni.getMotorConfig(mHandle);
        return config == null ? null : config.getConfig();
    }

    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig()
    {
        double maxSpeed = SpeedControllerWrapperJni.getMotorSimSimpleModelConfig(mHandle);
        return new SimpleMotorSimulationConfig(maxSpeed);
    }

    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig()
    {
        double load = SpeedControllerWrapperJni.getMotorSimStaticModelConfig_load(mHandle);
        double conversionFactor = SpeedControllerWrapperJni.getMotorSimStaticModelConfig_conversionFactor(mHandle);
        return new StaticLoadMotorSimulationConfig(load, conversionFactor);
    }

    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig()
    {
        double load = SpeedControllerWrapperJni.getMotorSimGravitationalModelConfig(mHandle);
        return new GravityLoadMotorSimulationConfig(load);
    }

    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig()
    {
        double armCOM = SpeedControllerWrapperJni.getMotorSimRotationalModelConfig_armCenterOfMass(mHandle);
        double armMass = SpeedControllerWrapperJni.getMotorSimRotationalModelConfig_armMass(mHandle);
        return new RotationalLoadMotorSimulationConfig(armCOM, armMass);
    }

    public SpeedControllerWrapperAccessor.MotorSimType getMotorSimType()
    {
        int rawType = SpeedControllerWrapperJni.getMotorSimTypeNative(mHandle);
        return SpeedControllerWrapperAccessor.MotorSimType.values()[rawType];
    }

    public double getPosition()
    {
        return SpeedControllerWrapperJni.getPosition(mHandle);
    }

    public double getVelocity()
    {
        return SpeedControllerWrapperJni.getVelocity(mHandle);
    }

    public double getCurrent()
    {
        return SpeedControllerWrapperJni.getCurrent(mHandle);
    }

    public double getAcceleration()
    {
        return SpeedControllerWrapperJni.getAcceleration(mHandle);
    }

    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        SpeedControllerWrapperJni.reset(mHandle, aPosition, aVelocity, aCurrent);
    }

    public String getType()
    {
        return null;
    }

    public void removeSimluator(int aPort)
    {
        SpeedControllerWrapperJni.removeSimluator(aPort);
    }
}
