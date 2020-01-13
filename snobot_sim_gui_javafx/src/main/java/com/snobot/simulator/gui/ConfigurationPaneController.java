package com.snobot.simulator.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class ConfigurationPaneController
{
    @FXML
    private Pane mMainPane;

    private final List<WidgetGroupController> mControllers;

    public ConfigurationPaneController()
    {
        mControllers = new ArrayList<>();
    }

    public void loadWidgets(Supplier<Boolean> aSaveFunction)
    {
        try
        {
            initializeWidgetGroup(aSaveFunction, "Speed Controllers", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/speed_controller_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Solenoids", DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/solenoid_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Digital IO", DataAccessorFactory.getInstance().getDigitalAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/digital_io_controller_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Relays", DataAccessorFactory.getInstance().getRelayAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/relay_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Analog In", DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/analog_in_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Analog Out", DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/analog_out_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Encoders", DataAccessorFactory.getInstance().getEncoderAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/encoder_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Gyros", DataAccessorFactory.getInstance().getGyroAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/gyro_widget.fxml");
            initializeWidgetGroup(aSaveFunction, "Accelerometers", DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList(),
                    "/com/snobot/simulator/gui/widgets/accelerometer_widget.fxml");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void initializeWidgetGroup(Supplier<Boolean> aSaveFunction, String aGroupName, List<Integer> aIds, String aFxmlConfig) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/snobot/simulator/gui/widget_group.fxml"));
        Pane newGroup = loader.load();

        TitledPane titlePane = new TitledPane(aGroupName, newGroup);

        mMainPane.getChildren().add(titlePane);

        WidgetGroupController controller = loader.getController();
        controller.initialize(aSaveFunction, aIds, aFxmlConfig);

        mControllers.add(controller);
    }

    public void update()
    {
        for (WidgetGroupController controller : mControllers)
        {
            controller.update();
        }
    }
}
