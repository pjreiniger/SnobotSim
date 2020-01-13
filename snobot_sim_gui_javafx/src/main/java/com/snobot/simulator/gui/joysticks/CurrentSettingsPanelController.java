package com.snobot.simulator.gui.joysticks;

import java.util.Set;

import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickFactory;
import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;

import edu.wpi.first.wpilibj.DriverStation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CurrentSettingsPanelController
{
    @FXML
    private GridPane mPane;

    public void setControllerConfig(Set<String> aControllerNames, IMockJoystick[] aSelectedJoysticks)
    {
        System.out.println("HELLO");
        ObservableList<String> controllerNames = FXCollections.observableArrayList();
        controllerNames.add(NullJoystick.sNAME);
        controllerNames.addAll(aControllerNames);

        for (int i = 0; i < DriverStation.kJoystickPorts; ++i)
        {
            int joystickNum = i;

            ComboBox<String> comboBox = new ComboBox<>(controllerNames);
            comboBox.getSelectionModel().select(aSelectedJoysticks[i].getName());
            comboBox.valueProperty().addListener((obsValue, oldValue, newValue) ->
            {
                JoystickFactory.getInstance().setJoysticks(joystickNum, newValue);
            });

            mPane.add(new Label("Joystick " + i), 0, i);
            mPane.add(comboBox, 1, i);
            
        }

    }

}
