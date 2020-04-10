package com.snobot.simulator.simulator_components.ctre.unsupported;

import com.snobot.test.utilities.BaseSimulatorJniTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;
import com.ctre.phoenix.sensors.PigeonIMU.FusionStatus;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;
import com.ctre.phoenix.sensors.PigeonIMUConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU_ControlFrame;
import com.ctre.phoenix.sensors.PigeonIMU_Faults;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;
import com.ctre.phoenix.sensors.PigeonIMU_StickyFaults;

@SuppressWarnings({"PMD.NcssCount", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
@Tag("CTRE")
public class TestPigeonIMUFunctions extends BaseSimulatorJniTest
{
    @Test
    public void testAllFunctions()
    {
        PigeonIMU imu = new PigeonIMU(0);

        imu.setYaw(0, 0);
        imu.setYaw(0);
        imu.addYaw(0, 0);
        imu.addYaw(0);
        imu.setYawToCompass(0);
        imu.setYawToCompass();
        imu.setFusedHeading(0, 0);
        imu.setFusedHeading(0);
        imu.addFusedHeading(0, 0);
        imu.addFusedHeading(0);
        imu.setFusedHeadingToCompass(0);
        imu.setFusedHeadingToCompass();
        imu.setAccumZAngle(0, 0);
        imu.setAccumZAngle(0);
        imu.configTemperatureCompensationEnable(false, 0);
        imu.setTemperatureCompensationDisable(false, 0);
        imu.setTemperatureCompensationDisable(false);
        imu.setCompassDeclination(0, 0);
        imu.setCompassDeclination(0);
        imu.setCompassAngle(0, 0);
        imu.setCompassAngle(0);
        for (CalibrationMode calibrationMode : CalibrationMode.values())
        {
            imu.enterCalibrationMode(calibrationMode, 0);
        }
        for (CalibrationMode calibrationMode : CalibrationMode.values())
        {
            imu.enterCalibrationMode(calibrationMode);
        }
        imu.getGeneralStatus(new GeneralStatus());
        imu.getLastError();
        imu.get6dQuaternion(new double[4]);
        imu.getYawPitchRoll(new double[3]);
        imu.getAccumGyro(new double[3]);
        imu.getAbsoluteCompassHeading();
        imu.getCompassHeading();
        imu.getCompassFieldStrength();
        imu.getTemp();
        imu.getState();
        imu.getUpTime();
        imu.getRawMagnetometer(new short[3]);
        imu.getBiasedMagnetometer(new short[3]);
        imu.getBiasedAccelerometer(new short[3]);
        imu.getRawGyro(new double[3]);
        imu.getAccelerometerAngles(new double[3]);
        imu.getFusedHeading(new FusionStatus());
        imu.getFusedHeading();
        imu.getFirmwareVersion();
        imu.hasResetOccurred();
        imu.configSetCustomParam(0, 0, 0);
        imu.configSetCustomParam(0, 0);
        imu.configGetCustomParam(0, 0);
        imu.configGetCustomParam(0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            imu.configSetParameter(paramEnum, 0, 0, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            imu.configSetParameter(paramEnum, 0, 0, 0);
        }
        imu.configSetParameter(0, 0, 0, 0, 0);
        imu.configSetParameter(0, 0, 0, 0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            imu.configGetParameter(paramEnum, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            imu.configGetParameter(paramEnum, 0);
        }
        imu.configGetParameter(0, 0, 0);
        imu.configGetParameter(0, 0);
        for (PigeonIMU_StatusFrame statusFrame : PigeonIMU_StatusFrame.values())
        {
            imu.setStatusFramePeriod(statusFrame, 0, 0);
        }
        for (PigeonIMU_StatusFrame statusFrame : PigeonIMU_StatusFrame.values())
        {
            imu.setStatusFramePeriod(statusFrame, 0);
        }
        imu.setStatusFramePeriod(0, 0, 0);
        imu.setStatusFramePeriod(0, 0);
        for (PigeonIMU_StatusFrame statusFrame : PigeonIMU_StatusFrame.values())
        {
            imu.getStatusFramePeriod(statusFrame, 0);
        }
        for (PigeonIMU_StatusFrame statusFrame : PigeonIMU_StatusFrame.values())
        {
            imu.getStatusFramePeriod(statusFrame);
        }
        for (PigeonIMU_ControlFrame statusFrame : PigeonIMU_ControlFrame.values())
        {
            imu.setControlFramePeriod(statusFrame, 0);
        }
        imu.setControlFramePeriod(0, 0);
        imu.getFaults(new PigeonIMU_Faults());
        imu.getStickyFaults(new PigeonIMU_StickyFaults());
        imu.clearStickyFaults(0);
        imu.clearStickyFaults();
        imu.getDeviceID();
        imu.configAllSettings(new PigeonIMUConfiguration(), 0);
        imu.configAllSettings(new PigeonIMUConfiguration());
        imu.getAllConfigs(new PigeonIMUConfiguration(), 0);
        imu.getAllConfigs(new PigeonIMUConfiguration());
        imu.configFactoryDefault(0);
        imu.configFactoryDefault();

        imu.DestroyObject();
    }

}
