//package com.snobot.simulator.joysticks;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Properties;
//
//import org.apache.logging.log4j.Level;
//
//import edu.wpi.first.wpilibj.DriverStation;
//
//public class JoystickConfigurationReader
//{
//
//    private final Map<String, ControllerConfiguration> mControllerConfig;
//
//    private void writeJoystickFile()
//    {
//        try
//        {
//            Properties p = new Properties();
//            for (int i = 0; i < DriverStation.kJoystickPorts; ++i)
//            {
//                String joystickName = mJoystickMap[i].getName();
//
//                ControllerConfiguration config = mControllerConfig.get(joystickName);
//                String specializationName = config == null ? null : config.mSpecialization.getName();
//
//                p.put(sKEY + i, joystickName + "---" + specializationName);
//            }
//
//            try (FileOutputStream stream = new FileOutputStream(sJOYSTICK_CONFIG_FILE))
//            {
//                p.store(stream, "");
//            }
//
//            sLOGGER.log(Level.INFO, "Wrote joystick config file to " + new File(sJOYSTICK_CONFIG_FILE).getAbsolutePath());
//        }
//        catch (Exception ex)
//        {
//            sLOGGER.log(Level.ERROR, ex);
//        }
//    }
//
//    private void readLegacyFile(String aConfigFile)
//    {
//        if (!Files.exists(Paths.get(aConfigFile)))
//        {
//            writeJoystickFile();
//            return;
//        }
//
//        try
//        {
//            InputStream inputStream = new FileInputStream(aConfigFile);
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            inputStream.close();
//
//            for (Entry<Object, Object> i : properties.entrySet())
//            {
//                int number = Integer.parseInt(i.getKey().toString().substring(sKEY.length()));
//
//                String config = i.getValue().toString();
//                String[] parts = config.split("---");
//
//                String joystickName = parts[0];
//                String specialization = parts[1];
//
//                if (!"null".equals(specialization))
//                {
//                    setSpecialization(joystickName, (Class<? extends IMockJoystick>) Class.forName(specialization), false);
//                }
//                setJoysticks(number, joystickName, false);
//            }
//        }
//        catch (IOException | ClassNotFoundException ex)
//        {
//            sLOGGER.log(Level.ERROR, ex);
//        }
//    }
//}
