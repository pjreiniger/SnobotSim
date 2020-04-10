

#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/MotorSim/IMotorSimulator.h"
#include "SnobotSim/SimulatorComponents/IFeedbackSensor.h"

class BaseSpeedControllerWrapper : public AModuleWrapper, public ISpeedControllerWrapper
{
public:
    explicit BaseSpeedControllerWrapper(const std::string& aName, int aPort);
    virtual ~BaseSpeedControllerWrapper();

    int GetId() override;

    void SetMotorSimulator(const std::shared_ptr<IMotorSimulator>& aSimulator) override;

    const std::shared_ptr<IMotorSimulator>& GetMotorSimulator() override;

    void SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aSimulator) override;

    const std::shared_ptr<IFeedbackSensor>& GetFeedbackSensor() override;

    double GetVoltagePercentage() override;

    void SetVoltagePercentage(double aSpeed) override;

    void Update(double aWaitTime) override;

    double GetPosition() override;

    double GetVelocity() override;

    double GetCurrent() override;

    double GetAcceleration() override;

    void Reset() override;

    void Reset(double aPosition, double aVelocity, double aCurrent) override;

protected:
    int mId;
    std::shared_ptr<IMotorSimulator> mMotorSimulator;
    std::shared_ptr<IFeedbackSensor> mFeedbackSensor;
};
