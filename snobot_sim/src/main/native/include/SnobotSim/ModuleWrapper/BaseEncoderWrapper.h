
#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IEncoderWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"

class BaseEncoderWrapper : public AModuleWrapper,
                           public std::enable_shared_from_this<BaseEncoderWrapper>,
                           public IEncoderWrapper
{
public:
    explicit BaseEncoderWrapper(const std::string& aName);
    virtual ~BaseEncoderWrapper();

    void Reset() override;

    double GetVelocity() override;

    bool IsHookedUp() override;

    void SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper) override;

    const std::shared_ptr<ISpeedControllerWrapper>& GetSpeedController() override;

    double GetDistance() override;
    double GetPosition() override;

    void SetPosition(double aPosition) override;

    void SetVelocity(double aVelocity) override;

protected:
    std::shared_ptr<ISpeedControllerWrapper> mMotorWrapper;
    double mPosition;
    double mVelocity;
};
