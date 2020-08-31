package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

@Tag("CTRE")
public class TestCtreCanTalonControlMotionProfile extends BaseSimulatorJniTest
{
    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testSetWithMotionProfile(int aCanHandle)
    {
        final int rawHandle = aCanHandle + 100;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());
        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        talon.config_kP(0, .045, 5);
        talon.config_kF(0, 0.018, 5);
        talon.config_IntegralZone(0, 1, 5);


        MotionProfileStatus status = new MotionProfileStatus();
        talon.getMotionProfileStatus(status);
        printMotionProfileStatus(talon, status);

        List<TrajectoryPoint> points = generatePoints(1, 3, 4, 12, 12, 35, .02);
        for (TrajectoryPoint point : points)
        {
            Assertions.assertEquals(ErrorCode.OK, talon.pushMotionProfileTrajectory(point));
            talon.getMotionProfileStatus(status);
            // printMotionProfileStatus(talon, status);
            // Assertions.assertEquals(0, status.btmBufferCnt, 0);
            // Assertions.assertEquals(i + 1, status.topBufferCnt);
            // Assertions.assertEquals(2048 - i - 1, status.topBufferRem);
        }
        for (int i = 0; i < points.size(); ++i) // NOPMD
        {
            talon.processMotionProfileBuffer();
        }

        // Hookup motor config
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(rawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        // talon.set(TalonSRX.SetValueMotionProfile.Disable.value);
        simulateForTime(.2, () ->
        {
            talon.set(ControlMode.MotionProfile, -1);
        });

        // talon.set(TalonSRX.SetValueMotionProfile.Hold.value);
        simulateForTime(.2, () ->
        {
            talon.set(ControlMode.MotionProfile, 0);
        });

        // talon.set(TalonSRX.SetValueMotionProfile.Enable.value);
        simulateForTime(10, () ->
        {
            talon.set(ControlMode.MotionProfile, 1);
        });
    }

    private List<TrajectoryPoint> generatePoints(double aT1, double aT2, double aT3, double aMaxVelocity, double aMaxAccel, double aPosition, double aDt)
    {
        List<TrajectoryPoint> output = new ArrayList<>();

        double pos;
        double vel;

        int duration = (int) (aDt * 1e3);

        for (double t = 0; t < aT1; t += aDt)
        {
            pos = .5 * aMaxAccel * (t * t);
            vel = aMaxAccel * t;

            TrajectoryPoint point = new TrajectoryPoint();
            point.isLastPoint = false;
            point.position = pos;
            point.velocity = vel;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.zeroPos = t == 0;
            point.headingDeg = 0;
            point.timeDur = duration;
            output.add(point);
        }

        for (double t = aT1; t < aT2; t += aDt)
        {
            pos = .5 * ((aMaxVelocity * aMaxVelocity) / aMaxAccel) + aMaxVelocity * (t - (aMaxVelocity / aMaxAccel));
            vel = aMaxVelocity;

            TrajectoryPoint point = new TrajectoryPoint();
            point.isLastPoint = false;
            point.position = pos;
            point.velocity = vel;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.zeroPos = t == 0;
            point.headingDeg = 0;
            point.timeDur = duration;
            output.add(point);
        }

        for (double t = aT2; t < aT3; t += aDt)
        {
            pos = aPosition - .5 * aMaxAccel * Math.pow(t - (aT1 + aT2), 2);
            vel = aMaxAccel * ((aMaxVelocity / aMaxAccel) + (aPosition / aMaxVelocity) - t);

            TrajectoryPoint point = new TrajectoryPoint();
            point.isLastPoint = false;
            point.position = pos;
            point.velocity = vel;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.zeroPos = t == 0;
            point.headingDeg = 0;
            point.timeDur = duration;
            output.add(point);
        }

        return output;
    }

    @SuppressWarnings({"unused", "PMD.SystemPrintln"})
    private void printMotionProfileStatus(TalonSRX aTalon, MotionProfileStatus aStatus)
    {
        System.out.println("Getting status...");

        System.out.println("  btmBufferCnt     : " + aStatus.btmBufferCnt);
        System.out.println("  topBufferCnt     : " + aStatus.topBufferCnt);
        System.out.println("  topBufferRem     : " + aStatus.topBufferRem);
        System.out.println("  activePointValid : " + aStatus.activePointValid);
        System.out.println("  hasUnderrun      : " + aStatus.hasUnderrun);
        System.out.println("  isUnderrun       : " + aStatus.isUnderrun);
        System.out.println("  outputEnable     : " + aStatus.outputEnable);
        System.out.println("  point            :");
        System.out.println("      position          : " + aTalon.getActiveTrajectoryPosition());
        System.out.println("      velocity          : " + aTalon.getActiveTrajectoryVelocity());
    }
}
