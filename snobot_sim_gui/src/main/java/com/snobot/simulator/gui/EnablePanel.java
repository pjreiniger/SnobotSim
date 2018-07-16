package com.snobot.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This panel shows the Enable and Autonomous buttons
 *
 * @author PJ
 *
 */
public class EnablePanel extends JPanel
{
    private final JCheckBox mEnableButton = new JCheckBox("Enabled");
    private final JCheckBox mAutonButton = new JCheckBox("Autonomous");
    private final JLabel mTimeLabel = new JLabel("Time: 000.00");
    private final boolean mUseSnobotSim;

    public EnablePanel(boolean aUseSnobotSim)
    {
        mUseSnobotSim = aUseSnobotSim;

        add(mEnableButton);
        add(mAutonButton);
        add(mTimeLabel);

        if (mUseSnobotSim)
        {
            ActionListener actionListener = new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent aUnused)
                {
                    DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(!isEnabled());
                    DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(isAuton());
                }
            };
            mEnableButton.addActionListener(actionListener);
            mAutonButton.addActionListener(actionListener);

            DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);
            DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(false);

            setRobotEnabled(true);
            DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);
        }
        else
        {
            mEnableButton.setEnabled(false);
            mAutonButton.setEnabled(false);
        }
    }

    @Override
    public boolean isEnabled()
    {
        return mEnableButton.isSelected();
    }

    public boolean isAuton()
    {
        return mAutonButton.isSelected();
    }

    public void setTime(double aTime)
    {
        DecimalFormat df = new DecimalFormat("000.00");
        mTimeLabel.setText("Time: " + df.format(aTime));
    }

    public final void setRobotEnabled(boolean aState)
    {
        mEnableButton.setSelected(aState);
    }

    public void updateLoop()
    {
        if (mUseSnobotSim)
        {
            setTime(DataAccessorFactory.getInstance().getDriverStationAccessor().getTimeSinceEnabled());
        }
        else
        {
            setTime(DriverStation.getInstance().getMatchTime());
            mEnableButton.setSelected(DriverStation.getInstance().isEnabled());
            mAutonButton.setSelected(DriverStation.getInstance().isAutonomous());
        }
    }
}
