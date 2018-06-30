/*
 * BaseAdxAccelWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_BASEADXACCELWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_BASEADXACCELWRAPPER_H_

#include <memory>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAccelerometerWrapper.h"
#include "ThreeAxisAccelerometerData.h"

class BaseAdxAccelWrapper
{
public:
    BaseAdxAccelWrapper(int aBasePort, const std::shared_ptr<hal::ThreeAxisAccelerometerData>& aAccel);
    virtual ~BaseAdxAccelWrapper();

protected:
    class AccelerometerWrapper : public AModuleWrapper, public IAccelerometerWrapper
    {
    public:
        enum AxisType
        {
            AXIS_X,
            AXIS_Y,
            AXIS_Z
        };

        AccelerometerWrapper(AxisType aAxisType, const std::shared_ptr<hal::ThreeAxisAccelerometerData>& aAccel);

        void SetAcceleration(double aAcceleration) override;

        double GetAcceleration() override;

        AxisType mAxisType;
        std::shared_ptr<hal::ThreeAxisAccelerometerData> mAccel;
    };

    std::shared_ptr<AccelerometerWrapper> mXWrapper;
    std::shared_ptr<AccelerometerWrapper> mYWrapper;
    std::shared_ptr<AccelerometerWrapper> mZWrapper;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_ADXWRAPPERS_BASEADXACCELWRAPPER_H_
