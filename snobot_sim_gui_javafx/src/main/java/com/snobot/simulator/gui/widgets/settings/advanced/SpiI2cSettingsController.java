package com.snobot.simulator.gui.widgets.settings.advanced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SpiI2cSettingsController
{
    private static final String DEFAULT_ITEM = "None";

    private final Map<Integer, ComponentRow> mSpiSettings;
    private final Map<Integer, ComponentRow> mI2CSettings;

    @FXML
    private GridPane mSpiPane;

    @FXML
    private GridPane mI2CPane;

    public SpiI2cSettingsController()
    {
        mSpiSettings = new HashMap<>();
        mI2CSettings = new HashMap<>();
    }

    @FXML
    public void initialize()
    {
        Map<Integer, String> defaultSpiMapping = DataAccessorFactory.getInstance().getSpiAccessor().getSpiWrapperTypes();
        Map<Integer, String> defaultI2CMapping = DataAccessorFactory.getInstance().getI2CAccessor().getI2CWrapperTypes();
        Collection<String> availableSpiOptions = new ArrayList<>();
        Collection<String> availableI2COptions = new ArrayList<>();

        availableSpiOptions.add(DEFAULT_ITEM);
        availableSpiOptions.addAll(DataAccessorFactory.getInstance().getSpiAccessor().getAvailableSpiSimulators());

        availableI2COptions.add(DEFAULT_ITEM);
        availableI2COptions.addAll(DataAccessorFactory.getInstance().getI2CAccessor().getAvailableI2CSimulators());

        int rowCtr = 0;
        for (SPI.Port port : SPI.Port.values())
        {
            String selectedValue = defaultSpiMapping.get(port.value);

            ComponentRow row = new ComponentRow(port.name(), "(" + port.ordinal() + ")", availableSpiOptions, selectedValue);
            mSpiSettings.put(port.value, row);

            mSpiPane.add(row.mNameLabel, 0, rowCtr);
            mSpiPane.add(row.mIndexLabel, 1, rowCtr);
            mSpiPane.add(row.mSimType, 2, rowCtr);
            ++rowCtr;
        }

        rowCtr = 0;
        for (I2C.Port port : I2C.Port.values())
        {
            String selectedValue = defaultI2CMapping.get(port.value);

            ComponentRow row = new ComponentRow(port.name(), "(" + port.ordinal() + ")", availableSpiOptions, selectedValue);
            mI2CSettings.put(port.value, row);

            mI2CPane.add(row.mNameLabel, 0, rowCtr);
            mI2CPane.add(row.mIndexLabel, 1, rowCtr);
            mI2CPane.add(row.mSimType, 2, rowCtr);
            ++rowCtr;
        }

    }

    public void onSubmit()
    {
        for (Entry<Integer, ComponentRow> pair : mSpiSettings.entrySet())
        {
            Object selected = pair.getValue().mSimType.getSelectionModel().getSelectedItem();
            String value = null;
            if (selected != null && !DEFAULT_ITEM.equals(selected))
            {
                value = selected.toString();
            }
            DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(pair.getKey(), value);
        }

        for (Entry<Integer, ComponentRow> pair : mI2CSettings.entrySet())
        {
            Object selected = pair.getValue().mSimType.getSelectionModel().getSelectedItem();
            String value = null;
            if (selected != null && !DEFAULT_ITEM.equals(selected))
            {
                value = selected.toString();
            }
            DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(pair.getKey(), value);
        }

        Alert closeAlert = new Alert(AlertType.WARNING,
                "Most SPI and I2C simulators are required to be initialized on startup, so it is recommended that you save your updates and restart the simulator");
        closeAlert.showAndWait();
    }

    private static class ComponentRow
    {
        private final Label mNameLabel;
        private final Label mIndexLabel;
        private final ComboBox<String> mSimType;

        private ComponentRow(String aName, String aIndex, Collection<String> aOptions, String aSelectedValue)
        {
            mNameLabel = new Label(aName);
            mIndexLabel = new Label(aIndex);
            mSimType = new ComboBox<>(FXCollections.observableArrayList(aOptions));
            mSimType.getSelectionModel().select(aSelectedValue);
        }
    }

}
