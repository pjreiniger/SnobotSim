package com.snobot.simulator.simulator_components.ctre;

import org.junit.Test;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.test.utilities.BaseSimulatorTest;

public class TestUnsupportedTalonOperations extends BaseSimulatorTest
{
    @Test
    public void testAllFunctions()
    {
        final TalonSRX motorToFollow = new TalonSRX(0);
        final TalonSRX motorToTest = new TalonSRX(11);

        motorToTest.getDeviceID();
        for (ControlMode mode : ControlMode.values())
        {
            motorToTest.set(mode, 0);
            motorToTest.set(mode, 0, 0);
        }
        motorToTest.getHandle();
        motorToTest.getDeviceID();
        motorToTest.neutralOutput();
        motorToTest.setNeutralMode(NeutralMode.Brake);
        motorToTest.enableHeadingHold(false);
        motorToTest.selectDemandType(false);
        motorToTest.setSensorPhase(false);
        motorToTest.setInverted(false);
        motorToTest.getInverted();
        motorToTest.configOpenloopRamp(0, 0);
        motorToTest.configClosedloopRamp(0, 0);
        motorToTest.configPeakOutputForward(0, 0);
        motorToTest.configPeakOutputReverse(0, 0);
        motorToTest.configNominalOutputForward(0, 0);
        motorToTest.configNominalOutputReverse(0, 0);
        motorToTest.configNeutralDeadband(0, 0);
        motorToTest.configVoltageCompSaturation(0, 0);
        motorToTest.configVoltageMeasurementFilter(0, 0);
        motorToTest.enableVoltageCompensation(false);
        motorToTest.getBusVoltage();
        motorToTest.getMotorOutputPercent();
        motorToTest.getMotorOutputVoltage();
        motorToTest.getOutputCurrent();
        motorToTest.getTemperature();
        motorToTest.configSelectedFeedbackSensor(RemoteFeedbackDevice.None, 0, 0);
        motorToTest.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        motorToTest.configRemoteFeedbackFilter(0, RemoteSensorSource.CANifier_PWMInput0, 0, 0);
//        motorToTest.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.Analog, 0);
        motorToTest.getSelectedSensorPosition(0);
        motorToTest.getSelectedSensorVelocity(0);
        motorToTest.setSelectedSensorPosition(0, 0, 0);
        motorToTest.setControlFramePeriod(ControlFrame.Control_3_General, 0);
        motorToTest.setControlFramePeriod(0, 0);
        motorToTest.setStatusFramePeriod(0, 0, 0);
        motorToTest.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 0, 0);
        motorToTest.getStatusFramePeriod(0, 0);
        motorToTest.getStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 0);
        motorToTest.getStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 0);
        motorToTest.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0);
        motorToTest.configVelocityMeasurementWindow(0, 0);
        motorToTest.configForwardLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0, 0);
        motorToTest.configReverseLimitSwitchSource(RemoteLimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0, 0);
        motorToTest.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
        // motorToTest.overrideLimitSwitchesEnable(false);
        // motorToTest.configForwardSoftLimitThreshold(0, 0);
        // motorToTest.configReverseSoftLimitThreshold(0, 0);
        // motorToTest.configForwardSoftLimitEnable(false, 0);
        // motorToTest.configReverseSoftLimitEnable(false, 0);
        // motorToTest.overrideSoftLimitsEnable(false);
        motorToTest.config_kP(0, 0, 0);
        motorToTest.config_kI(0, 0, 0);
        motorToTest.config_kD(0, 0, 0);
        motorToTest.config_kF(0, 0, 0);
        motorToTest.config_IntegralZone(0, 0, 0);
        motorToTest.configAllowableClosedloopError(0, 0, 0);
        motorToTest.configMaxIntegralAccumulator(0, 0, 0);
        motorToTest.setIntegralAccumulator(0, 0, 0);
        motorToTest.getClosedLoopError(0);
        motorToTest.getIntegralAccumulator(0);
        motorToTest.getErrorDerivative(0);
        motorToTest.selectProfileSlot(0, 0);
        motorToTest.getActiveTrajectoryPosition();
        motorToTest.getActiveTrajectoryVelocity();
        motorToTest.getActiveTrajectoryHeading();
        motorToTest.configMotionCruiseVelocity(0, 0);
        motorToTest.configMotionAcceleration(0, 0);
        motorToTest.clearMotionProfileTrajectories();
        motorToTest.getMotionProfileTopLevelBufferCount();
        TrajectoryPoint point = new TrajectoryPoint();
        point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
        motorToTest.pushMotionProfileTrajectory(point);
        motorToTest.isMotionProfileTopLevelBufferFull();
        motorToTest.processMotionProfileBuffer();
        motorToTest.getMotionProfileStatus(new MotionProfileStatus());
        motorToTest.clearMotionProfileHasUnderrun(0);
        motorToTest.changeMotionControlFramePeriod(0);
        motorToTest.getLastError();
        motorToTest.getFaults(new Faults());
        motorToTest.getStickyFaults(new StickyFaults());
        motorToTest.clearStickyFaults(0);
        motorToTest.getFirmwareVersion();
        motorToTest.hasResetOccurred();
        motorToTest.configSetCustomParam(0, 0, 0);
        motorToTest.configGetCustomParam(0, 0);
        motorToTest.configSetParameter(ParamEnum.eAccumZ, 0, 0, 0, 0);
        motorToTest.configSetParameter(0, 0, 0, 0, 0);
        motorToTest.configGetParameter(ParamEnum.eAccumZ, 0, 0);
        motorToTest.configGetParameter(0, 0, 0);
        motorToTest.getBaseID();
        motorToTest.follow(motorToFollow);
        motorToTest.valueUpdated();
        motorToTest.getSensorCollection();
        motorToTest.getControlMode();

        // Enhanced Motor Controller
        motorToTest.setStatusFramePeriod(StatusFrameEnhanced.Status_11_UartGadgeteer, 0, 0);
        motorToTest.getStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 0);
        motorToTest.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0);
        motorToTest.configVelocityMeasurementWindow(0, 0);
        motorToTest.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
        motorToTest.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
        motorToTest.configPeakCurrentLimit(0, 0);
        motorToTest.configPeakCurrentDuration(0, 0);
        motorToTest.configContinuousCurrentLimit(0, 0);
        motorToTest.enableCurrentLimit(false);
    }
}
