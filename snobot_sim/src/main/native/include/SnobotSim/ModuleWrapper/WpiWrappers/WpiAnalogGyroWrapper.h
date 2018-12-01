/*
* AnalogGyrWrapper.h
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#pragma once

#include <memory>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"

class WpiAnalogGyroWrapper : public AModuleWrapper, public IGyroWrapper
{
public:
    using AModuleWrapper::GetName;

    explicit WpiAnalogGyroWrapper(int aPort);
    virtual ~WpiAnalogGyroWrapper();

    void SetAngle(double aAngle) override;

    double GetAngle() override;

protected:
    const int mHandle;
};
