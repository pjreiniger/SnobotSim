package com.snobot.simulator.simulator_components.rev.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import org.junit.jupiter.api.Test;

public class TestCanPidXControllerFunctions extends BaseSimulatorJavaTest
{
    @SuppressWarnings("PMD.NcssCount")
    @Test
    public void testPidFunctions()
    {
        CANSparkMax sc = new CANSparkMax(12, MotorType.kBrushless);
        CANPIDController pid = new CANPIDController(sc);

        for (ControlType controlType : ControlType.values())
        {
            pid.setReference(0, controlType);
        }
        for (ControlType controlType : ControlType.values())
        {
            pid.setReference(0, controlType, 0);
        }
        for (ControlType controlType : ControlType.values())
        {
            pid.setReference(0, controlType, 0, 0);
        }
        for (ControlType controlType : ControlType.values())
        {
            for (ArbFFUnits arbFFUnits : ArbFFUnits.values())
            {
                pid.setReference(0, controlType, 0, 0, arbFFUnits);
            }
        }
        pid.setP(0);
        pid.setP(0, 0);
        pid.setI(0);
        pid.setI(0, 0);
        pid.setD(0);
        pid.setD(0, 0);
        pid.setDFilter(0);
        pid.setDFilter(0, 0);
        pid.setFF(0);
        pid.setFF(0, 0);
        pid.setIZone(0);
        pid.setIZone(0, 0);
        pid.setOutputRange(0, 0);
        pid.setOutputRange(0, 0, 0);
        pid.getP();
        pid.getP(0);
        pid.getI();
        pid.getI(0);
        pid.getD();
        pid.getD(0);
        pid.getDFilter(0);
        pid.getFF();
        pid.getFF(0);
        pid.getIZone();
        pid.getIZone(0);
        pid.getOutputMin();
        pid.getOutputMin(0);
        pid.getOutputMax();
        pid.getOutputMax(0);
        pid.setSmartMotionMaxVelocity(0, 0);
        pid.setSmartMotionMaxAccel(0, 0);
        pid.setSmartMotionMinOutputVelocity(0, 0);
        pid.setSmartMotionAllowedClosedLoopError(0, 0);
        for (AccelStrategy accelStrategy : AccelStrategy.values())
        {
            pid.setSmartMotionAccelStrategy(accelStrategy, 0);
        }
        pid.getSmartMotionMaxVelocity(0);
        pid.getSmartMotionMaxAccel(0);
        pid.getSmartMotionMinOutputVelocity(0);
        pid.getSmartMotionAllowedClosedLoopError(0);
        pid.getSmartMotionAccelStrategy(0);
        pid.setIMaxAccum(0, 0);
        pid.getIMaxAccum(0);
        pid.setIAccum(0);
        pid.getIAccum();

        CANEncoder sensor = sc.getEncoder();
        pid.setFeedbackDevice(sensor);
    }
}
