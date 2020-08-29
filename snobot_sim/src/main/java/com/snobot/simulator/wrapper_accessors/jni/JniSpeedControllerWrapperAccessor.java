
package com.snobot.simulator.wrapper_accessors.jni;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
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
    public IPwmWrapper createSimulator(int aPort, String aType)
    {
        SpeedControllerWrapper wrapper = new SpeedControllerWrapper(aPort, aType);
        register(aPort, wrapper);
        return wrapper;
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
}
