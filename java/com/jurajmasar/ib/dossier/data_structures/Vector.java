package com.jurajmasar.ib.dossier.data_structures;

/**
 * Vector class - data structure with dynamic allocation of memory.
 * 'Enhanced array'
 * 
 * @author Juraj Masar
 * @version 0.1
 */

import com.jurajmasar.ib.dossier.root.Static;

/** DOSSIER: HL mastery 12-15 - ADT **/
public class Vector<O>
{
    private VectorItem<O> first; //pointer to first VectorItem in the vector
    private VectorItem<O> last; //pointer to last VectorItem in the vector
    private int count; //number of elements in Vector
    /**
     * Constructor for objects of class Vector
     */
    public Vector()
    {
        // initialise instance variables
        first = null;
        last = null;
        count = 0;
    }

     /**
     * Constructor for objects of class Vector
     * which populates the Vector with data given.
     * @param c number of elements to insert
     * @param o default element
     */
    public Vector(int c, O o)
    {
        // initialise instance variables
        first = null;
        last = null;
        count = 0;
        for (int i=0;i<=c-1;i++) pushBack(o);
    }  
    
    
    /**
     * Returns the number of elements in the Vector
     *
     * @return     count of the elements in Vector
     */
    public int size()
    {
        return count;
    }

    /**
     * Checks whether the vector is empty.
     *
     * @return     true if the vector is empty
     */
    public boolean isEmpty()
    {
        if (count > 0) return false;
          else return true;
    }

    
    /**
     * Removes all elements from the Vector
     *
     */
    public void clear()
    {
        first = null;
        last = null;
        count = 0;
    }

    /**
     * Returns a clone of this vector.
     *
     * @return     vector
     */
    public Vector clone()
    {
        Vector<O> v = new Vector<O>();
            
        VectorItem<O> vi = first;
        while (vi != null)
        {
            v.pushBack (vi.getObject());
            vi = vi.getNext();
        }
        
        return v;
    }

    /**
     * Returns the first element
     *
     * @return     object
     */
    public O first()
    {
        if (isEmpty()) return null;        
        return first.getObject();
    }

    /**
     * Returns the first element
     *
     * @return     VectorItem
     */
    public VectorItem<O> firstVectorItem()
    {
        if (isEmpty()) return null;        
        return first;
    }
    
    
    /**
     * Returns the last element
     *
     * @return     object
     */
    public O last()
    {
        if (isEmpty()) return null;
        return last.getObject();
    }   
    
    /**
     * Adds element to the end of Vector
     *
     * @param  o   object to be inserted
     */
    public void pushBack (O o)
    {
        //create new VectorItem
        VectorItem<O> vi = new VectorItem<O> (o,last, null);
        if (isEmpty()) 
        {
            last = vi;
            first = vi;
        }
        else 
        {
          last.setNext(vi);
          last = vi;
        }
        count++;
    }

    /**
     * Adds element to the beginning of the vector
     *
     * @param  o   object to add
     */
    public void pushFront(O o)
    {
        //create new VectorItem
        VectorItem<O> vi = new VectorItem<O> (o,null, first);
        
        first = vi;
        if (isEmpty()) last = vi;
        count++;
    }

    /**
     * Removes and returns the last element of the vector.
     *
     * @return     object
     */
    public O popBack()
    {
        if (!isEmpty())
        {
            VectorItem<O> l = last;
            last = last.getPrevious();

            count--;
            if (isEmpty()) first = null;
              else last.setNext(null);
            return l.getObject();
        } else
            return null;
        
    }

    /**
     * Removes and returns the first element of the vector.
     *
     * @return     object
     */
    public O popFront()
    {
        if (!isEmpty())
        {
            VectorItem<O> l = first;
            first = first.getNext();
            
            count--;
            if (isEmpty()) last = null;
              else first.setPrevious(null);
            
            return l.getObject();
        } else
          return null;
    }
    
    /**
     * Returns object at index given.
     *
     * @param  index   index of the element
     * @return     object
     */
    public O get(int index)
    {
        if (index >= count || index < 0) return null;
        
        if (index > count/2)
        {
            //search from the end
            VectorItem<O> vi = last;
            for (int i=count-1;i>index;i--)
            {
                vi = vi.getPrevious();
            }
            return vi.getObject();            
            
        } else
        {
            //search from the beginning
            VectorItem<O> vi = first;
            
            for (int i=0;i<index;i++)
            {
                vi = vi.getNext();
            }
            return vi.getObject();            
        }
    }

    /**
     * Returns the VectorItem at index given.
     *
     * @param  index   index of the element
     * @return     Object
     */
    public VectorItem<O> getVectorItem(int index)
    {
        if (index >= count || index < 0) return null;
        
        if (index > count/2)
        {
            //search from the end
            VectorItem<O> vi = last;
            for (int i=count-1;i>index;i--)
            {
                vi = vi.getPrevious();
            }
            return vi;            
            
        } else
        {
            //search from the beginning
            VectorItem<O> vi = first;
            
            for (int i=0;i<index;i++)
            {
                vi = vi.getNext();
            }
            return vi;            
        }
    }    
    
    /**
     * Sets object to the vector at position given.
     *
     * @param  o   object
     * @param  index position
     * @return true if operation was successful
     */
    public boolean set(O o, int index)
    {
        if (index >= count || index < 0) return false;
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=index-1;i++)
        {
            vi = vi.getNext();
        }
        
        vi.setObject(o);
        return true;
        
    }

    /**
     * Returns the index of first occurance of given object.
     *
     * @param  o    object to search for
     * @return     index of the found object or -1 
     */
    public int indexOf(O o)
    {
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            if (vi.getObject().equals(o)) return i;
            vi = vi.getNext();
        }
        return -1;
    }

    /**
     * Inserts object given to the position given in Vector.
     *
     * @param  index    index where to insert
     * @param  o        object to insert
     * @return     true if successful
     */
    public boolean insertAt(int index, O o)
    {
        if (index < 0 || index > size()) return false; //out of range
        if (index == size()) //last
        {
            pushBack (o);
            return true;
        } else if (index == 0)
        {
            pushFront (o);
            return true;
        } else 
        {
            VectorItem<O> before, after;
            before = getVectorItem(index-1);
            after = getVectorItem(index);
                        
            VectorItem<O> vi = new VectorItem<O> (o,before,after);
            before.setNext(vi);
            after.setPrevious(vi);
            return true;
        }        
    }

    /**
     * Inserts the object given after the other given object.
     *
     * @param  where    object after which new object should be inserted
     * @param  what     object to insert
     * @return     true if successful
     */
    public boolean insertAfter(O where, O what)
    {
        return insertAt(indexOf(where)+1,what);
    }

    /**
     * Inserts the object given before the other given object.
     *
     * @param  where    object before which new object should be inserted
     * @param  what     object to insert
     * @return     true if successful
     */
    public boolean insertBefore(O where, O what)
    {
        return insertAt(indexOf(where)-1,what);
    }    
    
    /**
     * Returns object which is situated in the Vector just
     * before the object given or null
     *
     * @param  a    object to find
     * @return     object before the object given
     */
    public O before(O a)
    {
        int index = indexOf(a);
        if (index != -1 && index != 0) //is defined and not first
          return get(index-1);
        else return null;
    }

    /**
     * Returns object which is situated in the Vector just
     * after the object given or null
     *
     * @param  a    object to find
     * @return     object before the object given
     */
    public O after(O a)
    {
        int index = indexOf(a);
        if (index != -1 && index != size()-1) //is defined and not last
          return get(index+1);
        else return null;
    }
    
    /**
     * Returns the content of the vector in an array
     *
     * @return     array of objects
     */
    public O[] toArray()
    {

        @SuppressWarnings("unchecked")
        O[] a = (O[]) new Object[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }

    /**
     * Returns the content of the vector in an array of Integers
     *
     * @return     array of int
     */
    public int[] toIntArray()
    {
        int[] a = new int[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = (Integer) vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }    

    /**
     * Returns the content of the vector in an array of Integers
     *
     * @return     array of int
     */
    public long[] toLongArray()
    {
        long[] a = new long[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = (Long) vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }     

    /**
     * Returns the content of the vector in an array of Items
     *
     * @return     array of Item
     */
    public Item[] toItemArray()
    {
        Item[] a = new Item[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = (Item) vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }       

    /**
     * Returns the content of the vector in an array of Distributors
     *
     * @return     array of Distributor
     */
    public Distributor[] toDistributorArray()
    {
        Distributor[] a = new Distributor[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = (Distributor) vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }       
    
    
    /**
     * Returns the content of the vector in an array of Strings
     *
     * @return     array of Strings
     */
    public String[] toStringArray()
    {
        String[] a = new String[count];
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            a[i] = (String) vi.getObject();
            vi = vi.getNext();
        }

        return a;       
    }       
    
    /**
     * Returns the content of the vector in a String
     *
     * @return     string 
     */
    public String toString()
    {
        String s = new String();
        
        VectorItem<O> vi = first;
        
        for (int i=0;i<=count-1;i++)
        {
            s += vi.getObject().toString()+ Static.defaultSeparator;
            vi = vi.getNext();
        }
        
        return s;
    }

}
