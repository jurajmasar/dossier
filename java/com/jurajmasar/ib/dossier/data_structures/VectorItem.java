package com.jurajmasar.ib.dossier.data_structures;

/**
 * VectorItem object to be used in Vector data structure
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class VectorItem<O>
{
    private O o; 
    private VectorItem<O> next; //pointer to next VectorItem 
    private VectorItem<O> previous; //pointer to next VectorItem     

    /**
     * Constructor - initializes object variables to default values
     *
     */
    public VectorItem()
    {
        o = null;
        next = null;
        previous = null;
    }

    /**
     * Constructor - initializes object variables to given values
     *
     */
    public VectorItem(O o, VectorItem<O> previous, VectorItem<O> next)
    {
        this.o = o;
        this.next = next;
        this.previous = previous;   
    }

    /**
     * Returns the object encapsulated in VectorItem
     *
     * @return Object
     */    
    public O getObject()
    {
        return o;
    }
    
    /**
     * Returns pointer to next VectorItem in Vector.
     *
     * @return VectorItem
     */    
    public VectorItem<O> getNext()
    {
        return next;
    }

    /**
     * Returns pointer to next VectorItem in Vector.
     *
     * @return VectorItem
     */    
    public VectorItem<O> getPrevious()
    {
        return previous;
    }

    
    /**
     * Sets the object encapsulated in VectorItem 
     * to given object.
     *
     * @param p position
     */    
    public void setObject(O o)
    {
        this.o = o;
    }

    /**
     * Sets pointer to next Item in Vector.
     *
     * @param next Item
     */     
    public void setNext(VectorItem<O> next)
    {
        this.next = next;
    }

    /**
     * Sets pointer to previous Item in Vector.
     *
     * @param next Item
     */     
    public void setPrevious(VectorItem<O> previous)
    {
        this.previous = previous;
    }    
}
