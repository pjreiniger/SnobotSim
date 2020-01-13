package com.snobot.simulator.gui.joysticks.sub_panels;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.joysticks.IMockJoystick;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class WrappedPanelController extends BaseJoystickDisplayController
{
    private static final Logger LOGGER = LogManager.getLogger(WrappedPanelController.class);

    private IMockJoystick mJoystick;

    @FXML
    private GridPane mAnalogPane;
    @FXML
    private GridPane mDigitalPane;

    public final void setJoystick(IMockJoystick aJoystick)
    {
        mJoystick = aJoystick;
        // mAnalogPanel.removeAll();
        // mDigitalPanel.removeAll();

        if (mJoystick != null)
        {
            mAnalogControllers.clear();
            mDigitalDisplays.clear();

            for (int i = 0; i < mJoystick.getAxisCount(); ++i)
            {
                Pane pane = createAnalogDisplay("Analog " + i);
                mAnalogPane.add(pane, 0, i);
            }
            for (int i = 0; i < mJoystick.getButtonCount(); ++i)
            {
                Pane pane = createDigitalDisplay("Digital " + i);
                mDigitalPane.add(pane, 0, i);
            }
        }
    }

    public void updateDisplay()
    {
        if (mJoystick == null)
        {
            LOGGER.log(Level.WARN, "Joystick is null");
        }
        else
        {
            float[] axisValues = mJoystick.getAxisValues();
            int buttonMask = mJoystick.getButtonMask();

            for (int i = 0; i < axisValues.length; ++i)
            {
                AnalogInputPanelController panel = mAnalogControllers.get(i);
                panel.setValue(axisValues[i]);
            }
            for (int i = 0; i < mJoystick.getButtonCount(); ++i)
            {
                DigitalInputPanelController panel = mDigitalDisplays.get(i);
                boolean active = (buttonMask & (1 << i)) != 0;
                panel.setValue(active);
            }
        }
    }

}
