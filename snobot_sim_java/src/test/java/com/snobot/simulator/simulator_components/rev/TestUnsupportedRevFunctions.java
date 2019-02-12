package com.snobot.simulator.simulator_components.rev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.ParameterType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
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

        CANSparkMax follower = new CANSparkMax(11, MotorType.kBrushed);
        ExternalFollower externalFollower = new ExternalFollower(15, 0);

        sc.set(0);
        sc.set(-.1);
        sc.set(0);

        sc.burnFlash();
        sc.clearFaults();
        sc.disable();
        sc.follow(follower);
        sc.follow(follower, true);
        sc.follow(externalFollower, 11);
        sc.follow(externalFollower, 12, true);
        sc.get(); // TODO investigate
        sc.getAppliedOutput();

        sc.getBusVoltage();

        sc.getFaults();
        sc.getIdleMode();
        sc.getInverted();
        sc.getMotorTemperature();
        sc.getOutputCurrent();
        sc.getPIDController();
        sc.getRampRate();
        for (FaultID faultId : FaultID.values())
        {
            sc.getStickyFault(faultId); // TODO investigate
            sc.getFault(faultId);
        }
        sc.getStickyFaults();
        sc.isFollower();
        sc.pidWrite(0);
        sc.set(.5);
        sc.setCANTimeout(20);

        for (LimitSwitchPolarity polarity : LimitSwitchPolarity.values())
        {
            CANDigitalInput reverseSwitch = sc.getReverseLimitSwitch(polarity);
            CANDigitalInput fowrardSwitch = sc.getForwardLimitSwitch(polarity);

            reverseSwitch.enableLimitSwitch(false);
            reverseSwitch.enableLimitSwitch(true);
            reverseSwitch.get();
            reverseSwitch.isLimitSwitchEnabled();

            fowrardSwitch.enableLimitSwitch(false);
            fowrardSwitch.enableLimitSwitch(true);
            fowrardSwitch.get();
            fowrardSwitch.isLimitSwitchEnabled();
        }

        for (IdleMode mode : IdleMode.values())
        {
            sc.setIdleMode(mode);
        }
        sc.setInverted(true);
        sc.setInverted(false);
        sc.setRampRate(.5);
        sc.setSecondaryCurrentLimit(.6);
        sc.setSecondaryCurrentLimit(.8, 10);
        sc.setSmartCurrentLimit(3);
        sc.setSmartCurrentLimit(5, 8);
        sc.setSmartCurrentLimit(6, 7, 12);
        sc.stopMotor();

        sc.getControlFramePeriod();
        sc.getDeviceId();
        sc.getFirmwareString();
        sc.getFirmwareVersion();
        // sc.getMotorType();

        for (ConfigParameter parameter : ConfigParameter.values())
        {
            sc.getParameterBoolean(parameter);
            sc.getParameterDouble(parameter);
            sc.getParameterInt(parameter);
            sc.setParameter(parameter, false);
            sc.setParameter(parameter, 1.0);
            sc.setParameter(parameter, 1);

            for (ParameterType type : ParameterType.values())
            {
                sc.getParameterCore(parameter, type);
                sc.setParameterCore(parameter, type, 1);
            }
        }

        // sc.getParameterType(ConfigParameter.kI_0);

        for (PeriodicFrame frame : PeriodicFrame.values())
        {
            sc.setPeriodicFramePeriod(frame, 5);
        }
        sc.getSerialNumber();
        sc.setControlFramePeriod(32);

        for (MotorType motorType : MotorType.values())
        {
            sc.setMotorType(motorType);
        }

        CANEncoder encoder = sc.getEncoder();
        encoder.getPosition();
        encoder.getVelocity();

        sc.close();
    }
}
