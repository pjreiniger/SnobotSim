
package com.snobot.simulator.wrapper_accessors;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;

public interface SpeedControllerWrapperAccessor extends IBasicSensorActuatorWrapperAccessor<IPwmWrapper>
{
    public enum MotorSimType
    {
        None("None"), Simple("Simple"), StaticLoad("Static Load"), RotationalLoad("Rotational Load"), GravitationalLoad("Gravitational Load");

        private String mDisplayName;

        MotorSimType(String aDisplayName)
        {
            mDisplayName = aDisplayName;
        }

        @Override
        public String toString()
        {
            return mDisplayName;
        }
    }

    DcMotorModelConfig getMotorConfig(int aPort);

    SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort);

    StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort);

    GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort);

    RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort);

    MotorSimType getMotorSimType(int aHandle);

}
