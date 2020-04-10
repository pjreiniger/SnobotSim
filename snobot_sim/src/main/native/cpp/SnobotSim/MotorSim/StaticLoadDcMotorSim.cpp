/*
 * StaticLoadDcMotorSim.cpp
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#include "SnobotSim/MotorSim/StaticLoadDcMotorSim.h"

StaticLoadDcMotorSim::StaticLoadDcMotorSim(const DcMotorModel& aMotorModel, double aLoad, double aConversionFactor) :
        BaseDcMotorSimulator(GetType(), aMotorModel, aConversionFactor),
        mLoad(aLoad)
{
}

StaticLoadDcMotorSim::~StaticLoadDcMotorSim()
{
}

double StaticLoadDcMotorSim::GetLoad()
{
    return mLoad;
}

void StaticLoadDcMotorSim::Update(double cycleTime)
{
    mMotorModel.Step(mVoltagePercentage * 12, mLoad, 0, cycleTime);
}
