package com.snobot.simulator.wrapper_accessors.java;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultPwmWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.BaseDcMotorSimulator;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadDcMotorSim;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.module_wrapper.interfaces.IMotorSimulator;
import com.snobot.simulator.motor_sim.RotationalLoadDcMotorSim;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulator;
import com.snobot.simulator.motor_sim.StaticLoadDcMotorSim;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;

public class JavaSpeedControllerWrapperAccessor extends BaseWrapperAccessor<IPwmWrapper> implements SpeedControllerWrapperAccessor
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaSpeedControllerWrapperAccessor.class);

    private static final String sWRONG_SIMULATOR_TYPE_ERROR = "Wrong simulator type, returning default";

    public JavaSpeedControllerWrapperAccessor()
    {
        super(new DefaultPwmWrapperFactory());
    }

    @Override
    public Map<Integer, IPwmWrapper> getWrappers()
    {
        return SensorActuatorRegistry.get().getSpeedControllers();
    }

    @Override
    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort)
    {
        IMotorSimulator baseMotorSim = getWrapper(aPort).getMotorSimulator();
        if (baseMotorSim instanceof SimpleMotorSimulator)
        {
            SimpleMotorSimulator simulator = (SimpleMotorSimulator) getWrapper(aPort).getMotorSimulator();
            return simulator.getConfig();
        }

        sLOGGER.log(Level.DEBUG, sWRONG_SIMULATOR_TYPE_ERROR);
        return new SimpleMotorSimulationConfig(0);
    }

    @Override
    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort)
    {
        IMotorSimulator baseMotorSim = getWrapper(aPort).getMotorSimulator();
        if (baseMotorSim instanceof StaticLoadDcMotorSim)
        {
            StaticLoadDcMotorSim simulator = (StaticLoadDcMotorSim) getWrapper(aPort).getMotorSimulator();
            return simulator.getConfig();
        }

        sLOGGER.log(Level.DEBUG, sWRONG_SIMULATOR_TYPE_ERROR);
        return new StaticLoadMotorSimulationConfig(0);
    }

    @Override
    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort)
    {
        IMotorSimulator baseMotorSim = getWrapper(aPort).getMotorSimulator();
        if (baseMotorSim instanceof GravityLoadDcMotorSim)
        {
            GravityLoadDcMotorSim simulator = (GravityLoadDcMotorSim) getWrapper(aPort).getMotorSimulator();
            return simulator.getConfig();
        }

        sLOGGER.log(Level.DEBUG, sWRONG_SIMULATOR_TYPE_ERROR);
        return new GravityLoadMotorSimulationConfig(0);
    }

    @Override
    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort)
    {
        IMotorSimulator baseMotorSim = getWrapper(aPort).getMotorSimulator();
        if (baseMotorSim instanceof RotationalLoadDcMotorSim)
        {
            RotationalLoadDcMotorSim simulator = (RotationalLoadDcMotorSim) getWrapper(aPort).getMotorSimulator();
            return simulator.getConfig();
        }

        sLOGGER.log(Level.DEBUG, sWRONG_SIMULATOR_TYPE_ERROR);
        return new RotationalLoadMotorSimulationConfig(0, 0);
    }

    @Override
    public MotorSimType getMotorSimType(int aHandle)
    {
        IMotorSimulator simulator = getWrapper(aHandle).getMotorSimulator();

        if (simulator instanceof SimpleMotorSimulator)
        {
            return MotorSimType.Simple;
        }
        if (simulator instanceof StaticLoadDcMotorSim)
        {
            return MotorSimType.StaticLoad;
        }
        if (simulator instanceof GravityLoadDcMotorSim)
        {
            return MotorSimType.GravitationalLoad;
        }
        if (simulator instanceof RotationalLoadDcMotorSim)
        {
            return MotorSimType.RotationalLoad;
        }
        return MotorSimType.None;
    }

    @Override
    public DcMotorModelConfig getMotorConfig(int aPort)
    {
        IMotorSimulator simulator = getWrapper(aPort).getMotorSimulator();
        if (simulator instanceof BaseDcMotorSimulator)
        {
            BaseDcMotorSimulator castSim = (BaseDcMotorSimulator) simulator;
            return castSim.getMotorConfig();
        }
        return null;
    }
}
