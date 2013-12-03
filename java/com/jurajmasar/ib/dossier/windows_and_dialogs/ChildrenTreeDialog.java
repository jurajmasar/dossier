package com.jurajmasar.ib.dossier.windows_and_dialogs;

import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
/**
 * Displays JDialog filled with JTree full of children distributors.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class ChildrenTreeDialog extends JDialog implements TreeSelectionListener
{
    Distributor d;
    //definitions of controls
    private JLabel label;
    private JScrollPane scrollPane;
    private JTree tree;    

    
    /**
     * Constructor for objects of class ChildrenTreeDialog.
     * Designs and creates the window.
     */
    public ChildrenTreeDialog (final Distributor d, ProfileWindow profile)
    {
        super (profile,"Children distributors in tree", true);
        
        this.d = d;
        
        scrollPane = new JScrollPane();        
        label = new JLabel();
        tree = new JTree();
        
        Thread worker = new Thread() 
        {
            public void run() 
            {
                final DefaultMutableTreeNode top = new DefaultMutableTreeNode();
                d.getTree().setJTreeNode(top, true);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() 
                    {
                        tree.setModel(new DefaultTreeModel(top));
                        //tree.setRootVisible(false);
                    }
                });
            }
        };

        tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);

            
        //set icons
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();      
        renderer.setLeafIcon(Dialog.createImageIcon(Static.profileIconSmallPath, "Manager"));
        renderer.setOpenIcon(Dialog.createImageIcon(Static.profileIconSmallPath,"Manager"));
        renderer.setClosedIcon(Dialog.createImageIcon(Static.profileIconSmallPath,"Manager"));
        //renderer.setLeafIcon(null);
        //renderer.setOpenIcon(null);
        //renderer.setClosedIcon(null);

        
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);       
        
        scrollPane.setViewportView(tree);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(10, 33, 235, 365);
        
        worker.start(); // So we don't hold up the dispatch thread.                 
          
         
         
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);


        label.setText("Click on the distributor to open thier profile.");
        getContentPane().add(label);
        label.setBounds(10, 11, 225, 14);

        
        setSize(260,440);
        setResizable(false);        
        setLocationRelativeTo(Dialog.mainWindow); //center
        
        //display
        setVisible(true);                
    }
    
    /**
     * Defines action when TreeSelectionEvent on JTree fires
     *
     * @param  e TreeSelectionEvent
     */
    public void valueChanged(TreeSelectionEvent e)
    {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               tree.getLastSelectedPathComponent();
        
        if (node == null)
        //Nothing is selected.	
        return;
    
        Object nodeInfo = node.getUserObject();
        dispose();
        Dialog.displayProfile(
            ((String)nodeInfo).substring(0, ((String)nodeInfo).indexOf(" "))
        );
       
    }    
}
