/*
 * BaseNavxWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include <memory>

#include "NavxSim/NavxSimulator.h"
#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAccelerometerWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"

class BaseNavxWrapper
{
public:
    BaseNavxWrapper(int aBasePort, const std::shared_ptr<NavxSimulator>& aNavx);
    virtual ~BaseNavxWrapper();

    class AccelerometerWrapper : public AModuleWrapper, public IAccelerometerWrapper
    {
    public:
        enum AxisType
        {
            AXIS_X,
            AXIS_Y,
            AXIS_Z
        };

        AccelerometerWrapper(AxisType aAxisType, const std::shared_ptr<NavxSimulator>& aNavx);

        void SetAcceleration(double aAcceleration) override;

        double GetAcceleration() override;

        AxisType mAxisType;
        std::shared_ptr<NavxSimulator> mNavx;
    };
    class GyroWrapper : public AModuleWrapper, public IGyroWrapper
    {
    public:
        enum AxisType
        {
            AXIS_YAW,
            AXIS_PITCH,
            AXIS_ROLL
        };

        GyroWrapper(AxisType aAxisType, const std::shared_ptr<NavxSimulator>& aAccel);

        void SetAngle(double aAngle) override;

        double GetAngle() override;

        AxisType mAxisType;
        std::shared_ptr<NavxSimulator> mNavx;
    };

    std::shared_ptr<AccelerometerWrapper> mXWrapper;
    std::shared_ptr<AccelerometerWrapper> mYWrapper;
    std::shared_ptr<AccelerometerWrapper> mZWrapper;

    std::shared_ptr<GyroWrapper> mYawWrapper;
    std::shared_ptr<GyroWrapper> mPitchWrapper;
    std::shared_ptr<GyroWrapper> mRollWrapper;
};
