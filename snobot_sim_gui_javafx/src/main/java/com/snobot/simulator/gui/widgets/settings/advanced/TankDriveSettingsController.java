package com.snobot.simulator.gui.widgets.settings.advanced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.widgets.settings.SensorHandleOption;
import com.snobot.simulator.simulator_components.config.TankDriveConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TankDriveSettingsController
{
    private static final Logger LOGGER = LogManager.getLogger(TankDriveSettingsController.class);

    private final List<SingleSettingWidget> mWidgetPanels;

    @FXML
    private GridPane mSpiPane;

    @FXML
    private VBox mPane;

    public TankDriveSettingsController()
    {
        mWidgetPanels = new ArrayList<>();
    }

    @FXML
    public void initialize()
    {
        Collection<Object> simulatorComponents = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs();
        for (Object comp : simulatorComponents)
        {
            if (comp instanceof TankDriveConfig)
            {
                addPanel((TankDriveConfig) comp);
            }
        }

    }

    @FXML
    public void handleAddButton()
    {
        addPanel(new TankDriveConfig());
    }

    private void addPanel(TankDriveConfig aConfig)
    {
        SingleSettingWidget panel = new SingleSettingWidget(aConfig);
        mWidgetPanels.add(panel);

        TitledPane titledPane = new TitledPane("Tank Drive Config", panel);
        mPane.getChildren().add(titledPane);
    }

    public void onSubmit()
    {
        Collection<Object> toRemove = new ArrayList<>();
        Collection<Object> simulatorComponents = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs();

        for (Object comp : simulatorComponents)
        {
            if (comp instanceof TankDriveConfig)
            {
                toRemove.add(comp);
            }
        }

        for (Object comp : toRemove)
        {
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().removeSimulatorComponent(comp);
        }

        for (SingleSettingWidget panel : mWidgetPanels)
        {
            TankDriveConfig config = panel.getConfig();
            if (config == null)
            {
                throw new IllegalArgumentException("Could not create tank drive simulator");
            }
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(config.getmLeftMotorHandle(),
                    config.getmRightMotorHandle(), config.getmGyroHandle(), config.getmTurnKp());
        }
    }

    private static class SingleSettingWidget extends GridPane
    {
        private final ComboBox<SensorHandleOption> mLeftMotorSelection;
        private final ComboBox<SensorHandleOption> mRightMotorSelection;
        private final ComboBox<SensorHandleOption> mGyroSelection;
        private final TextField mKpField;

        private SingleSettingWidget(TankDriveConfig aConfig)
        {
            SensorHandleOption leftSelection = null;
            SensorHandleOption rightSelection = null;
            SensorHandleOption gyroSelection = null;

            List<SensorHandleOption> speedControllerOptions = new ArrayList<>();
            List<Integer> speedControllers = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList();
            for (int handle : speedControllers)
            {
                SensorHandleOption option = new SensorHandleOption(handle,
                        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(handle));
                speedControllerOptions.add(option);

                if (option.mHandle == aConfig.getmLeftMotorHandle())
                {
                    leftSelection = option;
                }

                if (option.mHandle == aConfig.getmRightMotorHandle())
                {
                    rightSelection = option;
                }
            }

            List<SensorHandleOption> gyroOptions = new ArrayList<>();
            List<Integer> gyros = DataAccessorFactory.getInstance().getGyroAccessor().getPortList();
            for (int handle : gyros)
            {
                SensorHandleOption option = new SensorHandleOption(handle, DataAccessorFactory.getInstance().getGyroAccessor().getName(handle));
                gyroOptions.add(option);

                if (option.mHandle == aConfig.getmGyroHandle())
                {
                    gyroSelection = option;
                }
            }

            mLeftMotorSelection = new ComboBox<>(FXCollections.observableList(speedControllerOptions));
            mRightMotorSelection = new ComboBox<>(FXCollections.observableList(speedControllerOptions));
            mGyroSelection = new ComboBox<>(FXCollections.observableList(gyroOptions));

            mLeftMotorSelection.getSelectionModel().select(leftSelection == null ? speedControllerOptions.get(0) : leftSelection);
            mRightMotorSelection.getSelectionModel().select(rightSelection == null ? speedControllerOptions.get(0) : rightSelection);
            mGyroSelection.getSelectionModel().select(gyroSelection == null ? gyroOptions.get(0) : gyroSelection);

            mKpField = new TextField(Double.toString(aConfig.getmTurnKp()));

            add(new Label("Left Motor"), 0, 0);
            add(mLeftMotorSelection, 1, 0);

            add(new Label("Right Motor"), 0, 1);
            add(mRightMotorSelection, 1, 1);

            add(new Label("Gyroscope"), 0, 2);
            add(mGyroSelection, 1, 2);

            add(new Label("Turning kP"), 0, 3);
            add(mKpField, 1, 3);
        }

        public TankDriveConfig getConfig()
        {
            SensorHandleOption left = mLeftMotorSelection.getSelectionModel().getSelectedItem();
            SensorHandleOption right = mRightMotorSelection.getSelectionModel().getSelectedItem();
            SensorHandleOption gyro = mGyroSelection.getSelectionModel().getSelectedItem();

            if (left == null || right == null || gyro == null)
            {
                return null;
            }

            int leftHandle = left.mHandle;
            int rightHanle = right.mHandle;
            int gyroHandle = gyro.mHandle;
            double kp = 1;

            try
            {
                kp = Double.parseDouble(mKpField.getText());
            }
            catch (NumberFormatException e)
            {
                LOGGER.log(Level.ERROR, e);
            }

            return new TankDriveConfig(leftHandle, rightHanle, gyroHandle, kp);
        }
    }

}
