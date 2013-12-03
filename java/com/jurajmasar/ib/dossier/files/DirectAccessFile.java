package com.jurajmasar.ib.dossier.files;

import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import java.io.*;
/**
 * Provides manipulation of random access files
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class DirectAccessFile
{
    private RandomAccessFile f; //pointer to random access file 
    private long recordLength; //defines number of bytes of one record in a file
    private boolean ready; //state of the file

    /**
     * constructor for objects of class DirectAccessFile
     * opens file for reading & writing or throws an I/O exception
     * 
     * @param fileName name of file to work with
     * @param        recordLength number of bytes of one record in a file
     */
    public DirectAccessFile(String fileName, long recordLength)
    {
        ready = false;
        try
        {
            f = new RandomAccessFile (fileName, "rw");
            this.recordLength = recordLength;
            ready = true;
        }
        catch (IOException e)
        {
            ready = false;
            Dialog.error("Opening file " + fileName + " was unsuccessful. \n Error: #1 "
                    + e.getMessage());
        }        
    }

    /**
     * checks if the file is ready for manipulation
     *
     * @return     true if the file is ready
     */
    public boolean isReady()
    {
        return ready;
    }

    /**
     * returns length of the file in bytes or throws an I/O exception
     *
     * @return     length of the file in bytes
     */
    public long getLength()
    {
        if (ready)
            try
            {
                  return f.length();
            } 
            catch (IOException e)
            {
                Dialog.error  ("The following error occured while working with file: #2 \n "
                               +e.getMessage());
                return -1;
            } 
        else
        {
            return -1;
        }
    }
    

    /**
     * returns length of one record
     *
     * @return     length of one record
     */
    public long getRecordLength()
    {
        if (ready)
          return recordLength;
        else return -1;  
    }

    /**
     * according to file length and length of one record it returns num. of records in a file.
     *
     * @return     number of records in a file
     */
    public long getNumberOfRecords()
    {
        if (ready)
          return getLength()/recordLength; 
        else return -1; 
    }

    /**
     * moves the file pointer to a different record or throws an exception
     *
     * @param  i    position of the record
     */
    public void goToRecord (long i)
    {
        try
        {
            if (ready && i >= 0)
                f.seek (i*recordLength); //uses RandomAccessFile method seek
        }
        catch (IOException e)
        {
            Dialog.error ("The following error occured while working with file: \n #3 "
                          +e.getMessage());
        }        
    }
    
    /**
     * writes string to the current record or throws an exception
     *
     * @param  s   string to write into file
     */
    public void write(String s)
    {
        try
        {
            //method editString firstly customizes the length of the string
            //to the length recordLength+2
            //-2 is there for the metadata of UTF8
            if (ready) f.writeUTF(Static.setStringLength(s, (int) recordLength - 2));
        }
        catch (IOException e)
        {
            Dialog.error ("The following error occured while working with file: \n #4 "
                          +e.getMessage());
        } 
    }
    
    /**
     * writes string to the record given or throws an exception
     *
     * @param  s   string to write into file
     * @param  record   position to which string should be written
     */
    public void write(String s, long record)
    {
        /** DOSSIER: HL mastery 1 - adding data to RAF by seek method **/ 
        if (ready)
        {
            goToRecord(record);
            write(s);
        }
    }
    
    /**
     * reads and returns string - content of current record or throws an exception
     *
     * @return     string - content of the current record
     */
    public String read ()
    {
        if (ready)
        {
            try
            {
                //readUTF returns all the characters until the null character \00
                //trim - function of a String - removes all spaces at the beginning and end of the string
                return f.readUTF().trim();
            }
            catch (IOException e)
            {
                Dialog.error ("The following error occured while working with file: \n #5 "
                              +e.getMessage());
                return null;
            }         
        } else
        {
            return null;
        }
    }
    
    /**
     * reads and returns string - content of record given or throws an exception
     *
     * @return     string - content of the record given
     */
    public String read (long record)
    {
        if (ready)
        {
            goToRecord (record);
            return read();
        } else
        {
            return null;
        }
    }
    
    /**
     * closes the pointer to the random access file or throws an exception
     *
     */
    public void close ()
    {
        if (ready)
        {
            ready = false;
            try
            {
                f.close(); //sends close signal to the RandomAccessFile object
            }
            catch (IOException e)
            {
                Dialog.error ("The following error occured while working with file: #6 \n "
                              +e.getMessage());
            }
        }
    } 


    /**
     * shortens file to the current number of records - the rest of the file is cut off
     * or throws an exception
     *
     */
    public void shortenFile ()
    {
        if (ready)
        {
            try
            {
                //uses the method of RandomAccessFile setLength to shorten the file
                f.setLength((getNumberOfRecords()-1)*recordLength);           
            }
            catch (IOException e)
            {
                Dialog.error ("The following error occured while working with file: #7 \n "
                               +e.getMessage());
            }     
        }
    }
    
    /**
     * appends string to the end of the file
     *
     * @param  s    string to append
     */
    public void append(String s)
    {
        if (ready) write (s, getNumberOfRecords());
    }

    /**
     * Deletes the currect record.
     *
     * @param position  number of record
     * @return true if successful
     */
    public boolean delete(long position)
    {
        /** DOSSIER: HL mastery 2 - deleting data from RAF by seek method **/ 
        if (ready && position < getNumberOfRecords())
        {
            write (read(getNumberOfRecords()-1), position);
            shortenFile();
            return true;
        }
        else return false;
    }

}
