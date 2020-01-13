package com.snobot.simulator.gui.widgets.settings.motor_sim;

import javafx.fxml.FXML;

public abstract class BaseMotorSimWithDcMotorModelController implements IMotorSimController // NOPMD.AbstractClassWithoutAnyMethod
{
    @FXML
    protected DcMotorModelParamsController mMotorPanelController;

}
