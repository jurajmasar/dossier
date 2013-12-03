package com.jurajmasar.ib.dossier.files;

import com.jurajmasar.ib.dossier.root.Dialog;

import java.io.*;

/**
 * Used for writing text content into sequential files
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class TextFileWriter
{
    private boolean ready; //is file ready for writing?
    private PrintWriter pw; //Java's class for sequential files manipulation

    /**
     * constructor - tries to open the file and get it ready for writing or throws an I/O exception
     *
     * @param  fileName name of file for writing  
     */
    public TextFileWriter(String fileName)
    {
        try 
        {
            pw = new PrintWriter (new FileOutputStream (fileName));
            ready = true;
        } catch (IOException e)
        {
            ready = false;
            Dialog.error("Opening file " + fileName + " was unsuccessful.");
        }
    }

    /**
     * checks whether the writing to file is ready
     *
     * @return  true if the file is ready 
     */
    public boolean isReady()
    {
        return ready;
    }

    /**
     * writes text into the current position of sequential file
     *
     * @param  line text to be written
     */
    public void write(String line)
    {
        if (ready) pw.print (line);       
    }
    
    /**
     * writes line ended by \n - newline character into the current position in sequential file
     *
     * @param  line string to be written into file
     */
    public void writeln(String line)
    {
        if (ready) pw.println (line);       
    }

    /**
     * closes the writing stream to the file
     *
     */
    public void close()
    {
        if (ready) 
        {
            pw.flush();
            pw.close();
            ready = false;
        }
    }

}
