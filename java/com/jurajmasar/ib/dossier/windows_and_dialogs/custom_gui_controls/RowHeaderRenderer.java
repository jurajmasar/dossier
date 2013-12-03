package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

/**
 * Enhances the TableCellRenderer to make the left header in JTable possible.
 *  
 *  Originally from http://www.esus.com/docs/GetQuestionPage.jsp?uid=1276
 *  
 * @author Juraj Masar
 * @version 0.1
 */
public class RowHeaderRenderer extends JLabel implements TableCellRenderer
{  
    /**
     * Sets the properties of the TableCellRenderer to wished
     * 
     * @param table JTable
     */
    public RowHeaderRenderer(JTable table)
    {
      JTableHeader header = table.getTableHeader();
      setOpaque(true);
      setBorder(table.getTableHeader().getBorder());
      setHorizontalAlignment(LEFT);
      setForeground(header.getForeground());
      setBackground(header.getBackground());
      setFont(header.getFont());
      setToolTipText(getText());
    }  
    /**
     * Renders cells
     * 
     * @param table JTable instance
     * @param value    Object instance
     * @param isSelected    boolean
     * @param hasFocus  boolean
     * @param row
     * @param column
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) 
    {
      setText((value == null) ? "" : value.toString());
      return this;
    }
}
