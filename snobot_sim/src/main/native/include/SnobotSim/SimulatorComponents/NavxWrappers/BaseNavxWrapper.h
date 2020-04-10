/*
 * BaseNavxWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAccelerometerWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/SimulatorComponents/LazySimDoubleWrapper.h"

class BaseNavxWrapper
{
public:
    BaseNavxWrapper(const std::string& aBaseName, const std::string& aDeviceName, int aBasePort);
    virtual ~BaseNavxWrapper();

    class AccelerometerWrapper : public AModuleWrapper, public IAccelerometerWrapper
    {
    public:
        explicit AccelerometerWrapper(const LazySimDoubleWrapper& aSimWrapper);

        void SetAcceleration(double aAcceleration) override;

        double GetAcceleration() override;

        LazySimDoubleWrapper mSimWrapper;

        const std::string& GetType() override
        {
            return "baseNavxAccel";
        }
    };
    class GyroWrapper : public AModuleWrapper, public IGyroWrapper
    {
    public:
        explicit GyroWrapper(const LazySimDoubleWrapper& aSimWrapper);

        void SetAngle(double aAngle) override;

        double GetAngle() override;

        LazySimDoubleWrapper mSimWrapper;

        const std::string& GetType() override
        {
            return "baseNavxGyro";
        }
    };

    std::shared_ptr<AccelerometerWrapper> mXWrapper;
    std::shared_ptr<AccelerometerWrapper> mYWrapper;
    std::shared_ptr<AccelerometerWrapper> mZWrapper;

    std::shared_ptr<GyroWrapper> mYawWrapper;
    std::shared_ptr<GyroWrapper> mPitchWrapper;
    std::shared_ptr<GyroWrapper> mRollWrapper;
};
