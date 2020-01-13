package com.snobot.simulator.gui.widgets.settings.motor_sim;

import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SimpleMotorSimController implements IMotorSimController
{
    @FXML
    private TextField mMaxSpeed;

    @Override
    public void saveSettings(int aHandle)
    {
        double maxSpeed = Double.parseDouble(mMaxSpeed.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(aHandle,
                new SimpleMotorSimulationConfig(maxSpeed));
    }

    @Override
    public void populate(int aHandle)
    {
        SimpleMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(aHandle);
        mMaxSpeed.setText(Double.toString(config.mMaxSpeed));
    }
}
