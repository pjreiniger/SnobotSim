
#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IDigitalIoWrapper.h"

class SmartSCLimitSwitch : public AModuleWrapper, public IDigitalIoWrapper
{
public:
    static const std::string TYPE;

    explicit SmartSCLimitSwitch(int aPort);
    virtual ~SmartSCLimitSwitch();

    bool Get() override;
    void Set(bool aState) override;

    std::string GetType() override
    {
        return TYPE;
    }

protected:
    bool mState{ false };
};
