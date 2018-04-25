
package com.snobot.simulator.wrapper_accessors;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;

public interface SpeedControllerWrapperAccessor extends IBasicSensorActuatorWrapperAccessor
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

    public double getVoltagePercentage(int aPort);

    public DcMotorModelConfig getMotorConfig(int aPort);

    public SimpleMotorSimulationConfig getMotorSimSimpleModelConfig(int aPort);

    public StaticLoadMotorSimulationConfig getMotorSimStaticModelConfig(int aPort);

    public GravityLoadMotorSimulationConfig getMotorSimGravitationalModelConfig(int aPort);

    public RotationalLoadMotorSimulationConfig getMotorSimRotationalModelConfig(int aPort);

    public MotorSimType getMotorSimType(int aHandle);

    public double getPosition(int aHandle);

    public double getVelocity(int aHandle);

    public double getCurrent(int aHandle);

    public double getAcceleration(int aHandle);

    public default void reset(int aHandle)
    {
        reset(aHandle, 0, 0, 0);
    }

    public void reset(int aHandle, double aPosition, double aVelocity, double aCurrent);
}
