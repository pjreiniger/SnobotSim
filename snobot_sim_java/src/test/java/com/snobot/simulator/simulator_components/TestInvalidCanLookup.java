package com.snobot.simulator.simulator_components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.simulator_components.rev.RevSpeedControllerSimWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

public class TestInvalidCanLookup extends BaseSimulatorJavaTest
{
    @Test
    public void testCtreInConfigCreateRev()
    {
        int canId = 6;
        int simId = canId + 100;

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(simId, CtreTalonSrxSpeedControllerSim.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(simId).isInitialized());

        CANSparkMax spark = new CANSparkMax(canId, CANSparkMaxLowLevel.MotorType.kBrushed);
        spark.set(1.0);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(simId).isInitialized());
        Assertions.assertEquals(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simId), 1.0, 1e-6);

    }

    @Test
    public void testRevInConfigCreateCtre()
    {
        int canId = 6;
        int simId = canId + 100;

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(simId, RevSpeedControllerSimWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(simId).isInitialized());

        TalonSRX talon = new TalonSRX(canId);
        talon.set(ControlMode.PercentOutput, 1.0);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(simId).isInitialized());
        Assertions.assertEquals(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(simId), 1.0, 1e-6);

    }

}
