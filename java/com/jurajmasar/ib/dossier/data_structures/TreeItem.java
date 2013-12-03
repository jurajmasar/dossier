package com.jurajmasar.ib.dossier.data_structures;

import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.root.Static;
import com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls.CheckNode;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Item of Tree object - describing the structure in MLM system.
 * 
 * @author Juraj Masar 
 * @version 0.1
 */
public class TreeItem implements Cloneable
{
    public String regNum; //registration number of a distributor
    public TreeItem[] children; //array of TreeItems for children of this distributor
    public TreeItem parent; //TreeItem of the sponsor of this distributor
    public String name;
    public String surname;
    public Date registrationDate; //registration date of this distributor
    public long position; //position in file of this distributor

    /**
     * Constructor of TreeItem - initialize the regNum and variables
     */
    public TreeItem (String regNum) 
    {
        this.regNum = regNum;
        parent = null;
        registrationDate = null;
        position = -1;
        children = null;
    }

    /**
     * Returns a clone of current object
     *
     * @return     clone of current Object
     */
    public Object clone() 
    {
        try 
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }    

    /**
     * Returns the number of direct children of this distributor
     *
     * @return     number of direct children
     */
    public int getNumberOfDirectChildren()
    {
        if (children == null) return 0;

        return children.length;
    }

    /**
     * Returns total number of children
     *
     * @return     number of children
     */
    public int getNumberOfAllChildren()
    {
        if (children == null) return 0;

        int count = 0;

        for (int i=0;i<children.length;i++)
        {
            count += 1 + children[i].getNumberOfAllChildren();
        }

        return count;

    }

    /**
     * Prints this TreeItem and all its children.
     * For debugging purposes.
     *
     * @param  level    height in which the item is
     */
    public void print(int level)
    {
        for (int i=0;i<level;i++) System.out.print("-");
        System.out.println(regNum+" "+ Static.dateToString(registrationDate));
        if (children != null )for (int i=0;i<children.length;i++) children[i].print(level+1);
    }

    /**
     * Checks whether the distributor was registrated before given date.
     *
     * @param  s  date given in string
     * @return     true if was
     */
    public boolean wasRegisteredBefore(String s)
    {
        return (Static.stringToDate(s).compareTo(registrationDate) > 0); 
    }

    /**
     * Counts direct children distributors registered before
     * given date.
     *
     * @param  s  date given in string
     * @return     number of distributors before given date
     */
    public int getNumberOfDirectChildrenBefore(String s)
    {
        if (!wasRegisteredBefore(s)) return 0;

        int count = 0;

        if (children != null) 
            for (int i=0;i<children.length;i++) 
                if (children[i].registrationDate.compareTo(Static.stringToDate(s)) < 0) count++;

        return count;        
    }

    /**
     * Counts all children distributors registered before
     * given date.
     *
     * @param  s  date given in string
     * @return     number of distributors before given date
     */
    public int getNumberOfAllChildrenBefore(String s)
    {
        if (!wasRegisteredBefore(s)) return 0;

        int count = 0;

        if (children != null) 
            for (int i=0;i<children.length;i++) 
            {
                if (children[i].registrationDate.compareTo(Static.stringToDate(s)) < 0)
                { 
                    count += 1 + children[i].getNumberOfAllChildrenBefore(s);
                }
        }

        return count;        
    }

    /**
     * Returns key - a String identificator of this 
     * distributor.
     *
     * @return     String identificator
     */
    public String getKey()
    {
        Distributor d = DistributorManager.getDistributorByRegNum(regNum);
        return regNum+" ("+d.getFullname()+")";
    }

    /**
     * Appends to node to JTree fulfilled with data about distributor and
     * all its children.
     *
     */
    public void setJTreeNode(DefaultMutableTreeNode top, boolean first)
    {
        if (top != null)
        {
            if (first)
            {
                top.setUserObject(getKey());
            } else {
                DefaultMutableTreeNode item = new DefaultMutableTreeNode(getKey());
                top.add (item);
                top = item;
            }
            if (children != null)
            for (int i=0;i<children.length;i++)
            {
                children[i].setJTreeNode(top, false);
            }
        }
    }

    /**
     * Appends to node to JTree fulfilled with data about distributor and
     * all its children.
     *
     */
    public void setJTreeNode(CheckNode top, boolean first)
    {
        if (top != null)
        {
            if (first)
            {
                top.setUserObject(getKey());
            } else {
                CheckNode item = new CheckNode(getKey());
                top.add (item);
                top = item;
            }
            if (children != null)
            for (int i=0;i<children.length;i++)
            {
                children[i].setJTreeNode(top, false);
            }
        }
    }    
    
    /**
     * Set the position in file of current distributor 
     * to Vector<Long> given in parameters and
     * recursively sends this command to ist children
     * distributors.
     *
     * @param  positions    Vector of positions
     */
    public void getPositions(Vector<Long> positions, boolean includeCurrent)
    {
        if (includeCurrent) positions.pushBack(position);

        if (children != null)
        for (int i=0;i<children.length;i++)
        {
            children[i].getPositions(positions, true);
        }
    }

}
