/*
 * VexMotorFactory.cpp
 *
 *  Created on: Jun 21, 2017
 *      Author: preiniger
 */

#include "SnobotSim/MotorFactory/VexMotorFactory.h"

#include "SnobotSim/Logging/SnobotLogger.h"

const std::string VexMotorFactory::MOTOR_NAME_CIM = "CIM";
const std::string VexMotorFactory::MOTOR_NAME_MINI_CIM = "Mini CIM";
const std::string VexMotorFactory::MOTOR_NAME_BAG = "Bag";
const std::string VexMotorFactory::MOTOR_NAME_775_PRO = "775 Pro";
const std::string VexMotorFactory::MOTOR_NAME_AM_RS_775_125 = "Andymark RS 775-125";
const std::string VexMotorFactory::MOTOR_NAME_BB_RS_775 = "Banebots RS 775";
const std::string VexMotorFactory::MOTOR_NAME_AM_9015 = "Andymark 9015";
const std::string VexMotorFactory::MOTOR_NAME_BB_RS_550 = "Banebots RS 550";

const std::string VexMotorFactory::MOTOR_NAME_RS775 = "rs775";

#define OZIN_TO_NM 0.00706155183333

VexMotorFactory::VexMotorFactory()
{
}

VexMotorFactory::~VexMotorFactory()
{
}

DcMotorModelConfig VexMotorFactory::MakeTransmission(
        const DcMotorModelConfig& aMotorConfig, int aNumMotors, double aReduction, double aEfficiency)
{
    DcMotorModelConfig::FactoryParams factoryParams;
    factoryParams.mMotorName = aMotorConfig.mFactoryParams.mMotorName;
    factoryParams.mNumMotors = aNumMotors;
    factoryParams.mGearReduction = aReduction;
    factoryParams.mTransmissionEfficiency = aEfficiency;

    DcMotorModelConfig output(
            factoryParams,
            aMotorConfig.NOMINAL_VOLTAGE,
            aMotorConfig.FREE_SPEED_RPM / aReduction,
            aMotorConfig.FREE_CURRENT * aNumMotors,
            aMotorConfig.STALL_TORQUE * aNumMotors,
            aMotorConfig.STALL_CURRENT * aNumMotors,
            aMotorConfig.mMotorInertia * aNumMotors * aReduction * aReduction,
            aMotorConfig.mHasBrake,
            aMotorConfig.mInverted);

    output.mKT *= aEfficiency * aNumMotors * aReduction;

    return output;
}

DcMotorModelConfig VexMotorFactory::CreateMotor(
        const std::string& aName)
{
    const double NOMINAL_VOLTAGE = 12;

    DcMotorModelConfig::FactoryParams factoryParams;
    factoryParams.mMotorName = aName;
    factoryParams.mNumMotors = 1;
    factoryParams.mGearReduction = 1;
    factoryParams.mTransmissionEfficiency = 1;

    if (aName == MOTOR_NAME_CIM)
    {
        const double FREE_SPEED_RPM = 5330;
        const double FREE_CURRENT = 2.7;
        const double STALL_TORQUE = 2.41;
        const double STALL_CURRENT = 131;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_MINI_CIM)
    {
        const double FREE_SPEED_RPM = 5840;
        const double FREE_CURRENT = 3;
        const double STALL_TORQUE = 1.41;
        const double STALL_CURRENT = 89;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_BAG)
    {
        const double FREE_SPEED_RPM = 13180;
        const double FREE_CURRENT = 1.8;
        const double STALL_TORQUE = .43;
        const double STALL_CURRENT = 53;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_775_PRO)
    {
        const double FREE_SPEED_RPM = 18730;
        const double FREE_CURRENT = .7;
        const double STALL_TORQUE = .71;
        const double STALL_CURRENT = 134;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_AM_RS_775_125)
    {
        const double FREE_SPEED_RPM = 5800;
        const double FREE_CURRENT = 1.6;
        const double STALL_TORQUE = .28;
        const double STALL_CURRENT = 18;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_BB_RS_775)
    {
        const double FREE_SPEED_RPM = 13050;
        const double FREE_CURRENT = 2.7;
        const double STALL_TORQUE = .72;
        const double STALL_CURRENT = 97;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_AM_9015)
    {
        const double FREE_SPEED_RPM = 14270;
        const double FREE_CURRENT = 3.7;
        const double STALL_TORQUE = .36;
        const double STALL_CURRENT = 71;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_BB_RS_550)
    {
        const double FREE_SPEED_RPM = 19000;
        const double FREE_CURRENT = .4;
        const double STALL_TORQUE = .38;
        const double STALL_CURRENT = 84;

        return DcMotorModelConfig(factoryParams, NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
    else if (aName == MOTOR_NAME_RS775)
    {
        const double FREE_SPEED_RPM = 19500;
        const double FREE_CURRENT = 2.7;
        const double STALL_TORQUE = 166.65 * OZIN_TO_NM;
        const double STALL_CURRENT = 130;
        const double MOTOR_INERTIA = 1.20348237E-5;

        return DcMotorModelConfig(factoryParams, 18, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, MOTOR_INERTIA);
    }
    else
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Unknown motor sim name " << aName);
    }

    factoryParams.mMotorName = "";
    return DcMotorModelConfig(factoryParams, 0, 0, 0, 0, 0, 0);
}
