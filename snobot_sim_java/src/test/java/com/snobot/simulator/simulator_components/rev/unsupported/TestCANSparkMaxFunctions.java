package com.snobot.simulator.simulator_components.rev.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANAnalog.AnalogMode;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;
import org.junit.jupiter.api.Test;

public class TestCANSparkMaxFunctions extends BaseSimulatorJavaTest
{
    @SuppressWarnings({ "PMD.NcssCount", "PMD.DataflowAnomalyAnalysis", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    @Test
    public void testUnsupportedOperations()
    {
        CANSparkMax spark = new CANSparkMax(10, MotorType.kBrushless);
        CANSparkMax follower = new CANSparkMax(11, MotorType.kBrushed);

        spark.set(0);
        spark.get();
        spark.setInverted(false);
        spark.getInverted();
        spark.disable();
        spark.stopMotor();
        spark.pidWrite(0);
        spark.getEncoder();
        for (EncoderType encoderType : EncoderType.values())
        {
            spark.getEncoder(encoderType, 0);
        }
        spark.getAlternateEncoder();
        for (AlternateEncoderType alternateEncoderType : AlternateEncoderType.values())
        {
            spark.getAlternateEncoder(alternateEncoderType, 0);
        }
        for (AnalogMode analogMode : AnalogMode.values())
        {
            spark.getAnalog(analogMode);
        }
        spark.getPIDController();
        for (LimitSwitchPolarity limitSwitchPolarity : LimitSwitchPolarity.values())
        {
            spark.getForwardLimitSwitch(limitSwitchPolarity);
        }
        for (LimitSwitchPolarity limitSwitchPolarity : LimitSwitchPolarity.values())
        {
            spark.getReverseLimitSwitch(limitSwitchPolarity);
        }
        spark.setSmartCurrentLimit(0);
        spark.setSmartCurrentLimit(0, 0);
        spark.setSmartCurrentLimit(0, 0, 0);
        spark.setSecondaryCurrentLimit(0);
        spark.setSecondaryCurrentLimit(0, 0);
        for (IdleMode idleMode : IdleMode.values())
        {
            spark.setIdleMode(idleMode);
        }
        spark.getIdleMode();
        spark.enableVoltageCompensation(0);
        spark.disableVoltageCompensation();
        spark.getVoltageCompensationNominalVoltage();
        spark.setOpenLoopRampRate(0);
        spark.setClosedLoopRampRate(0);
        spark.getOpenLoopRampRate();
        spark.getClosedLoopRampRate();

        spark.follow(follower);
        spark.follow(follower, false);
//        spark.follow(externalFollower, 0);
//        spark.follow(externalFollower, 0, false);
        spark.isFollower();
        spark.getFaults();
        spark.getStickyFaults();
        for (FaultID faultID : FaultID.values())
        {
            spark.getFault(faultID);
        }
        for (FaultID faultID : FaultID.values())
        {
            spark.getStickyFault(faultID);
        }
        spark.getBusVoltage();
        spark.getAppliedOutput();
        spark.getOutputCurrent();
        spark.getMotorTemperature();
        spark.clearFaults();
        spark.burnFlash();
        spark.setCANTimeout(0);
        for (SoftLimitDirection softLimitDirection : SoftLimitDirection.values())
        {
            spark.enableSoftLimit(softLimitDirection, false);
        }
        for (SoftLimitDirection softLimitDirection : SoftLimitDirection.values())
        {
            spark.setSoftLimit(softLimitDirection, 0);
        }
        for (SoftLimitDirection softLimitDirection : SoftLimitDirection.values())
        {
            spark.getSoftLimit(softLimitDirection);
        }
        for (SoftLimitDirection softLimitDirection : SoftLimitDirection.values())
        {
            spark.isSoftLimitEnabled(softLimitDirection);
        }
        spark.getLastError();


        spark.close();
    }

}
