package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.data_structures.Vector;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.CheckNode;
import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.CheckRenderer;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.NodeSelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.*;
import java.util.Enumeration;

/**
 * Displays JDialog filled with JTree full of distributors.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class DistributorTreeDialog extends JDialog implements ActionListener
{
    String m; //String of given month
    
    //definitions of controls
    private JLabel label;
    private JScrollPane scrollPane;
    private JTree tree;    
    private JButton continueBtn;
    private JButton cancelBtn;    
    private CheckNode top;
    
    /**
     * Constructor for objects of class DistributorTreeDialog.
     * Designs and creates the window.
     * 
     * @param m month in String
     */
    public DistributorTreeDialog (String m)
    {
        super (Dialog.mainWindow,"Select distributors", true);
       
        this.m = m;
        
        scrollPane = new JScrollPane();        
        label = new JLabel();
        continueBtn = new JButton();
        cancelBtn = new JButton();        

        
        Thread worker = new Thread() 
        {
            public void run() 
            {
                DistributorManager.init();
                Distributor d = DistributorManager.getDistributorBySponsor("0");
                top = new CheckNode();
                
                d.getTree().setJTreeNode(top, true);
                                           
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() 
                    {
                       tree = new JTree(top);
                       tree.setCellRenderer(new CheckRenderer());
                        
                       tree.getSelectionModel().setSelectionMode
                           (TreeSelectionModel.SINGLE_TREE_SELECTION);
                       tree.addMouseListener(new NodeSelectionListener(tree));
                       scrollPane.setViewportView(tree);
                    }
                });
            }
        };
        worker.start(); // So we don't hold up the dispatch thread.                 

        getContentPane().add(scrollPane);
        scrollPane.setBounds(10, 33, 400, 365);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);


        label.setText("Select users for which you want to calculate provision for month "+m+".");
        getContentPane().add(label);
        label.setBounds(10, 11, 380, 14);

        continueBtn.setText("Continue...");
        getContentPane().add(continueBtn);
        continueBtn.setBounds(10, 400, 200, 23);
        continueBtn.addActionListener(this);        

        cancelBtn.setText("Cancel");
        getContentPane().add(cancelBtn);
        cancelBtn.setBounds(210, 400, 200, 23);
        cancelBtn.addActionListener(this);        
        
        
        setSize(425,460);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        
        //display
        setVisible(true);                
    }
    
    /**
     * Handles ActionEvents from components form
     * DistributorTreeDialog
     *
     * @param  e   ActionEvent object
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == continueBtn)
        {
            Enumeration en = top.breadthFirstEnumeration();
            Vector<String> regNums = new Vector<String>();
            while (en.hasMoreElements()) 
            {
                CheckNode node = (CheckNode) en.nextElement();
                if (node.isSelected()) 
                {
                  TreeNode[] nodes = node.getPath();
                  String s = nodes[nodes.length-1].toString();
                  regNums.pushBack(s.substring(0,s.indexOf('(')-1));
                }
            }
            dispose();
            
            //start next dialog
            new PointsDialog (m, regNums);
        } else if (e.getSource() == cancelBtn)
        {
            dispose();
        } 
    }

}
