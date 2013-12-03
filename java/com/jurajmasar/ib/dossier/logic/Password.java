package com.jurajmasar.ib.dossier.logic;

import com.jurajmasar.ib.dossier.files.TextFileReader;
import com.jurajmasar.ib.dossier.files.TextFileWriter;
import com.jurajmasar.ib.dossier.root.Dialog;
import com.jurajmasar.ib.dossier.root.Static;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

/**
 * Contains methods for application's password manipulation.
 * 
 * @author Juraj Masar 
 * @version 0.1
 */
public class Password
{
    /**
     * Sets password to the application.
     * It generates salted hash of the password and consequently saves it.
     *
     * @param  pass   password to save
     */
    public static void setPassword(String pass)
    {
        TextFileWriter t = new TextFileWriter (Static.filePassword);
        try
        {
            /** DOSSIER: HL mastery 17 - inserting data into orderded sequential file **/
            t.writeln(SHA1(pass+ Static.hashSalt));
        } catch (NoSuchAlgorithmException ae)
        {
            Dialog.error("Setting new password was unsuccessful. \nError:" + ae.getMessage());
        } catch (UnsupportedEncodingException ae)
        {
            Dialog.error ("Setting new password was unsuccessful. \nError:"+ae.getMessage());
        }
        t.close();
    }

    /**
     * Checks if given password is equal to password set in application.
     * It computes salted SHA1 hash which is consequently compared to hash
     * stored by the application.
     *
     * @param  pass     password to check
     * @return     true if the password is valid
     */
    public static boolean checkPassword(String pass)
    {
        String hash = new String();    
        try
        {
             hash = SHA1(pass+Static.hashSalt);
        } catch (NoSuchAlgorithmException ae)
        {
            Dialog.error ("Password checking was unsuccessful. \nError:"+ae.getMessage());
        } catch (UnsupportedEncodingException ae)
        {
            Dialog.error ("Password checking was unsuccessful. \nError:"+ae.getMessage());
        }
        TextFileReader t = new TextFileReader (Static.filePassword);
        /** DOSSIER: HL mastery 9 - parsing text file **/         
        return t.readLine().equals(hash);
    }

    /**
     * Converts text into hex-string.
     * 
     * Originally from 
     * http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
     * (accessed 23. February 2010).
     * 
     * @param  data   array of bytes
     * @return     hex-string
     */
    private static String convertToHex(byte[] data) 
    { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 

    /**
     * Ccomputes SHA1 hash from a given string.
     * 
     * Originally from 
     * http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
     * (accessed 23. February 2010).
     * 
     * @param  text original string to compute hash from
     * @return     SHA1 hash
     */  
    private static String SHA1(String text) 
        throws NoSuchAlgorithmException, UnsupportedEncodingException  
    { 
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("utf-8"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    } 
}
