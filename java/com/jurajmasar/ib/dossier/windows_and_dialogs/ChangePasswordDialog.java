package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.logic.Password;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Change password Dialog.
 * Customized JDialog.
 * 
 * @author Juraj Masar
 * @version 0.1
 */

public class ChangePasswordDialog extends JDialog implements ActionListener
{
    //definitions of controls
    private JButton cancelBtn;
    private JButton changeBtn;
    private JPasswordField confirmField;
    private JLabel confirmLabel;
    private JLabel introLabel;
    private JPasswordField oldPasswordField;
    private JLabel oldPasswordLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    
    /**
     * Designs and creates the window.
     */
    public ChangePasswordDialog(JFrame frame, String title, boolean modal) 
    {
        super (frame, title, modal);        
        introLabel = new JLabel();
        oldPasswordLabel = new JLabel();
        passwordLabel = new JLabel();
        confirmLabel = new JLabel();
        confirmField = new JPasswordField();
        passwordField = new JPasswordField();
        oldPasswordField = new JPasswordField();
        changeBtn = new JButton();
        cancelBtn = new JButton();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        introLabel.setText("Please, insert the following information:");
        getContentPane().add(introLabel);
        introLabel.setBounds(10, 11, 190, 14);

        oldPasswordLabel.setText("* Old password:");
        getContentPane().add(oldPasswordLabel);
        oldPasswordLabel.setBounds(10, 34, 80, 14);

        passwordLabel.setText("* New password:");
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(10, 60, 85, 14);

        confirmLabel.setText("* Confirm password:");
        getContentPane().add(confirmLabel);
        confirmLabel.setBounds(10, 86, 100, 14);


        getContentPane().add(confirmField);
        confirmField.setBounds(110, 83, 111, 20);
        
        getContentPane().add(passwordField);
        passwordField.setBounds(110, 57, 111, 20);

        getContentPane().add(oldPasswordField);
        oldPasswordField.setBounds(110, 31, 111, 20);

        changeBtn.setText("Change");
        getContentPane().add(changeBtn);
        changeBtn.setBounds(10, 109, 94, 23);
        changeBtn.addActionListener(this);
        
        cancelBtn.setText("Cancel");
        getContentPane().add(cancelBtn);
        cancelBtn.setBounds(110, 109, 111, 23);
        cancelBtn.addActionListener(this);
        
        setSize(231,165);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        
        //display
        setVisible(true);
    }
    
    /**
     * Defines action when ActionEvent on controls 
     * at this frame fires
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void actionPerformed(ActionEvent e)
    {
         if (e.getSource () == changeBtn)
         {
             if (Password.checkPassword(Static.charArrayToString(oldPasswordField.getPassword())))
             {
                 if (Static.charArrayToString(passwordField.getPassword()).equals (
                            Static.charArrayToString(confirmField.getPassword())))
                 {
                     Password.setPassword(Static.charArrayToString(passwordField.getPassword()));
                     Dialog.info ("New password was successfully saved.");
                     dispose();
                 } else
                 {
                    Dialog.error ("New passwords are not identical.");    
                    oldPasswordField.setText("");
                    passwordField.setText("");
                    confirmField.setText("");                    
                 }
             } else
             {
                Dialog.error ("The old password inserted is not valid.");    
                oldPasswordField.setText("");
                passwordField.setText("");
                confirmField.setText("");                                    
             }
         } else if (e.getSource () == cancelBtn)
         {
             dispose();
         }
    }
}
