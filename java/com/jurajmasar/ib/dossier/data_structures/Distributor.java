package com.jurajmasar.ib.dossier.data_structures;
import com.jurajmasar.ib.dossier.files.DirectAccessFile;
import com.jurajmasar.ib.dossier.logic.DistributorManager;
import com.jurajmasar.ib.dossier.logic.MonthManager;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

/**
 * defines the way the entity distributor is stored in file
 * 
 * @author Juraj Masar 
 * @version 0.1
 */
/** DOSSIER: SL mastery 3 - objects as data records **/
/** DOSSIER: HL mastery 7 - inheritance **/ 
public class Distributor extends Record
{
    public MonthManager months;
    private TreeItem tree;
    /**
     * constructor for objects of class Distributor
     * initializes the data variable
     */
    public Distributor()
    {
        //initialization of object variables
        months = null;
        tree = null;
        
        data = new String[11]; //allocates memory for all required information about Distributor
        //the order of information in data array is as (with max chars possible):
        //data[0] - registration number     - 10  - getRegNum / setRegNum
        //data[1] - sponsor number          - 10  - getSponsor / setSponsor
        //data[2] - country                 - 3   - getCountry / setCountry
        //data[3] - date of registration    - 15  - getRegistrationDate / setRegistrationDate
        //data[4] - status                  - 5   - getStatus / setStatus
        //data[5] - name                    - 20  - getName / setName
        //data[6] - surname                 - 20  - getSurname / setSurname
        //data[7] - date of birth           - 15  - getBirthDate / setBirthDate
        //data[8] - email                   - 50  - getEmail / setEmail
        //data[9] - telephone               - 20  - getTelephone / setTelephone
        //data[10] - note                   - 300 - getNote / setNote
        
        //initialize values of all properties to empty string
        for (int i=0;i<=data.length-1;i++) data[i] = new String();
        
    }

    /**
     * constructor for objects of class Distributor
     * initializes the data variable and sets regNum
     */
    public Distributor(String regNum)
    {
        //initialization of object variables
        months = null;
        tree = null;
        
        data = new String[11]; //allocates memory for all required information about Distributor
        //initialize values of all properties to empty string
        for (int i=0;i<=data.length-1;i++) data[i] = new String();
        if (!setRegNum(regNum)) Dialog.error("Error while creating new distributor:\n" +
                "the registration number has to be numeric.");
    }    

    /**
     * returns the key according to which it is logical to sort distributors
     *
     * @return     registration number of a distributor
     */
    public String getKey()
    {
        return getRegNum();
    }

    /**
     * returns registration number of a distributor
     *
     * @return     registration number
     */
    public String getRegNum()
    {
        return data[0];
    }
    
    /**
     * Checks if distributor is the root distributor in the system.
     *
     * @return     true if it is
     */
    public boolean isRoot()
    {
        if (getSponsor().equals("0")) return true;
          else return false;
    }

    /**
     * Returns the generation of distributors to which
     * this distributor belongs to.
     *
     * @return     generation (0 is root)
     */
    public int getGeneration()
    {
        if (checkTree())
        {
            TreeItem t = tree;
            int generation = 0;
            while (t.parent != null)
            {
                t = t.parent;
                generation++;
            }
            return generation;
        } else
        {
            return -1;
        }
    }

    
    /**
     * sets registratration number of a distributor to the string given
     *
     * @param  s    registration number
     * @return true if successful
     */
    private boolean setRegNum (String s)
    {
        if (Static.isNumeric(s))
        {
          data[0] = Static.cutIfLongerThan(s,10);
          
          //initialize MonthManager for this Distributor
          months = new MonthManager(this);
          
          return true;
        } else
          return false;
    }

    /**
     * returns sponsor number of a distributor
     *
     * @return     sponsor number
     */
    public String getSponsor()
    {
        return data[1];
    }
    
    /**
     * sets sponsor of a distributor to the string given
     *
     * @param  s    sponsor number
     */
    public boolean setSponsor (String s)
    {
        if (Static.isNumeric(s))
        {
          data[1] = Static.cutIfLongerThan(s,10);
          return true;
        } else
          return false;
    }    
    
    /**
     * returns country of a distributor
     *
     * @return     country
     */
    public String getCountry()
    {
        return data[2];
    }
    
    /**
     * sets country of a distributor to the string given
     *
     * @param  s    country
     */
    public void setCountry (String s)
    {
        data[2] = Static.cutIfLongerThan(s,3);
    }     
    
    /**
     * returns date of registration of a distributor
     *
     * @return     date of registration
     */
    public String getRegistrationDate()
    {
        return data[3];
    }
    
    /**
     * sets registration date of a distributor to the string given
     *
     * @param  s    registration date
     */
    public void setRegistrationDate (String s)
    {
        data[3] = Static.cutIfLongerThan(s,15);
    }     
    
    /**
     * returns status of a distributor
     *
     * @return     status
     */
    public String getStatus()
    {
        return data[4];
    }
    
    /**
     * sets status of a distributor to the string given
     *
     * @param  s    status
     */
    public void setStatus (String s)
    {
        data[4] = Static.cutIfLongerThan(s,5);
    }       
    
    /**
     * returns name of a distributor
     *
     * @return     name
     */
    public String getName()
    {
        return data[5];
    }
    
    /**
     * sets name of a distributor to the string given
     *
     * @param  s    name
     */
    public void setName (String s)
    {
        data[5] = Static.cutIfLongerThan(s,20);
    }      
    
    /**
     * returns surname of a distributor
     *
     * @return     surname
     */
    public String getSurname()
    {
        return data[6];
    }
    
    /**
     * Returns the full name of the distributor 
     * a combination of their first name and surname.
     *
     * @return     fullname of distributor
     */
    public String getFullname()
    {
        return getName()+" "+getSurname();
    }

    /**
     * sets surname of a distributor to the string given
     *
     * @param  s    surname
     */
    public void setSurname (String s)
    {
        data[6] = Static.cutIfLongerThan(s,20);
    }     
    
    /**
     * returns date of birth of a distributor
     *
     * @return     date of birth
     */
    public String getBirthDate()
    {
        return data[7];
    }
    
    /**
     * sets birth date of a distributor to the string given
     *
     * @param  s    birth date
     */
    public void setBirthDate (String s)
    {
        data[7] = Static.cutIfLongerThan(s,15);
    }   
    
    /**
     * returns email of a distributor
     *
     * @return     email
     */
    public String getEmail()
    {
        return data[8];
    }
    
    /**
     * sets email of a distributor to the string given
     *
     * @param  s    email
     */
    public void setEmail (String s)
    {
        data[8] = Static.cutIfLongerThan(s,50);
    }      

    /**
     * returns telephone of a distributor
     *
     * @return     telephone
     */
    public String getTelephone()
    {
        return data[9];
    }
    
    /**
     * sets telephone of a distributor to the string given
     *
     * @param  s    telephone
     */
    public void setTelephone (String s)
    {
        data[9] = Static.cutIfLongerThan(s,20);
    }      
    
    /**
     * returns note of a distributor
     *
     * @return     note
     */
    public String getNote()
    {
        return data[10];
    }
    
    /**
     * sets note of a distributor to the string given
     *
     * @param  s    note
     */
    public void setNote (String s)
    {
        data[10] = Static.cutIfLongerThan(s,300);
    }      
    
    /**
     * Saves the record to the database.
     *
     * @return     true if successful
     */
    public boolean save()
    {
        return DistributorManager.editDistributor(this);
    }

    /**
     * Deletes the record of particular distributor in database.
     *
     * @return     true if successful
     */
    public boolean delete()
    {
        return DistributorManager.deleteDistributor(this);
    }

    /**
     * reads and populates the record with info from given string
     *
     * @param  s    string with information
     */
    public void readFromString (String s)
    {
        super.readFromString(s);
        
        //initialize MonthManager for this Distributor
        months = new MonthManager(this);        
    }     
    
    /**
     * reads and populates the record with info from file
     *
     * @param  f    DirectAccessFile to read from
     */
    /** DOSSIER: HL mastery 6 - polymorphism **/ 
    public void readFromFile (DirectAccessFile f)
    {
        super.readFromFile (f);
        
        //initialize MonthManager for this Distributor
        months = new MonthManager(this);                
    }

    /**
     * reads and populates the record with info from file 
     * from given position
     *
     * @param  f    DirectAccessFile to read from
     * @param pos   position in the file to read from
     */
    public void readFromFile (DirectAccessFile f, long pos)
    {
        super.readFromFile (f, pos);
        
        //initialize MonthManager for this Distributor
        months = new MonthManager(this);                
        
    }
    
    /**
     * Checks whether the pointer to TreeItem is ready.
     * If not it makes it ready.
     * 
     * @return true if successful
     */
    public boolean checkTree ()
    {
         tree = ((ItemRegNum)DistributorManager.listRegNum.find(getRegNum())).treeItem;
         if (tree != null) return true;
           else return false;
    }

    /**
     * Checks if the tree pointer is correct and returns it
     *
     * @return     pointer to TreeItem
     */
    public TreeItem getTree()
    {
        if (checkTree()) return tree;
          else return null;
    }

    /**
     * Returns the number of direct children of this distributor
     *
     * @return      number of direct children
     */
    public int getNumberOfDirectChildren()
    {
        if (checkTree()) 
          return tree.getNumberOfDirectChildren();
        else return -1;  
    }

    /**
     * Returns the number of all children of this distributor
     *
     * @return      number of all children
     */
    public int getNumberOfAllChildren()
    {
        if (checkTree()) 
          return tree.getNumberOfAllChildren();
        else return -1;  
    }    

    /**
     * Retrieves direct children Distributors
     *
     * @return     array of Distributor objects
     */
    public Distributor[] getDirectChildrenDistributors()
    {
       if (checkTree())
       {
           Vector<Long> positions = new Vector<Long>();
           if (tree.children != null)
             for (int i=0;i<tree.children.length;i++) positions.pushBack (tree.children[i].position);
           return DistributorManager.getDistributorsFromPositions(positions);
        } else return null;
    }

    /**
     * Retrieves all children Distributors
     *
     * @return     array of Distributor objects
     */
    public Distributor[] getAllChildrenDistributors()
    {
       if (checkTree())
       {
           Vector<Long> positions = new Vector<Long>();
           tree.getPositions(positions, false);
           return DistributorManager.getDistributorsFromPositions(positions);
        } else return null;
    }    
    
}
