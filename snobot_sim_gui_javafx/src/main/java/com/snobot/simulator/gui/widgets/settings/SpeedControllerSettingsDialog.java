package com.snobot.simulator.gui.widgets.settings;

import com.snobot.simulator.gui.widgets.settings.motor_sim.GravitationalLoadMotorSimController;
import com.snobot.simulator.gui.widgets.settings.motor_sim.IMotorSimController;
import com.snobot.simulator.gui.widgets.settings.motor_sim.RotationalLoadMotorSimController;
import com.snobot.simulator.gui.widgets.settings.motor_sim.SimpleMotorSimController;
import com.snobot.simulator.gui.widgets.settings.motor_sim.StaticLoadMotorSimController;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;

public class SpeedControllerSettingsDialog extends BasicSettingsDialog
{
    @FXML
    private ComboBox<MotorSimType> mSelectionBox;

    @FXML
    private StackPane mLayoutManager;

    @FXML
    private Node mSimpleConfigPanel;

    @FXML
    private SimpleMotorSimController mSimpleConfigPanelController;

    @FXML
    private Node mStaticLoadConfigPanel;

    @FXML
    private StaticLoadMotorSimController mStaticLoadConfigPanelController;

    @FXML
    private Node mRotationalLoadConfigPanel;

    @FXML
    private RotationalLoadMotorSimController mRotationalLoadConfigPanelController;

    @FXML
    private Node mGravitationalLoadConfigPanel;

    @FXML
    private GravitationalLoadMotorSimController mGravitationalLoadConfigPanelController;

    private IMotorSimController mActiveController;

    @FXML
    public void initialize()
    {
        mSelectionBox.getItems().addAll(MotorSimType.values());
    }

    public void initialize(int aSpeedControllerHandle, MotorSimType aMode)
    {
        mSelectionBox.getSelectionModel().select(aMode);
        handleSimType(aMode);
        mActiveController.populate(aSpeedControllerHandle);
    }

    @FXML
    public void handleSimType()
    {
        MotorSimType selectedType = mSelectionBox.getSelectionModel().getSelectedItem();
        handleSimType(selectedType);
    }

    public void handleSimType(MotorSimType aSelectedType)
    {
        mLayoutManager.getChildren().clear();

        switch (aSelectedType)
        {
        case Simple:
            mLayoutManager.getChildren().add(mSimpleConfigPanel);
            mActiveController = mSimpleConfigPanelController;
            break;
        case StaticLoad:
            mLayoutManager.getChildren().add(mStaticLoadConfigPanel);
            mActiveController = mStaticLoadConfigPanelController;
            break;
        case RotationalLoad:
            mLayoutManager.getChildren().add(mRotationalLoadConfigPanel);
            mActiveController = mRotationalLoadConfigPanelController;
            break;
        case GravitationalLoad:
            mLayoutManager.getChildren().add(mGravitationalLoadConfigPanel);
            mActiveController = mGravitationalLoadConfigPanelController;
            break;
        default:
            throw new IllegalArgumentException("Unknown type " + aSelectedType);
        }
    }

    public void saveMotorSim(int aSpeedControllerHandle)
    {
        mActiveController.saveSettings(aSpeedControllerHandle);
    }
}
