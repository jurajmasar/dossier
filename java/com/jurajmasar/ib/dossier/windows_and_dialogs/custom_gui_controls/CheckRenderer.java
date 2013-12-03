package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import java.awt.Color;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import java.awt.Dimension;
import javax.swing.tree.TreeCellRenderer;

/**
 * CheckRenderer is an enhanced version of TableCellRenderer meant to be used
 * in JTree with implemented checkboxes.
 * 
 * Inspired by: 
 * http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class CheckRenderer extends JPanel implements TreeCellRenderer
{
  protected JCheckBox check;

  protected TreeLabel label;

  /**
   * Constructor of the object. Sets the object variables
   * to default values.
   */
  public CheckRenderer() 
  {
    setLayout(null);
    add(check = new JCheckBox());
    add(label = new TreeLabel());
    check.setBackground(UIManager.getColor("Tree.textBackground"));
    label.setForeground(UIManager.getColor("Tree.textForeground"));
  }

  /**
   * Returns the instance of the CheckRender class with
   * object variables set to values given in parameters.
   * 
   * @param tree        JTree object
   * @param value       Object instance
   * @param isSelected  whether the option is selected or not
   * @param expanded    whether the path to option is expanded or not
   * @param leaf        wether the option is leaf or not
   * @param row         the row in JTree object
   * @param hasFocus    whether the option is focused or not
   * @return            Component object
   */
  public Component getTreeCellRendererComponent(
    JTree tree, 
    Object value,
    boolean isSelected,
    boolean expanded,
    boolean leaf, 
    int row,
    boolean hasFocus
  ) {
    String stringValue = tree.convertValueToText(value, isSelected,
        expanded, leaf, row, hasFocus);
    setEnabled(tree.isEnabled());
    check.setSelected(((CheckNode) value).isSelected());
    label.setFont(tree.getFont());
    label.setText(stringValue);
    label.setSelected(isSelected);
    label.setFocus(hasFocus);
    
    //set icon
    label.setIcon(Dialog.createImageIcon(Static.profileIconSmallPath, "Manager"));

    return this;
  }

  /**
   * Generates preferred size of the whole component.
   * 
   * @return Dimension object
   */
  public Dimension getPreferredSize() 
  {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = label.getPreferredSize();
    return new Dimension(d_check.width + d_label.width,
        (d_check.height < d_label.height ? d_label.height
            : d_check.height));
  }

  /**
   * Sets the locations of components of which this 
   * Component consists.
   */
  public void doLayout() 
  {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = label.getPreferredSize();
    int y_check = 0;
    int y_label = 0;
    if (d_check.height < d_label.height) 
      y_check = (d_label.height - d_check.height) / 2;
    else 
      y_label = (d_check.height - d_label.height) / 2;
    
    check.setLocation(0, y_check-2);
    check.setBounds(0, y_check-2, d_check.width, d_check.height);
    label.setLocation(d_check.width, y_label);
    label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
  }

  /**
   * Sets the background of the component to 
   * Color given in parameters
   * 
   * @param color object
   */
  public void setBackground(Color color) 
  {
    if (color instanceof ColorUIResource)
      color = null;
    super.setBackground(color);
  }
}

