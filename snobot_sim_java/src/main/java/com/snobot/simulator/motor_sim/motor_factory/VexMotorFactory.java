package com.snobot.simulator.motor_sim.motor_factory;

import java.util.HashMap;
import java.util.Map;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;

public final class VexMotorFactory
{
    private static final double NOMINAL_VOLTAGE = 12;

    private static final Map<String, DcMotorModelConfig.MotorParams> sMOTOR_PARAMS;

    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_CIM = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 5330, 2.7, 2.41, 131);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_MINI_CIM = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 5840, 3, 1.41,
            89);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_BAG = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 13180, 1.8, .43, 53);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_775PRO = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 18730, .7, .71,
            134);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_AM_775_125 = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 5800, 1.6, .28,
            18);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_BB_RS775 = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 13050, 2.7, .72,
            97);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_AM_9015 = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 14270, 3.7, .36,
            71);
    private static final DcMotorModelConfig.MotorParams MOTOR_PARAMS_BB_RS550 = new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 19000, .4, .38,
            84);

    static
    {
        sMOTOR_PARAMS = new HashMap<>();
        sMOTOR_PARAMS.put("CIM", MOTOR_PARAMS_CIM);
        sMOTOR_PARAMS.put("Mini CIM", MOTOR_PARAMS_MINI_CIM);
        sMOTOR_PARAMS.put("Bag", MOTOR_PARAMS_BAG);
        sMOTOR_PARAMS.put("775 Pro", MOTOR_PARAMS_775PRO);
        sMOTOR_PARAMS.put("Andymark RS 775-125", MOTOR_PARAMS_AM_775_125);
        sMOTOR_PARAMS.put("Banebots RS 775", MOTOR_PARAMS_BB_RS775);
        sMOTOR_PARAMS.put("Andymark 9015", MOTOR_PARAMS_AM_9015);
        sMOTOR_PARAMS.put("Banebots RS 550", MOTOR_PARAMS_BB_RS550);
    }

    private VexMotorFactory()
    {

    }

    public static DcMotorModelConfig createMotor(String aMotorName)
    {
        DcMotorModelConfig.FactoryParams factoryParams = new DcMotorModelConfig.FactoryParams(aMotorName, 1, 1, 1, false, false);
        DcMotorModelConfig.MotorParams motorParams = sMOTOR_PARAMS.get(aMotorName);

        if (motorParams != null)
        {
            return new DcMotorModelConfig(factoryParams, motorParams);
        }
        return new DcMotorModelConfig(factoryParams, new DcMotorModelConfig.MotorParams(NOMINAL_VOLTAGE, 0, 0, 0, 0));
    }
}
