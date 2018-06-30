package com.snobot.simulator.config.v0;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import com.snobot.simulator.config.BasicModuleConfig;
import com.snobot.simulator.config.EncoderConfig;
import com.snobot.simulator.config.v1.SimulatorConfigV1;
import com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiDigitalIoWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiEncoderWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiRelayWrapper;
import com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim.CtreEncoder;

/**
 * Class to read the configuration file and set up the simulator components
 *
 * @author PJ
 *
 */
public class SimulatorConfigReaderV0
{
    private static final Logger sLOGGER = LogManager.getLogger(SimulatorConfigReaderV0.class);

    /**
     * Loads the given config file. Calling this will save off the config, but
     * not actually make any of the {@code setName()} or
     * {@code hookUpSpeedController()} calls, as the robot may not be set up
     * yet. It will however setup the SPI and I2C factories
     *
     * @param aConfigFile
     *            The config file to load
     * @return True if the config was loaded succesfully
     */
    public SimulatorConfigV1 tryLoadAndConvert(File aConfigFile)
    {
        try
        {
            sLOGGER.log(Level.WARN, "Try to load " + aConfigFile.getAbsolutePath() + " with the v0 format");

            // Need to hack up the old config file to try to get it in something
            // snakeyaml can read
            File tempFile = createTempFile(File.createTempFile("convertedConfig", "yml"), aConfigFile);

            Yaml yaml = new Yaml();

            try (Reader fr = new InputStreamReader(new FileInputStream(tempFile), "UTF-8"))
            {
                SimulatorConfigV0 simulatorConfig = (SimulatorConfigV0) yaml.load(fr);
                SimulatorConfigV1 output = convert(simulatorConfig);
                if (output != null)
                {
                    sLOGGER.log(Level.WARN,
                            "V0 config file converted to V1.  It is recommended you replace your file with " + tempFile.getAbsolutePath());
                }
                return output;
            }
        }
        catch (IOException ex)
        {
            sLOGGER.log(Level.WARN, ex);
        }

        return null;
    }

    private File createTempFile(File aTempFile, File aInputFile)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(aInputFile)); BufferedWriter bw = new BufferedWriter(new FileWriter(aTempFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                if (line.contains("!!com.snobot.simulator.config.SimulatorConfig"))
                {
                    line = "!!com.snobot.simulator.config.v0.SimulatorConfigV0";
                }

                bw.write(line + "\n");
            }
            bw.flush();
        }
        catch (IOException ex)
        {
            sLOGGER.log(Level.WARN, ex);
        }

        return aTempFile;
    }

    private SimulatorConfigV1 convert(SimulatorConfigV0 aSimulatorConfig)
    {
        SimulatorConfigV1 output = new SimulatorConfigV1();

        convertGyros(aSimulatorConfig, output);
        convertAnalogIO(aSimulatorConfig, output);
        convertDigitalIO(aSimulatorConfig, output);
        convertRelays(aSimulatorConfig, output);
        convertRelays(aSimulatorConfig, output);
        convertSolenoids(aSimulatorConfig, output);
        convertPmws(aSimulatorConfig, output);
        convertEncoders(aSimulatorConfig, output);

        return output;
    }

    private void convertGyros(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (BasicModuleConfig config : aSimulatorConfig.getmGyros())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiAnalogGyroWrapper.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert gyro " + handle);
            }
        }
        aOutput.setmGyros(aSimulatorConfig.getmGyros());
    }

    private void convertDigitalIO(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (BasicModuleConfig config : aSimulatorConfig.getmDigitalIO())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiDigitalIoWrapper.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert DIO " + handle);
            }
        }
        aOutput.setmDigitalIO(aSimulatorConfig.getmDigitalIO());
    }

    private void convertAnalogIO(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        if (!aSimulatorConfig.getmAnalogIO().isEmpty())
        {
            sLOGGER.log(Level.WARN, "Representation of analog IO has changed, and cannot be automatically converted");
        }
    }

    private void convertRelays(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (BasicModuleConfig config : aSimulatorConfig.getmRelays())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiRelayWrapper.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert relay " + handle);
            }
        }
        aOutput.setmRelays(aSimulatorConfig.getmRelays());
    }

    private void convertSolenoids(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (BasicModuleConfig config : aSimulatorConfig.getmSolenoids())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiSolenoidWrapper.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert solenoid " + handle);
            }
        }
        aOutput.setmSolenoids(aSimulatorConfig.getmSolenoids());
    }

    private void convertPmws(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (BasicModuleConfig config : aSimulatorConfig.getmPwm())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiPwmWrapper.class.getName());
            }
            else if (handle >= 100 && handle <= 164)
            {
                config.setmType(CtreTalonSrxSpeedControllerSim.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert Speed Controller " + handle);
            }
        }
        aOutput.setmPwm(aSimulatorConfig.getmPwm());
    }

    private void convertEncoders(SimulatorConfigV0 aSimulatorConfig, SimulatorConfigV1 aOutput)
    {
        for (EncoderConfig config : aSimulatorConfig.getmEncoders())
        {
            int handle = config.getmHandle();
            if (handle >= 0 && handle <= 100)
            {
                config.setmType(WpiEncoderWrapper.class.getName());
            }
            else if (handle >= 100 && handle <= 164)
            {
                config.setmType(CtreEncoder.class.getName());
            }
            else
            {
                sLOGGER.log(Level.WARN, "Didn't know how to convert encoder " + handle);
            }
        }
        aOutput.setmEncoders(aSimulatorConfig.getmEncoders());
    }
}
