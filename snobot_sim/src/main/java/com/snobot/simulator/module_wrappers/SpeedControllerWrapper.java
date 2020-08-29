package com.snobot.simulator.module_wrappers;

import com.snobot.simulator.jni.LocalDcMotorModelConfig;
import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IMotorFeedbackSensor;
import com.snobot.simulator.module_wrapper.interfaces.IMotorSimulator;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class SpeedControllerWrapper implements IPwmWrapper
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

    @Override
    public boolean isInitialized()
    {
        return SpeedControllerWrapperJni.isInitialized(mHandle);
    }

    @Override
    public void setInitialized(boolean aInitialized)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close()
    {
        SpeedControllerWrapperJni.removeSimluator(mHandle);
    }

    @Override
    public void setName(String aName)
    {
        SpeedControllerWrapperJni.setName(mHandle, aName);
    }

    @Override
    public String getName()
    {
        return SpeedControllerWrapperJni.getName(mHandle);
    }

    @Override
    public boolean getWantsHidden()
    {
        return SpeedControllerWrapperJni.getWantsHidden(mHandle);
    }

    @Override
    public void setWantsHidden(boolean aVisible)
    {

    }

    @Override
    public double get()
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

    @Override
    public double getPosition()
    {
        return SpeedControllerWrapperJni.getPosition(mHandle);
    }

    @Override
    public double getVelocity()
    {
        return SpeedControllerWrapperJni.getVelocity(mHandle);
    }

    @Override
    public double getCurrent()
    {
        return SpeedControllerWrapperJni.getCurrent(mHandle);
    }

    @Override
    public void set(double aSpeed)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(double aWaitTime)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFeedbackSensor(IMotorFeedbackSensor aFeedbackSensor)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public IMotorFeedbackSensor getFeedbackSensor()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHandle()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAcceleration()
    {
        return SpeedControllerWrapperJni.getAcceleration(mHandle);
    }

    @Override
    public IMotorSimulator getMotorSimulator()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMotorSimulator(IMotorSimulator aSimulator)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        SpeedControllerWrapperJni.reset(mHandle, aPosition, aVelocity, aCurrent);
    }

    @Override
    public void reset()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType()
    {
        return null;
    }
}
