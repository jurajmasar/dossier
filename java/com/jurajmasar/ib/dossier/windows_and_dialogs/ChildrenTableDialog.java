package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.NonEditableDefaultTableModel;
import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.root.Dialog;

import javax.swing.*;
import java.awt.event.*;

/**
 * Displays JDialog filled with JTable full of children distributors.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class ChildrenTableDialog extends JDialog
{
    Distributor d;
    //definitions of controls\
    private JLabel label;
    private JScrollPane scrollPane;
    private JTable table;    
    
    /**
     * Constructor for objects of class ChildrenTableDialog.
     * Designs and creates the window.
     */
    public ChildrenTableDialog (final Distributor d, ProfileWindow profile)
    {
        super (profile,"Children distributors of "+d.getFullname()+" in table", true);
        
        this.d = d;
        
        scrollPane = new JScrollPane();        
        label = new JLabel();
        table = new JTable();

        //disable dragndrop
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.addMouseListener(new MouseAdapter() 
        {
           public void mouseClicked(MouseEvent e) 
           {
              if (e.getClickCount() == 2) 
              {
                 JTable target = (JTable)e.getSource();
                 int row = target.getSelectedRow();
                 
                 String regNum = (String)table.getValueAt(row, 0);
                 dispose();
                 Dialog.displayProfile(regNum);
              }
           }
        });            
                               
        
        Thread worker = new Thread() 
        {
            public void run() 
            {
                Distributor[] distributors = d.getAllChildrenDistributors();
                final Object[][] objects = new Object[distributors.length][9];
                
                for (int i=0;i<distributors.length;i++)
                {
                    objects[i][0] = distributors[i].getRegNum();
                    objects[i][1] = distributors[i].getName();
                    objects[i][2] = distributors[i].getSurname();
                    objects[i][3] = distributors[i].getEmail();
                    objects[i][4] = distributors[i].getCountry();                
                    objects[i][5] = distributors[i].getStatus();                                                 
                    objects[i][6] = distributors[i].getRegistrationDate();                
                    objects[i][7] = distributors[i].getTelephone();                
                    objects[i][8] = distributors[i].getNote();
                }
              
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() 
                    {
                        //insert new data
                        String[] tableHeader = new String [] {
                                "Reg. num.", "Name", "Surname", "E-mail","Country",
                                "Status", "Reg. date", "Telephone","Note"
                            };                        
                        table.setModel(
                          new NonEditableDefaultTableModel(objects, tableHeader)
                        );                      
                
                        //set email column width
                        table.getColumnModel().getColumn(0).setPreferredWidth(25);
                        //set email column width
                        table.getColumnModel().getColumn(3).setPreferredWidth(100);           
                        //set country column width
                        table.getColumnModel().getColumn(4).setPreferredWidth(25); 
                        //set status column width
                        table.getColumnModel().getColumn(5).setPreferredWidth(25); 
                    }
                });
            }
        };
        worker.start(); // So we don't hold up the dispatch thread.                 
        
        scrollPane.setViewportView(table);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(10, 31, 837, 451);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);


        label.setText("Doubleclick on the distributor to open thier profile.");
        getContentPane().add(label);
        label.setBounds(10, 11, 240, 14);

        
        setSize(865,520);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        
        //display
        setVisible(true);                
    }    
}
