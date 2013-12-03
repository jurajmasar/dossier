package com.jurajmasar.ib.dossier.data_structures;

/**
 * Class List - data structure with dynamic allocation of memory.
 * 
 * @author Juraj Masar 
 * @version 0.1
 */
public class List
{
    private Item first; //pointer to first item of the list
    private boolean sorted; //is the list sorted?
    
    /**
     * Constructor - sets object variables to default values
     *
     */
    public List()
    {
        first = null;
        sorted = false;
    }

    /**
     * Constructor - sets object variables to default values
     * and flags the list as sorted if given.
     * 
     * @param sorted if the array is supposed to be sorted or not
     */    
    public List (boolean sorted)
    {
        first = null;
        this.sorted = sorted;
    }

    /**
     * Constructor - sets object variables to given values.
     * 
     * @param first pointer to first item in the array
     * @param sorted if the array is supposed to be sorted or not
     */      
    public List(Item first, boolean sorted)
    {
        this.first = first;
        this.sorted = sorted;
    }

    /**
     * Checks if the list is sorted.
     *
     * @return boolean true if sorted
     */    
    public boolean isSorted ()
    {
        return sorted;
    }

    /**
     * Checks if the list is empty.
     *
     * @return boolean true if list is empty
     */        
    public boolean isEmpty()
    {
        return (first == null);
    }
    
    /**
     * Returns pointer to first item in the list.
     * 
     * @return pointer to first item in the list
     */      
    public Item getFirst ()
    {
        return first;
    }

    /**
     * Adds given item to the list according to information 
     * whether the list is sorted or not.
     * 
     * @param newItem item to add
     */        
    public void add(Item newItem)
    {
        if (!sorted)
        {
            addToBeginning(newItem);
        }else
        {
            addSorted(newItem);
        }
    }
    
    /**
     * Adds item created upon given information to the list 
     * according to fact whether the list is sorted or not.
     * 
     * @param key   key of the item
     * @param pos   position in DAF to which this item corresponds
     */      
    public void add(String key, long pos)
    {
        Item newItem = new Item(key,pos,null);
        add(newItem);
    }
        
    /**
     * Displays the content of the list on output.
     * Useful for debugging purposes.
     */    
    public void display()
    {
        Item act = first;
        System.out.println("List:");
        while(act != null)
        {
            act.print();
            act = act.getNext();
        }
    }
    
    /**
     * Finds and returns Item according to given key.
     * 
     * @param k key to find
     * @return pointer to found item
     */        
    public Item find(String k)
    {
        if (sorted)
        {
            return findSorted(k);
        }else
        {
            return findUnsorted(k);
        }
    }

    /**
     * Finds and returns position according to given key.
     * 
     * @param k key to find
     * @return position of the record
     */        
    public long findPosition(String k)
    {
        Item i = find(k);
        if (i != null)
          return i.getPos();
        else return -1;
    }

    /**
     * Finds and returns vector of positions according to given key.
     * 
     * @param k key to find
     * @return vector of positions of the records
     */        
    public Vector<Integer> findPositions(String k)
    {
        Item[] items = findMore(k);
        Vector<Integer> positions = new Vector<Integer>();
        
        for (int i=0;i<=items.length-1;i++) positions.pushBack((int) items[i].getPos());
        
        return positions;
    }

    /**
     * Finds and returns vector of positions according to given key.
     * The key could be only substring of elements' keys.
     * 
     * @param k key to find
     * @return vector of positions of the records
     */        
    public Vector<Integer> findPositionsSubstring(String k)
    {
        Item[] items = findMoreSubstring(k);
        Vector<Integer> positions = new Vector<Integer>();
        
        for (int i=0;i<=items.length-1;i++) positions.pushBack((int) items[i].getPos());
        
        return positions;
    }
    
    /**
     * Finds and returns array of Item according to given key.
     * The key could be only substring of elements' keys.
     * 
     * @param k key to find
     * @return array of pointers to found Item
     */        
    public Item[] findMoreSubstring(String k)
    {
        if (!isEmpty())
        {
            Vector<Item> items = new Vector<Item>();
            Item act = first;
            while (act != null)
            {
                if (act.getKey().indexOf(k) != -1) 
                  items.pushBack((Item) act.clone());
                act = act.getNext();
            }
            return items.toItemArray();
        }else
        {
            //the list is empty
            return null;
        }
    }    
    
    /**
     * Finds and returns array of Item according to given key.
     * 
     * @param k key to find
     * @return array of pointers to found Item
     */        
    public Item[] findMore(String k)
    {
        if (sorted)
        {
            return findSortedMore(k);
        }else
        {
            return findUnsortedMore(k);
        }
    }
    
    
    /**
     * Finds and returns Item according to given position.
     * 
     * @param pos position of item to find
     * @return pointer to found item
     */     
    public Item findByPosition(long pos)
    {
        if (isEmpty() == false)
        {
            Item act = first;
            while (act.getPos() != pos)
            {
                if (act.getNext() != null)
                {
                    act = act.getNext();
                }else
                {
                    //there is nothing to find
                    return null;
                }
            }
            return act;    
        }else
        {
            //the list is empty
            return null;
        }
    }
    
    
    /**
     * Adds given item to the beginning of the list.
     * 
     * @param newItem item to add
     */      
    private void addToBeginning(Item newItem)
    {
        if (newItem != null)
        {
            if (!isEmpty())
            {
                newItem.setNext(first);
            }
            first = newItem;
        }
    }

    /**
     * Adds given item to the list on the right place assuming the list is sorted.
     * 
     * @param newItem item to add
     */ 
    private void addSorted(Item newItem)
    {
        if (newItem != null)
        {
            if (!isEmpty())
            {
                Item act = first;

                if (first.getKey().compareTo(newItem.getKey()) > 0)
                {
                    addToBeginning(newItem);
                }else if (act.getNext() != null)
                {
                    while (act.getNext().getKey().compareTo(newItem.getKey()) < 0)
                    {
                        act = act.getNext();
                        if (act.getNext() == null) break;                        
                    }
                    newItem.setNext(act.getNext());
                    act.setNext(newItem);
                }else
                {
                    act.setNext(newItem);
                }
            }else
            {
                first = newItem;
            }
        }
    }    
    
    /**
     * Finds and returns Item according to given key
     * assuming the list is sorted.
     * 
     * @param k key to find
     * @return pointer to found item
     */            
    private Item findSorted(String k)
    {
        if (!isEmpty())
        {
            Item act = first;
            while (act != null && act.getKey().compareTo(k) < 0)
            {
                act = act.getNext();
            }
            if(act != null && act.getKey().equals(k))
            {
                return act;    
            }else
            {
                //there is nothing to find
                return null;
            }
        }else
        {
            //the list is empty
            return null;
        }
    }

    /**
     * Finds and returns array of Item according to given key
     * assuming the list is sorted.
     * 
     * @param k key to find
     * @return array of pointers to found Item
     */            
    private Item[] findSortedMore(String k)
    {
        if (!isEmpty())
        {
            Vector<Item> items = new Vector<Item>();
            Item act = first;
            while (act != null && act.getKey().compareTo(k) < 0)
            {
                act = act.getNext();
            }
            if(act != null && act.getKey().equals(k))
            {
                while (act != null && act.getKey().equals(k))
                {
                    items.pushBack((Item)act.clone());
                    act = act.getNext();
                }
                return items.toItemArray();
            }else
            {
                //there is nothing to find
                return null;
            }
        }else
        {
            //the list is empty
            return null;
        }
    }

    /**
     * Finds and returns array of Item according to given key
     * assuming the list is unsorted.
     * 
     * @param k key to find
     * @return array of pointers to found Item
     */            
    private Item[] findUnsortedMore(String k)
    {
        if (!isEmpty())
        {
            Vector<Item> items = new Vector<Item>();
            Item act = first;
            while (act != null)
            {
                if (act.getKey().equals(k)) items.pushBack((Item)act.clone());
                act = act.getNext();
            }
            return items.toItemArray();
        }else
        {
            //the list is empty
            return null;
        }
    }    
    
    /**
     * Finds and returns Item according to given key
     * assuming the list is unsorted.
     * 
     * @param k key to find
     * @return pointer to found item
     */     
    private Item findUnsorted(String k)
    {
        if (isEmpty() == false)
        {
            Item act = first;
            while (act.getKey().compareTo(k) != 0)
            {
                if (act.getNext() != null)
                {
                    act = act.getNext();
                }else
                {
                    //there is nothing to find
                    return null;
                }
            }
            return act;    
        }else
        {
            //the list is empty
            return null;
        }
    }    
    
}
