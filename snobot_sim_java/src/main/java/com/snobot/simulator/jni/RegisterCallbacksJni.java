package com.snobot.simulator.jni;

import com.snobot.simulator.jni.can.CanCallbackJni;
import com.snobot.simulator.jni.standard_components.AddressableLedCallbackJni;
import com.snobot.simulator.jni.standard_components.AnalogGyroCallbackJni;
import com.snobot.simulator.jni.standard_components.AnalogInCallbackJni;
import com.snobot.simulator.jni.standard_components.AnalogOutCallbackJni;
import com.snobot.simulator.jni.standard_components.DigitalCallbackJni;
import com.snobot.simulator.jni.standard_components.EncoderCallbackJni;
import com.snobot.simulator.jni.standard_components.I2CCallbackJni;
import com.snobot.simulator.jni.standard_components.PcmCallbackJni;
import com.snobot.simulator.jni.standard_components.PdpCallbackJni;
import com.snobot.simulator.jni.standard_components.PwmCallbackJni;
import com.snobot.simulator.jni.standard_components.RelayCallbackJni;
import com.snobot.simulator.jni.standard_components.SpiCallbackJni;

import edu.wpi.first.hal.sim.SimDeviceSim;
import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;

public final class RegisterCallbacksJni extends BaseSnobotJni
{
    static
    {
        reset();
    }

    private RegisterCallbacksJni()
    {

    }

    public static void reset()
    {
        SimulatorJNI.resetHandles();
        SimDeviceSim.resetData();

        AddressableLedCallbackJni.reset();
        AnalogInCallbackJni.reset();
        AnalogOutCallbackJni.reset();
        AnalogGyroCallbackJni.reset();
        DigitalCallbackJni.reset();
        EncoderCallbackJni.reset();
        I2CCallbackJni.reset();
        PcmCallbackJni.reset();
        PdpCallbackJni.reset();
        PwmCallbackJni.reset();
        RelayCallbackJni.reset();
        SpiCallbackJni.reset();

        CanCallbackJni.reset();
    }
}
