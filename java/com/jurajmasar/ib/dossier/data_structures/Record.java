package com.jurajmasar.ib.dossier.data_structures;

import com.jurajmasar.ib.dossier.files.DirectAccessFile;
import com.jurajmasar.ib.dossier.root.Static;

/**
 * Abstract class Record - predefines the behaviour of an entity which is to be saved in DAF
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public abstract class Record implements Cloneable
{
    protected String[] data;

    /**
     * returns key of the record - what sorting of records is based on
     *
     * @return     array of strings
     */    
    public abstract String getKey();

    /**
     * Returns all data in an array
     *
     * @return     data array
     */
    public String[] toArray()
    {
        return data;
    }

    /**
     * Returns all data in a string
     *
     * @return     data string
     */
    public String toString()
    {
        return Static.implode(data);
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
     * writes the content of the record to the given DirectAccessFile
     *
     * @param  f    file to which the record should be written
     */
    public void writeToFile(DirectAccessFile f)
    {
        f.write(Static.encrypt(Static.implode(data)));
    }
    
    /**
     * writes the content of the record to the given DirectAccessFile
     * at given position
     *
     * @param  f    file to which the record should be written
     * @param  pos  position in the file
     */
    public void writeToFile(DirectAccessFile f, long pos)
    {
        f.write(Static.encrypt(Static.implode(data)), pos);
    }
    
    /**
     * reads and populates the record with info from file
     *
     * @param  f    DirectAccessFile to read from
     */
    public void readFromFile (DirectAccessFile f)
    {
        data = Static.explode(Static.decrypt(f.read()));
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
        /** DOSSIER: HL mastery 9 - parsing data stream **/                 
        data = Static.explode(Static.decrypt(f.read(pos)));
    }
    
    /**
     * appends the content of the record to the end of given file
     *
     * @param  f    DirectAccessFile to which record should be appended
     */
    public void appendToFile(DirectAccessFile f)
    {
        f.append(Static.encrypt(Static.implode(data)));
    }

    /**
     * reads and populates the record with info from given string
     *
     * @param  s    string with information
     */
    public void readFromString (String s)
    {
        data = Static.explode(Static.decrypt(s));
    }    

    /**
     * returns the content of the record 
     *
     * @return  imploded string with all information
     */
    public String writeToString()
    {
        return Static.encrypt(Static.implode(data));
    }
        
    
}
