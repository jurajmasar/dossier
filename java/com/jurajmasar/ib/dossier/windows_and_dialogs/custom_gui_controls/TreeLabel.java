package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.Icon;

/**
 * TreeLabel is an enhanced version of JLabel meant to be used
 * in JTree with implemented checkboxes.
 * 
 * Inspired by: 
 * http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
 * 
 * @author Juraj Masar
 * @version 0.1
 */
class TreeLabel extends JLabel 
 {
    boolean isSelected;

    boolean hasFocus;

    /**
     * Constructor of the class. 
     * It sets the local variables to default values.
     */
    public TreeLabel() 
    {
        isSelected = false;
        hasFocus = false;
    }

    /**
     * Sets the background color to Color given
     * in parameters.
     * 
     * @param Color Color object
     */
    public void setBackground(Color color) 
    {
      if (color instanceof ColorUIResource)
        color = null;
      super.setBackground(color);
    }

    /**
     * Paints the object to canvas given in parameters
     * 
     * @param g Graphics object
     */
    public void paint(Graphics g) 
    {
      String str;
      if ((str = getText()) != null) 
      {
        if (0 < str.length()) 
        {
          if (isSelected) 
          {
            g.setColor(UIManager
                .getColor("Tree.selectionBackground"));
          } else 
            g.setColor(UIManager.getColor("Tree.textBackground"));

          Dimension d = getPreferredSize();
          int imageOffset = 0;
          Icon currentI = getIcon();
          if (currentI != null) 
            imageOffset = currentI.getIconWidth()
                + Math.max(0, getIconTextGap() - 1);

          g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
              d.height);
          if (hasFocus) 
          {
            g.setColor(UIManager
                .getColor("Tree.selectionBorderColor"));
            g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                d.height - 1);
          }
        }
      }
      super.paint(g);
    }

    /**
     * Generates preferred size for the object according
     * to typical JLabel preferred size.
     * 
     * @return Dimension object
     */
    public Dimension getPreferredSize() 
    {
      Dimension retDimension = super.getPreferredSize();
      if (retDimension != null) 
        retDimension = new Dimension(retDimension.width + 3,
            retDimension.height);
      return retDimension;
    }

    
    /**
     * Sets the object selected value to value
     * given in parameters.
     * 
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected) 
    {
      this.isSelected = isSelected;
    }

    /**
     * Sets the object hasFocus value to value
     * given in parameters.
     * 
     * @param hasFocus boolean
     */    
    public void setFocus(boolean hasFocus) 
    {
      this.hasFocus = hasFocus;
    }
}
