/*
 * AdxGyroWrapper.h
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#pragma once
#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IGyroWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpiWrapper.h"
#include "SnobotSim/SimulatorComponents/LazySimDoubleWrapper.h"

class AdxGyroWrapper : public AModuleWrapper, public ISpiWrapper, public IGyroWrapper
{
public:
    static const std::string TYPE;

    explicit AdxGyroWrapper(const std::string& aBaseName, int aPort);
    virtual ~AdxGyroWrapper();

    void SetAngle(double aAngle) override;

    double GetAngle() override;

    std::string GetType() override
    {
        return TYPE;
    }

protected:
    LazySimDoubleWrapper mSimWrapper;
};
