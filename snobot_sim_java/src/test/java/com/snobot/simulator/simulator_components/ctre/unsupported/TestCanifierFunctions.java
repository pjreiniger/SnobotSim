package com.snobot.simulator.simulator_components.ctre.unsupported;

import com.snobot.test.utilities.BaseSimulatorJavaTest;
import org.junit.jupiter.api.Test;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.GeneralPin;
import com.ctre.phoenix.CANifier.LEDChannel;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.CANifier.PinValues;
import com.ctre.phoenix.CANifierConfiguration;
import com.ctre.phoenix.CANifierControlFrame;
import com.ctre.phoenix.CANifierFaults;
import com.ctre.phoenix.CANifierStatusFrame;
import com.ctre.phoenix.CANifierStickyFaults;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.VelocityPeriod;

@SuppressWarnings({"PMD.NcssCount", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public class TestCanifierFunctions extends BaseSimulatorJavaTest
{
    @Test
    public void testAllFunctions()
    {
        CANifier canifier = new CANifier(0);

        for (LEDChannel ledChannel : LEDChannel.values())
        {
            canifier.setLEDOutput(0, ledChannel);
        }
        for (GeneralPin generalPin : GeneralPin.values())
        {
            canifier.setGeneralOutput(generalPin, false, false);
        }
        canifier.setGeneralOutputs(0, 0);
        canifier.getGeneralInputs(new PinValues());
        for (GeneralPin generalPin : GeneralPin.values())
        {
            canifier.getGeneralInput(generalPin);
        }
        canifier.getLastError();
        canifier.setPWMOutput(0, 0);
        canifier.enablePWMOutput(0, false);
        for (PWMChannel pwmChannel : PWMChannel.values())
        {
            canifier.getPWMInput(pwmChannel, new double[2]);
        }
        canifier.getQuadraturePosition();
        canifier.setQuadraturePosition(0, 0);
        canifier.getQuadratureVelocity();
        for (VelocityPeriod velocityPeriod : VelocityPeriod.values())
        {
            canifier.configVelocityMeasurementPeriod(velocityPeriod, 0);
        }
        for (VelocityPeriod velocityPeriod : VelocityPeriod.values())
        {
            canifier.configVelocityMeasurementPeriod(velocityPeriod);
        }
        canifier.configVelocityMeasurementWindow(0, 0);
        canifier.configVelocityMeasurementWindow(0);
        canifier.configClearPositionOnLimitF(false, 0);
        canifier.configClearPositionOnLimitR(false, 0);
        canifier.configClearPositionOnQuadIdx(false, 0);
        canifier.configSetCustomParam(0, 0, 0);
        canifier.configSetCustomParam(0, 0);
        canifier.configGetCustomParam(0, 0);
        canifier.configGetCustomParam(0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            canifier.configSetParameter(paramEnum, 0, 0, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            canifier.configSetParameter(paramEnum, 0, 0, 0);
        }
        canifier.configSetParameter(0, 0, 0, 0, 0);
        canifier.configSetParameter(0, 0, 0, 0);
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            canifier.configGetParameter(paramEnum, 0, 0);
        }
        for (ParamEnum paramEnum : ParamEnum.values())
        {
            canifier.configGetParameter(paramEnum, 0);
        }
        for (CANifierStatusFrame canifierStatusFrame : CANifierStatusFrame.values())
        {
            canifier.setStatusFramePeriod(canifierStatusFrame, 0, 0);
        }
        for (CANifierStatusFrame canifierStatusFrame : CANifierStatusFrame.values())
        {
            canifier.setStatusFramePeriod(canifierStatusFrame, 0);
        }
        canifier.setStatusFramePeriod(0, 0, 0);
        canifier.setStatusFramePeriod(0, 0);
        for (CANifierStatusFrame canifierStatusFrame : CANifierStatusFrame.values())
        {
            canifier.getStatusFramePeriod(canifierStatusFrame, 0);
        }
        for (CANifierStatusFrame canifierStatusFrame : CANifierStatusFrame.values())
        {
            canifier.getStatusFramePeriod(canifierStatusFrame);
        }
        for (CANifierControlFrame canifierControlFrame : CANifierControlFrame.values())
        {
            canifier.setControlFramePeriod(canifierControlFrame, 0);
        }
        canifier.setControlFramePeriod(0, 0);
        canifier.getFirmwareVersion();
        canifier.hasResetOccurred();
        canifier.getFaults(new CANifierFaults());
        canifier.getStickyFaults(new CANifierStickyFaults());
        canifier.clearStickyFaults(0);
        canifier.getBusVoltage();
        canifier.getDeviceID();
        canifier.configAllSettings(new CANifierConfiguration(), 0);
        canifier.configAllSettings(new CANifierConfiguration());
        canifier.getAllConfigs(new CANifierConfiguration(), 0);
        canifier.getAllConfigs(new CANifierConfiguration());
        canifier.configFactoryDefault(0);
        canifier.configFactoryDefault();

        canifier.DestroyObject();
    }
}
