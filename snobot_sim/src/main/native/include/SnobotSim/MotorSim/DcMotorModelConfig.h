/*
 * DcMotorModelConfig.h
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#pragma once
#include <string>

#include "SnobotSim/ExportHelper.h"

struct EXPORT_ DcMotorModelConfig
{
public:
    struct FactoryParams
    {
        std::string mMotorName;
        int mNumMotors;
        double mGearReduction;
        double mTransmissionEfficiency;

        FactoryParams() :
                mMotorName(""),
                mNumMotors(0),
                mGearReduction(0),
                mTransmissionEfficiency(0)
        {
        }
    };

    DcMotorModelConfig(
            const FactoryParams& aFactoryParams,
            double aNominalVoltage,
            double aFreeSpeedRpm,
            double aFreeCurrent,
            double aStallTorque,
            double aStallCurrent,
            double aMotorInertia,
            bool aHasBrake = false,
            bool aInverted = false);

    DcMotorModelConfig(
            const FactoryParams& aFactoryParams,
            double aNominalVoltage,
            double aFreeSpeedRpm,
            double aFreeCurrent,
            double aStallTorque,
            double aStallCurrent,
            double aMotorInertia,

            bool aHasBrake,
            bool aInverted,

            double aKT,
            double aKV,
            double aResistance);

    virtual ~DcMotorModelConfig();

    FactoryParams mFactoryParams;

    // Motor Parameters
    const double NOMINAL_VOLTAGE;
    const double FREE_SPEED_RPM;
    const double FREE_CURRENT;
    const double STALL_TORQUE;
    const double STALL_CURRENT;

    // Motor constants
    double mKT;
    double mKV;
    double mResistance;
    double mMotorInertia;
    bool mInverted;

    // Indicates the motor has a brake, i.e. when givin 0 volts it will stay put
    bool mHasBrake;
};
