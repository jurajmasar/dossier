package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;

/**
 * NodeSelectionListener is an enhanced version of MouseAdapter meant to be used
 * in JTree with implemented checkboxes.
 * 
 * Inspired by: 
 * http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class NodeSelectionListener extends MouseAdapter
{
    JTree tree;
    /**
     * Constructor - sets the pointer to JTree
     * to JTree given in parameters.
     * 
     * @param tree JTree instance
     */
    public NodeSelectionListener(JTree tree)
    {
      this.tree = tree;
    }
    
    /**
     * Catches the MouseEvent object and produces 
     * appropriate action.
     * 
     * @param e MouseEvent instance
     */
    public void mouseClicked(MouseEvent e) 
    {
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      TreePath  path = tree.getPathForRow(row);
      //TreePath  path = tree.getSelectionPath();
      if (path != null) 
      {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = ! (node.isSelected());
        node.setSelected(isSelected);
        if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION && isSelected) 
            tree.expandPath(path);
            
        ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
        // I need revalidate if node is root.  but why?
        if (row == 0) 
        {
          tree.revalidate();
          tree.repaint();
        }
      }
    }
}