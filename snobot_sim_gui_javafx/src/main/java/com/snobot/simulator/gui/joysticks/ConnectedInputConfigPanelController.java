package com.snobot.simulator.gui.joysticks;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.joysticks.sub_panels.RawPanelController;
import com.snobot.simulator.gui.joysticks.sub_panels.WrappedPanelController;
import com.snobot.simulator.gui.joysticks.sub_panels.XboxDisplayController;
import com.snobot.simulator.joysticks.ControllerConfiguration;
import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickDiscoverer;
import com.snobot.simulator.joysticks.JoystickFactory;
import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

public class ConnectedInputConfigPanelController
{
    private static final Logger LOGGER = LogManager.getLogger(ConnectedInputConfigPanelController.class);

    @FXML
    private RawPanelController mRawPanelController;
    @FXML
    private WrappedPanelController mWrappedPanelController;
    @FXML
    private XboxDisplayController mXboxPanelController;
    @FXML
    private ComboBox<String> mInterpretAsComboBox;

    private String mJoystickName;
    private Controller mController;

    public void update()
    {
        mRawPanelController.updateDisplay();
        mWrappedPanelController.updateDisplay();
        mXboxPanelController.updateDisplay();
    }

    public void setJoystick(String aControllerName, IMockJoystick aSelectedJoystick, ControllerConfiguration aConfig)
    {
        mJoystickName = aControllerName;
        mController = aConfig.mController;

        initializeInterpretComboBox(aConfig);
        // mXboxPanelController.setJoystick(aSelectedJoystick);
        mRawPanelController.setController(aConfig.mController);
    }

    private void initializeInterpretComboBox(ControllerConfiguration aConfig)
    {

        if (aConfig.mController.getType() == Type.KEYBOARD)
        {
            mInterpretAsComboBox.getItems().add("Keyboard");
        }
        else
        {
            for (String name : JoystickDiscoverer.getJoystickNames())
            {
                mInterpretAsComboBox.getItems().add(name);
            }
        }

        if (JoystickDiscoverer.getSpecializationTypes().contains(aConfig.mSpecialization))
        {
            mInterpretAsComboBox.getSelectionModel().select(JoystickDiscoverer.getSpecialization(aConfig.mSpecialization));
        }
        handleWrapperSelected(mInterpretAsComboBox.getSelectionModel().getSelectedItem());

        mInterpretAsComboBox.valueProperty().addListener((obsValue, oldValue, newValue) ->
        {
            System.out.println(obsValue + ", " + oldValue + ", " + newValue);
            handleWrapperSelected(newValue);
        });
    }

    private void handleWrapperSelected(String aType)
    {
        IMockJoystick wrappedJoystick = null;

        // Assuming values are unique as well as keys
        for (Class<? extends IMockJoystick> specializationType : JoystickDiscoverer.getSpecializationTypes())
        {
            String value = JoystickDiscoverer.getSpecialization(specializationType);
            if (value.equals(aType))
            {
                try
                {
                    JoystickFactory.getInstance().setSpecialization(mJoystickName, specializationType);
                    wrappedJoystick = specializationType.getDeclaredConstructor(Controller.class).newInstance(mController);
                }
                catch (Exception e)
                {
                    LOGGER.log(Level.ERROR, e);
                }
                break;
            }
        }

        if (wrappedJoystick == null)
        {
            wrappedJoystick = new NullJoystick();
        }

        mWrappedPanelController.setJoystick(wrappedJoystick);
        mXboxPanelController.setJoystick(wrappedJoystick);
    }
}
