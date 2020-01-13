package com.snobot.simulator.gui.widgets.settings.motor_sim;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StaticLoadMotorSimController extends BaseMotorSimWithDcMotorModelController
{
    @FXML
    private TextField mLoad;

    @Override
    public void saveSettings(int aHandle)
    {
        double load = Double.parseDouble(mLoad.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(aHandle, mMotorPanelController.getMotorConfig(),
                new StaticLoadMotorSimulationConfig(load));
    }

    @Override
    public void populate(int aHandle)
    {
        DcMotorModelConfig modelConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(aHandle);
        mMotorPanelController.setModelConfig(modelConfig);

        StaticLoadMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimStaticModelConfig(aHandle);
        mLoad.setText(Double.toString(config.mLoad));
    }
}
