package com.snobot.simulator.gui.motor_graphs;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class MotorCurveDisplayController
{
    protected static final int POINT_RESOLUTION = 600;

    @FXML
    private LineChart<Integer, Double> mChart;

    private final XYChart.Series<Integer, Double> mCurrent = new XYChart.Series<>();
    private final XYChart.Series<Integer, Double> mTorque = new XYChart.Series<>();
    private final XYChart.Series<Integer, Double> mPower = new XYChart.Series<>();
    private final XYChart.Series<Integer, Double> mEfficiency = new XYChart.Series<>();

    public MotorCurveDisplayController()
    {
        mCurrent.setName("Current");
        mTorque.setName("Torque");
        mPower.setName("Power");
        mEfficiency.setName("Efficiency");
    }

    @FXML
    public void initialize()
    {
        mChart.setAnimated(false);
        mChart.getData().addAll(mCurrent, mTorque, mPower, mEfficiency);
    }

    public void setCurveParams(DcMotorModelConfig aModel)
    {
        setCurveParams(aModel.mFactoryParams.mMotorType, aModel.mMotorParams.mNominalVoltage, aModel.mMotorParams.mFreeSpeedRpm,
                aModel.mMotorParams.mStallCurrent, aModel.mMotorParams.mFreeCurrent, aModel.mMotorParams.mStallTorque);
    }

    public void setCurveParams(String aMotorName, double aNominalVoltage, double aFreeSpeedRpm, double aStallCurrent, double aFreeCurrent,
            double aStallTorque)
    {
        mChart.setTitle(aMotorName);

        mCurrent.getData().clear();
        mTorque.getData().clear();
        mPower.getData().clear();
        mEfficiency.getData().clear();

        double currentSlope = (aFreeCurrent - aStallCurrent) / aFreeSpeedRpm;
        double torqueSlope = (0 - aStallTorque) / aFreeSpeedRpm;

        int dRpm = (int) Math.ceil(aFreeSpeedRpm / POINT_RESOLUTION);

        for (int rpm = 0; rpm < aFreeSpeedRpm; rpm += dRpm)
        {
            addPoint(aNominalVoltage, aStallCurrent, aStallTorque, rpm, currentSlope, torqueSlope);
        }

        // Add the last point always
        addPoint(aNominalVoltage, aStallCurrent, aStallTorque, (int) aFreeSpeedRpm, currentSlope, torqueSlope);
    }

    private void addPoint(double aNominalVoltage, double aStallCurrent, double aStallTorque, int aRpm, double aCurrentSlope, double aTorqueSlope)
    {
        final double omega = 2 * aRpm * Math.PI / 60;
        final double current = aStallCurrent + aRpm * aCurrentSlope;
        final double torque = aStallTorque + aRpm * aTorqueSlope;
        final double inputPower = aNominalVoltage * current;
        final double outputPower = torque * omega;
        final double efficiency = outputPower / inputPower * 100;

        mCurrent.getData().add(new XYChart.Data<Integer, Double>(aRpm, current));
        mTorque.getData().add(new XYChart.Data<Integer, Double>(aRpm, torque));
        mPower.getData().add(new XYChart.Data<Integer, Double>(aRpm, outputPower));
        mEfficiency.getData().add(new XYChart.Data<Integer, Double>(aRpm, efficiency));
    }

}
