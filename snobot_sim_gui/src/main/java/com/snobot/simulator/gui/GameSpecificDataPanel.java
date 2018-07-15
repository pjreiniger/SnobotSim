package com.snobot.simulator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor.MatchType;

public class GameSpecificDataPanel extends JPanel
{
    private JComboBox<String> mGameDataCB;
    private JComboBox<MatchType> mMatchTypeCB;
    private JTextField mEventNameField;
    private JTextField mMatchNumberField;

    private final ItemListener mItemListener = new ItemListener()
    {

        @Override
        public void itemStateChanged(ItemEvent aEvent)
        {
            if (aEvent.getStateChange() == ItemEvent.SELECTED)
            {
                handleUpdate();
            }
        }
    };

    private final DocumentListener mTextChangeListener = new DocumentListener()
    {

        @Override
        public void removeUpdate(DocumentEvent aEvent)
        {
            handleUpdate();
        }

        @Override
        public void insertUpdate(DocumentEvent aEvent)
        {
            handleUpdate();
        }

        @Override
        public void changedUpdate(DocumentEvent aEvent)
        {
            handleUpdate();
        }
    };

    public GameSpecificDataPanel()
    {

        initComponents();

        handleUpdate();
    }

    private void initComponents()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 156, 28, 0 };
        gridBagLayout.rowHeights = new int[]
        { 20, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        // Game Data
        GridBagConstraints gbcblGameData = new GridBagConstraints();
        gbcblGameData.insets = new Insets(0, 0, 5, 0);
        gbcblGameData.gridx = 0;
        gbcblGameData.gridy = 0;
        JLabel lblGameData = new JLabel("Game Data");
        add(lblGameData, gbcblGameData);

        GridBagConstraints gbcGameDataCB = new GridBagConstraints();
        gbcGameDataCB.fill = GridBagConstraints.HORIZONTAL;
        gbcGameDataCB.anchor = GridBagConstraints.NORTH;
        gbcGameDataCB.insets = new Insets(0, 0, 5, 5);
        gbcGameDataCB.gridx = 1;
        gbcGameDataCB.gridy = 0;
        mGameDataCB = new JComboBox<>();
        add(mGameDataCB, gbcGameDataCB);

        // Match Number
        GridBagConstraints gbcblMatchNumber = new GridBagConstraints();
        gbcblMatchNumber.insets = new Insets(0, 0, 5, 0);
        gbcblMatchNumber.gridx = 0;
        gbcblMatchNumber.gridy = 1;
        JLabel lblMatchNumber = new JLabel("Match Number");
        add(lblMatchNumber, gbcblMatchNumber);

        GridBagConstraints gbcMatchNumberField = new GridBagConstraints();
        gbcMatchNumberField.fill = GridBagConstraints.HORIZONTAL;
        gbcMatchNumberField.insets = new Insets(0, 0, 5, 5);
        gbcMatchNumberField.anchor = GridBagConstraints.NORTH;
        gbcMatchNumberField.gridx = 1;
        gbcMatchNumberField.gridy = 1;
        mMatchNumberField = new JTextField("1");
        add(mMatchNumberField, gbcMatchNumberField);

        // Match Type
        GridBagConstraints gbcblMatchType = new GridBagConstraints();
        gbcblMatchType.insets = new Insets(0, 0, 5, 0);
        gbcblMatchType.gridx = 0;
        gbcblMatchType.gridy = 2;
        JLabel lblMatchType = new JLabel("Match Type");
        add(lblMatchType, gbcblMatchType);

        GridBagConstraints gbcMatchTypeCB = new GridBagConstraints();
        gbcMatchTypeCB.fill = GridBagConstraints.HORIZONTAL;
        gbcMatchTypeCB.anchor = GridBagConstraints.NORTH;
        gbcMatchTypeCB.insets = new Insets(0, 0, 5, 5);
        gbcMatchTypeCB.gridx = 1;
        gbcMatchTypeCB.gridy = 2;
        mMatchTypeCB = new JComboBox<>();
        add(mMatchTypeCB, gbcMatchTypeCB);

        // Event Name
        GridBagConstraints gbcblEventName = new GridBagConstraints();
        gbcblEventName.insets = new Insets(0, 0, 5, 0);
        gbcblEventName.gridx = 0;
        gbcblEventName.gridy = 3;
        JLabel lblEventName = new JLabel("Event Name");
        add(lblEventName, gbcblEventName);

        mEventNameField = new JTextField("Simulation");
        GridBagConstraints gbcEventNameField = new GridBagConstraints();
        gbcEventNameField.insets = new Insets(0, 0, 5, 5);
        gbcEventNameField.fill = GridBagConstraints.HORIZONTAL;
        gbcEventNameField.anchor = GridBagConstraints.NORTH;
        gbcEventNameField.gridx = 1;
        gbcEventNameField.gridy = 3;
        add(mEventNameField, gbcEventNameField);

        // Setup
        for (MatchType type : MatchType.values())
        {
            mMatchTypeCB.addItem(type);
        }
        mMatchTypeCB.addItemListener(mItemListener);

        mGameDataCB.addItem("LLL");
        mGameDataCB.addItem("LRL");
        mGameDataCB.addItem("RLR");
        mGameDataCB.addItem("RRR");
        mGameDataCB.addItemListener(mItemListener);

        mEventNameField.getDocument().addDocumentListener(mTextChangeListener);
        mMatchNumberField.getDocument().addDocumentListener(mTextChangeListener);
    }

    private void handleUpdate()
    {
        int matchNumber = 1;

        try
        {
            matchNumber = Integer.parseInt(mMatchNumberField.getText());
        }
        catch (NumberFormatException ex)
        {
            LogManager.getLogger().log(Level.ERROR, "Could not parse match number", ex);
        }

        DataAccessorFactory.getInstance().getDriverStationAccessor().setMatchInfo(
                mEventNameField.getText(),
                (MatchType) mMatchTypeCB.getSelectedItem(),
                matchNumber,
                0,
                (String) mGameDataCB.getSelectedItem());
    }
}
