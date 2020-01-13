package com.snobot.simulator.gui.joysticks.sub_panels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.snobot.simulator.gui.joysticks.JoystickManagerController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class BaseJoystickDisplayController
{
    protected final List<AnalogInputPanelController> mAnalogControllers;
    protected final List<DigitalInputPanelController> mDigitalDisplays;

    protected BaseJoystickDisplayController()
    {
        mAnalogControllers = new ArrayList<>();
        mDigitalDisplays = new ArrayList<>();
    }

    protected Pane createAnalogDisplay(String aName)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(
                    JoystickManagerController.class.getResource("/com/snobot/simulator/gui/joysticks/sub_panels/analog_input_panel.fxml"));

            Pane widgetPane = loader.load();
            AnalogInputPanelController controller = loader.getController();
            controller.setName(aName);
            mAnalogControllers.add(controller);

            return widgetPane;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    protected Pane createDigitalDisplay(String aName)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(
                    JoystickManagerController.class.getResource("/com/snobot/simulator/gui/joysticks/sub_panels/digital_input_panel.fxml"));

            Pane widgetPane = loader.load();
            DigitalInputPanelController controller = loader.getController();
            controller.setName(aName);
            mDigitalDisplays.add(controller);

            return widgetPane;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
