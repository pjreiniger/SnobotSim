
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.module_wrappers.SpeedControllerWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JniSpeedControllerWrapperAccessor extends BaseWrapperAccessor<SpeedControllerWrapper> implements SpeedControllerWrapperAccessor
{
    public static final int sCAN_SC_OFFSET = 100;

    @Override
    public boolean isInitialized(int aPort)
    {
        return getWrapper(aPort).isInitialized();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return SpeedControllerWrapperJni.createSimulator(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        getWrapper(aPort).removeSimluator(aPort);
    }

    @Override
    public void setName(int aPort, String aName)
    {
        getWrapper(aPort).setName(aName);
    }

    @Override
    public String getName(int aPort)
    {
        return getWrapper(aPort).getName();
    }

    @Override
    public boolean getWantsHidden(int aPort)
    {
        return getWrapper(aPort).getWantsHidden();
    }

    @Override
    public double getVoltagePercentage(int aPort)
    {
        return getWrapper(aPort).getVoltagePercentage();
    }

    @Override
    protected SpeedControllerWrapper createWrapperForExistingType(int aHandle)
    {
        return new SpeedControllerWrapper(aHandle);
    }

    @Override
    public List<Integer> getPortList()
    {
        return IntStream.of(SpeedControllerWrapperJni.getPortList()).boxed().collect(Collectors.toList());
    }

    @Override
    public DcMotorModelConfig getMotorConfig(int aPort)
    {
        return getWrapper(aPort).getMotorConfig();
    }

    @Override
    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort)
    {
        return getWrapper(aPort).getMotorSimSimpleModelConfig();
    }

    @Override
    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort)
    {
        return getWrapper(aPort).getMotorSimStaticModelConfig();
    }

    @Override
    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort)
    {
        return getWrapper(aPort).getMotorSimGravitationalModelConfig();
    }

    @Override
    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort)
    {
        return getWrapper(aPort).getMotorSimRotationalModelConfig();
    }

    @Override
    public MotorSimType getMotorSimType(int aHandle)
    {
        return getWrapper(aHandle).getMotorSimType();
    }

    @Override
    public double getPosition(int aHandle)
    {
        return getWrapper(aHandle).getPosition();
    }

    @Override
    public double getVelocity(int aHandle)
    {
        return getWrapper(aHandle).getVelocity();
    }

    @Override
    public double getCurrent(int aHandle)
    {
        return getWrapper(aHandle).getCurrent();
    }

    @Override
    public double getAcceleration(int aHandle)
    {
        return getWrapper(aHandle).getAcceleration();
    }

    @Override
    public void reset(int aHandle, double aPosition, double aVelocity, double aCurrent)
    {
        getWrapper(aHandle).reset(aPosition, aVelocity, aCurrent);
    }

    @Override
    public String getType(int aPort)
    {
        return null;
    }
}
