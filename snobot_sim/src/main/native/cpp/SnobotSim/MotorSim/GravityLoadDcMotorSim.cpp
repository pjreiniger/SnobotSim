/*
 * GravityLoadDcMotorSim.cpp
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#include "SnobotSim/MotorSim/GravityLoadDcMotorSim.h"

const double GravityLoadDcMotorSim::sGRAVITY = 9.8;

GravityLoadDcMotorSim::GravityLoadDcMotorSim(const DcMotorModel& aMotorModel, double aLoad) :
        BaseDcMotorSimulator(GetType(), aMotorModel),
        mLoad(aLoad)
{
}

GravityLoadDcMotorSim::~GravityLoadDcMotorSim()
{
}

double GravityLoadDcMotorSim::GetLoad()
{
    return mLoad;
}

void GravityLoadDcMotorSim::Update(double cycleTime)
{
    double extraAcceleration = -sGRAVITY;

    mMotorModel.Step(mVoltagePercentage * 12, mLoad, extraAcceleration, cycleTime);
}
