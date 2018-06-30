/*
 * RotationalLoadDcMotorSim.cpp
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#include "SnobotSim/MotorSim/RotationalLoadDcMotorSim.h"

#include <cmath>

const double RotationalLoadDcMotorSim::sGRAVITY = 9.8;

RotationalLoadDcMotorSim::RotationalLoadDcMotorSim(const DcMotorModel& aMotorModel, const std::shared_ptr<ISpeedControllerWrapper>& aSpeedController,
        double aArmCenterOfMass, double aArmMass, double aConstantAssistTorque, double aOverCenterAssistTorque) :
        BaseDcMotorSimulator("Rotational Load", aMotorModel),
        mSpeedController(aSpeedController),
        mArmInertia(aArmMass * aArmCenterOfMass * aArmCenterOfMass),
        mGravityBasedTorqueFactor(aArmMass * aArmCenterOfMass * sGRAVITY),
        mConstantAssistTorque(aConstantAssistTorque),
        mOverCenterAssistTorque(aOverCenterAssistTorque),
        mArmCenterOfMass(aArmCenterOfMass),
        mArmMass(aArmMass)
{
}

RotationalLoadDcMotorSim::~RotationalLoadDcMotorSim()
{
}

void RotationalLoadDcMotorSim::Update(double cycleTime)
{
    double position = mSpeedController->GetPosition();
    double gravityTorque = mGravityBasedTorqueFactor * std::sin(position);
    gravityTorque += mConstantAssistTorque;
    gravityTorque += mOverCenterAssistTorque * std::sin(position);

    double inVolts = mVoltagePercentage * 12;

    mMotorModel.Step(inVolts, mArmInertia, gravityTorque, cycleTime);
}

double RotationalLoadDcMotorSim::GetArmCenterOfMass()
{
    return mArmCenterOfMass;
}

double RotationalLoadDcMotorSim::GetArmMass()
{
    return mArmMass;
}
