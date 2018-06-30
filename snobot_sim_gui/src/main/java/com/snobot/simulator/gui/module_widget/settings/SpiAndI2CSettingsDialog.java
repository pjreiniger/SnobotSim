package com.snobot.simulator.gui.module_widget.settings;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

public class SpiAndI2CSettingsDialog extends JDialog
{
    private static final String sDEFAULT_ITEM = "None";

    private Map<Integer, ComponentRow> mSpiSettings;
    private Map<Integer, ComponentRow> mI2CSettings;

    public SpiAndI2CSettingsDialog()
    {
        setTitle("SPI and I2C");

        initComponents();
    }

    private void initComponents()
    {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        mSpiSettings = new HashMap<>();
        mI2CSettings = new HashMap<>();

        Map<Integer, String> defaultSpiMapping = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSpiWrapperTypes();
        Map<Integer, String> defaultI2CMapping = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getI2CWrapperTypes();
        Collection<String> availableSpiOptions = new ArrayList<>();
        Collection<String> availableI2COptions = new ArrayList<>();

        availableSpiOptions.add(sDEFAULT_ITEM);
        availableSpiOptions.addAll(DataAccessorFactory.getInstance().getSimulatorDataAccessor().getAvailableSpiSimulators());

        availableI2COptions.add(sDEFAULT_ITEM);
        availableI2COptions.addAll(DataAccessorFactory.getInstance().getSimulatorDataAccessor().getAvailableI2CSimulators());

        int rowCtr = 0;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 5;

        for (SPI.Port port : SPI.Port.values())
        {
            String selectedValue = defaultSpiMapping.get(port.value);

            ComponentRow row = new ComponentRow("SPI (" + port.ordinal() + "): " + port.name(), availableSpiOptions, selectedValue);
            mSpiSettings.put(port.value, row);

            constraints.gridx = rowCtr += 1;

            constraints.gridx = 0;
            container.add(row.mLabel, constraints);

            constraints.gridx = 1;
            container.add(row.mSelection, constraints);
        }

        for (I2C.Port port : I2C.Port.values())
        {
            String selectedValue = defaultI2CMapping.get(port.value);

            ComponentRow row = new ComponentRow("I2C (" + port.ordinal() + "): " + port.name(), availableI2COptions, selectedValue);
            mI2CSettings.put(port.value, row);

            constraints.gridx = rowCtr += 1;

            constraints.gridx = 0;
            container.add(row.mLabel, constraints);

            constraints.gridx = 1;
            container.add(row.mSelection, constraints);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                onSubmit();
                dispose();
            }
        });

        add(container, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void onSubmit()
    {
        for (Entry<Integer, ComponentRow> pair : mSpiSettings.entrySet())
        {
            Object selected = pair.getValue().mSelection.getSelectedItem();
            String value = null;
            if (selected != null && !sDEFAULT_ITEM.equals(selected))
            {
                value = selected.toString();
            }
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().createSpiSimulator(pair.getKey(), value);
        }

        for (Entry<Integer, ComponentRow> pair : mI2CSettings.entrySet())
        {
            Object selected = pair.getValue().mSelection.getSelectedItem();
            String value = null;
            if (selected != null && !sDEFAULT_ITEM.equals(selected))
            {
                value = selected.toString();
            }
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().createI2CSimulator(pair.getKey(), value);
        }

        JOptionPane.showMessageDialog(null,
                "SPI and I2C simulators are loaded on startup, so you will need to save your updates and restart the simulator", "SPI & I2C Config",
                JOptionPane.WARNING_MESSAGE);
    }

    private static class ComponentRow
    {
        private final JComboBox<String> mSelection;
        private final JLabel mLabel;

        public ComponentRow(String aName, Collection<String> aOptions, String aSelectedValue)
        {
            mLabel = new JLabel(aName);
            mSelection = new JComboBox<>();
            for (String option : aOptions)
            {
                mSelection.addItem(option);
            }

            if (aSelectedValue == null)
            {
                mSelection.setSelectedItem(sDEFAULT_ITEM);
            }
            else
            {
                mSelection.setSelectedItem(aSelectedValue);
            }

        }
    }


}
