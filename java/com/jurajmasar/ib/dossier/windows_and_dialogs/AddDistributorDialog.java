package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.JTextFieldMaxLength;
import com.jurajmasar.ib.dossier.data_structures.ItemRegNum;
import com.jurajmasar.ib.dossier.data_structures.Vector;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;

/**
 * Add distributor windows of the application.
 * Customized JFrame.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class AddDistributorDialog extends JDialog implements ActionListener
{
    private Border defaultBorder;

    //definitions of controls
    private JScrollPane scrollPane;
    private JButton addBtn;
    private JButton cancelBtn;
    private JLabel birthDateLabel;
    private JLabel countryLabel;
    private JLabel emailLabel;
    private JLabel headlineLabel;
    private JLabel nameLabel;
    private JLabel noteLabel;
    private JLabel regNumLabel;
    private JLabel registrationDateLabel;
    private JLabel sponsorLabel;
    private JLabel surnameLabel;
    private JLabel telephoneLabel;   
    private JLabel statusLabel;    
    private JTextFieldMaxLength surnameField;
    private JTextFieldMaxLength telephoneField;
    private JTextFieldMaxLength regNumField;    
    private JTextFieldMaxLength nameField;    
    private JTextFieldMaxLength countryField;
    private JTextFieldMaxLength emailField;
    private JFormattedTextField registrationDateField;
    private JFormattedTextField birthDateField;
    private JComboBox<String> sponsorBox;
    private JTextPane noteField;
    private JCheckBox consumerCheckbox;
    private JCheckBox salesmanCheckbox;
    private JCheckBox managerCheckbox;    

    /**
     * Designs and creates the window.
     */
    public AddDistributorDialog(JFrame frame, String title, boolean modal) 
    {
        super (frame, title, modal);
        statusLabel = new JLabel();
        headlineLabel = new JLabel();
        nameLabel = new JLabel();
        surnameLabel = new JLabel();
        regNumLabel = new JLabel();
        sponsorLabel = new JLabel();
        countryLabel = new JLabel();
        registrationDateLabel = new JLabel();
        birthDateLabel = new JLabel();
        emailLabel = new JLabel();
        telephoneLabel = new JLabel();
        noteLabel = new JLabel();
        registrationDateField = new JFormattedTextField();
        sponsorBox = new JComboBox<String>();
        scrollPane = new JScrollPane();
        noteField = new JTextPane();
        addBtn = new JButton();
        birthDateField = new JFormattedTextField();
        cancelBtn = new JButton();
        nameField = new JTextFieldMaxLength(20);
        surnameField = new JTextFieldMaxLength(20);
        emailField = new JTextFieldMaxLength(50);
        telephoneField = new JTextFieldMaxLength(20);
        regNumField = new JTextFieldMaxLength(10);
        countryField = new JTextFieldMaxLength(3);
        consumerCheckbox = new JCheckBox();
        salesmanCheckbox = new JCheckBox();
        managerCheckbox = new JCheckBox();

        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        headlineLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        headlineLabel.setText("Add new distributor");
        getContentPane().add(headlineLabel);
        headlineLabel.setBounds(10, 11, 650, 22);

        nameLabel.setText("* Name:");
        getContentPane().add(nameLabel);
        nameLabel.setBounds(10, 51, 55, 14);

        surnameLabel.setText("* Surname:");
        getContentPane().add(surnameLabel);
        surnameLabel.setBounds(10, 77, 55, 14);

        regNumLabel.setText("* Registration number:");
        getContentPane().add(regNumLabel);
        regNumLabel.setBounds(326, 48, 110, 14);

        sponsorLabel.setText("* Sponsor:");
        getContentPane().add(sponsorLabel);
        sponsorLabel.setBounds(326, 130, 55, 14);

        countryLabel.setText("* Country:");
        getContentPane().add(countryLabel);
        countryLabel.setBounds(326, 74, 55, 14);

        registrationDateLabel.setText("* Date of registration:");
        getContentPane().add(registrationDateLabel);
        registrationDateLabel.setBounds(326, 100, 110, 14);

        birthDateLabel.setText("* Date of birth:");
        getContentPane().add(birthDateLabel);
        birthDateLabel.setBounds(10, 103, 75, 14);

        emailLabel.setText("E-mail:");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(10, 129, 32, 14);

        telephoneLabel.setText("Telephone:");
        getContentPane().add(telephoneLabel);
        telephoneLabel.setBounds(10, 155, 54, 14);

        noteLabel.setText("Note:");
        getContentPane().add(noteLabel);
        noteLabel.setBounds(10, 178, 27, 14);

        getContentPane().add(nameField);
        nameField.setBounds(107, 45, 190, 20);
        
        getContentPane().add(surnameField);
        surnameField.setBounds(107, 71, 190, 20);
        
        getContentPane().add(emailField);
        emailField.setBounds(107, 123, 190, 20);
        
        getContentPane().add(telephoneField);
        telephoneField.setBounds(107, 149, 190, 20);
        
        getContentPane().add(regNumField);
        regNumField.setBounds(437, 45, 190, 20);
        
        getContentPane().add(countryField);
        countryField.setBounds(437, 71, 190, 20);

        getContentPane().add(registrationDateField);
        registrationDateField.setBounds(437, 97, 190, 20);
        registrationDateField.setFormatterFactory(
         new DefaultFormatterFactory(new DateFormatter(
            new java.text.SimpleDateFormat("dd.MM.yyyy"))));  
        registrationDateField.setText("01.01.2011");

        String[] sponsors = new String[DistributorManager.getCount()];
        ItemRegNum item = (ItemRegNum) DistributorManager.listRegNum.getFirst();
        for (int i=0;i<DistributorManager.getCount();i++)
        {
            sponsors[i] = item.getKey()+" ("+item.treeItem.name+" "+item.treeItem.surname+")";
            item = (ItemRegNum) item.getNext();
        }      
        sponsorBox.setModel(new DefaultComboBoxModel<String>(sponsors));

        getContentPane().add(sponsorBox);
        sponsorBox.setBounds(437, 128, 190, 20);

        scrollPane.setViewportView(noteField);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(107, 176, 190, 51);

        addBtn.setText("Add distributor");
        getContentPane().add(addBtn);
        addBtn.setBounds(326, 176, 301, 23);
        addBtn.addActionListener(this);
        
        getContentPane().add(birthDateField);
        birthDateField.setBounds(107, 97, 190, 20);
        birthDateField.setFormatterFactory(
         new DefaultFormatterFactory(new DateFormatter(
            new java.text.SimpleDateFormat("dd.MM.yyyy"))));  
        birthDateField.setText("01.01.2011");
            
        cancelBtn.setText("Cancel");
        getContentPane().add(cancelBtn);
        cancelBtn.setBounds(326, 205, 301, 23);
        cancelBtn.addActionListener(this);

        statusLabel.setText("Status:");
        getContentPane().add(statusLabel);
        statusLabel.setBounds(326, 152, 35, 14);
        
        consumerCheckbox.setText("Consumer");
        getContentPane().add(consumerCheckbox);
        consumerCheckbox.setBounds(414, 150, 73, 23);

        salesmanCheckbox.setText("Salesman");
        getContentPane().add(salesmanCheckbox);
        salesmanCheckbox.setBounds(487, 150, 71, 23);

        managerCheckbox.setText("Manager");
        getContentPane().add(managerCheckbox);
        managerCheckbox.setBounds(560, 150, 67, 23);        
        
        setSize(640,270);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        defaultBorder = nameField.getBorder();
        
        //display
        setVisible(true);
    }
    
    /**
     * Defines action when ActionEvent on controls 
     * at this frame fires
     *
     * @param  e ActionEvent object
     */
    public void actionPerformed(ActionEvent e)
    {
        
        if (e.getSource () == cancelBtn)
        {
            dispose();
        } else if (e.getSource () == addBtn)
        {
            //reset all colors
           nameLabel.setForeground(null);
           nameField.setBorder(defaultBorder);

           surnameLabel.setForeground(null);
           surnameField.setBorder(defaultBorder);

           regNumLabel.setForeground(null);
           regNumField.setBorder(defaultBorder);

           emailLabel.setForeground(null);
           emailField.setBorder(defaultBorder);

           countryLabel.setForeground(null);
           countryField.setBorder(defaultBorder);

           registrationDateLabel.setForeground(null);
           registrationDateField.setBorder(defaultBorder);
           
           
           //validation
           Vector<String> errorMsgs = new Vector<String>();
           MatteBorder border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red);
           
           //name
           String s = Static.validateString(nameField.getText(),1,true,"Name");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             nameLabel.setForeground(Color.red);
             nameField.setBorder(border);
           }

           //surname
           s = Static.validateString(surnameField.getText(),1,true,"Surname");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             surnameLabel.setForeground(Color.red);
             surnameField.setBorder(border);
           }
           
           //regNum
           s = Static.validateString(regNumField.getText(), 0, true, "Registration number");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             regNumLabel.setForeground(Color.red);
             regNumField.setBorder(border);
           }

           //email
           s = Static.validateString(emailField.getText(),2,false,"Email");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             emailLabel.setForeground(Color.red);
             emailField.setBorder(border);
           }           
                      
           //country
           s = Static.validateString(countryField.getText(),1,true,"Country");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             countryLabel.setForeground(Color.red);
             countryField.setBorder(border);
           }      
           
           //registrationDate
           s = Static.validateString(registrationDateField.getText(),-1,true,"Registration date");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             registrationDateLabel.setForeground(Color.red);
             registrationDateField.setBorder(border);
           } 
           
           String sponsor = (String) sponsorBox.getSelectedItem();
           sponsor = sponsor.substring(0, sponsor.indexOf(" "));
           
           String status = "";
           if (consumerCheckbox.isSelected()) status += "C";
           if (salesmanCheckbox.isSelected()) status += "S";
           if (managerCheckbox.isSelected()) status += "M";           
           //result
           if (errorMsgs.size() == 0)
           {
                //data is valid    
                if (DistributorManager.addDistributor(
                  regNumField.getText(),  
                  sponsor,  
                  countryField.getText(),  
                  registrationDateField.getText(),  
                  status,  
                  nameField.getText(),  
                  surnameField.getText(),  
                  birthDateField.getText(),  
                  emailField.getText(),  
                  telephoneField.getText(),  
                  noteField.getText()                  
                )) 
                { 
                    Dialog.info("Distributor has been successfully added to the database!");
                    dispose();
                }
                  else 
                  { 
                      //such regNum exists!
                     regNumLabel.setForeground(Color.red);
                     regNumField.setBorder(border);                      
                     Dialog.error("Distributor with identical registration number already\n"
                                  +"exists in the database. Please, choose different "
                                  +"registration number!");
                  }
           } else
           {
                s = "The form is invalid because of following "
                           +errorMsgs.size()+" reasons:\n";
                while (errorMsgs.size() != 0)         
                  s += "- "+errorMsgs.popFront()+"\n";

                Dialog.error (s);
           }
        }
    }
}
