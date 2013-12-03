package com.jurajmasar.ib.dossier.root;

import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.data_structures.ItemRegNum;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.windows_and_dialogs.MainWindow;
import com.jurajmasar.ib.dossier.windows_and_dialogs.ProfileWindow;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.text.*;
import javax.swing.*;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * Contains methods for executing window dialogs.
 * 
 * @author Juraj Masar 
 * @version 0.1
 */
public class Dialog
{
    public static MainWindow mainWindow;
    /**
     * Displays error message with a given string and title.
     *
     * @param  msg   message to display
     * @param  title   title of the window
     */
    public static void error(String msg, String title)
    {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);        
    }

    /**
     * Displays error message with a given string.
     *
     * @param  msg   message to display
     */
    public static void error(String msg)
    {
        error (msg, Static.defaultErrorTitle);
    }

    /**
     * Displays warning message with a given string and title.
     *
     * @param  msg   message to display
     * @param  title   title of the window
     */
    public static void warning(String msg, String title)
    {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);        
    }

    /**
     * Displays warning message with a given string.
     *
     * @param  msg   message to display
     */
    public static void warning(String msg)
    {
        warning (msg, Static.defaultWarningTitle);
    }

    /**
     * Displays info message with a given string and title.
     *
     * @param  msg   message to display
     * @param  title   title of the window
     */
    public static void info(String msg, String title)
    {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);        
    }

    /**
     * Displays info message with a given string.
     *
     * @param  msg   message to display
     */
    public static void info(String msg)
    {
        info (msg, Static.defaultInfoTitle);
    }


    /**
     * Displays plain message - without icon - with a given string and title.
     *
     * @param  msg   message to display
     * @param  title   title of the window
     */
    public static void plain(String msg, String title)
    {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.PLAIN_MESSAGE);        
    }

    /**
     * Displays info message with a given string.
     *
     * @param  msg   message to display
     */
    public static void plain(String msg)
    {
        plain (msg, Static.defaultPlainTitle);
    }
 
    /**
     * Creates ImageIcon object out of given path.
     * 
     * @param path path to file
     * @param description description of the icon
     * @return ImageIcon object
     */
    public static ImageIcon createImageIcon(String path, String description) 
    {
        java.net.URL imgURL = null;
        try {
            imgURL = new File(path).toURI().toURL();
        } catch (MalformedURLException e) {
            Dialog.error(e.getMessage());
        }

        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
           Dialog.error("Static error: \n couldn't find icon: " + path);
           return null;
        }
    }    

    /**
     * Displays profile window for given distributor
     *
     * @param  regNum   registration number of the distributor
     * @return true if successful
     */
    public static boolean displayProfile(final String regNum)
    {
        DistributorManager.init();
        ItemRegNum item = ((ItemRegNum)DistributorManager.listRegNum.find(regNum));
        if (item == null) return false;
        if (item.window != null && item.window.isDisplayable() && item.window.isShowing())
        {
            item.window.toFront();            
            return true;
        } else 
        {
            final Distributor d = DistributorManager.getDistributorByRegNum(regNum);
            item.window = new ProfileWindow(d);
            return true;
        }
    }    

    /**
     * Using modal window it asks user to insert month in
     * MM/yyyy format. If not valid, query is repeated
     * after error message. The inserted String is returned.
     *
     * @return     the sum of x and y
     */
    public static String askForMonth()
    {
        //show the month query screen
        final String monthFormat = "MM/yyyy";
        JFormattedTextField monthField = new JFormattedTextField();
        //submit the dialog when enter is pressed
        monthField.addKeyListener(new java.awt.event.KeyAdapter() 
                {
                    public void keyPressed(java.awt.event.KeyEvent kEvt) 
                    {
                         if (kEvt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) 
                         {
                            kEvt.consume();
                            // auto generate TAB + Enter keypress events
                            try 
                            {
                                java.awt.Robot robot = new java.awt.Robot();
                                robot.setAutoDelay(100);
                                robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
                                robot.keyPress(java.awt.event.KeyEvent.VK_SPACE);
                                robot.keyRelease(java.awt.event.KeyEvent.VK_SPACE);                        
                            }
                            catch (java.awt.AWTException awtEx) {
                                awtEx.printStackTrace();
                            }
                        }
                    }
                });
            
        monthField.setFormatterFactory(
         new DefaultFormatterFactory(new DateFormatter(
            new java.text.SimpleDateFormat(monthFormat))));  
        monthField.setText("01/2011");            
        Object[] obj = {"Please, insert month to which you want to add points:\n",
                         monthField};
        Object stringArray[] = {"Continue...","Cancel"};
            
        if (JOptionPane.showOptionDialog(
                        null,
                        obj, 
                        "Insert month",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        //icon from
                        //http://www.iconarchive.com/show/security-icons-by-aha-soft/key-icon.html
                        stringArray,
                        null) == JOptionPane.YES_OPTION)
        {
            String s = monthField.getText();                

            //check format
            try 
            {
                DateFormat formatter = new SimpleDateFormat(monthFormat);
                if ((Date)formatter.parse(s) != null) //if the format is valid
                {
                    return s;
                }
            } catch (ParseException e)
            {
                error("The inserted format is not valid.");
                return askForMonth();
            }            
        } 
        return null;
    }
}
