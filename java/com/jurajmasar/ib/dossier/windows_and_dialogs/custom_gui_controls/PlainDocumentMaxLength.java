package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.Toolkit;
import javax.swing.text.PlainDocument;

/**
 * JTextFieldMaxLength enhances JTextField and
 * adds maxLength functionality.
 * 
 * Originally from:
 * http://www.java2s.com/Code/Java/Swing-JFC/JTextFieldMaxLength.htm
 * 
 * @author Juraj Masar
 * @version 0.1
 */

class PlainDocumentMaxLength extends PlainDocument
{
  private int maxLength; //holds the maximum length of content

  /**
   * Constructor of the class, it sets the object variable
   * maxLength to the value given in parameters.
   * 
   * @param maxLength maximum length of the content
   */
  public PlainDocumentMaxLength(int maxLength) 
  {
    this.maxLength = maxLength;
  }

  /**
   * Inserts String to the content - it checks
   * whether the total length of the content
   * does not exceed the maximum length set.
   * If it does, it produces beeping.
   * 
   * @param offset offset of insertion
   * @param str String to insert
   * @param a AttributeSet object
   */
  public void insertString (
    int offset,
    String str,
    AttributeSet a
  ) throws BadLocationException 
  { 
    if (getLength() + str.length() > maxLength) 
        Toolkit.getDefaultToolkit().beep();
    else 
        super.insertString(offset,str,a);    
  }
}