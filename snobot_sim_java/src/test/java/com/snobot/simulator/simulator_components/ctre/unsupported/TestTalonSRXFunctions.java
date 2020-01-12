package com.snobot.simulator.simulator_components.ctre.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

public class TestTalonSRXFunctions extends BaseSimulatorJavaTest
{
    @Test
    public void testAllFunctions()
    {
        TalonSRX talon = new TalonSRX(0);

        TrajectoryPoint trajectoryPoint = new TrajectoryPoint();
        trajectoryPoint.timeDur = 10;

        //////////////////////////////////////////////

        talon.getSensorCollection();
        for (TalonSRXFeedbackDevice talonSRXFeedbackDevice : TalonSRXFeedbackDevice.values())
        {
            talon.configSelectedFeedbackSensor(talonSRXFeedbackDevice, 0, 0);
            talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(), 0);
            talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration());
        }
        talon.configPeakCurrentLimit(0, 0);
        talon.configPeakCurrentLimit(0);
        talon.configPeakCurrentDuration(0, 0);
        talon.configPeakCurrentDuration(0);
        talon.configContinuousCurrentLimit(0, 0);
        talon.configContinuousCurrentLimit(0);
        talon.enableCurrentLimit(false);
        talon.getPIDConfigs(new TalonSRXPIDSetConfiguration(), 0, 0);
        talon.getPIDConfigs(new TalonSRXPIDSetConfiguration());
        talon.configAllSettings(new TalonSRXConfiguration(), 0);
        talon.configAllSettings(new TalonSRXConfiguration());
        talon.getAllConfigs(new TalonSRXConfiguration(), 0);
        talon.getAllConfigs(new TalonSRXConfiguration());

        talon.DestroyObject();
    }

}
