package com.jurajmasar.ib.dossier.logic;

import com.jurajmasar.ib.dossier.data_structures.Distributor;
import com.jurajmasar.ib.dossier.data_structures.Item;
import com.jurajmasar.ib.dossier.data_structures.List;
import com.jurajmasar.ib.dossier.data_structures.Month;
import com.jurajmasar.ib.dossier.files.DirectAccessFile;
import com.jurajmasar.ib.dossier.root.Static;

/**
 * Manipulates Month entities and their info in files.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class MonthManager
{
    private DirectAccessFile f; //file with records of months for particular distributor
    private Distributor d; //distributor to which this MonthManager belongs to
    private List list;
    
    /**
     * Constructor - creates the pointer to file and initialize list
     *
     * @param  d   registration number of distributor
     */
    public MonthManager(Distributor d)
    {
        //initialize pointer to DAF 
        f = new DirectAccessFile (Static.fileMonthPrefix+d.getRegNum()+Static.fileMonthSuffix,
            Static.monthRecordLength);  
        populateLists();
        this.d = d;
    }

    /**
     * Populates lists with information from records.
     *
     */
    public void populateLists()
    {
        //initialize lists
        list = new List(true);
        
        //populate lists
        Month m;

        for (int i=0;i<=f.getNumberOfRecords()-1;i++)
        {
            m = new Month(d);
            m.readFromFile(f,i);

            //populating
            addToLists(m, i);   
        }       
        
    }

    /**
     * Adds Month given to lists
     *
     * @param  m    Month object
     * @param position  position in file
     */
    public void addToLists(Month m, long position)
    {
       list.add(Static.removeDiacritics(m.getYear()+m.getMonth()).toLowerCase(), position);
    }
    
    /**
     * Returns the number of Months in the file.
     *
     * @return     the number of Months in the file
     */
    public int getCount()
    {
        return (int) f.getNumberOfRecords();
    }    

    /**
     * Adds new Month to the database.
     * Creates new record in particular DAF and creates list items.
     *
     * @param  year
     * @param  month
     * @param  pw
     * @param  groupPw
     * @param  gw
     * @param  groupGw
     * @return     true if successful
     */
    public boolean addMonth(
        String year,
        String month,
        String pw,
        String groupPw,
        String gw,
        String groupGw
    )
    {
        Month m = new Month(d);
        //check if there is no such month in the system
        if (list.findPosition(year+month) == -1) 
        {        
            m.setYear(year);
            if (Integer.parseInt(month) < 10) 
              month = "0"+Integer.parseInt(month);
            m.setMonth(month);
            m.setPw(pw);
            m.setGroupPw(groupPw);
            m.setGw(gw);
            m.setGroupGw(gw);
            
            m.appendToFile(f);
            
            //add to lists
            addToLists(m,f.getNumberOfRecords()-1);
            
            return true;
        } else
        {
            //there is a such month
            return false;
        }
    }
    
    /**
     * Edits Month record in the database
     * according to given information.
     *
     * @param  m    Month object with new information
     * @param  position position in the file
     * @return     true if the editing was successful
     */
    public boolean editMonth(Month m, long position)
    {
        if (f.getNumberOfRecords() > position)
        {
            m.writeToFile(f,position);
            populateLists();
            return true;
        } else
        {
            return false;
        }
    }
    
    /**
     * Edits Month record in the database
     * according to given information.
     *
     * @param  m    Month object with new information
     * @return     true if the editing was successful
     */
    public boolean editMonth(Month m)
    {
        return editMonth (m,list.findPosition(m.getYear()+m.getMonth()));
    }        
    
    /**
     * Delete Month by given position.
     *
     * @param  position   position in the file
     * @return     true if successful
     */
    public boolean deleteMonth(long position)
    {
        if (f.delete(position))
        {
            populateLists();
            return true;
        } else return false;
    }
   
    /**
     * Delete Month by given object.
     *
     * @param  m    Month object
     * @return     true if successful
     */
    public boolean deleteMonth(Month m)
    {
        return f.delete(list.findPosition(m.getYear()+m.getMonth()));
    }     

    /**
     * Retrieves Month object from given position.
     *
     * @param  position record in file
     * @return     Month object
     */
    public Month getMonth(long position)
    {
        Month m = new Month(d);
        m.readFromFile(f,position);
        return m;
    }

    /**
     * Retrieves Month object by given key.
     *
     * @param  key  key of the month
     * @return     Month object
     */
    public Month getMonth(String key)
    {
        long position = list.findPosition(key);
        if (position != -1)
        {
            Month m = new Month(d);
            m.readFromFile(f,position);
            return m;
        } else
          return null;
    }
    
    
    /**
     * Returns all Months sorted by YYYYMM
     *
     * @return     the sum of x and y
     */
    public Month[] getAll()
    {
        Month[] months = new Month[getCount()];

        Item act = list.getFirst();
        int i = 0;
        while (act != null)
        {
            months[i] = getMonth(act.getPos());
            act = act.getNext();
            i++;
        }
        return months;
    }
    
    /**
     * Returns last N months in records. If n is greater
     * than number of actual Months available, the maximum
     * number of Months is returned.
     *
     * @param  n    number of months
     * @return     array of Months
     */
    public Month[] getLastMonths(int n)
    {
        Month[] months = getAll();
        if (n > months.length) n = months.length;
        Month[] ret = new Month[n];
        for (int i=0;i<n;i++)
        {
            ret[i] = months[months.length-i-1];
        }
        
        return ret;
    }

}
