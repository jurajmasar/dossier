package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import javax.swing.JTextField;

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
public class JTextFieldMaxLength extends JTextField
{
  /**
   * Constructor of the class. It initializes object variables
   * to length given.
   * 
   * @param length  maximum length of the content
   */
  public JTextFieldMaxLength(int length)
  {
    this(null,length);
  }

  /**
   * Constructor of the class. It initializes the text
   * and length variables to values given in parameters.
   * 
   * @param text content
   * @param length maximum length of the content
   */
  public JTextFieldMaxLength(String text, int length)
  {
    super(new PlainDocumentMaxLength(length),text,length);
  }
}
