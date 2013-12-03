package com.jurajmasar.ib.dossier.data_structures;

/**
 * Item object to be used in List data structure
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class Item implements Cloneable
{
    protected String key; //according to which items are sort
    protected long pos; //position in DirectAccessFile
    protected Item next; //pointer to next item in list
    
    /** DOSSIER: HL mastery 8 - encapsulation **/ 
    
    /**
     * Constructor - initializes object variables to default values
     *
     */
    public Item()
    {
        key = "";
        pos = -1;
        next = null;
    }

    /**
     * Constructor - initializes object variables to given values
     *
     */
    public Item(String key, long pos, Item next)
    {
        this.key = key;
        this.pos = pos;
        this.next = next;
    }
  
    /**
     * Produces a clone of current object
     *
     * @return     Object instance
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
     * Returns key - string according to which items can be sorted.
     *
     * @return key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Returns position of record with key set in this item in DAF.
     *
     * @return position
     */    
    public long getPos()
    {
        return pos;
    }
    
    /**
     * Returns pointer to next Item in List.
     *
     * @return Item
     */    
    public Item getNext()
    {
        return next;
    }
    
    /**
     * Sets key - string according to which list can be sorted.
     *
     * @param k key
     */    
    public void setKey(String k)
    {
        key = k;
    }
    
    /**
     * Sets position of record with key set in this item in DAF.
     *
     * @param p position
     */    
    public void setPos(long p)
    {
        pos = p;
    }

    /**
     * Sets pointer to next Item in List.
     *
     * @param n Item
     */     
    public void setNext(Item n)
    {
        next = n;
    }


    /**
     * Prints the content of the Item.
     * Only for debugging purposes.
     *
     */
    public void print()
    {
        System.out.println(getKey()+" "+getPos());
    }


    
}
