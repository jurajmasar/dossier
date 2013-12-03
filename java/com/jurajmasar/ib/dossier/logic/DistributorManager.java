package com.jurajmasar.ib.dossier.logic;
import com.jurajmasar.ib.dossier.data_structures.*;
import com.jurajmasar.ib.dossier.files.DirectAccessFile;
import com.jurajmasar.ib.dossier.root.Static;

/**
 * Manipulates Distributor entities and their info in file.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class DistributorManager
{
    private static DirectAccessFile f; //file with records of distributors
    private static TreeItem root; //pointer to first TreeItem in the structure
    public static List listRegNum, listName, listSurname, listSponsor;
    
    /**
     * Populates Lists with information from records
     *
     */
    private static void populateLists()
    {
        init();

        //initialize queue for populating tree of distributors
        Vector<TreeItem> queue = new Vector<TreeItem>();

        //create lists
        listRegNum      = new List(true);   
        listName        = new List(true);
        listSurname     = new List(true);
        listSponsor     = new List(true);        
        
        //populate lists
        for (int i=0;i<=f.getNumberOfRecords()-1;i++)
        {
            Distributor d = new Distributor();
            d.readFromFile(f,i);
            
            //set the root for tree of distributors
            if (d.getSponsor().trim().equals("0"))
            { 
                root = new TreeItem (d.getRegNum());
                root.position = i;
                root.name = d.getName();
                root.surname = d.getSurname();
                root.registrationDate = Static.stringToDate(d.getRegistrationDate());
                queue.pushBack(root);
            }
            //populating
            addToLists(d, i);   
        }
        
        //fix listRegNum -> tree for item with sponsor 0 bug
        ItemSponsor itemSponsor = (ItemSponsor)listSponsor.find("0");
        if (itemSponsor != null) itemSponsor.itemRegNum.treeItem = root;
        //populateTree
        while (!queue.isEmpty())
        {
            TreeItem act = queue.popFront();
            Item[] distributors = listSponsor.findMore(act.regNum);
            if (distributors != null)
            {
                TreeItem[] children = new TreeItem[distributors.length];                               
                
                for (int i=0;i<distributors.length;i++)
                {
                    TreeItem child = new TreeItem (((ItemSponsor) distributors[i]).regNum);
                    
                    //set properties for new child
                    child.position = distributors[i].getPos();
                    child.parent = act;
                    child.name = ((ItemSponsor) distributors[i]).name;
                    child.surname = ((ItemSponsor) distributors[i]).surname;                    
                    child.registrationDate = ((ItemSponsor) distributors[i]).registrationDate;
                    children[i] = (TreeItem) child.clone();
                    
                    //set new pointer from listRegNum to tree
                    ((ItemSponsor) distributors[i]).itemRegNum.treeItem = children[i];
                    
                    queue.pushBack(children[i]);
                }
                
                act.children = children.clone();
            } else
              act.children = null;
        }
        
    }

    /**
     * Prints the tree structure of distributors in the system.
     * For debugging purposes.
     *
     */
    public static void printTree ()
    {
        init();
        if (root != null) root.print(0);
    }

    
    /**
     * Initializes static variables of the object.
     *
     */
    public static void init()
    {
        if (!(f instanceof DirectAccessFile))
        {
            //initialize pointer to DAF 
            f = new DirectAccessFile (Static.fileDistributor, Static.distributorRecordLength);     
            
            //create lists
            listRegNum      = new List(true);   
            listName        = new List(true);
            listSurname     = new List(true);
            listSponsor     = new List(true);
            
            if (getCount() > 0) populateLists();
        } 
    }

    
    /**
     * Returns the number of distributors in the system.
     *
     * @return     the number of distributors in the system
     */
    public static int getCount()
    {
        init();
        return (int) f.getNumberOfRecords();
    }

    /**
     * Adds new distributor to the system. 
     * It creates new record in DAF and also adds new
     * item to particular distributor lists.
     * 
     * @param regNum           registration number
     * @param sponsor          sponsor's registration number
     * @param country          distributor's country
     * @param registrationDate registration date
     * @param status           distributor's status   
     * @param name             distributor's name
     * @param surname          distributor's surname
     * @param birthDate        distributor's birthDate
     * @param email            distributor's email
     * @param telephone        distributor's telephone
     * @param note             note about distributor
     */
    public static boolean addDistributor(
        String regNum,
        String sponsor,
        String country,
        String registrationDate,
        String status,
        String name,
        String surname,
        String birthDate, 
        String email,
        String telephone, 
        String note
    )
    {
        init();
        if (!Static.isNumeric(regNum)) return false;
        Distributor d = new Distributor (regNum);
        
        //check if there is no such regNum in the system
        if (listRegNum.findPosition(regNum) == -1) 
        {        
            d.setSponsor(sponsor);
            d.setCountry(country);
            d.setRegistrationDate(registrationDate);
            d.setStatus(status);
            d.setName(name);
            d.setSurname(surname);
            d.setBirthDate(birthDate);
            d.setEmail(email);
            d.setTelephone(telephone);
            d.setNote(note);
            
            d.appendToFile(f);
            
            //add to lists
            //addToLists(d,f.getNumberOfRecords()-1);
            populateLists(); //addToLists is insufficient because TreeItem uses fixed array for children
            
            return true;
        } else
        {
            //there is a distributor with such regNum
            return false;
        }
    }
    
    
    
    /**
     * Adds distributor given in parameters to lists
     *
     * @param regNum           registration number
     * @param sponsor          sponsor's registration number
     * @param country          distributor's country
     * @param registrationDate registration date
     * @param status           distributor's status   
     * @param name             distributor's name
     * @param surname          distributor's surname
     * @param birthDate        distributor's birthDate
     * @param email            distributor's email
     * @param telephone        distributor's telephone
     * @param note             note about distributor
     * @param position         position of record in DAF
     */
    private static void addToLists(
        String regNum,
        String sponsor,
        String country,
        String registrationDate,
        String status,
        String name,
        String surname,
        String birthDate, 
        String email,
        String telephone, 
        String note,
        long position //position of this record in file
    )
    {
        init();
        Distributor d = new Distributor (regNum);

        d.setSponsor(sponsor);
        d.setCountry(country);
        d.setRegistrationDate(registrationDate);
        d.setStatus(status);
        d.setName(name);
        d.setSurname(surname);
        d.setBirthDate(birthDate);
        d.setEmail(email);
        d.setTelephone(telephone);
        d.setNote(note);
        
        addToLists(d, position);
    }

    /**
     * Adds distributor given in parameters to lists
     *
     * @param d                object Distributor
     * @param position         position of record in DAF
     */
    private static void addToLists(Distributor d, long position)
    {
       init();
       ItemRegNum clone, itemRegNum = new ItemRegNum(Static.removeDiacritics(d.getRegNum()).toLowerCase(),
                                    position,null);
       clone = (ItemRegNum) itemRegNum.clone();                             
       listRegNum.add(clone);
       listName.add(Static.removeDiacritics(d.getName()).toLowerCase(), position);
       listSurname.add(Static.removeDiacritics(d.getSurname()).toLowerCase(), position);
       listSponsor.add(new ItemSponsor(Static.removeDiacritics(d.getSponsor()).toLowerCase(), 
                             position,null,d.getRegNum(),d.getRegistrationDate(),clone,d.getName(),
                             d.getSurname()));           
    }    
    

    /**
     * Reads from file and returns array of objects
     * of all distributors in the system.
     *
     * @return     array of distributor objects
     *
     */
    public static Distributor[] getAllDistributors ()
    {
        init();
        Distributor[] distributors = new Distributor[(int) f.getNumberOfRecords()];
        
        for (int i=0;i<=f.getNumberOfRecords()-1;i++)
        {
            distributors[i] = new Distributor();
            distributors[i].readFromFile(f,(long) i);            
        }
        
        return distributors;
    }

    /**
     * Returns array of Distributors from records from given positions.
     *
     * @param  v   vector of positions (long)
     * @return     array of distributors
     */
    public static Distributor[] getDistributorsFromPositions(Vector<Long> v)
    {
        init();
        Distributor[] distributors = new Distributor[v.size()];
        
        Distributor d = new Distributor();
        int i=0;
        while (!v.isEmpty())
        {
            d.readFromFile(f, v.popFront());
            distributors[i] = (Distributor) d.clone();
            i++;
        }
        
        return distributors;        
    }

    /**
     * Returns array of Distributors from records from given positions.
     *
     * @param  v   vector of positions (long)
     * @return     array of distributors
     */
    public static Distributor[] getDistributorsFromPositions(int[] positions)
    {
        init();
        Distributor[] distributors = new Distributor[positions.length];
        
        Distributor d = new Distributor();
        int index = 0;
        for (int i=0;i<=positions.length-1;i++)
        {
            d.readFromFile(f,(long) positions[i]);
            distributors[index] = (Distributor) d.clone();
            index++;
        }
        
        return distributors;        
    }    
    
    /**
     * Return array of distributors by search in a list
     *
     * @param  list list of distributors
     * @param needle  string to find
     * @return     array of distributors
     */
    public static Distributor[] findInList(List list, String needle)
    {
        init();
        return getDistributorsFromPositions(list.findPositions(needle).toIntArray());
    }

    /**
     * Find distributors according to given parameters in String.
     *
     * @param  regNum   registration number in string
     * @param   firstName   first name of distributor
     * @param   lastName    last name of distributor
     * @return     array of distributors
     */
    public static Distributor[] find(String regNum, String name, String surname)
    {
        init();
        int c = 0;
        if (regNum != null && !regNum.isEmpty()) c++;
        if (name != null && !name.isEmpty()) c++;
        if (surname != null && !surname.isEmpty()) c++;
        
        int[][] a = new int[c][];
        int i=0;
        
        if (regNum != null && !regNum.isEmpty())
        {
           a[i] = listRegNum.findPositionsSubstring(Static.removeDiacritics(regNum).toLowerCase())
                  .toIntArray();
           i++;
        }
        if (name != null && !name.isEmpty())
        {
           a[i] = listName.findPositionsSubstring(Static.removeDiacritics(name).toLowerCase())
                  .toIntArray();
           i++;
        }
        if (surname != null && !surname.isEmpty())
        {
           a[i] = listSurname.findPositionsSubstring(Static.removeDiacritics(surname).toLowerCase())
                  .toIntArray();
           i++;
        }
        int[] arr = Static.conjunction(a);
        return getDistributorsFromPositions(arr);
    }

    /**
     * Retrieves Distributor object by given regNum.
     *
     * @param  regNum   registration number of the distributor
     * @return     distributor object
     */
    public static Distributor getDistributorByRegNum(String regNum)
    {
        /** DOSSIER: HL mastery 3 - Searching for a specified data in a file **/ 
        init();
        long position = listRegNum.findPosition(regNum);
        if (position == -1) return null;
          else
          {
            Distributor d = new Distributor ();
            d.readFromFile(f, position);
            return d;
        }
    }

    
    /**
     * Edits distributor's record in the database
     * according to given information.
     *
     * @param  d    distributor object with new information
     * @param  position position in the file
     * @return     true if the editing was successful
     */
    public static boolean editDistributor(Distributor d, long position)
    {
        init();
        if (f.getNumberOfRecords() > position)
        {
            d.writeToFile(f,position);
            populateLists();
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Edits distributor's record in the database 
     * according to given information.
     *
     * @param regNum           registration number
     * @param sponsor          sponsor's registration number
     * @param country          distributor's country
     * @param registrationDate registration date
     * @param status           distributor's status   
     * @param name             distributor's name
     * @param surname          distributor's surname
     * @param birthDate        distributor's birthDate
     * @param email            distributor's email
     * @param telephone        distributor's telephone
     * @param note             note about distributor
     * @param position         record number in the file
     * @return true if editing was successful
     */
    public static boolean editDistributor(
        String regNum,
        String sponsor,
        String country,
        String registrationDate,
        String status,
        String name,
        String surname,
        String birthDate, 
        String email,
        String telephone, 
        String note,
        long position
    )    
    {
        init();
        Distributor d = new Distributor (regNum);
        
        d.setSponsor(sponsor);
        d.setCountry(country);
        d.setRegistrationDate(registrationDate);
        d.setStatus(status);
        d.setName(name);
        d.setSurname(surname);
        d.setBirthDate(birthDate);
        d.setEmail(email);
        d.setTelephone(telephone);
        d.setNote(note);
        
        return editDistributor (d, position);
    }

    /**
     * Edits distributor's record in the database
     * according to given information.
     *
     * @param  d    distributor object with new information
     * @param  regNum registration number
     * @return     true if editing was successful
     */
    public static boolean editDistributor(Distributor d)
    {
        init();
        return editDistributor (d,listRegNum.findPosition(d.getRegNum()));
    }    


    /**
     * Edits distributor's record in the database 
     * according to given information.
     *
     * @param regNum           registration number
     * @param sponsor          sponsor's registration number
     * @param country          distributor's country
     * @param registrationDate registration date
     * @param status           distributor's status   
     * @param name             distributor's name
     * @param surname          distributor's surname
     * @param birthDate        distributor's birthDate
     * @param email            distributor's email
     * @param telephone        distributor's telephone
     * @param note             note about distributor
     * @return true if editing was successful
     */
    public static boolean editDistributor(
        String regNum,
        String sponsor,
        String country,
        String registrationDate,
        String status,
        String name,
        String surname,
        String birthDate, 
        String email,
        String telephone, 
        String note
    )    
    {
        init();
        Distributor d = new Distributor (regNum);
        
        d.setSponsor(sponsor);
        d.setCountry(country);
        d.setRegistrationDate(registrationDate);
        d.setStatus(status);
        d.setName(name);
        d.setSurname(surname);
        d.setBirthDate(birthDate);
        d.setEmail(email);
        d.setTelephone(telephone);
        d.setNote(note);
        
        return editDistributor (d);
    }
    
    /**
     * Delete distributor by given position.
     *
     * @param  position   position in the file
     * @return     true if successful
     */
    public static boolean deleteDistributor(long position)
    {
        init();
        return deleteDistributor(listRegNum.findByPosition(position).getKey());
    }

   
    /**
     * Delete distributor by given regNum.
     * and trigger to repopulate lists.
     * 
     * @param  regNum   registration number
     * @param  repopulate boolean
     * @return     true if successful
     */
    public static boolean deleteDistributor(String regNum)
    {
        init();
        
        //delete all children
        Item[] items = listSponsor.findMore(regNum);
        if (items != null) 
        {
            String childrenRegNums[] = new String[items.length];
            
            for (int i=0;i<items.length;i++) 
              childrenRegNums[i] = ((ItemSponsor) items[i]).itemRegNum.getKey();
            
            for (int i=0;i<childrenRegNums.length;i++) deleteDistributor (childrenRegNums[i]);
        }
        //delete current Distributor        
        if (f.delete(listRegNum.findPosition(regNum))) 
        {
            populateLists();
            return true;
        }
          else return false;
    }
      
    /**
     * Delete distributor by given object.
     *
     * @param  d    distributor object
     * @return     true if successful
     */
    public static boolean deleteDistributor(Distributor d)
    {
        init();
        return deleteDistributor(d.getRegNum());
    }    
    
    /**
     * Retrieves distributor object according to its sponsor.
     * Returns the first occurence in list.
     *
     * @param  sponsor string
     * @return     distributor object
     */
    public static Distributor getDistributorBySponsor(String sponsor)
    {
        init();
        ItemSponsor item = ((ItemSponsor)listSponsor.find(sponsor));
        if (item == null) return null;
        return getDistributorByRegNum(item.regNum);
    }


    /**
     * From a given array of regNums this method returns array of distributors
     * of all distributors given in vector together with all their children.
     *
     * @param  regNums  vector filled with regNums
     * @return     array of distributors
     */
    public static Distributor[] getDistributorsWithDirectChildrenByRegNums(Vector<String> regNums)
    {
        init();
        /** DOSSIER: HL mastery 10 - hierarchical composite data structure **/                 
        Vector<Distributor> ret = new Vector<Distributor>(); //array for returning
        String[] regNumsArray = regNums.toStringArray();
        for (int i=0;i<regNumsArray.length;i++)
        {
            Distributor d = getDistributorByRegNum(regNumsArray[i]);
            ret.pushBack (d);

            //all their children
            Distributor[] children = d.getDirectChildrenDistributors();
            
            if (children != null)
            for (int b=0;b<children.length;b++)
            {
                if (regNums.indexOf(children[b].getRegNum()) == -1)
                {
                    regNums.pushBack(children[b].getRegNum());
                    
                    ret.pushBack(children[b]);
                }
            }
        }
         return ret.toDistributorArray();
    }

    
    
    
    
    
    
    

}
