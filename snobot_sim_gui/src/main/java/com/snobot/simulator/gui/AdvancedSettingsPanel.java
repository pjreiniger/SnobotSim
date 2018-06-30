package com.snobot.simulator.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.module_widget.settings.SpiAndI2CSettingsDialog;
import com.snobot.simulator.gui.module_widget.settings.TankDriveSettingsDialog;

public class AdvancedSettingsPanel extends JPanel
{
    private static final Logger sLOGGER = LogManager.getLogger(AdvancedSettingsPanel.class);

    private final List<ButtonDialogPair> mDialogs;

    private class ButtonDialogPair
    {
        private final JButton mButton;

        public ButtonDialogPair(String aName, ImageIcon aIcon, JDialog aDialog)
        {
            mButton = new JButton(aName, aIcon);
            mButton.setVisible(false);

            mButton.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent aEvent)
                {

                    aDialog.pack();
                    aDialog.setModal(true);
                    aDialog.setLocationRelativeTo(AdvancedSettingsPanel.this);
                    aDialog.setVisible(true);
                }
            });
        }
    }

    public AdvancedSettingsPanel()
    {
        ImageIcon icon = null;
        try
        {
            Image img = ImageIO.read(getClass().getResource("/com/snobot/simulator/gui/module_widget/gear.png"));
            icon = new ImageIcon(img);
        }
        catch (IOException e)
        {
            sLOGGER.log(Level.ERROR, e);
        }

        mDialogs = new ArrayList<>();
        mDialogs.add(new ButtonDialogPair("SPI & I2C", icon, new SpiAndI2CSettingsDialog()));
        mDialogs.add(new ButtonDialogPair("Tank Drive", icon, new TankDriveSettingsDialog()));

        for (ButtonDialogPair pair : mDialogs)
        {
            add(pair.mButton);
        }
    }

    public void showSettingsButtons(boolean aShow)
    {
        for (ButtonDialogPair pair : mDialogs)
        {
            pair.mButton.setVisible(aShow);
        }
    }

}
