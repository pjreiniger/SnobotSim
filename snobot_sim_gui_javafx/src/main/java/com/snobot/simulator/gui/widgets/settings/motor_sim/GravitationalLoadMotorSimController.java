package com.snobot.simulator.gui.widgets.settings.motor_sim;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GravitationalLoadMotorSimController extends BaseMotorSimWithDcMotorModelController
{
    @FXML
    private TextField mLoad;

    @Override
    public void saveSettings(int aHandle)
    {
        double load = Double.parseDouble(mLoad.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(aHandle,
                mMotorPanelController.getMotorConfig(),
                new GravityLoadMotorSimulationConfig(load));
    }

    @Override
    public void populate(int aHandle)
    {
        DcMotorModelConfig modelConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(aHandle);
        mMotorPanelController.setModelConfig(modelConfig);

        GravityLoadMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor()
                .getMotorSimGravitationalModelConfig(aHandle);
        mLoad.setText(Double.toString(config.getLoad()));
    }
}
