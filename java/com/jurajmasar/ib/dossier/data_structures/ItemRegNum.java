package com.jurajmasar.ib.dossier.data_structures;

import com.jurajmasar.ib.dossier.windows_and_dialogs.ProfileWindow;

/**
 * Item object to be used in listRegNum data structure
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class ItemRegNum extends Item
{
    public TreeItem treeItem;
    public ProfileWindow window;
    /**
     * Constructor - initializes object variables to given values
     *
     */
    public ItemRegNum(String key, long pos, Item next)
    {
        this.key = key;
        this.pos = pos;
        this.next = next;
        treeItem = null;
        window = null;
    }    
    
    /**
     * Prints the content of the Item.
     * Only for debugging purposes.
     *
     */
    public void print()
    {
        System.out.println(getKey()+" "+getPos()+" "
          +((treeItem == null) ? "null" : treeItem.regNum));
    }    
}
