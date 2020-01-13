package com.snobot.simulator.gui.joysticks.sub_panels;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import net.java.games.input.Component;
import net.java.games.input.Controller;

public class RawPanelController extends BaseJoystickDisplayController
{
    private Controller mJoystick;

    private final List<Component> mAnalogComponents;
    private final List<Component> mDigitalComponents;

    @FXML
    private GridPane mAnalogPane;
    @FXML
    private GridPane mDigitalPane;

    public RawPanelController()
    {
        mAnalogComponents = new ArrayList<>();
        mDigitalComponents = new ArrayList<>();
    }

    public void updateDisplay()
    {
        if (mJoystick != null)
        {
            mJoystick.poll();
        }

        for (int i = 0; i < mAnalogComponents.size(); ++i)
        {
            float rawValue = mAnalogComponents.get(i).getPollData();

            AnalogInputPanelController controller = mAnalogControllers.get(i);
            controller.setValue((int) (rawValue * 127));
        }
        for (int i = 0; i < mDigitalComponents.size(); ++i)
        {
            float rawValue = mDigitalComponents.get(i).getPollData();

            DigitalInputPanelController controller = mDigitalDisplays.get(i);
            controller.setValue(rawValue == 1);
        }
    }

    public void setController(Controller aJoystick)
    {
        mJoystick = aJoystick;

        if (mJoystick != null)
        {
            mAnalogControllers.clear();
            mDigitalDisplays.clear();
            mAnalogComponents.clear();
            mDigitalComponents.clear();

            Component[] components = mJoystick.getComponents();
            for (int j = 0; j < components.length; j++)
            {
                Component component = components[j];

                if (component.isAnalog())
                {
                    mAnalogComponents.add(component);
                }
                else
                {
                    mDigitalComponents.add(component);
                }
            }

            int modulo = 5;

            for (int i = 0; i < mAnalogComponents.size(); ++i)
            {
                Pane pane = createAnalogDisplay("Analog " + i);
                mAnalogPane.add(pane, i % modulo, i / modulo);
            }
            for (int i = 0; i < mDigitalComponents.size(); ++i)
            {
                Pane pane = createDigitalDisplay("Digital " + i);
                mDigitalPane.add(pane, i % modulo, i / modulo);
            }
        }
    }

}
