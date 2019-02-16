package com.snobot.simulator.simulator_components.rev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
        new TalonSRX(15);
        ExternalFollower externalFollower = new ExternalFollower(15, 0);

        runOperations(sc, follower, externalFollower);
    }

    private void runOperations(CANSparkMax aSc, CANSparkMax aFollower, ExternalFollower aExternalFollower)
    {

        aSc.set(0);
        aSc.set(-.1);
        aSc.set(0);

        aSc.burnFlash();
        aSc.clearFaults();
        aSc.disable();
        aSc.follow(aFollower);
        aSc.follow(aFollower, true);
        aSc.follow(aExternalFollower, 10);
        aSc.follow(aExternalFollower, 10, true);
        aSc.get(); // TODO investigate
        aSc.getAppliedOutput();

        aSc.getBusVoltage();

        aSc.getFaults();
        aSc.getIdleMode();
        aSc.getInverted();
        aSc.getMotorTemperature();
        aSc.getOutputCurrent();
        aSc.getPIDController();
        for (FaultID faultId : FaultID.values())
        {
            aSc.getStickyFault(faultId); // TODO investigate
            aSc.getFault(faultId);
        }
        aSc.getStickyFaults();
        aSc.isFollower();
        aSc.pidWrite(0);
        aSc.set(.5);
        aSc.setCANTimeout(20);

        for (LimitSwitchPolarity polarity : LimitSwitchPolarity.values())
        {
            CANDigitalInput reverseSwitch = aSc.getReverseLimitSwitch(polarity);
            CANDigitalInput fowrardSwitch = aSc.getForwardLimitSwitch(polarity);

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
            aSc.setIdleMode(mode);
        }
        aSc.setInverted(true);
        aSc.setInverted(false);
        aSc.setSecondaryCurrentLimit(.6);
        aSc.setSecondaryCurrentLimit(.8, 10);
        aSc.setSmartCurrentLimit(3);
        aSc.setSmartCurrentLimit(5, 8);
        aSc.setSmartCurrentLimit(6, 7, 12);
        aSc.stopMotor();

        aSc.getDeviceId();
        aSc.getFirmwareString();
        aSc.getFirmwareVersion();
        // sc.getMotorType();

        for (ConfigParameter parameter : ConfigParameter.values())
        {
            aSc.getParameterBoolean(parameter);
            aSc.getParameterDouble(parameter);
            aSc.getParameterInt(parameter);
            aSc.setParameter(parameter, false);
            aSc.setParameter(parameter, 1.0);
            aSc.setParameter(parameter, 1);

            for (ParameterType type : ParameterType.values())
            {
                aSc.getParameterCore(parameter, type);
                aSc.setParameterCore(parameter, type, 1);
            }
        }

        // sc.getParameterType(ConfigParameter.kI_0);

        for (PeriodicFrame frame : PeriodicFrame.values())
        {
            aSc.setPeriodicFramePeriod(frame, 5);
        }
        aSc.getSerialNumber();

        for (MotorType motorType : MotorType.values())
        {
            aSc.setMotorType(motorType);
        }

        CANEncoder encoder = aSc.getEncoder();
        encoder.getPosition();
        encoder.getVelocity();

        aSc.close();
    }
}
