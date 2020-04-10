/*
* AnalogGyrWrapper.h
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ExportHelper.h"
#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"

class WpiAnalogGyroWrapper : public AModuleWrapper, public IGyroWrapper
{
public:
    static const std::string TYPE;

    using AModuleWrapper::GetName;

    explicit WpiAnalogGyroWrapper(int aPort);
    virtual ~WpiAnalogGyroWrapper();

    void SetAngle(double aAngle) override;

    double GetAngle() override;

    const std::string& GetType() override
    {
        return TYPE;
    }

protected:
    const int mHandle;
};
