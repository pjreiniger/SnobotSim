package com.snobot.simulator.gui.widgets.settings.motor_sim;

import java.text.DecimalFormat;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.motor_graphs.MotorCurveDisplayController;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class DcMotorModelParamsController
{
    private static final Logger sLOGGER = LogManager.getLogger(DcMotorModelParamsController.class);

    @FXML
    private ComboBox<String> mMotorType;

    @FXML
    private Spinner<Integer> mNumMotors;

    @FXML
    private TextField mGearRatio;

    @FXML
    private TextField mEfficiency;

    @FXML
    private CheckBox mInverted;

    @FXML
    private CheckBox mBrake;

    @FXML
    private TextField mNominalVoltage;

    @FXML
    private TextField mFreeSpeedRpm;

    @FXML
    private TextField mNominalCurrent;

    @FXML
    private TextField mStallTorque;

    @FXML
    private TextField mStallCurrent;

    @FXML
    private MotorCurveDisplayController mMotorChartController;

    private final DecimalFormat mDecimalFormat;

    public DcMotorModelParamsController()
    {
        mDecimalFormat = new DecimalFormat("0.000");
    }

    @FXML
    protected void handleMotorType()
    {
        updateMotorConfig();
    }

    public DcMotorModelConfig getMotorConfig()
    {
        DcMotorModelConfig output = null;

        try
        {
            String selectedMotor = mMotorType.getSelectionModel().getSelectedItem();
            int numMotors = (Integer) (mNumMotors.getValue());
            double gearReduction = Double.parseDouble(mGearRatio.getText());
            double efficiency = Double.parseDouble(mEfficiency.getText());

            output = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor(selectedMotor, numMotors, gearReduction, efficiency,
                    mInverted.isSelected(), mBrake.isSelected());
        }
        catch (NumberFormatException e)
        {
            sLOGGER.log(Level.ERROR, e);
        }

        return output;
    }

    private void updateMotorConfig()
    {
        setModelConfig(getMotorConfig());
    }

    public void setModelConfig(DcMotorModelConfig aConfig)
    {
        mMotorType.getSelectionModel().select(aConfig.mFactoryParams.mMotorType);
        mNumMotors.getValueFactory().setValue(aConfig.mFactoryParams.mNumMotors);
        mGearRatio.setText(Double.toString(aConfig.mFactoryParams.mGearReduction));
        mEfficiency.setText(Double.toString(aConfig.mFactoryParams.mGearboxEfficiency));

        mNominalVoltage.setText(mDecimalFormat.format(aConfig.mMotorParams.mNominalVoltage));
        mFreeSpeedRpm.setText(mDecimalFormat.format(aConfig.mMotorParams.mFreeSpeedRpm));
        mNominalCurrent.setText(mDecimalFormat.format(aConfig.mMotorParams.mFreeCurrent));
        mStallTorque.setText(mDecimalFormat.format(aConfig.mMotorParams.mStallTorque));
        mStallCurrent.setText(mDecimalFormat.format(aConfig.mMotorParams.mStallCurrent));
        mInverted.setSelected(aConfig.mFactoryParams.ismInverted());
        mBrake.setSelected(aConfig.mFactoryParams.ismHasBrake());

        mMotorChartController.setCurveParams(aConfig);
    }
}
