/*
 * BaseDcMotorSimulator.cpp
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#include "SnobotSim/MotorSim/BaseDcMotorSimulator.h"

BaseDcMotorSimulator::BaseDcMotorSimulator(
        const std::string& aSimulatorType,
        const DcMotorModel& aMotorModel,
        double aConversionFactor) :
        mSimulatorType(aSimulatorType),
        mMotorModel(aMotorModel),
        mConversionFactor(aConversionFactor),
        mVoltagePercentage(0)

{
}

BaseDcMotorSimulator::~BaseDcMotorSimulator()
{
}

const std::string& BaseDcMotorSimulator::GetSimulatorType()
{
    return mSimulatorType;
}

void BaseDcMotorSimulator::SetVoltagePercentage(double aSpeed)
{
    mVoltagePercentage = aSpeed;
}

double BaseDcMotorSimulator::GetVoltagePercentage()
{
    return mVoltagePercentage;
}

double BaseDcMotorSimulator::GetVelocity()
{
    return mMotorModel.GetVelocity() * mConversionFactor;
}

double BaseDcMotorSimulator::GetPosition()
{
    return mMotorModel.GetPosition() * mConversionFactor;
}

double BaseDcMotorSimulator::GetAcceleration()
{
    return mMotorModel.GetAcceleration() * mConversionFactor;
}

void BaseDcMotorSimulator::Reset()
{
    Reset(0, 0, 0);
}

void BaseDcMotorSimulator::Reset(double aPosition, double aVelocity, double aCurrent)
{
    mMotorModel.Reset(aPosition, aVelocity, aCurrent);
}

double BaseDcMotorSimulator::GetConversionFactor()
{
    return mConversionFactor;
}

double BaseDcMotorSimulator::GetCurrent()
{
    return mMotorModel.GetCurrent();
}

const DcMotorModel& BaseDcMotorSimulator::GetMotorModel()
{
    return mMotorModel;
}
