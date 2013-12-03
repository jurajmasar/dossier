package com.jurajmasar.ib.dossier.files;

import com.jurajmasar.ib.dossier.root.Dialog;

import java.io.*;
/**
 * Used for reading text from a sequential file.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class TextFileReader
{
    private BufferedReader br; //java's object for reading sequential files
    private String line;
    private boolean ready; //states whether the file is ready
    /**
     * constructor for objects of class TextFileReader.
     * initializes the access to the file or throws an exception.
     * 
     * @param fileName name of the file to read
     */
    public TextFileReader(String fileName)
    {
        line = null;
        ready = false;
        try 
        {
            br = new BufferedReader(new FileReader(fileName));
            ready = true;
        }
        catch (IOException e)
        {
            ready = false;
            Dialog.error("Opening file " + fileName + " was unsuccessful.");
        }                 
    }

    /**
     * checks whether file is ready for reading
     *
     * @return     true if the file is ready for reading
     */
    public boolean isReady()
    {
        return ready;
    }
    
    /**
     * reads and returns current line from the sequential file or throws an I/O exception
     *
     * @return     current line in the sequential file
     */
    public String readLine()
    {
       String line = "";
       try 
       {
           if (ready) line = br.readLine();
           if (line == null) ready = false;
           return line;
       }
       catch (IOException e)
       {
            ready = false;
            Dialog.error("Reading from file was unsuccessful.");
            return null;
       }   
    }

}
