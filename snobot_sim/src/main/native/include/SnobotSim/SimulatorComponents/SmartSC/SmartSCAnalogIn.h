
#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"

class SmartSCAnalogIn : public AModuleWrapper, public IAnalogInWrapper
{
public:
    static const std::string TYPE;

    explicit SmartSCAnalogIn(int aPort);
    virtual ~SmartSCAnalogIn();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

    std::string GetType() override
    {
        return TYPE;
    }

protected:
    double mVoltage{ 0 };
};
