package com.snobot.simulator.simulator_components.rev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

public class TestUnsupportedRevFunctions extends BaseSimulatorJavaTest
{

    @Test
    public void testUnsupportedOperations()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sc = new CANSparkMax(10, MotorType.kBrushless);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        sc.burnFlash();
        sc.clearFaults();
        sc.disable();
        // sc.follow(leader); x 3
        sc.get(); // TODO investigate
        sc.getAppliedOutput();

        sc.getBusVoltage();
        sc.getEncoder(); // TODO investigate
        sc.getFault(FaultID.kBrownout);
        sc.getFaults();
        sc.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        sc.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        sc.getIdleMode();
        sc.getInverted();
        sc.getMotorTemperature();
        sc.getOutputCurrent();
        sc.getPIDController();
        sc.getRampRate();
        sc.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        sc.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        sc.getStickyFault(FaultID.kCANRX); // TODO investigate
        sc.getStickyFaults();
        sc.isFollower();
        sc.pidWrite(0);
        sc.set(.5);
        sc.setCANTimeout(20);
        sc.setIdleMode(IdleMode.kBrake);
        sc.setIdleMode(IdleMode.kCoast);
        sc.setInverted(true);
        sc.setInverted(false);
        sc.setRampRate(.5);
        sc.setSecondaryCurrentLimit(.6);
        sc.setSecondaryCurrentLimit(.8, 10);
        sc.setSmartCurrentLimit(3);
        sc.setSmartCurrentLimit(5, 8);
        sc.setSmartCurrentLimit(6, 7, 12);
        sc.stopMotor();

        sc.close();
    }
}
