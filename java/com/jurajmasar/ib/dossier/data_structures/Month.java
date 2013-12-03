package com.jurajmasar.ib.dossier.data_structures;

import com.jurajmasar.ib.dossier.root.Static;

import java.util.Date;

/**
 * defines the way the entity month (of a distributor) is stored in file
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class Month extends Record
{
    Distributor d; //distributor to which this Month belongs to
    /**
     * constructor for objects of class Month
     * initializes the data variable
     */
    public Month(Distributor d)
    {
        data = new String[6]; //allocates memory for all required information about Month
        //the order of information in data array is as:
        //data[0] - year     - 4  - getYear / setYear
        //data[1] - month    - 2  - getMonth / setMonth
        //data[2] - pw       - 10 - getPw / setPw
        //data[3] - grouppw  - 10 - getGroupPw / setGroupPw
        //data[4] - gw       - 10 - getGw / setGw
        //data[5] - groupgw  - 10 - getGroupGw / setGroupGw
        
        //initialize values of all properties to empty string
        for (int i=0;i<=data.length-1;i++) data[i] = new String();
        
        //set pointer do Distributor
        this.d = d;
    }

    /**
     * Checks whether Month has been filled with data or not
     *
     * @return     true if empty
     */
    public boolean isEmpty()
    {
        return !(getPwInt() != 0 || getGroupPwInt() != 0 || getGwInt() != 0 || getGroupGwInt() != 0);
    }

    /**
     * returns the key according to which it is logical to sort distributors
     *
     * @return     combination of year and month
     */
    public String getKey()
    {
        return getYear()+getMonth();
    }    
    
    /**
     * returns year of the Month object
     *
     * @return     year
     */
    public String getYear()
    {
        return data[0];
    }
    
    /**
     * sets year of the Month object to the string given
     *
     * @param  s    year
     */
    public void setYear (String s)
    {
        data[0] = Static.cutIfLongerThan(s, 4);
    }      
    
    /**
     * returns month of the Month object
     *
     * @return     month
     */
    public String getMonth()
    {
        return data[1];
    }
    
    /**
     * sets month of the Month object to the string given
     *
     * @param  s    month
     */
    public void setMonth (String s)
    {
        data[1] = Static.cutIfLongerThan(s,2);
    }      
    
    /**
     * returns Pw of the Month object
     *
     * @return     Pw
     */
    public String getPw()
    {
        return data[2];
    }

    /**
     * returns Pw of the Month object
     *
     * @return     Pw
     */
    public int getPwInt()
    {
        try
        {
          return Integer.parseInt(data[2]);
        } catch (java.lang.NumberFormatException e)
        {
            return 0;
        }
    }    
    
    /**
     * sets Pw of the Month object to the string given
     *
     * @param  s    Pw
     */
    public void setPw (String s)
    {
        data[2] = Static.cutIfLongerThan(s,10);
    }      
    
    /**
     * returns GroupPw of the Month object
     *
     * @return     GroupPw
     */
    public String getGroupPw()
    {
        return data[3];
    }

    /**
     * returns GroupPw of the Month object
     *
     * @return     GroupPw
     */
    public int getGroupPwInt()
    {
        try
        {
            return Integer.parseInt(data[3]);
        } catch (java.lang.NumberFormatException e)
        {
            return 0;
        }
    }    
    
    /**
     * sets GroupPw of the Month object to the string given
     *
     * @param  s    GroupPw
     */
    public void setGroupPw (String s)
    {
        data[3] = Static.cutIfLongerThan(s,10);
    }      
    
    /**
     * returns Gw of the Month object
     *
     * @return     Gw
     */
    public String getGw()
    {
        return data[4];
    }

    /**
     * returns Gw of the Month object
     *
     * @return     Gw
     */
    public int getGwInt()
    {
        try
        {
            return Integer.parseInt(data[4]);
        } catch (java.lang.NumberFormatException e)
        {
            return 0;
        }
    }    
    
    /**
     * sets Gw of the Month object to the string given
     *
     * @param  s    Gw
     */
    public void setGw (String s)
    {
        data[4] = Static.cutIfLongerThan(s,10);
    }      
    
    /**
     * returns GroupGw of the Month object
     *
     * @return     GroupGw
     */
    public String getGroupGw()
    {
        return data[5];
    }

    /**
     * returns GroupGw of the Month object
     *
     * @return     GroupGw
     */
    public int getGroupGwInt()
    {
        try
        {
            return Integer.parseInt(data[5]);
        } catch (java.lang.NumberFormatException e)
        {
            return 0;
        }
    }    
    
    /**
     * sets GroupGw of the Month object to the string given
     *
     * @param  s    GroupGw
     */
    public void setGroupGw (String s)
    {
        data[5] = Static.cutIfLongerThan(s,10);
    }          

    /**
     * Saves the record to the database.
     *
     * @return     true if successful
     */
    public boolean save()
    {
        return d.months.editMonth(this);
    }

    /**
     * Deletes the record of particular Month in database.
     *
     * @return     true if successful
     */
    public boolean delete()
    {
        return d.months.deleteMonth(this);
    }    

    /**
     * Calculates total pw
     *
     * @return     the sum of pw and groupPw
     */
    public int getTotalPw()
    {
        return getPwInt()+getGroupPwInt();
    }    
    

    /**
     * Calculates total Gw
     *
     * @return     the sum of pw and groupPw
     */
    public int getTotalGw()
    {
        return getGwInt()+getGroupGwInt();
    }    
    

    /**
     * Determines the level for current Month
     *
     * @return     level in MLM system
     */
    public int getLevel()
    {
        if (getTotalPw() < 2) return 0;
        else if (getTotalPw() >= 2 && getTotalPw() < 500) return 3;
        else if (getTotalPw() >= 500 && getTotalPw() < 1000) return 6;
        else if (getTotalPw() >= 1000 && getTotalPw() < 2000) return 9;
        else if (getTotalPw() >= 2000 && getTotalPw() < 4000) return 12;
        else if (getTotalPw() >= 4000 && getTotalPw() < 8000) return 15;
        else if (getTotalPw() >= 8000 && getTotalPw() < 12000) return 18;
        else return 21;
    }    

    /**
     * Determines the bonus level according to given number
     * of distributors with level 21;
     *
     * @param  count21 distributors with level 21
     * @return     bonus level
     */
    public static double getBonusLevel(int count21)
    {
        if (count21 < 1) return 0;
        if (count21 == 1) return 7;
        if (count21 < 4) return 7.5;        
        if (count21 < 6) return 8;
        if (count21 < 8) return 8.5;        
        if (count21 < 10) return 9;        
        if (count21 < 12) return 9.5;        
          else return 10;        
    }

    /**
     * Calculates provision for particular month
     *
     * @return     provision
     */
    public int getProvision()
    {
        double provision = 0;
        
        //init provision from differences + provision from 21 levels
        int count21 = 0;
        double totalGw21 = 0;
        
        Distributor[] children = d.getDirectChildrenDistributors();
        for (int i=0;i<children.length;i++)
        {
            Month m = children[0].months.getMonth(getKey());
            if (m != null && !m.isEmpty())
            {
                if (m.getLevel() == 21)
                {
                    count21++;
                    totalGw21 += m.getTotalGw();
                } else
                provision += (double)(getLevel()-m.getLevel())*m.getTotalGw();
            }
        }
        int prov = (int) (provision + (double)(getBonusLevel(count21)*totalGw21)
               +(double)getLevel()*getGwInt());

        if (prov < 0) return 0; //check for incorrect data
          else return prov;
    }
    
    /**
     * Calculates the number of children distributors of particular distributor
     * who were registered until the end of this month. 
     * 
     * @return number of direct children
     */
    public int getNumberOfDirectChildren ()
    {
        if (isEmpty()) return 0;
        
        //get border date
        int bmonth = Integer.parseInt(getMonth());
        int byear = Integer.parseInt(getYear());
        
        //increase month
        if (bmonth == 12)
        {
            bmonth = 1;
            byear ++;
        } else
        {
            bmonth ++;
        }
        
        return d.getTree().getNumberOfDirectChildrenBefore("1."+bmonth+"."+byear);
    }

    /**
     * Calculates the number of children distributors of particular distributor
     * who were registered until the end of this month. 
     * 
     * @return number of direct children
     */
    public int getNumberOfAllChildren ()
    {
        if (isEmpty()) return 0;
        
        //get border date
        int bmonth = Integer.parseInt(getMonth());
        int byear = Integer.parseInt(getYear());
        
        //increase month
        if (bmonth == 12)
        {
            bmonth = 1;
            byear ++;
        } else
        {
            bmonth ++;
        }
        
        return d.getTree().getNumberOfAllChildrenBefore("1."+bmonth+"."+byear);
    }    
    
}
