package com.snobot.simulator.gui.joysticks.sub_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.joysticks.IMockJoystick;

public class XboxPanel extends JPanel
{
    private static final Logger sLOGGER = LogManager.getLogger(XboxPanel.class);
    private static final Color sPRESSED_BTN_COLOR = new Color(0, 255, 0, 180);
    private static final Color sJOYSTICK_BACKGROUND = new Color(255, 255, 255, 180);

    private final Image mControllerImage;
    private IMockJoystick mJoystick;

    public XboxPanel() throws IOException
    {
        URL url = getClass().getResource("/com/snobot/simulator/gui/joysticks/sub_panels/xbox_controller.png");
        mControllerImage = ImageIO.read(url);

        setPreferredSize(new Dimension(mControllerImage.getWidth(null), mControllerImage.getHeight(null)));
    }

    public void setJoystick(IMockJoystick aJoystick)
    {
        mJoystick = aJoystick;
    }

    public void updateDisplay()
    {
        repaint();
    }

    public void paint(Graphics aGraphics)
    {
        if (mControllerImage == null)
        {
            return;
        }

        aGraphics.clearRect(0, 0, getWidth(), getHeight());
        aGraphics.drawImage(mControllerImage, 0, 0, null);

        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.X_BUTTON - 1), 490, 218);
        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.Y_BUTTON - 1), 550, 170);
        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.B_BUTTON - 1), 602, 215);
        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.A_BUTTON - 1), 540, 260);
        colorBumper(aGraphics, mJoystick.getRawButton(XboxButtonMap.LB_BUTTON - 1), 95, 95);
        colorBumper(aGraphics, mJoystick.getRawButton(XboxButtonMap.RB_BUTTON - 1), 510, 95);

        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON - 1), 265, 220);
        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.START_BUTTON - 1), 415, 220);
        colorButton(aGraphics, mJoystick.getRawButton(XboxButtonMap.XBOX_BUTTON), 340, 217);

        drawJoystick(aGraphics, mJoystick.getRawButton(XboxButtonMap.L3_BUTTON - 1), mJoystick.getRawAxis(XboxButtonMap.LEFT_X_AXIS),
                mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS), 115, 228);
        drawJoystick(aGraphics, mJoystick.getRawButton(XboxButtonMap.R3_BUTTON - 1), mJoystick.getRawAxis(XboxButtonMap.RIGHT_X_AXIS),
                mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS), 420, 330);

        drawTrigger(aGraphics, mJoystick.getRawAxis(XboxButtonMap.LEFT_TRIGGER), 155, 40);
        drawTrigger(aGraphics, mJoystick.getRawAxis(XboxButtonMap.RIGHT_TRIGGER), 530, 40);

        drawPOV(aGraphics, mJoystick.getPovValues());
    }

    private void drawPOV(Graphics aGraphics, short[] aPov) // NOPMD
    {
        if (aPov.length != 0)
        {
            short pov = aPov[0];

            switch (pov)
            {
            case 0:
                drawPOV(aGraphics, 250, 300);
                break;
            case 45:
                drawPOV(aGraphics, 250, 300);
                drawPOV(aGraphics, 288, 340);
                break;
            case 90:
                drawPOV(aGraphics, 288, 340);
                break;
            case 135:
                drawPOV(aGraphics, 288, 340);
                drawPOV(aGraphics, 250, 365);
                break;
            case 180:
                drawPOV(aGraphics, 250, 365);
                break;
            case 225:
                drawPOV(aGraphics, 250, 365);
                drawPOV(aGraphics, 210, 340);
                break;
            case 270:
                drawPOV(aGraphics, 210, 340);
                break;
            case -45:
                drawPOV(aGraphics, 210, 340);
                drawPOV(aGraphics, 250, 300);
                break;
            case -1:
                break;
            default:
                sLOGGER.log(Level.DEBUG, "Unexpected POV value: " + pov);
                break;
            }
        }
    }

    private void drawPOV(Graphics aGraphics, int aX, int aY)
    {
        aGraphics.setColor(Color.red);
        aGraphics.fillRect(aX, aY, 40, 40);
    }

    private void drawTrigger(Graphics aGraphics, double aValue, int aX, int aY)
    {
        Color color = Util.alphaColor(Util.colorGetShadedColor(aValue, 1, -1), 40);

        aGraphics.setColor(color);
        aGraphics.fillRect(aX, aY, 60, 60);
    }

    private void drawJoystick(Graphics aGraphics, boolean aPressed, double aXAxis, double aYAxis, int aX, int aY)
    {
        int width = 98;
        int height = 80;

        if (aPressed)
        {
            aGraphics.setColor(sPRESSED_BTN_COLOR);
        }
        else
        {
            aGraphics.setColor(sJOYSTICK_BACKGROUND);
        }
        aGraphics.fillRect(aX, aY, width, height);

        int xAxisSpot = (int) (aXAxis * width * .5 + width * .5 + aX);
        int yAxisSpot = (int) (aYAxis * height * .5 + height * .5 + aY);

        aGraphics.setColor(Color.black);
        aGraphics.fillOval(xAxisSpot, yAxisSpot, 5, 5);

    }

    private void colorBumper(Graphics aGraphics, boolean aPressed, int aX, int aY)
    {
        if (aPressed)
        {
            aGraphics.setColor(sPRESSED_BTN_COLOR);
            aGraphics.fillRect(aX, aY, 140, 55);
        }
    }

    private void colorButton(Graphics aGraphics, boolean aPressed, int aX, int aY)
    {
        if (aPressed)
        {
            aGraphics.setColor(sPRESSED_BTN_COLOR);
            aGraphics.fillOval(aX, aY, 60, 60);
        }
    }
}
