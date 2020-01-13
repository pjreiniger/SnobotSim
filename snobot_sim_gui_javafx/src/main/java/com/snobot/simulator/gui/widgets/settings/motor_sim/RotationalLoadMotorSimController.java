package com.snobot.simulator.gui.widgets.settings.motor_sim;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RotationalLoadMotorSimController extends BaseMotorSimWithDcMotorModelController
{
    @FXML
    private TextField mLoad;

    @Override
    public void saveSettings(int aHandle)
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Rotational(aHandle,
                mMotorPanelController.getMotorConfig(),
                new RotationalLoadMotorSimulationConfig(0, 0));
    }

    @Override
    public void populate(int aHandle)
    {
        DcMotorModelConfig modelConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(aHandle);
        mMotorPanelController.setModelConfig(modelConfig);

    }
}
