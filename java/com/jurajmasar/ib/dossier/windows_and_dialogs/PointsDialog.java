package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.data_structures.Month;
import com.jurajmasar.ib.dossier.data_structures.Vector;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.NonEditableDefaultTableModel;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.RowHeaderRenderer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.*;

/**
 * Customized version of JDialog.
 * Displays form implemented using JTable for inserting Distributors' points
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class PointsDialog extends JDialog implements ActionListener
{
    Distributor[] distributors;
    String monthFormatted;
    
    //definitions of components
    private JButton cancelBtn;
    private JButton finishBtn;
    private JLabel label;
    private JScrollPane scrollPane;
    private JTable table;
    
    /**
     * Constructor for objects of class PointsDialog
     * It designes the form.
     * 
     * @param m month in String
     * @param regNums Vector of strings with registration numbers
     */
    public PointsDialog(String m, final Vector<String> regNums)
    {
        super (Dialog.mainWindow, "Insert Distributors' points", true);
               
        monthFormatted = m.substring(3,7)+m.substring(0,2);
              
        // initialise instance variables
        label = new JLabel();
        scrollPane = new JScrollPane();
        table = new JTable();
        finishBtn = new JButton();
        cancelBtn = new JButton();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        label.setText("Calculation of provisions of selected users for month "+m
                      +" requires providing the following information:");
        getContentPane().add(label);
        label.setBounds(10, 11, 535, 14);
              
        Thread worker = new Thread() 
        {
            public void run() 
            {
                DistributorManager.init();
                distributors = 
                  DistributorManager.getDistributorsWithDirectChildrenByRegNums(regNums);
                if (distributors.length == 0)  
                {
                    dispose();
                    Dialog.warning("You have to choose some distributors. Please, try again.");
                }
                final String[][] content = new String[distributors.length][5];

                for (int i=0;i<distributors.length;i++)
                {
                     Month month = distributors[i].months.getMonth(monthFormatted);
                     if (month == null) 
                     {
                         month = new Month(distributors[i]);
                         month.setPw("0");
                         month.setGroupPw("0");
                         month.setGw("0");
                         month.setGroupGw("0");
                     }
                     content[i] = new String[] {distributors[i].getFullname()+" ("
                        +distributors[i].getRegNum()+")",
                        month.getPw(),
                        month.getGroupPw(),
                        month.getGw(),
                        month.getGroupGw()
                     };
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() 
                    {
                        String[] headerTitles =  new String [] {
                                "Distributor", "PW", "Group PW", "GW", "Group GW"
                            };
                        table.setModel(
                            new NonEditableDefaultTableModel(content, headerTitles)
                            {
                                public boolean isCellEditable(int row, int column) 
                                {
                                    //enable editing for pw, group pw, gw and group gw columns
                                    if (column >= 1) return true;
                                    else return false;
                                }
                                //checks if the inserted value to table is numeric                                
                                public void setValueAt(Object value, int row, int col) 
                                {
                                    if (Static.isNumeric((String) value))
                                    {
                                        super.setValueAt(value,row,col);
                                    } else
                                    {
                                        Dialog.error ("The value inserted has to be numeric!");
                                    }
                                }                                  
                            }
                        );                            
                    
                        //disable left column
                        table.getColumn("Distributor").setCellRenderer(
                            new RowHeaderRenderer(table)
                        );
                
                        //set widths for columns        
                        table.getColumnModel().getColumn(0).setPreferredWidth(200);
                        
                        //disable dragndrop
                        table.setDragEnabled(false);
                        table.getTableHeader().setReorderingAllowed(false);
                            
                    }
                });
            }
        };
        worker.start(); // So we don't hold up the dispatch thread.        
        
        scrollPane.setViewportView(table);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(10, 31, 535, 361);

        finishBtn.setText("Finish");
        getContentPane().add(finishBtn);
        finishBtn.setBounds(10, 398, 261, 23);
        finishBtn.addActionListener(this);
        
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(this);
        getContentPane().add(cancelBtn);
        cancelBtn.setBounds(277, 398, 268, 23);        
        
                
        setSize(560,460);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        
        //display
        setVisible(true);        
    }

    /**
     * Listens to ActionEvents within this JDialog.
     *
     * @param  e   ActionEvent object
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cancelBtn)
        {
            dispose();
        } else if (e.getSource() == finishBtn) 
        {
            for (int i=0;i<distributors.length;i++)
            {
                Month month = distributors[i].months.getMonth(monthFormatted);
                if (month == null)
                {
                    month = new Month(distributors[i]);
                    month.setYear(monthFormatted.substring(0,4));
                    month.setMonth(monthFormatted.substring(4,6));
                }
                String pw = (String)table.getValueAt(i,1);
                String groupPw = (String)table.getValueAt(i,2);
                String gw = (String)table.getValueAt(i,3);
                String groupGw = (String)table.getValueAt(i,4);
                
                if (pw == null || pw == "") pw = "0";
                if (groupPw == null || groupPw == "") groupPw = "0";
                if (gw == null || groupGw == "") gw = "0";
                if (groupGw == null || groupGw == "") groupGw = "0";                
                
                if (!Static.isNumeric(pw)) pw = "0";
                if (!Static.isNumeric(groupPw)) groupPw = "0";
                if (!Static.isNumeric(gw)) gw = "0";
                if (!Static.isNumeric(groupGw)) groupGw = "0";                
                
                
                month.setPw(pw);
                month.setGroupPw(groupPw);
                month.setGw(gw);
                month.setGroupGw(groupGw);                
                month.save();
            }
            dispose();
            Dialog.info("Data have been successfully inserted to database.");
        }
    }

}
