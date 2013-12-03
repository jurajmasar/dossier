package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.JPasswordField;

/**
 * Creates PasswordField which generates Tab+Enter when Enter is pressed.
 * Useful for JOptionPane.showOptionDialog
 * 
 * @author tommylee
 * @version 0.1
 * 
 * Originally from: 
 * http://forums.devshed.com/java-help-9/
 * is-there-a-password-field-option-for-joptionpane-showinputdialog-566325.html
 */
public class CustomPasswordField extends JPasswordField
{
    /**
     * Constructor - enhances the JPasswordField
     */
    public CustomPasswordField() 
    {
        addKeyListener(new java.awt.event.KeyAdapter() 
        {
            @Override
            public void keyPressed(java.awt.event.KeyEvent kEvt) 
            {
                /* if (kEvt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
                 {
                    kEvt.consume();
                    // auto generate TAB + TAB + Enter keypress events
                    try 
                    {
                        java.awt.Robot robot = new java.awt.Robot();
                        robot.setAutoDelay(100);
                        robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
                        robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
                        robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
                        robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
                        robot.keyPress(java.awt.event.KeyEvent.VK_SPACE);
                        robot.keyRelease(java.awt.event.KeyEvent.VK_SPACE);                        
                    }
                    catch (java.awt.AWTException awtEx) {
                        awtEx.printStackTrace();
                    }
                }*/
            }
        });
    }
}