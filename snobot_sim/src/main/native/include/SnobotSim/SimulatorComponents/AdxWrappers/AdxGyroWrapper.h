/*
 * AdxGyroWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include <memory>

#include "ADXRS450_SpiGyroWrapperData.h"
#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"

class AdxGyroWrapper : public AModuleWrapper, public ISpiWrapper, public IGyroWrapper
{
public:
    explicit AdxGyroWrapper(int aPort);
    virtual ~AdxGyroWrapper();

    void SetAngle(double aAngle) override;

    double GetAngle() override;

protected:
    std::shared_ptr<hal::ADXRS450_SpiGyroWrapper> mGyro;
};
