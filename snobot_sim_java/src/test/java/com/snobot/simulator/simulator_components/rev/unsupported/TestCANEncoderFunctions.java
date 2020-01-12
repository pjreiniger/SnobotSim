package com.snobot.simulator.simulator_components.rev.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.junit.jupiter.api.Test;

public class TestCANEncoderFunctions extends BaseSimulatorJavaTest
{
    @Test
    public void testEncoderFunctions()
    {
        CANSparkMax sc = new CANSparkMax(12, MotorType.kBrushed);
        CANEncoder encoder = sc.getEncoder();

        encoder.getPosition();
        encoder.getVelocity();
        encoder.setPosition(0);
        encoder.setPositionConversionFactor(0);
        encoder.setVelocityConversionFactor(0);
        encoder.getPositionConversionFactor();
        encoder.getVelocityConversionFactor();
        encoder.setAverageDepth(0);
        encoder.getAverageDepth();
        encoder.setMeasurementPeriod(0);
        encoder.getMeasurementPeriod();
        encoder.getCPR();
        encoder.getCountsPerRevolution();
        encoder.setInverted(false);
        encoder.getInverted();
    }
}
