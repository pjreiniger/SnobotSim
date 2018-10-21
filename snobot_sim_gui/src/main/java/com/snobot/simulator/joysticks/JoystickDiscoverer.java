package com.snobot.simulator.joysticks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.joysticks.joystick_specializations.GenericGamepadJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.KeyboardJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.Ps4Joystick;
import com.snobot.simulator.joysticks.joystick_specializations.XboxJoystick;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

public final class JoystickDiscoverer
{
    private static final Logger sLOGGER = LogManager.getLogger(JoystickDiscoverer.class);
    private static final List<Type> sTYPES_TO_IGNORE = Arrays.asList(Type.UNKNOWN, Type.MOUSE);

    private static final Map<Class<? extends IMockJoystick>, String> sAVAILABLE_SPECIALIZATIONS;

    static
    {
        sAVAILABLE_SPECIALIZATIONS = new HashMap<>();
        sAVAILABLE_SPECIALIZATIONS.put(Ps4Joystick.class, "PS4");
        sAVAILABLE_SPECIALIZATIONS.put(GenericGamepadJoystick.class, "Generic Gamepad");
        sAVAILABLE_SPECIALIZATIONS.put(XboxJoystick.class, "XBOX");
        sAVAILABLE_SPECIALIZATIONS.put(KeyboardJoystick.class, "Keyboard");
    }

    private JoystickDiscoverer()
    {

    }

    public static Collection<Class<? extends IMockJoystick>> getSpecializationTypes()
    {
        return sAVAILABLE_SPECIALIZATIONS.keySet();
    }

    public static Collection<String> getJoystickNames()
    {
        return sAVAILABLE_SPECIALIZATIONS.values();
    }

    public static String getSpecialization(Class<?> aDefaultSpecialization)
    {
        return sAVAILABLE_SPECIALIZATIONS.get(aDefaultSpecialization);
    }

    public static Map<String, ControllerConfiguration> rediscoverJoysticks()
    {
        Controller[] availableControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        Map<String, ControllerConfiguration> controllerConfig = new LinkedHashMap<>();
        Map<String, Integer> deviceCounter = new LinkedHashMap<>();

        controllerConfig.clear();
        for (Controller controller : availableControllers)
        {
            if (sTYPES_TO_IGNORE.contains(controller.getType()))
            {
                continue;
            }

            Class<? extends IMockJoystick> specializationClass = getDefaultSpecialization(controller);

            int deviceCtr = deviceCounter.getOrDefault(controller.getName(), 0);
            deviceCounter.put(controller.getName(), ++deviceCtr);

            String deviceName = controller.getName() + " " + deviceCtr;

            ControllerConfiguration configuration = new ControllerConfiguration(controller, specializationClass);
            controllerConfig.put(deviceName, configuration);
        }

        return sort(controllerConfig);
    }

    // http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
    private static Map<String, ControllerConfiguration> sort(Map<String, ControllerConfiguration> aMap)
    {
        List<Map.Entry<String, ControllerConfiguration>> list = new LinkedList<Map.Entry<String, ControllerConfiguration>>(aMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ControllerConfiguration>>()
        {
            @Override
            public int compare(Map.Entry<String, ControllerConfiguration> aObject1, Map.Entry<String, ControllerConfiguration> aObject2)
            {
                // Hacky sort, we just want the keyboard to bubble down
                Class<? extends IMockJoystick> clazz1 = aObject1.getValue().mSpecialization;
                Class<? extends IMockJoystick> clazz2 = aObject2.getValue().mSpecialization;

                int clazz1Value = KeyboardJoystick.class.equals(clazz1) ? -1 : 1;
                int clazz2Value = KeyboardJoystick.class.equals(clazz2) ? -1 : 1;

                return clazz2Value - clazz1Value;
            }
        });

        Map<String, ControllerConfiguration> result = new LinkedHashMap<String, ControllerConfiguration>();
        for (Map.Entry<String, ControllerConfiguration> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private static Class<? extends IMockJoystick> getDefaultSpecialization(Controller aController)
    {
        Class<? extends IMockJoystick> output = null;

        Type type = aController.getType();
        String name = aController.getName();

        if (type == Type.KEYBOARD)
        {
            output = KeyboardJoystick.class;
        }
        else if (type == Type.GAMEPAD)
        {
            sLOGGER.log(Level.ERROR, "Unknown joystick name " + name);
            output = GenericGamepadJoystick.class;
        }
        else if (type == Type.STICK)
        {
            if ("Wireless Controller".equals(name))
            {
                output = Ps4Joystick.class;
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown joystick name " + name);
                output = GenericGamepadJoystick.class;
            }
        }
        else
        {
            sLOGGER.log(Level.WARN, "Unknown joystick type " + type);
        }

        return output;
    }
}
