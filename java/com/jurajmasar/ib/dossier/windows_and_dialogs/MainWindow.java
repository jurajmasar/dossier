package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.NonEditableDefaultTableModel;
import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;


/**
 * MainWindows of the application.
 * Customized JFrame.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class MainWindow extends JFrame implements ActionListener
{
    final String[] searchResultsTableHeader;

    //definitions of controls
    private JButton closeSearchBtn;    
    private JButton aboutBtn;
    private JButton addDistributorBtn;
    private JButton changePasswordBtn;
    private JButton exitBtn;
    private JButton managePointsBtn;
    private JButton myProfileBtn;
    private JTextField nameField;
    private JLabel nameLabel;
    private JTextField regNumField;
    private JLabel regNumLabel;
    private JSeparator resultsSeparator;
    private JScrollPane scrollPane;
    public JButton searchBtn;
    private JPanel searchPanel;
    private JLabel searchResultsLabel;
    private JTable searchResultsTable;
    private JTextField surnameField;
    private JLabel surnameLabel;
    private JLabel welcomeLabel;
    private JLabel countLabel;    
    
    /**
     * Designs and creates the window.
     */
    public MainWindow() 
    {
        
        //gui
        welcomeLabel = new JLabel();
        countLabel = new JLabel();
        myProfileBtn = new JButton();
        addDistributorBtn = new JButton();
        managePointsBtn = new JButton();
        exitBtn = new JButton();
        changePasswordBtn = new JButton();
        searchPanel = new JPanel();
        nameField = new JTextField();
        nameLabel = new JLabel();
        surnameField = new JTextField();
        surnameLabel = new JLabel();
        regNumField = new JTextField();
        regNumLabel = new JLabel();
        searchBtn = new JButton();
        resultsSeparator = new JSeparator();
        searchResultsLabel = new JLabel();
        scrollPane = new JScrollPane();
        searchResultsTable = new JTable();
        aboutBtn = new JButton();
        closeSearchBtn = new JButton();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        welcomeLabel.setText("Welcome to Multilevel Marketing Manager v1.0");
        getContentPane().add(welcomeLabel);
        welcomeLabel.setBounds(10, 11, 223, 14);

        updateCounter();
        getContentPane().add(countLabel);
        countLabel.setBounds(10, 120, 250, 14);        
        
        myProfileBtn.setText("My Profile");
        getContentPane().add(myProfileBtn);
        myProfileBtn.setBounds(10, 31, 117, 23);
        myProfileBtn.addActionListener(this);
        
        addDistributorBtn.setText("Add distributor");
        getContentPane().add(addDistributorBtn);
        addDistributorBtn.setBounds(10, 60, 117, 23);
        addDistributorBtn.addActionListener(this);
        
        managePointsBtn.setText("Manage points");
        getContentPane().add(managePointsBtn);
        managePointsBtn.setBounds(10, 89, 117, 23);
        managePointsBtn.addActionListener(this);
        
        exitBtn.setText("Exit");
        getContentPane().add(exitBtn);
        exitBtn.setBounds(133, 90, 120, 23);
        exitBtn.addActionListener(this);
        
        changePasswordBtn.setText("Change password");
        getContentPane().add(changePasswordBtn);
        changePasswordBtn.setBounds(133, 31, 120, 23);
        changePasswordBtn.addActionListener(this);
        
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search for distributor"));
        searchPanel.setLayout(null);
        searchPanel.add(nameField);

        nameField.setBounds(79, 20, 143, 20);
        nameLabel.setText("Name:");
        searchPanel.add(nameLabel);
        nameField.addActionListener(this);
        
        nameLabel.setBounds(16, 23, 31, 14);
        searchPanel.add(surnameField);
        surnameField.setBounds(79, 46, 143, 20);
        surnameField.addActionListener(this);
        
        surnameLabel.setText("Surname:");
        searchPanel.add(surnameLabel);
        surnameLabel.setBounds(16, 49, 46, 14);       
        searchPanel.add(regNumField);
        regNumField.setBounds(79, 72, 143, 20);
        regNumField.addActionListener(this);
        
        regNumLabel.setText("Reg. num:");
        searchPanel.add(regNumLabel);
        regNumLabel.setBounds(16, 75, 50, 14);

        searchBtn.setText("Search");
        searchPanel.add(searchBtn);
        searchBtn.setBounds(16, 98, 206, 23);
        searchBtn.addActionListener(this);

        getContentPane().add(searchPanel);
        searchPanel.setBounds(278, 11, 238, 130);
        getContentPane().add(resultsSeparator);
        resultsSeparator.setBounds(10, 150, 500, 2);

        searchResultsLabel.setText("Search Results");
        getContentPane().add(searchResultsLabel);
        searchResultsLabel.setBounds(10, 160, 450, 14);

        closeSearchBtn.setText("close");
        getContentPane().add(closeSearchBtn);
        closeSearchBtn.setBounds(455, 155, 57, 23);        
        closeSearchBtn.addActionListener(this);
        
        searchResultsTableHeader = new String [] {
                "Reg. num.", "Name", "Surname", "E-mail","Country"
            };
        
        searchResultsTable.setModel(new NonEditableDefaultTableModel(
            new Object [][] {
                null
            }, searchResultsTableHeader
        ));
        scrollPane.setViewportView(searchResultsTable);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(10, 180, 500, 150);

        aboutBtn.setText("About");
        getContentPane().add(aboutBtn);
        aboutBtn.setBounds(133, 60, 120, 23);
        aboutBtn.addActionListener(this);
        
        
        setIconImage(Dialog.createImageIcon(Static.iconPath,"Manager").getImage());
        setResizable(false);        
        hideSearch(); //setSize
        setLocationRelativeTo(null); //center
        setTitle("Multilevel Marketing Manager");
        
        //display
        setVisible(true);
    }
    
    /**
     * Shows search results - changes the size of the window
     *
     */
    public void showSearch ()
    {
        setSize(530, 370);
    }

    /**
     * Hides search results - changes the size of the window
     *
     */
    public void hideSearch ()
    {
        setSize(530, 175);
    }    
    
    /**
     * Updates the number of distributors displayed in the window.
     *
     */
    public void updateCounter()
    {
        if (DistributorManager.getCount() == 0)
            countLabel.setText("There is currently no distributor in the system.");
        else if (DistributorManager.getCount() == 1)
            countLabel.setText("There is currently one distributor in the system.");
        else
            countLabel.setText("There are currently "+DistributorManager.getCount()
                      +" distributors in the system.");
    }

    /**
     * Defines action when ActionEvent on controls 
     * at this frame fires
     *
     * @param  e   a sample parameter for a method
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource () == searchBtn || 
            e.getSource () == nameField || 
            e.getSource () == surnameField || 
            e.getSource () == regNumField             
        )
        {
            if (regNumField.getText().equals("") &&
                nameField.getText().equals("") &&
                surnameField.getText().equals(""))
                {
                    Dialog.warning ("Please insert what to search for.");
                    return;
                }
            Distributor[] distributors = DistributorManager.find(regNumField.getText(),
                                        nameField.getText(), surnameField.getText());
            final Object[][] objects = new Object[distributors.length][5];
            
            for (int i=0;i<distributors.length;i++)
            {
                objects[i][0] = distributors[i].getRegNum();
                objects[i][1] = distributors[i].getName();
                objects[i][2] = distributors[i].getSurname();
                objects[i][3] = distributors[i].getEmail();
                objects[i][4] = distributors[i].getCountry();                
                             
            }

            //insert new data
            searchResultsTable.setModel(
              new NonEditableDefaultTableModel(objects, searchResultsTableHeader)
            );                      

            //disable dragndrop
            searchResultsTable.setDragEnabled(false);
            searchResultsTable.getTableHeader().setReorderingAllowed(false);

            //set email column width
            searchResultsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
            //set email column width
            searchResultsTable.getColumnModel().getColumn(3).setPreferredWidth(100);           
            //set country column width
            searchResultsTable.getColumnModel().getColumn(4).setPreferredWidth(25);           
            
            searchResultsLabel.setText("Search Results for "
                                        +nameField.getText()+"/"
                                        +surnameField.getText()+"/"
                                        +regNumField.getText()
            );

            searchResultsTable.addMouseListener(new MouseAdapter() 
            {
               public void mouseClicked(MouseEvent e) 
               {
                  if (e.getClickCount() == 2) 
                  {
                     JTable target = (JTable)e.getSource();
                     int row = target.getSelectedRow();
                     
                     String regNum = (String)searchResultsTable.getValueAt(row, 0);

                     if (!Dialog.displayProfile(regNum))                     
                       Dialog.error ("Such distributor does not exist anymore!");
                      
                  }
               }
            });            
                       
            
            //enlarge form
            showSearch();            
        } else if (e.getSource () == closeSearchBtn)
        {
            hideSearch();
        } else if (e.getSource () == aboutBtn)
        {
            Dialog.info ("Multilevel Marketing Manager 1.0"
             +" is an application for managing human resources.\nIt has been originally "
             + "created for the MLM system Network World Alliance."
             +" \n\n Author: Juraj Masar (2011) mail@jurajmasar.com",
             "Multilevel Marketing Manager - About");
        } else if (e.getSource () == exitBtn)
        {
            dispose();
        } else if (e.getSource () == changePasswordBtn)
        {
            new ChangePasswordDialog (this, "Change Password", true);
        } else if (e.getSource () == addDistributorBtn)
        {
            new AddDistributorDialog (this, "Add Distributor", true);
        } else if (e.getSource() == myProfileBtn)
        {
            myProfileBtn.setEnabled(false);       
            Distributor d = DistributorManager.getDistributorBySponsor("0");
            if (d != null)
              Dialog.displayProfile(d.getRegNum());
            else 
              //data is inconcistent 
              Dialog.error("Error: Data of the application is inconsistent.\n"
                            +"The root distributor cannot be found.\n"
                            +"Please, reinstall the application."
              );
            myProfileBtn.setEnabled(true);
        } else if (e.getSource() == managePointsBtn)
        {
            String m = Dialog.askForMonth();
            if (m == null) return;
            
            new DistributorTreeDialog (m);
            
            
        }
    }
}
