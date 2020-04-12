
#pragma once

#include <memory>

#include "SnobotSim/Config/SimulatorConfigV1.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSimGui/ModuleWrapperDisplay/IWidgetDisplay.h"

class SpeedControllerWidget : public IWidgetDisplay
{
public:
    explicit SpeedControllerWidget(SaveCallback callback);

    void updateDisplay() override;

protected:
    void RenderMotorConfigPopup(std::shared_ptr<ISpeedControllerWrapper>& wrapper, FullMotorSimConfig& motorConfig);

    SaveCallback mSaveCallback;
};
