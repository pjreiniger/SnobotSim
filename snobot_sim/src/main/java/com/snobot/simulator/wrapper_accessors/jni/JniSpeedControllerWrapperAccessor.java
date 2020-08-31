
package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.module_wrappers.SpeedControllerWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JniSpeedControllerWrapperAccessor extends BaseWrapperAccessor<IPwmWrapper> implements SpeedControllerWrapperAccessor
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
    public int[] getPortList()
    {
        return SpeedControllerWrapperJni.getPortList();
    }

    private SpeedControllerWrapper getSpeedControllerWrapper(int aHandle)
    {
        return (SpeedControllerWrapper) getWrapper(aHandle);
    }

    @Override
    public DcMotorModelConfig getMotorConfig(int aPort)
    {
        return getSpeedControllerWrapper(aPort).getMotorConfig();
    }

    @Override
    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort)
    {
        return getSpeedControllerWrapper(aPort).getMotorSimSimpleModelConfig();
    }

    @Override
    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort)
    {
        return getSpeedControllerWrapper(aPort).getMotorSimStaticModelConfig();
    }

    @Override
    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort)
    {
        return getSpeedControllerWrapper(aPort).getMotorSimGravitationalModelConfig();
    }

    @Override
    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort)
    {
        return getSpeedControllerWrapper(aPort).getMotorSimRotationalModelConfig();
    }

    @Override
    public MotorSimType getMotorSimType(int aHandle)
    {
        return getSpeedControllerWrapper(aHandle).getMotorSimType();
    }
}
