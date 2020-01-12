package com.snobot.simulator.simulator_components.rev.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import org.junit.jupiter.api.Test;

public class TestCANSparkMaxLowLevelFunctions extends BaseSimulatorJavaTest
{
    @SuppressWarnings({ "PMD.NcssCount", "PMD.DataflowAnomalyAnalysis", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    @Test
    public void testUnsupportedOperations()
    {
        CANSparkMax spark = new CANSparkMax(10, MotorType.kBrushless);


        spark.getFirmwareVersion();
        spark.setControlFramePeriodMs(0);
        spark.getFirmwareString();
        spark.getSerialNumber();
        spark.getDeviceId();
        spark.getInitialMotorType();
        for (MotorType motorType : MotorType.values())
        {
            spark.setMotorType(motorType);
        }
        spark.getMotorType();
        for (PeriodicFrame periodicFrame : PeriodicFrame.values())
        {
            spark.setPeriodicFramePeriod(periodicFrame, 0);
        }
        spark.enableExternalUSBControl(false);
        spark.getSafeFloat(0.0f);
        spark.restoreFactoryDefaults();
        spark.restoreFactoryDefaults(false);
    }
}
