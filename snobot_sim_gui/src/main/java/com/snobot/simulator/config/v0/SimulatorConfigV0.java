package com.snobot.simulator.config.v0;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.snobot.simulator.config.BasicModuleConfig;
import com.snobot.simulator.config.EncoderConfig;
import com.snobot.simulator.config.PwmConfig;

public class SimulatorConfigV0
{
    private List<BasicModuleConfig> mAccelerometers;
    private List<BasicModuleConfig> mAnalogIO;
    private List<BasicModuleConfig> mDigitalIO;
    private List<BasicModuleConfig> mGyros;
    private List<BasicModuleConfig> mRelays;
    private List<BasicModuleConfig> mSolenoids;
    private List<EncoderConfig> mEncoders;
    private List<PwmConfig> mPwm;
    private Map<Integer, String> mDefaultI2CWrappers;
    private Map<Integer, String> mDefaultSpiWrappers;
    private List<Object> mSimulatorComponents;

    public SimulatorConfigV0()
    {
        mAccelerometers = new ArrayList<>();
        mAnalogIO = new ArrayList<>();
        mDigitalIO = new ArrayList<>();
        mGyros = new ArrayList<>();
        mRelays = new ArrayList<>();
        mSolenoids = new ArrayList<>();
        mEncoders = new ArrayList<>();
        mPwm = new ArrayList<>();

        mDefaultI2CWrappers = new LinkedHashMap<>();
        mDefaultSpiWrappers = new LinkedHashMap<>();
        mSimulatorComponents = new ArrayList<>();
    }

    public List<BasicModuleConfig> getmDigitalIO()
    {
        return mDigitalIO;
    }

    public void setmDigitalIO(List<BasicModuleConfig> aDigitalIO)
    {
        this.mDigitalIO = aDigitalIO;
    }

    public List<BasicModuleConfig> getmAnalogIO()
    {
        return mAnalogIO;
    }

    public void setmAnalogIO(List<BasicModuleConfig> aAnalogIO)
    {
        this.mAnalogIO = aAnalogIO;
    }

    public List<BasicModuleConfig> getmRelays()
    {
        return mRelays;
    }

    public void setmRelays(List<BasicModuleConfig> aRelays)
    {
        this.mRelays = aRelays;
    }

    public List<BasicModuleConfig> getmSolenoids()
    {
        return mSolenoids;
    }

    public void setmSolenoids(List<BasicModuleConfig> aSolenoids)
    {
        this.mSolenoids = aSolenoids;
    }

    public List<EncoderConfig> getmEncoders()
    {
        return mEncoders;
    }

    public void setmEncoders(List<EncoderConfig> aEncoders)
    {
        this.mEncoders = aEncoders;
    }

    public List<PwmConfig> getmPwm()
    {
        return mPwm;
    }

    public void setmPwm(List<PwmConfig> aPwm)
    {
        this.mPwm = aPwm;
    }

    public Map<Integer, String> getmDefaultI2CWrappers()
    {
        return mDefaultI2CWrappers;
    }

    public void setmDefaultI2CWrappers(Map<Integer, String> aDefaultI2CWrappers)
    {
        this.mDefaultI2CWrappers = aDefaultI2CWrappers;
    }

    public Map<Integer, String> getmDefaultSpiWrappers()
    {
        return mDefaultSpiWrappers;
    }

    public void setmDefaultSpiWrappers(Map<Integer, String> aDefaultSpiWrappers)
    {
        this.mDefaultSpiWrappers = aDefaultSpiWrappers;
    }

    public List<BasicModuleConfig> getmAccelerometers()
    {
        return mAccelerometers;
    }

    public void setmAccelerometers(List<BasicModuleConfig> aAccelerometers)
    {
        this.mAccelerometers = aAccelerometers;
    }

    public List<BasicModuleConfig> getmGyros()
    {
        return mGyros;
    }

    public void setmGyros(List<BasicModuleConfig> aGyros)
    {
        this.mGyros = aGyros;
    }

    public List<Object> getmSimulatorComponents()
    {
        return mSimulatorComponents;
    }

    public void setmSimulatorComponents(List<Object> aSimulatorComponents)
    {
        this.mSimulatorComponents = aSimulatorComponents;
    }

}
