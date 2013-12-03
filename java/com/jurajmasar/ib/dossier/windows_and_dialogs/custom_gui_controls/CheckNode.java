package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Customizes version of DefaultMutableTreeNode
 * for Checkbox implementation to JTree.
 *
 * Inspired by: 
 * http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html 
 *
 * @author Juraj Masar 
 * @version 0.1
 */
public class CheckNode extends DefaultMutableTreeNode
{
  public final static int SINGLE_SELECTION = 0;

  public final static int DIG_IN_SELECTION = 4;

  protected int selectionMode;

  public boolean isSelected;

  /**
   * Constructor of the object.
   * Sets the userObject to null
   */
  public CheckNode() 
  {
    this(null);
  }

  /**
   * Constructor of the object.
   * Sets the userObject to value given
   * 
   * @param userObject Object
   */  
  public CheckNode(Object userObject) 
  {
    this(userObject, true, false);
  }

  /**
   * Constructor of the object.
   * Sets the object variables to values given.
   * 
   * @param userObject Object
   * @param allowsChildren boolean
   * @param isSelected boolean
   */  
  public CheckNode(
    Object userObject,
    boolean allowsChildren,
    boolean isSelected
  ) {
    super(userObject, allowsChildren);
    this.isSelected = isSelected;
    setSelectionMode(DIG_IN_SELECTION);
  }

  /**
   * Sets selection mode of the node to value given.
   *
   * @param mode selection mode
   */
  public void setSelectionMode(int mode) 
  {
    selectionMode = mode;
  }

  /**
   * Returns selection mode of the node.
   * 
   * @return selectionMode
   */
  public int getSelectionMode() 
  {
    return selectionMode;
  }

  /**
   * Sets the node isSelected value to value given.
   * 
   * @param isSelected whether the object is selected or not
   */
  public void setSelected(boolean isSelected) 
  {
    this.isSelected = isSelected;
  }

  /**
   * Returns the isSelected value of the object.
   * 
   * @return isSelected boolean
   */
  public boolean isSelected() 
  {
    return isSelected;
  }
}
