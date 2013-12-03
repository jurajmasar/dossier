package com.jurajmasar.ib.dossier.windows_and_dialogs;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.Color;

import com.jurajmasar.ib.dossier.data_structures.Vector;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.Chart;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.JTextFieldMaxLength;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.NonEditableDefaultTableModel;
import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.data_structures.ItemRegNum;
import com.jurajmasar.ib.dossier.data_structures.Month;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.RowHeaderRenderer;
import org.jfree.chart.ChartPanel;

/**
 * Profile window of a distributor - customized JFrame.
 * Displays info about particular distributor.
 *  
 * @author Juraj Masar
 * @version 0.1
 */
public class ProfileWindow extends JFrame implements ActionListener
{
    private Border defaultBorder;
    private Distributor d;
    private Month[] months;
    
    //definitions of controls
    private JFormattedTextField birthDateField;
    private JLabel birthDateLabel;
    private JLabel editableLabel;    
    private JButton childTableBtn;
    private JButton childTreeBtn;
    private JCheckBox consumerCheckbox;
    private JTextFieldMaxLength countryField;
    private JLabel countryLabel;
    private JButton deleteBtn;
    private JTextFieldMaxLength emailField;
    private JLabel emailLabel;
    private JScrollPane jScrollPane1;
    private JCheckBox managerCheckbox;
    private JTextFieldMaxLength nameField;
    private JLabel nameLabel;
    private JTextPane noteField;
    private JLabel noteLabel;
    private JFormattedTextField registrationDateField;
    private JLabel registrationDateLabel;
    private JCheckBox salesmanCheckbox;
    private JButton saveBtn;
    private JScrollPane scrollPane;
    private JSeparator separator;
    private JComboBox<String> sponsorBox;
    private JLabel sponsorLabel;
    private JLabel statisticsLabel;
    private JTable statsTable;
    private JLabel statusLabel;
    private JTextFieldMaxLength surnameField;
    private JLabel surnameLabel;
    private JTextFieldMaxLength telephoneField;
    private JLabel telephoneLabel;
    private JLabel titleLabel;
    private ChartPanel chartPanel;    
    
    /**
     * Designs and creates the window.
     * 
     * @param d Distributor object to display
     */
    public ProfileWindow(Distributor d) 
    {
        this.d = d;
        
        editableLabel = new JLabel();
        statsTable = new JTable();
        titleLabel = new JLabel();
        nameLabel = new JLabel();
        surnameLabel = new JLabel();
        sponsorLabel = new JLabel();
        countryLabel = new JLabel();
        registrationDateLabel = new JLabel();
        birthDateLabel = new JLabel();
        emailLabel = new JLabel();
        telephoneLabel = new JLabel();
        noteLabel = new JLabel();
        nameField = new JTextFieldMaxLength(20);
        surnameField = new JTextFieldMaxLength(20);
        emailField = new JTextFieldMaxLength(50);
        telephoneField = new JTextFieldMaxLength(20);
        countryField = new JTextFieldMaxLength(3);
        registrationDateField = new JFormattedTextField();
        sponsorBox = new JComboBox<String>();
        jScrollPane1 = new JScrollPane();
        noteField = new JTextPane();
        saveBtn = new JButton();
        deleteBtn = new JButton();
        separator = new JSeparator();
        statisticsLabel = new JLabel();
        scrollPane = new JScrollPane();
        birthDateField = new JFormattedTextField();
        childTreeBtn = new JButton();
        childTableBtn = new JButton();
        statusLabel = new JLabel();
        consumerCheckbox = new JCheckBox();
        salesmanCheckbox = new JCheckBox();
        managerCheckbox = new JCheckBox();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);                         
        
        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 18));        
        getContentPane().add(titleLabel);
        titleLabel.setBounds(10, 10, 650, 22);

        nameLabel.setText("* Name:");
        getContentPane().add(nameLabel);
        nameLabel.setBounds(20, 47, 45, 14);

        surnameLabel.setText("* Surname:");
        getContentPane().add(surnameLabel);
        surnameLabel.setBounds(20, 73, 55, 14);

        countryLabel.setText("* Country:");
        getContentPane().add(countryLabel);
        countryLabel.setBounds(326, 47, 55, 14);

        registrationDateLabel.setText("* Date of registration:");
        getContentPane().add(registrationDateLabel);
        registrationDateLabel.setBounds(326, 73, 110, 14);

        birthDateLabel.setText("* Date of birth:");
        getContentPane().add(birthDateLabel);
        birthDateLabel.setBounds(20, 99, 75, 14);

        emailLabel.setText("E-mail:");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(20, 125, 32, 14);

        telephoneLabel.setText("Telephone:");
        getContentPane().add(telephoneLabel);
        telephoneLabel.setBounds(20, 151, 54, 14);

        noteLabel.setText("Note:");
        getContentPane().add(noteLabel);
        noteLabel.setBounds(20, 174, 27, 14);

        nameField.setText(d.getName());
        getContentPane().add(nameField);
        nameField.setBounds(107, 44, 190, 20);

        surnameField.setText(d.getSurname());
        getContentPane().add(surnameField);
        surnameField.setBounds(107, 70, 190, 20);

        emailField.setText(d.getEmail());
        getContentPane().add(emailField);
        emailField.setBounds(107, 122, 190, 20);

        telephoneField.setText(d.getTelephone());
        getContentPane().add(telephoneField);
        telephoneField.setBounds(107, 148, 190, 20);

        countryField.setText(d.getCountry());
        getContentPane().add(countryField);
        countryField.setBounds(437, 44, 190, 20);

        registrationDateField.setText(d.getRegistrationDate());
        getContentPane().add(registrationDateField);
        registrationDateField.setBounds(437, 70, 190, 20);
        registrationDateField.setFormatterFactory(
         new DefaultFormatterFactory(new DateFormatter(
            new java.text.SimpleDateFormat("dd.MM.yyyy"))));  
        registrationDateField.setText(d.getRegistrationDate());         

        if (!d.isRoot())
        {
            sponsorLabel.setText("* Sponsor:");
            getContentPane().add(sponsorLabel);
            sponsorLabel.setBounds(326, 125, 55, 14);
                        
            String[] sponsors = new String[DistributorManager.getCount()-1];
            ItemRegNum item = (ItemRegNum) DistributorManager.listRegNum.getFirst();
            int selected = 0, i = 0;
            while (item != null)
            {
                if (!item.getKey().equals(d.getRegNum()))
                {
                  sponsors[i] = item.getKey()+" ("+item.treeItem.name+" "+item.treeItem.surname+")";
                  if (item.getKey().equals(d.getSponsor())) selected = i;
                  i++;
                }
                item = (ItemRegNum) item.getNext();
            }      
            sponsorBox.setModel(new DefaultComboBoxModel<String>(sponsors));
            sponsorBox.setSelectedIndex(selected);

            getContentPane().add(sponsorBox);
            sponsorBox.setBounds(437, 122, 190, 20);
        }

        noteField.setText(d.getNote());
        jScrollPane1.setViewportView(noteField);
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(107, 175, 190, 51);

        saveBtn.setText("Save changes");
        getContentPane().add(saveBtn);
        saveBtn.setBounds(320, 180, 115, 23);
        saveBtn.addActionListener(this);
        
        if (!d.isRoot())
        {
            deleteBtn.setText("Delete distributor");
            getContentPane().add(deleteBtn);
            deleteBtn.addActionListener(this);
            deleteBtn.setBounds(320, 210, 115, 23);
        }
    
        getContentPane().add(separator);
        separator.setBounds(20, 241, 640, 10);

        statisticsLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
        statisticsLabel.setText("Statistics");
        getContentPane().add(statisticsLabel);
        statisticsLabel.setBounds(20, 257, 69, 22);

        editableLabel.setText("* are editable (use doubleclicking)");
        getContentPane().add(editableLabel);
        editableLabel.setBounds(470, 265, 170, 14);        
        
        /** table **/
        
        updateTable();
               
        scrollPane.setViewportView(statsTable);

        getContentPane().add(scrollPane);
        scrollPane.setBounds(20, 290, 640, 155);

        /** end of table **/
        
        
        birthDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        birthDateField.setText(d.getBirthDate());
        getContentPane().add(birthDateField);
        birthDateField.setBounds(107, 96, 190, 20);
        birthDateField.setFormatterFactory(
         new DefaultFormatterFactory(new DateFormatter(
            new java.text.SimpleDateFormat("dd.MM.yyyy"))));  
        birthDateField.setText(d.getBirthDate());
        
        childTreeBtn.setText("Open child distributors in tree");
        getContentPane().add(childTreeBtn);
        childTreeBtn.setBounds(450, 210, 180, 23);
        childTreeBtn.addActionListener(this);
        
        childTableBtn.setText("Open child distributors in table");
        getContentPane().add(childTableBtn);
        childTableBtn.setBounds(450, 180, 180, 23);
        childTableBtn.addActionListener(this);
        
        statusLabel.setText("Status:");
        getContentPane().add(statusLabel);
        statusLabel.setBounds(326, 99, 35, 14);

        consumerCheckbox.setText("Consumer");
        getContentPane().add(consumerCheckbox);
        consumerCheckbox.setBounds(414, 97, 73, 23);
        if (d.getStatus().indexOf('C') != -1) consumerCheckbox.setSelected(true);
            
        salesmanCheckbox.setText("Salesman");
        getContentPane().add(salesmanCheckbox);
        salesmanCheckbox.setBounds(487, 97, 71, 23);
        if (d.getStatus().indexOf('S') != -1) salesmanCheckbox.setSelected(true);
        
        managerCheckbox.setText("Manager");
        getContentPane().add(managerCheckbox);
        managerCheckbox.setBounds(560, 97, 67, 23);
        if (d.getStatus().indexOf('M') != -1) managerCheckbox.setSelected(true);
        
        
        //chart
        updateChart();
        
        setIconImage(Dialog.createImageIcon(Static.profileIconPath,"Manager").getImage());
        setResizable(false);        

        setSize(690,750);
        setLocationRelativeTo(Dialog.mainWindow); //center
        defaultBorder = nameField.getBorder();
        setDefaultTitle();
        
        //display
        setVisible(true);    
    }

    /**
     * Updates information shown in table.
     *
     */
    public void updateTable()
    {
        months = d.months.getAll();
        int length = months.length;
        //determine header titles

        
        final String[] headerTitles = new String [] {
                                                  "",
                                                  "Distributors",
                                                  "New dist.",
                                                  "New direct dist.",
                                                  "PW*",
                                                  "Group PW*",
                                                  "GW*",
                                                  "Group GW*",
                                                  "Provision"          
                                                 };
        String[][] content = new String [length][9];
        for (int row=0;row<length;row++)                                         
        {
            for (int col=0;col<9;col++)                                                      
            {
                if (col == 0) //left header
                {
                    content[row][col] = Static.monthByWord(months[length-row-1].getMonth())
                                        + " "+months[length-row-1].getYear();
                }
                if (col == 0) //total distributors
                   content[row][col+1] = ""+months[length-row-1].getNumberOfAllChildren();
                else if (col == 1) //new distributors
                   if (row == length-1)
                        content[row][col+1] = ""+months[length-row-1].getNumberOfAllChildren();
                   else
                        content[row][col+1] = ""+(months[length-row-1].getNumberOfAllChildren()
                                       -months[length-row-2].getNumberOfAllChildren());
                else if (col == 2) //new direct distributors
                   if (row == length-1)
                        content[row][col+1] = ""+months[length-row-1].getNumberOfDirectChildren();
                   else
                        content[row][col+1] = ""+(months[length-row-1].getNumberOfDirectChildren()
                                       -months[length-row-2].getNumberOfDirectChildren());   
                else if (col == 3) //pw
                   content[row][col+1] = ""+months[length-row-1].getPw();                
                else if (col == 4) //group pw
                   content[row][col+1] = ""+months[length-row-1].getGroupPw();                
                else if (col == 5) //gw
                   content[row][col+1] = ""+months[length-row-1].getGw();                
                else if (col == 6) //group gw
                   content[row][col+1] = ""+months[length-row-1].getGroupGw();                
                else if (col == 7) //provision
                   content[row][col+1] = ""+months[length-row-1].getProvision()+" �";                
            }
        }
        
        statsTable.setModel(
            new NonEditableDefaultTableModel(content, headerTitles)
            {
                public boolean isCellEditable(int row, int column) 
                {
                    //enable editing for pw, group pw, gw and group gw columns
                    if (column <= 7 && column >= 4) return true;
                    else return false;
                }        
                public void setValueAt(Object value, int row, int col) 
                {
                    //checks if the inserted value to table is numeric
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


        statsTable.getModel().addTableModelListener(
            new TableModelListener ()
            {
                public void tableChanged(TableModelEvent e) 
                {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    TableModel model = (TableModel)e.getSource();
                    String columnName = model.getColumnName(column);
                    Object data = model.getValueAt(row, column);
                    Month m = months[months.length-row-1];
                    if (column == 4) //PW
                    {
                        m.setPw((String)data);
                        Dialog.info("PW for "+Static.monthByWord(m.getMonth())
                          +" has been successfully updated to "+(String)data+".");
                    } else if (column == 5) //Group PW
                    {
                        m.setGroupPw((String)data);
                        Dialog.info("Group PW for "+Static.monthByWord(m.getMonth())
                          +" has been successfully updated to "+(String)data+".");  
                    } else if (column == 6) //GW
                    {
                        m.setGw((String)data);
                        Dialog.info("GW for "+Static.monthByWord(m.getMonth())
                          +" has been successfully updated to "+(String)data+".");  
                    } else if (column == 7) //Group GW
                    {
                        m.setGroupGw((String)data);
                        Dialog.info("Group GW for "+Static.monthByWord(m.getMonth())
                          +" has been successfully updated to "+(String)data+".");  
                    } 
                    m.save();
                    updateTable();
                    updateChart();
                }                
            }        
        );   
        
        
        //disable left column
        statsTable.getColumn("").setCellRenderer(
            new RowHeaderRenderer(statsTable)
        );

        //set widths for columns        
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(45);
        statsTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        statsTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        statsTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        statsTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        statsTable.getColumnModel().getColumn(6).setPreferredWidth(25);
        statsTable.getColumnModel().getColumn(7).setPreferredWidth(50);
        statsTable.getColumnModel().getColumn(8).setPreferredWidth(35);
        
        //disable dragndrop
        statsTable.setDragEnabled(false);
        statsTable.getTableHeader().setReorderingAllowed(false);             
    }

    /**
     * Updates chart by the newest information from files.
     */
    public void updateChart()
    {       
        if (chartPanel != null)
        {
          getContentPane().remove(chartPanel);
          chartPanel = null;
        }
        chartPanel = new ChartPanel(Chart.produce(d.months.getLastMonths(12)));
        //chartPanel.setPreferredSize(new Dimension(500, 270));
        chartPanel.setBounds(20,450,640,250);
        getContentPane().add(chartPanel);
        repaint();
    }
    
    /**
     * Sets the title of the window to current information.
     *
     */
    public void setDefaultTitle()
    {
        //setting title
        String title = "Profile of "+d.getName()+" "+d.getSurname()+
                           " ("+d.getRegNum();
        if (d.isRoot())
          title += " - the root distributor)";
        else title += " - "+d.getGeneration()+". generation)";  
        
        titleLabel.setText(title);
        super.setTitle(title);
    }

    /**
     * Defines action when ActionEvent on controls 
     * at this frame fires
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource () == saveBtn)
        {
            //reset all colors
           nameLabel.setForeground(null);
           nameField.setBorder(defaultBorder);

           surnameLabel.setForeground(null);
           surnameField.setBorder(defaultBorder);

           emailLabel.setForeground(null);
           emailField.setBorder(defaultBorder);

           countryLabel.setForeground(null);
           countryField.setBorder(defaultBorder);

           registrationDateLabel.setForeground(null);
           registrationDateField.setBorder(defaultBorder);
           
           
           //validation
           Vector<String> errorMsgs = new Vector<String>();
           MatteBorder border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red);
           
           //name
           String s = Static.validateString(nameField.getText(),1,true,"Name");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             nameLabel.setForeground(Color.red);
             nameField.setBorder(border);
           }

           //surname
           s = Static.validateString(surnameField.getText(),1,true,"Surname");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             surnameLabel.setForeground(Color.red);
             surnameField.setBorder(border);
           }           

           //email
           s = Static.validateString(emailField.getText(),2,false,"Email");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             emailLabel.setForeground(Color.red);
             emailField.setBorder(border);
           }           
                      
           //country
           s = Static.validateString(countryField.getText(),1,true,"Country");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             countryLabel.setForeground(Color.red);
             countryField.setBorder(border);
           }      
           
           //registrationDate
           s = Static.validateString(registrationDateField.getText(), -1, true, "Registration date");
           if (s != null)
           {
             errorMsgs.pushBack(s);
             registrationDateLabel.setForeground(Color.red);
             registrationDateField.setBorder(border);
           } 
           
           String sponsor = "";
           if (d.isRoot()) sponsor = "0";
           else 
           {
               sponsor = (String) sponsorBox.getSelectedItem();
               sponsor = sponsor.substring(0, sponsor.indexOf(" "));
           }
            
           String status = "";
           if (consumerCheckbox.isSelected()) status += "C";
           if (salesmanCheckbox.isSelected()) status += "S";
           if (managerCheckbox.isSelected()) status += "M";           
           //result
           if (errorMsgs.size() == 0)
           {
                //data is valid   
                d.setSponsor(sponsor);
                d.setCountry(countryField.getText());
                d.setRegistrationDate(registrationDateField.getText());
                d.setStatus(status);
                d.setName(nameField.getText());
                d.setSurname(surnameField.getText());                
                d.setBirthDate(birthDateField.getText()); 
                d.setEmail(emailField.getText());
                d.setTelephone(telephoneField.getText());
                d.setNote(noteField.getText());                
                if (d.save())
                { 
                    setDefaultTitle();
                    Dialog.info("Distributor "+d.getName()+" "
                      +d.getSurname()+" has been successfully updated!");
                }                  
           } else
           {
                s = "The form is invalid because of following "
                           +errorMsgs.size()+" reasons:\n";
                while (errorMsgs.size() != 0)         
                  s += "- "+errorMsgs.popFront()+"\n";

                Dialog.error (s);
           }            
        } else if (e.getSource() == deleteBtn)
        {
            int n = JOptionPane.showConfirmDialog(
                        this,
                        "Do you really want to delete "+d.getName()+" "+d.getSurname()+"?"
                        +"This action will also discard any information about any child\n"
                        +"distributors associated to this distirbutor!",
                        "Delete distributor",
                        JOptionPane.YES_NO_OPTION);
                        
            String name = d.getName()+" "+d.getSurname();
            if (n == 0 && d.delete())
            {
                dispose(); //close window
                
                //update search table
                ActionEvent ae = new ActionEvent (Dialog.mainWindow.searchBtn,0,null);
                Dialog.mainWindow.actionPerformed(ae);    
                Dialog.mainWindow.updateCounter();
                
                //inform the user
                Dialog.info ("Distributor "+name+" was successfully deleted.");                
            }
        } else if (e.getSource() == childTreeBtn)
        {
            if (d.getTree().getNumberOfAllChildren() > 0)
              new ChildrenTreeDialog(d, this);
            else Dialog.info(d.getFullname() + " does not have any child distributors yet.");
        } else if (e.getSource() == childTableBtn)
        {
            if (d.getTree().getNumberOfAllChildren() > 0)            
              new ChildrenTableDialog(d, this);
            else Dialog.info (d.getFullname()+" does not have any child distributors yet.");              
        }
    }
}
