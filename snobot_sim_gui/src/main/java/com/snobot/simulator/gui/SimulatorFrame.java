package com.snobot.simulator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.config.SimulatorConfigWriter;
import com.snobot.simulator.gui.joysticks.JoystickManagerDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

/**
 * Top level frame that displays all of the simulation displays
 *
 * @author PJ
 *
 */
public class SimulatorFrame extends JFrame
{
    private static final Logger sLOGGER = LogManager.getLogger(SimulatorFrame.class);

    private GraphicalSensorDisplayPanel mBasicPanel;
    private EnablePanel mEnablePanel;
    private String mSimulatorConfigFile;

    public SimulatorFrame(String aSimulatorConfigFile)
    {
        initComponenents();

        mSimulatorConfigFile = aSimulatorConfigFile;
    }

    public void updateLoop()
    {
        mBasicPanel.update();
        mEnablePanel.setTime(DataAccessorFactory.getInstance().getSimulatorDataAccessor().getTimeSinceEnabled());
    }

    private void initComponenents()
    {
        mBasicPanel = new GraphicalSensorDisplayPanel();
        mEnablePanel = new EnablePanel();

        mEnablePanel.addStateChangedListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDisabled(!mEnablePanel.isEnabled());
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setAutonomous(mEnablePanel.isAuton());
            }
        });

        JButton configureJoystickBtn = new JButton("Configure Joysticks");
        configureJoystickBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                showJoystickDialog();
            }
        });

        JButton changeSettingsBtn = new JButton("Change Settings");
        JButton saveSettingsBtn = new JButton("Save Settings");

        changeSettingsBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                changeSettingsBtn.setVisible(false);
                saveSettingsBtn.setVisible(true);

                showSettingsOptions(true);
            }
        });

        saveSettingsBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent aEvent)
            {
                changeSettingsBtn.setVisible(true);
                saveSettingsBtn.setVisible(false);

                saveSettings();
            }
        });
        saveSettingsBtn.setVisible(false);

        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.add(changeSettingsBtn, BorderLayout.NORTH);
        settingsPanel.add(saveSettingsBtn, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(configureJoystickBtn, BorderLayout.NORTH);
        buttonPanel.add(settingsPanel, BorderLayout.SOUTH);


        JPanel driverStationPanel = new JPanel();
        driverStationPanel.setLayout(new BoxLayout(driverStationPanel, BoxLayout.Y_AXIS));
        driverStationPanel.add(mEnablePanel);
        driverStationPanel.add(new GameSpecificDataPanel());

        JScrollPane scrollPane = new JScrollPane(mBasicPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(driverStationPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDisabled(false);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setAutonomous(false);

        mEnablePanel.setRobotEnabled(true);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDisabled(false);
    }

    private void saveSettings()
    {
        SimulatorConfigWriter writer = new SimulatorConfigWriter();

        String dumpFile = null;

        if (mSimulatorConfigFile == null)
        {
            JFileChooser fc = new JFileChooser(".");
            int result = fc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                dumpFile = fc.getSelectedFile().toString();
            }
        }
        else
        {
            dumpFile = mSimulatorConfigFile;
        }

        if (dumpFile == null)
        {
            sLOGGER.log(Level.INFO, "User cancelled save!");
        }
        else
        {
            sLOGGER.log(Level.INFO, "Saving to '" + dumpFile + "'");
            if (mSimulatorConfigFile == null)
            {
                String message = "<html>This does not update the simulator file, you must add this line to the simulator config file:<br><br><br>"
                        + "simulator_config: " + dumpFile + "<html>";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.INFORMATION_MESSAGE);
                mSimulatorConfigFile = dumpFile;
            }

            writer.writeConfig(dumpFile);
        }

        showSettingsOptions(false);
    }

    private void showJoystickDialog()
    {
        JoystickManagerDialog dialog = new JoystickManagerDialog();
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void showSettingsOptions(boolean aShow)
    {
        mBasicPanel.showSettingsButtons(aShow);
        pack();
    }
}
