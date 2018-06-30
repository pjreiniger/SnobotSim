package com.snobot.simulator.simulator_components.ctre;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;
import com.ctre.phoenix.sensors.PigeonIMU.FusionStatus;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;
import com.ctre.phoenix.sensors.PigeonIMU_ControlFrame;
import com.ctre.phoenix.sensors.PigeonIMU_Faults;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;
import com.ctre.phoenix.sensors.PigeonIMU_StickyFaults;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestUnsupportedPigeonImuOperations extends BaseSimulatorJavaTest
{
    @Test
    public void testAllFunctions()
    {
        PigeonIMU imu = new PigeonIMU(0);
        imu.setYaw(0, 0);
        imu.addYaw(0, 0);
        imu.setYawToCompass(0);
        imu.setFusedHeading(0, 0);
        imu.addFusedHeading(0, 0);
        imu.setFusedHeadingToCompass(0);
        imu.setAccumZAngle(0, 0);
        imu.configTemperatureCompensationEnable(false, 0);
        imu.setCompassDeclination(0, 0);
        imu.setCompassAngle(0, 0);
        imu.enterCalibrationMode(CalibrationMode.Accelerometer, 0);
        imu.getGeneralStatus(new GeneralStatus());
        imu.getLastError();
        imu.get6dQuaternion(new double[100]);
        imu.getYawPitchRoll(new double[100]);
        imu.getAccumGyro(new double[100]);
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
        imu.getFirmwareVersion();
        imu.hasResetOccurred();
        imu.configSetCustomParam(0, 0, 0);
        // imu.configGetCustomParam(0, 0); // TODO
        imu.configSetParameter(ParamEnum.eAccumZ, 0, 0, 0, 0);
        imu.configSetParameter(0, 0, 0, 0, 0);
        imu.configGetParameter(ParamEnum.eAccumZ, 0, 0);
        imu.configGetParameter(0, 0, 0);
        imu.setStatusFramePeriod(PigeonIMU_StatusFrame.BiasedStatus_2_Gyro, 0, 0);
        imu.getStatusFramePeriod(PigeonIMU_StatusFrame.BiasedStatus_2_Gyro, 0);
        imu.setControlFramePeriod(PigeonIMU_ControlFrame.Control_1, 0);
        imu.getFaults(new PigeonIMU_Faults());
        imu.getStickyFaults(new PigeonIMU_StickyFaults());
        imu.clearStickyFaults(0);
    }

}
