
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.LocalDcMotorModelConfig;
import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JniSpeedControllerWrapperAccessor implements SpeedControllerWrapperAccessor
{
    @Override
    public boolean isInitialized(int aPort)
    {
        return SpeedControllerWrapperJni.isInitialized(aPort);
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return SpeedControllerWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimluator(int aPort)
    {
        SpeedControllerWrapperJni.removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        SpeedControllerWrapperJni.setName(aPort, aName);
    }

    @Override
    public String getName(int aPort)
    {
        return SpeedControllerWrapperJni.getName(aPort);
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return SpeedControllerWrapperJni.getWantsHidden(aPort);
    }

    @Override
    public double getVoltagePercentage(int aPort)
    {
        return SpeedControllerWrapperJni.getVoltagePercentage(aPort);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(SpeedControllerWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public DcMotorModelConfig getMotorConfig(int aPort)
    {
        LocalDcMotorModelConfig config = SpeedControllerWrapperJni.getMotorConfig(aPort);
        return config == null ? null : config.getConfig();
    }

    @Override
    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort)
    {
        double maxSpeed = SpeedControllerWrapperJni.getMotorSimSimpleModelConfig(aPort);
        return new SimpleMotorSimulationConfig(maxSpeed);
    }

    @Override
    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort)
    {
        double load = SpeedControllerWrapperJni.getMotorSimStaticModelConfig(aPort);
        return new StaticLoadMotorSimulationConfig(load);
    }

    @Override
    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort)
    {
        double load = SpeedControllerWrapperJni.getMotorSimGravitationalModelConfig(aPort);
        return new GravityLoadMotorSimulationConfig(load);
    }

    @Override
    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort)
    {
        double armCOM = SpeedControllerWrapperJni.getMotorSimRotationalModelConfig_armCenterOfMass(aPort);
        double armMass = SpeedControllerWrapperJni.getMotorSimRotationalModelConfig_armMass(aPort);
        return new RotationalLoadMotorSimulationConfig(armCOM, armMass);
    }

    @Override
    public MotorSimType getMotorSimType(int aHandle)
    {
        int rawType = SpeedControllerWrapperJni.getMotorSimTypeNative(aHandle);
        return MotorSimType.values()[rawType];
    }

    @Override
    public double getPosition(int aHandle)
    {
        return SpeedControllerWrapperJni.getPosition(aHandle);
    }

    @Override
    public double getVelocity(int aHandle)
    {
        return SpeedControllerWrapperJni.getVelocity(aHandle);
    }

    @Override
    public double getCurrent(int aHandle)
    {
        return SpeedControllerWrapperJni.getCurrent(aHandle);
    }

    @Override
    public double getAcceleration(int aHandle)
    {
        return SpeedControllerWrapperJni.getAcceleration(aHandle);
    }

    @Override
    public void reset(int aHandle, double aPosition, double aVelocity, double aCurrent)
    {
        SpeedControllerWrapperJni.reset(aHandle, aPosition, aVelocity, aCurrent);
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}
