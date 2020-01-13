package com.snobot.simulator.gui.joysticks.sub_panels;


import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.XboxButtonMap;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class XboxDisplayController
{
    private static final Color BTN_PRESSED_COLOR = new Color(0, 1, 0, .5);
    private static final Color BTN_NOT_PRESSED_COLOR = Color.TRANSPARENT;

    @FXML
    private Circle mXButton;
    @FXML
    private Circle mYButton;
    @FXML
    private Circle mBButton;
    @FXML
    private Circle mAButton;
    @FXML
    private Circle mBackButton;
    @FXML
    private Circle mStartButton;
    @FXML
    private Circle mXboxButton;

    @FXML
    private Rectangle mLeftBumper;
    @FXML
    private Rectangle mRightBumper;

    @FXML
    private Rectangle mL3;
    @FXML
    private Rectangle mR3;

    @FXML
    private Circle mLeftJoystickCircle;
    @FXML
    private Circle mRightJoystickCircle;

    @FXML
    private Line mLeftJoystickLine;
    @FXML
    private Line mRightJoystickLine;

    private IMockJoystick mJoystick;

    public void setJoystick(IMockJoystick aSelectedJoystick)
    {
        mJoystick = aSelectedJoystick;
    }

    public void updateDisplay()
    {
        colorButton(mXButton, mJoystick.getRawButton(XboxButtonMap.X_BUTTON - 1));
        colorButton(mYButton, mJoystick.getRawButton(XboxButtonMap.Y_BUTTON - 1));
        colorButton(mBButton, mJoystick.getRawButton(XboxButtonMap.B_BUTTON - 1));
        colorButton(mAButton, mJoystick.getRawButton(XboxButtonMap.A_BUTTON - 1));
        colorButton(mLeftBumper, mJoystick.getRawButton(XboxButtonMap.LB_BUTTON - 1));
        colorButton(mRightBumper, mJoystick.getRawButton(XboxButtonMap.RB_BUTTON - 1));

        colorButton(mL3, mJoystick.getRawButton(XboxButtonMap.L3_BUTTON - 1));
        colorButton(mR3, mJoystick.getRawButton(XboxButtonMap.R3_BUTTON - 1));

        colorButton(mBackButton, mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON - 1));
        colorButton(mStartButton, mJoystick.getRawButton(XboxButtonMap.START_BUTTON - 1));
        colorButton(mXboxButton, mJoystick.getRawButton(XboxButtonMap.XBOX_BUTTON));

        drawJoystick(mLeftJoystickLine, mLeftJoystickCircle, mJoystick.getRawAxis(XboxButtonMap.LEFT_X_AXIS),
                mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS), 162, 270);

        drawJoystick(mRightJoystickLine, mRightJoystickCircle, mJoystick.getRawAxis(XboxButtonMap.RIGHT_X_AXIS),
                mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS), 468, 372);
        //
        // drawTrigger(aGraphics,
        // mJoystick.getRawAxis(XboxButtonMap.LEFT_TRIGGER), 155, 40);
        // drawTrigger(aGraphics,
        // mJoystick.getRawAxis(XboxButtonMap.RIGHT_TRIGGER), 530, 40);
        //
        // drawPOV(aGraphics, mJoystick.getPovValues());
    }

    private void drawJoystick(Line aJoystickVector, Circle aJoystickCircle, double aXAxis, double aYAxis, double aCircleHomeX, double aCircleHomeY)
    {
        double width = 98 / 2;
        double height = 80 / 2;

        aJoystickVector.setEndX(aJoystickVector.getStartX() + aXAxis * width);
        aJoystickVector.setEndY(aJoystickVector.getStartY() + aYAxis * height);

        aJoystickCircle.setCenterX(aCircleHomeX + aXAxis * width);
        aJoystickCircle.setCenterY(aCircleHomeY + aYAxis * height);

    }

    private void colorButton(Shape aShape, boolean aPressed)
    {
        if (aPressed)
        {
            aShape.setFill(BTN_PRESSED_COLOR);
        }
        else
        {
            aShape.setFill(BTN_NOT_PRESSED_COLOR);
        }
    }
}
