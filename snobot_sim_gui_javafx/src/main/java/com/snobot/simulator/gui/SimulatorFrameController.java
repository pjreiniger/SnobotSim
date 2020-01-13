package com.snobot.simulator.gui;

import com.snobot.simulator.config.SimulatorConfigWriter;
import com.snobot.simulator.gui.joysticks.JoystickManagerController;
import com.snobot.simulator.gui.widgets.AdvancedSettingsController;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SimulatorFrameController
{
    @FXML
    private ConfigurationPaneController mConfigurationPanelController;

    @FXML
    private AdvancedSettingsController mAdvancedSettingsWidgetController;

    @FXML
    private EnablePanelController mEnablePanelController;

    @FXML
    private Button mJoystickSettingsButton;

    private boolean mUseSnobotSimDriverstation;

    private String mSimulatorConfigFile;

    public void initialize(String aSimulatorConfigFile, boolean aUseSnobotSimDriverstation)
    {
        mUseSnobotSimDriverstation = aUseSnobotSimDriverstation;
        mSimulatorConfigFile = aSimulatorConfigFile;
        mConfigurationPanelController.loadWidgets(this::saveSettings);
        mAdvancedSettingsWidgetController.setSaveCallback(this::saveSettings);

        mEnablePanelController.setUseSnobotSim(mUseSnobotSimDriverstation);
        mJoystickSettingsButton.setVisible(mUseSnobotSimDriverstation);
    }

    public void updateLoop()
    {
        mConfigurationPanelController.update();
        mEnablePanelController.updateLoop(mUseSnobotSimDriverstation);
    }

    public boolean saveSettings()
    {
        SimulatorConfigWriter writer = new SimulatorConfigWriter();
        writer.writeConfig(mSimulatorConfigFile);

        return true;
    }

    public void setTime(double aTime)
    {
        mEnablePanelController.setTime(aTime);
    }

    @FXML
    public void handleJoystickSettingsButton()
    {
        DialogRunner<JoystickManagerController> dialog = new DialogRunner<>("/com/snobot/simulator/gui/joysticks/joystick_manager_controller.fxml");

        if (dialog.showAndWait())
        {
            System.out.println("XFDF");
        }
    }

}
