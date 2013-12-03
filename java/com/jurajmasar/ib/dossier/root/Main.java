package com.jurajmasar.ib.dossier.root;

import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.files.DirectAccessFile;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.logic.Password;
import com.jurajmasar.ib.dossier.windows_and_dialogs.MainWindow;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.CustomPasswordField;

import  javax.swing.JOptionPane;
import  javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.io.*;

/**
 * Main class of the program - it controls all other classes
 * 
 * @author Juraj Masar
 * @version 0.1
 */
/** DOSSIER: SL mastery 2 - user-defined objects **/
public class Main
{
    /**
     * Boostrapper of the application.
     * Loads the rest of the application.
     */
    public static void main(String[] args)
    {
        //set the windows look 
        try 
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }        

        //check if dictionary Data exists
        
        File dir = new File(Static.dataDirectory);
    
        if (!dir.exists() && !dir.mkdir())         
        {
            Dialog.error ("Error: Data directory \""+Static.dataDirectory+"\" does not exist\n"
             +"and cannot be created."
            );
            return;
        }
        
        
        //check the data consistency
        Distributor d = DistributorManager.getDistributorBySponsor("0");
        if (d == null)
        {
          //data is inconcistent                      	
          int n = JOptionPane.showConfirmDialog(
            null,
            "Data of the application is inconsistent.\n"
            +"The root distributor cannot be found.\n"
            +"Create initial data?",
            "Create initial data?",
            JOptionPane.YES_NO_OPTION);
           
          if (n == 0)//yes
          {
              String s = (String)JOptionPane.showInputDialog(
                null,
                "Please, insert the registration number of the \n"
                + "root distributor (the user of the application): ",
                "Installation",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "10000");
                if (s == null) return;
                if (s != null && s.length() > 0 && Static.isNumeric(s))
                {
                    createInitData(s);
                    Dialog.info ("Initial data has been successfully created.\n"
                                 +"Password has been set to \"\"");
                } else
                {
                    Dialog.error ("Registration number has to be numeric.");
                    return;
                }
          } else
            return;
        }        
        
        //show the password screen
        CustomPasswordField passwordField = new CustomPasswordField();

        Object[] obj = {"Welcome to Multilevel Marketing Manager.\n"
                        + "Please, enter password to continue: ", passwordField};
        Object stringArray[] = {"Login","Cancel"};
                
            if (JOptionPane.showOptionDialog(
                            null,
                            obj, 
                            "Multilevel Marketing Manager - Login",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            Dialog.createImageIcon(Static.keyIconPath, "Login"),
                            //icon from
                            //http://www.iconarchive.com/show/security-icons-by-aha-soft/key-icon.html
                            stringArray,
                            stringArray[0]) == JOptionPane.YES_OPTION)
            {
                String password = Static.charArrayToString(passwordField.getPassword());                

                if (Password.checkPassword(password))
                {
                    Dialog.mainWindow = new MainWindow();
                }
                else
                {
                    Dialog.warning ("The password you have inserted is invalid.");
                    main(args);
                }       
        }
    }


    /**
     * Creates initial data required for the program.
     * Sets the password empty - "";
     * Adds the root distributor with sponsor 0;
     * 
     * @param regNum registration number of the root distributor
     */
    public static void createInitData(String regNum)
    {
        //delete all existing data
        DirectAccessFile f = new DirectAccessFile (Static.fileDistributor,
                              Static.distributorRecordLength);
        while (f.getNumberOfRecords() != 0)
        {
            f.delete((long) Static.random(0,(int)f.getNumberOfRecords()-1));
        }        
        
        //set the password
        Password.setPassword("");
        
        //add the first distributor
        DistributorManager.addDistributor(regNum,
                          "0",
                          "SK",
                          "1.1.2010",
                          "",
                          "Name",
                          "Surname",
                          "1.1.1950",
                          "valid@email.com",
                          "00 421 907 123 456",
                          "Please, replace this data with your details."
                          );        
    }    
}
