package com.snobot.simulator.gui.module_widget;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseWidgetDisplay<KeyType, WidgetType extends Container> extends JPanel
{
    private static final Logger sLOGGER = LogManager.getLogger(BaseWidgetDisplay.class);

    protected Map<KeyType, WidgetType> mWidgetMap;
    protected Map<KeyType, JLabel> mLabelMap;
    protected List<JButton> mSettingsButtons;

    public BaseWidgetDisplay(Collection<KeyType> aKeys)
    {
        setLayout(new GridBagLayout());

        mSettingsButtons = new ArrayList<>();
        mWidgetMap = new HashMap<>();
        mLabelMap = new HashMap<>();

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

        int i = 0;
        for (KeyType key : aKeys)
        {
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridy = i;

            WidgetType panelPair = createWidget(key);
            if (panelPair != null)
            {
                JButton settingsButton = new JButton(icon);
                settingsButton.setPreferredSize(new Dimension(28, 28));
                settingsButton.setVisible(false);

                JDialog settingsDialog = createSettingsDialog(key);
                if (settingsDialog != null)
                {
                    settingsDialog.setModal(true);
                    mSettingsButtons.add(settingsButton);

                    settingsButton.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent aEvent)
                        {
                            settingsDialog.setLocationRelativeTo(BaseWidgetDisplay.this);
                            settingsDialog.setVisible(true);
                        }
                    });
                }

                JLabel label = new JLabel(getName(key));

                mWidgetMap.put(key, panelPair);
                mLabelMap.put(key, label);


                gc.gridx = 0;
                gc.anchor = GridBagConstraints.WEST;
                add(settingsButton, gc);

                gc.gridx = 1;
                gc.weightx = 1;
                gc.anchor = GridBagConstraints.EAST;
                add(label, gc);

                gc.gridx = 2;
                gc.weightx = 1;
                gc.anchor = GridBagConstraints.WEST;
                add(panelPair, gc);

                ++i;
            }
        }
    }

    protected abstract WidgetType createWidget(KeyType aKey);

    protected abstract JDialog createSettingsDialog(KeyType aKey);

    protected abstract String getName(KeyType aKey);

    public abstract void update();

    public boolean isEmpty()
    {
        return mWidgetMap.isEmpty();
    }

    public void showSettingsButtons(boolean aShow)
    {
        for (JButton btn : mSettingsButtons)
        {
            btn.setVisible(aShow);
        }
    }
}
