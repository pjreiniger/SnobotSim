package com.snobot.simulator.simulator_components.ctre.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.FilterConfiguration;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TestBaseMotorControllerFunctions extends BaseSimulatorJavaTest
{
    @Test
    public void testAllFunctions()
    {
        final TalonSRX followTalon = new TalonSRX(0);
        final TalonSRX talon = new TalonSRX(11);

        TrajectoryPoint trajectoryPoint = new TrajectoryPoint();
        trajectoryPoint.timeDur = 10;

        talon.getHandle();
        talon.getDeviceID();
        for (ControlMode controlMode : ControlMode.values())
        {
            talon.set(controlMode, 0);
        }
        for (ControlMode controlMode : ControlMode.values())
        {
            talon.set(controlMode, 0, 0);
        }
        for (ControlMode controlMode : ControlMode.values())
        {
            for (DemandType demandType : DemandType.values())
            {
                talon.set(controlMode, 0, demandType, 0);
            }
        }
        talon.neutralOutput();
        for (NeutralMode neutralMode : NeutralMode.values())
        {
            talon.setNeutralMode(neutralMode);
        }
        talon.enableHeadingHold(false);
        talon.selectDemandType(false);
        talon.setSensorPhase(false);
        talon.setInverted(false);
        for (InvertType invertType : InvertType.values())
        {
            talon.setInverted(invertType);
        }
        talon.getInverted();
        talon.configFactoryDefault(0);
        talon.configFactoryDefault();
        talon.configOpenloopRamp(0, 0);
        talon.configOpenloopRamp(0);
        talon.configClosedloopRamp(0, 0);
        talon.configClosedloopRamp(0);
        talon.configPeakOutputForward(0, 0);
        talon.configPeakOutputForward(0);
        talon.configPeakOutputReverse(0, 0);
        talon.configPeakOutputReverse(0);
        talon.configNominalOutputForward(0, 0);
        talon.configNominalOutputForward(0);
        talon.configNominalOutputReverse(0, 0);
        talon.configNominalOutputReverse(0);
        talon.configNeutralDeadband(0, 0);
        talon.configNeutralDeadband(0);
        talon.configVoltageCompSaturation(0, 0);
        talon.configVoltageCompSaturation(0);
        talon.configVoltageMeasurementFilter(0, 0);
        talon.configVoltageMeasurementFilter(0);
        talon.enableVoltageCompensation(false);
        talon.getBusVoltage();
        talon.getMotorOutputPercent();
        talon.getMotorOutputVoltage();
        talon.getTemperature();
        for (RemoteFeedbackDevice remoteFeedbackDevice : RemoteFeedbackDevice.values())
        {
            talon.configSelectedFeedbackSensor(remoteFeedbackDevice, 0, 0);
        }
        for (RemoteFeedbackDevice remoteFeedbackDevice : RemoteFeedbackDevice.values())
        {
            talon.configSelectedFeedbackSensor(remoteFeedbackDevice);
        }
        for (FeedbackDevice feedbackDevice : FeedbackDevice.values())
        {
            talon.configSelectedFeedbackSensor(feedbackDevice, 0, 0);
        }
        for (FeedbackDevice feedbackDevice : FeedbackDevice.values())
        {
            talon.configSelectedFeedbackSensor(feedbackDevice);
        }
        talon.configSelectedFeedbackCoefficient(0, 0, 0);
        talon.configSelectedFeedbackCoefficient(0);
        for (RemoteSensorSource remoteSensorSource : RemoteSensorSource.values())
        {
            talon.configRemoteFeedbackFilter(0, remoteSensorSource, 0, 0);
        }
        for (RemoteSensorSource remoteSensorSource : RemoteSensorSource.values())
        {
            talon.configRemoteFeedbackFilter(0, remoteSensorSource, 0);
        }
        for (SensorTerm sensorTerm : SensorTerm.values())
        {
            for (FeedbackDevice feedbackDevice : FeedbackDevice.values())
            {
                talon.configSensorTerm(sensorTerm, feedbackDevice, 0);
            }
        }
        for (SensorTerm sensorTerm : SensorTerm.values())
        {
            for (FeedbackDevice feedbackDevice : FeedbackDevice.values())
            {
                talon.configSensorTerm(sensorTerm, feedbackDevice);
            }
        }
        for (SensorTerm sensorTerm : SensorTerm.values())
        {
            for (RemoteFeedbackDevice remoteFeedbackDevice : RemoteFeedbackDevice.values())
            {
                talon.configSensorTerm(sensorTerm, remoteFeedbackDevice, 0);
            }
        }
        for (SensorTerm sensorTerm : SensorTerm.values())
        {
            for (RemoteFeedbackDevice remoteFeedbackDevice : RemoteFeedbackDevice.values())
            {
                talon.configSensorTerm(sensorTerm, remoteFeedbackDevice);
            }
        }
        talon.getSelectedSensorPosition(0);
        talon.getSelectedSensorPosition();
        talon.getSelectedSensorVelocity(0);
        talon.getSelectedSensorVelocity();
        talon.setSelectedSensorPosition(0, 0, 0);
        talon.setSelectedSensorPosition(0);
        for (ControlFrame controlFrame : ControlFrame.values())
        {
            talon.setControlFramePeriod(controlFrame, 0);
        }
        talon.setControlFramePeriod(0, 0);
        talon.setStatusFramePeriod(0, 0, 0);
        talon.setStatusFramePeriod(0, 0);
        for (StatusFrame statusFrame : StatusFrame.values())
        {
            talon.setStatusFramePeriod(statusFrame, 0, 0);
        }
        for (StatusFrame statusFrame : StatusFrame.values())
        {
            talon.setStatusFramePeriod(statusFrame, 0);
        }
        talon.getStatusFramePeriod(0, 0);
        talon.getStatusFramePeriod(0);
        for (StatusFrame statusFrame : StatusFrame.values())
        {
            talon.getStatusFramePeriod(statusFrame, 0);
        }
        for (StatusFrame statusFrame : StatusFrame.values())
        {
            talon.getStatusFramePeriod(statusFrame);
        }
        for (StatusFrameEnhanced statusFrameEnhanced : StatusFrameEnhanced.values())
        {
            talon.getStatusFramePeriod(statusFrameEnhanced, 0);
        }
        for (StatusFrameEnhanced statusFrameEnhanced : StatusFrameEnhanced.values())
        {
            talon.getStatusFramePeriod(statusFrameEnhanced);
        }
        for (VelocityMeasPeriod velocityMeasPeriod : VelocityMeasPeriod.values())
        {
            talon.configVelocityMeasurementPeriod(velocityMeasPeriod, 0);
        }
        for (VelocityMeasPeriod velocityMeasPeriod : VelocityMeasPeriod.values())
        {
            talon.configVelocityMeasurementPeriod(velocityMeasPeriod);
        }
        talon.configVelocityMeasurementWindow(0, 0);
        talon.configVelocityMeasurementWindow(0);
        for (RemoteLimitSwitchSource remoteLimitSwitchSource : RemoteLimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configForwardLimitSwitchSource(remoteLimitSwitchSource, limitSwitchNormal, 0, 0);
            }
        }
        for (RemoteLimitSwitchSource remoteLimitSwitchSource : RemoteLimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configForwardLimitSwitchSource(remoteLimitSwitchSource, limitSwitchNormal, 0);
            }
        }
        for (RemoteLimitSwitchSource remoteLimitSwitchSource : RemoteLimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configReverseLimitSwitchSource(remoteLimitSwitchSource, limitSwitchNormal, 0, 0);
            }
        }
        for (RemoteLimitSwitchSource remoteLimitSwitchSource : RemoteLimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configReverseLimitSwitchSource(remoteLimitSwitchSource, limitSwitchNormal, 0);
            }
        }
        for (LimitSwitchSource limitSwitchSource : LimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configForwardLimitSwitchSource(limitSwitchSource, limitSwitchNormal, 0);
            }
        }
        for (LimitSwitchSource limitSwitchSource : LimitSwitchSource.values())
        {
            for (LimitSwitchNormal limitSwitchNormal : LimitSwitchNormal.values())
            {
                talon.configForwardLimitSwitchSource(limitSwitchSource, limitSwitchNormal);
            }
        }
        talon.overrideLimitSwitchesEnable(false);
        talon.configForwardSoftLimitThreshold(0, 0);
        talon.configForwardSoftLimitThreshold(0);
        talon.configReverseSoftLimitThreshold(0, 0);
        talon.configReverseSoftLimitThreshold(0);
        talon.configForwardSoftLimitEnable(false, 0);
        talon.configForwardSoftLimitEnable(false);
        talon.configReverseSoftLimitEnable(false, 0);
        talon.configReverseSoftLimitEnable(false);
        talon.overrideSoftLimitsEnable(false);
        talon.config_kP(0, 0, 0);
        talon.config_kP(0, 0);
        talon.config_kI(0, 0, 0);
        talon.config_kI(0, 0);
        talon.config_kD(0, 0, 0);
        talon.config_kD(0, 0);
        talon.config_kF(0, 0, 0);
        talon.config_kF(0, 0);
        talon.config_IntegralZone(0, 0, 0);
        talon.config_IntegralZone(0, 0);
        talon.configAllowableClosedloopError(0, 0, 0);
        talon.configAllowableClosedloopError(0, 0);
        talon.configMaxIntegralAccumulator(0, 0, 0);
        talon.configMaxIntegralAccumulator(0, 0);
        talon.configClosedLoopPeakOutput(0, 0, 0);
        talon.configClosedLoopPeakOutput(0, 0);
        talon.configClosedLoopPeriod(0, 0, 0);
        talon.configClosedLoopPeriod(0, 0);
        talon.configAuxPIDPolarity(false, 0);
        talon.configAuxPIDPolarity(false);
        talon.setIntegralAccumulator(0, 0, 0);
        talon.setIntegralAccumulator(0);
        talon.getClosedLoopError(0);
        talon.getClosedLoopError();
        talon.getIntegralAccumulator(0);
        talon.getIntegralAccumulator();
        talon.getErrorDerivative(0);
        talon.getErrorDerivative();
        talon.selectProfileSlot(0, 0);
        talon.getClosedLoopTarget(0);
        talon.getClosedLoopTarget();
        talon.getActiveTrajectoryPosition();
        talon.getActiveTrajectoryPosition(0);
        talon.getActiveTrajectoryVelocity();
        talon.getActiveTrajectoryVelocity(0);
        talon.getActiveTrajectoryHeading();
        talon.getActiveTrajectoryArbFeedFwd();
        talon.getActiveTrajectoryArbFeedFwd(0);
        talon.configMotionCruiseVelocity(0, 0);
        talon.configMotionCruiseVelocity(0);
        talon.configMotionAcceleration(0, 0);
        talon.configMotionAcceleration(0);
        talon.configMotionSCurveStrength(0, 0);
        talon.configMotionSCurveStrength(0);
        talon.clearMotionProfileTrajectories();
        talon.getMotionProfileTopLevelBufferCount();
        talon.pushMotionProfileTrajectory(trajectoryPoint);
        for (ControlMode controlMode : ControlMode.values())
        {
            talon.startMotionProfile(new BufferedTrajectoryPointStream(), 0, controlMode);
        }
        talon.isMotionProfileFinished();
        talon.isMotionProfileTopLevelBufferFull();
        talon.processMotionProfileBuffer();
        talon.getMotionProfileStatus(new MotionProfileStatus());
        talon.clearMotionProfileHasUnderrun(0);
        talon.clearMotionProfileHasUnderrun();
        talon.changeMotionControlFramePeriod(0);
        talon.configMotionProfileTrajectoryPeriod(0, 0);
        talon.configMotionProfileTrajectoryPeriod(0);
        talon.configMotionProfileTrajectoryInterpolationEnable(false, 0);
        talon.configMotionProfileTrajectoryInterpolationEnable(false);
        talon.configFeedbackNotContinuous(false, 0);
        talon.configRemoteSensorClosedLoopDisableNeutralOnLOS(false, 0);
        talon.configClearPositionOnLimitF(false, 0);
        talon.configClearPositionOnLimitR(false, 0);
        talon.configClearPositionOnQuadIdx(false, 0);
        talon.configLimitSwitchDisableNeutralOnLOS(false, 0);
        talon.configSoftLimitDisableNeutralOnLOS(false, 0);
        talon.configPulseWidthPeriod_EdgesPerRot(0, 0);
        talon.configPulseWidthPeriod_FilterWindowSz(0, 0);
        talon.getLastError();
        talon.getFaults(new Faults());
        talon.getStickyFaults(new StickyFaults());
        talon.clearStickyFaults(0);
        talon.clearStickyFaults();
        talon.getFirmwareVersion();
        talon.hasResetOccurred();
        talon.configSetCustomParam(0, 0, 0);
        talon.configSetCustomParam(0, 0);
        talon.configGetCustomParam(0, 0);
        talon.configGetCustomParam(0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            talon.configSetParameter(paramEnum, 0, 0, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            talon.configSetParameter(paramEnum, 0, 0, 0);
        }
        talon.configSetParameter(0, 0, 0, 0, 0);
        talon.configSetParameter(0, 0, 0, 0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            talon.configGetParameter(paramEnum, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            talon.configGetParameter(paramEnum, 0);
        }
        talon.configGetParameter(0, 0, 0);
        talon.configGetParameter(0, 0);
        talon.getBaseID();
        talon.getControlMode();
        for (FollowerType followerType : FollowerType.values())
        {
            talon.follow(followTalon, followerType);
        }
        talon.follow(followTalon);
        talon.valueUpdated();
        talon.configureSlot(new SlotConfiguration());
        talon.configureSlot(new SlotConfiguration(), 0, 0);
        talon.getSlotConfigs(new SlotConfiguration(), 0, 0);
        talon.getSlotConfigs(new SlotConfiguration());
        talon.configureFilter(new FilterConfiguration(), 0, 0, false);
        talon.configureFilter(new FilterConfiguration(), 0, 0);
        talon.configureFilter(new FilterConfiguration());
        talon.getFilterConfigs(new FilterConfiguration(), 0, 0);
        talon.getFilterConfigs(new FilterConfiguration());

        talon.DestroyObject();
    }
}
