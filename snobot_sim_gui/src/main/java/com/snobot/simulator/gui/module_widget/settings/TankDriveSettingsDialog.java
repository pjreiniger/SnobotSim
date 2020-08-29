package com.snobot.simulator.gui.module_widget.settings;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.simulator_components.config.TankDriveConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class TankDriveSettingsDialog extends JDialog
{
    private static final Logger sLOGGER = LogManager.getLogger(TankDriveSettingsDialog.class);

    private List<TankDriveSettingsPanel> mPanels;
    private final JPanel mComponentPanel = new JPanel();

    public TankDriveSettingsDialog()
    {
        setTitle("Tank Drive Settings");

        initComponents();
    }

    private void initComponents()
    {
        setLayout(new BorderLayout());

        JButton addButton = new JButton("Add Simulator");
        addButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                handleAdd();
            }
        });

        JButton submitButton = new JButton("Submit changes");
        submitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                onSubmit();
                dispose();
            }
        });

        mPanels = new ArrayList<>();

        Collection<Object> simulatorComponents = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs();
        for (Object comp : simulatorComponents)
        {
            if (comp instanceof TankDriveConfig)
            {
                addPanel((TankDriveConfig) comp);
            }
        }

        add(addButton, BorderLayout.NORTH);
        add(mComponentPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void onSubmit()
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

        for (TankDriveSettingsPanel panel : mPanels)
        {
            TankDriveConfig config = panel.getConfig();
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(config.getmLeftMotorHandle(),
                    config.getmRightMotorHandle(), config.getmGyroHandle(), config.getmTurnKp());
        }
    }

    private void handleAdd()
    {
        addPanel(new TankDriveConfig());
        pack();
    }

    private void handleRemove(TankDriveSettingsPanel aPanel, TankDriveConfig aConfig)
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().removeSimulatorComponent(aConfig);
        mPanels.remove(aPanel);
        mComponentPanel.remove(aPanel);
        pack();
    }

    private void addPanel(TankDriveConfig aConfig)
    {
        TankDriveSettingsPanel panel = new TankDriveSettingsPanel(aConfig);
        mPanels.add(panel);
        mComponentPanel.add(panel);
    }

    class TankDriveSettingsPanel extends JPanel
    {
        protected JComboBox<SensorHandleOption> mLeftMotorSelection;
        protected JComboBox<SensorHandleOption> mRightMotorSelection;
        protected JComboBox<SensorHandleOption> mGyroSelection;
        protected JTextField mKpField;
        protected JButton mRemoveButton;

        public TankDriveSettingsPanel(TankDriveConfig aComp)
        {
            setLayout(new GridBagLayout());
            mLeftMotorSelection = new JComboBox<>();
            mRightMotorSelection = new JComboBox<>();
            mGyroSelection = new JComboBox<>();
            mKpField = new JTextField(Double.toString(aComp.getmTurnKp()), 10);
            mRemoveButton = new JButton("Remove");

            List<Integer> speedControllers = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList();
            for (int handle : speedControllers)
            {
                SensorHandleOption option = new SensorHandleOption(handle,
                        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(handle).getName());
                mLeftMotorSelection.addItem(option);
                mRightMotorSelection.addItem(option);

                if (option.mHandle == aComp.getmLeftMotorHandle())
                {
                    mLeftMotorSelection.setSelectedItem(option);
                }

                if (option.mHandle == aComp.getmRightMotorHandle())
                {
                    mRightMotorSelection.setSelectedItem(option);
                }
            }
            List<Integer> gyros = DataAccessorFactory.getInstance().getGyroAccessor().getPortList();
            for (int handle : gyros)
            {
                SensorHandleOption option = new SensorHandleOption(handle, DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(handle).getName());
                mGyroSelection.addItem(option);

                if (option.mHandle == aComp.getmGyroHandle())
                {
                    mGyroSelection.setSelectedItem(option);
                }
            }

            GridBagConstraints gc = new GridBagConstraints();

            gc.gridx = 0;
            gc.gridy = 0;
            add(new JLabel("Left Motor"), gc);

            gc.gridx = 1;
            gc.gridy = 0;
            add(mLeftMotorSelection, gc);

            gc.gridx = 0;
            gc.gridy = 1;
            add(new JLabel("Right Motor"), gc);

            gc.gridx = 1;
            gc.gridy = 1;
            add(mRightMotorSelection, gc);

            gc.gridx = 0;
            gc.gridy = 2;
            add(new JLabel("Gyro"), gc);

            gc.gridx = 1;
            gc.gridy = 2;
            add(mGyroSelection, gc);

            gc.gridx = 0;
            gc.gridy = 3;
            add(new JLabel("Turn Gain"), gc);

            gc.gridx = 1;
            gc.gridy = 3;
            add(mKpField, gc);

            gc.gridx = 0;
            gc.gridy = 4;
            gc.gridwidth = 2;
            gc.fill = GridBagConstraints.BOTH;
            add(mRemoveButton, gc);

            mRemoveButton.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent aEvent)
                {
                    handleRemove(TankDriveSettingsPanel.this, aComp);
                }
            });
        }

        public TankDriveConfig getConfig()
        {
            SensorHandleOption left = (SensorHandleOption) mLeftMotorSelection.getSelectedItem();
            SensorHandleOption right = (SensorHandleOption) mRightMotorSelection.getSelectedItem();
            SensorHandleOption gyro = (SensorHandleOption) mGyroSelection.getSelectedItem();
            int leftHandle = left.mHandle;
            int rightHanle = right.mHandle;
            int gyroHandle = gyro.mHandle;
            double kp = 1;

            try
            {
                kp = Double.parseDouble(mKpField.getText());
            }
            catch (Exception e)
            {
                sLOGGER.log(Level.ERROR, e);
            }

            return new TankDriveConfig(leftHandle, rightHanle, gyroHandle, kp);
        }

    }

}
