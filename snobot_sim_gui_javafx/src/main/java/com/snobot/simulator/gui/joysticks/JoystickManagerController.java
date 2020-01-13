package com.snobot.simulator.gui.joysticks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.joysticks.ControllerConfiguration;
import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class JoystickManagerController
{
    private static final Logger LOGGER = LogManager.getLogger(JoystickManagerController.class);
    private static final int UPDATE_TIME = 20;

    @FXML
    private CurrentSettingsPanelController mCurrentSettingsPanelController;

    @FXML
    private TabPane mInputConfigTabbedPane;

    private final List<ConnectedInputConfigPanelController> mInputControllers;
    private boolean mIsOpen;

    public JoystickManagerController()
    {
        mInputControllers = new ArrayList<>();
    }

    @FXML
    public void initialize()
    {

        JoystickFactory joystickFactory = JoystickFactory.getInstance();
        Map<String, ControllerConfiguration> goodControllers = joystickFactory.getControllerConfiguration();

        // <fx:include source="connected_input_config_panel.fxml" />

        for (Entry<String, ControllerConfiguration> pair : goodControllers.entrySet())
        {
            String controllerName = pair.getKey();
            ControllerConfiguration config = pair.getValue();
            createAndAddJoystickInput(controllerName, config, joystickFactory.getAll()[0]);
        }

        mCurrentSettingsPanelController.setControllerConfig(goodControllers.keySet(), joystickFactory.getAll());
        setVisible(true);
    }

    private void createAndAddJoystickInput(String aControllerName, ControllerConfiguration aConfig, IMockJoystick aSelectedJoystick)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(
                    JoystickManagerController.class.getResource("/com/snobot/simulator/gui/joysticks/connected_input_config_panel.fxml"));

            Pane widgetPane = loader.load();
            ConnectedInputConfigPanelController controller = loader.getController();
            controller.setJoystick(aControllerName, aSelectedJoystick, aConfig);

            Tab tab = new Tab(aControllerName, widgetPane);
            mInputConfigTabbedPane.getTabs().add(tab);
            mInputControllers.add(controller);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setVisible(boolean aVisible)
    {
        if (aVisible && !mIsOpen)
        {
            mIsOpen = true;
            Thread t = new Thread(mUpdateLooper);
            t.setName("Joystick Updater");
            t.start();
        }
    }

    private final Runnable mUpdateLooper = new Runnable()
    {

        @Override
        public void run()
        {
            while (mIsOpen)
            {
                for (ConnectedInputConfigPanelController controller : mInputControllers)
                {
                    controller.update();
                }

                try
                {
                    Thread.sleep(UPDATE_TIME);
                }
                catch (InterruptedException aEvent)
                {
                    LOGGER.log(Level.ERROR, aEvent);
                }
            }
        }
    };
}
