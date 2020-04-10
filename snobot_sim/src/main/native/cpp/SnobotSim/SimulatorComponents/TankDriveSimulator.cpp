/*
 * TankDriveSimulator.cpp
 *
 *  Created on: May 7, 2017
 *      Author: PJ
 */

#include "SnobotSim/SimulatorComponents/TankDriveSimulator.h"

#include "SnobotSim/Logging/SnobotLogger.h"

TankDriveSimulator::TankDriveSimulator(
        const std::shared_ptr<ISpeedControllerWrapper>& aLeftMotor,
        const std::shared_ptr<ISpeedControllerWrapper>& aRightMotor,
        const std::shared_ptr<IGyroWrapper>& aGyroWrapper,
        double aTurnKp) :

        mLeftMotor(aLeftMotor),
        mRightMotor(aRightMotor),
        mGyroWrapper(aGyroWrapper),
        mTurnKp(aTurnKp)
{
    mIsSetup = mLeftMotor && mRightMotor && mGyroWrapper;

    if (!mIsSetup)
    {
        SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL, "Tank drive simulator is not set up! Will not update gyro!");
    }
}

TankDriveSimulator::~TankDriveSimulator()
{
}

void TankDriveSimulator::Update()
{
    if (mIsSetup)
    {
        double rightDist = mLeftMotor->GetPosition();
        double leftDist = mRightMotor->GetPosition();

        double angle = (leftDist - rightDist) / (3.14159 * mTurnKp) * (180.0);

        mGyroWrapper->SetAngle(angle);
    }
}
