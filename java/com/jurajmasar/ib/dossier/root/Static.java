package com.jurajmasar.ib.dossier.root;

import com.jurajmasar.ib.dossier.data_structures.Vector;
import com.jurajmasar.ib.dossier.files.DesEncrypter;

import java.util.Random;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.io.File;
/**
 * Class full of useful static methods which could be easily reused anywhere
 * + definitions of constants for the application
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class Static
{
    //definitions of constants in the application
    public static final String defaultErrorTitle = "Error";
    public static final String defaultWarningTitle = "Warning";
    public static final String defaultInfoTitle = "Message";
    public static final String defaultPlainTitle = "Message";    
    public static final String keyIconPath = "resources/key-icon.png";    
    public static final String iconPath = "resources/user.png";     
    public static final String profileIconPath = "resources/profile.png";    
    public static final String profileIconSmallPath = "resources/profileSmall.png";    
    
    public static final char defaultSeparator = ';'; //separator used in DAF

    public static final String hashSalt = "6eH97dZ90"; //salt for password hash generation
    public static final String encryptionKey = "5SAcyh8F2vF0"; //key for data encryption
    
    public static final String dataDirectory = "data";
    public static final String filePassword = "data/options.dat";
    public static final String fileDistributor = "data/distributors.dat";
    public static final String fileMonthPrefix = "data/dist";
    public static final String fileMonthSuffix = ".dat";    
    
    public static final String dateFormat = "dd.MM.yyyy";
    
    public static final long monthRecordLength = 50;
    public static final long distributorRecordLength = 470;
    
    /**
     * customizes the length of the string given
     *
     * @param  y   a sample parameter for a method
     * @return     string customized to the given length
     */
    public static String setStringLength(String s, int numOfChars)
    {
        if (s.length() > numOfChars)//given string is longer    
        {
            s = s.substring(0, numOfChars); //shorten
        } else //given string is shorter
        {   //prolong
            for (int i=s.length(); i<=numOfChars-1;i++) s += " ";
        }
        return s;        
    }

    /**
     * Cuts the string given if its length exceeds the maximum length given.
     * Otherwise returns original string.
     *
     * @param  s    string to manipulate   
     * @param  max  maximum length of the sting
     * @return     the sum of x and y
     */
    public static String cutIfLongerThan(String s, int max)
    {
        if (s.length() > max)//given string is longer    
        {
            return s.substring(0, max); //shorten
        } else
        {
            return s;
        }
    }

    /**
     * counts number of occurences of a given char in a given string
     *
     * @param  s    string to count occurences in
     * @param   c   char to look for
     * @return     number of occurences
     */
    public static int charOccurences(String s, char c)
    {
        if (s == null) return -1;
        int count = 0; //count
        for (int i=0;i<=s.length()-1;i++)
            if (s.charAt(i) == c) count++;
        return count;    
    }

    /**
     * Checks whether the string contains only numerical characters.
     *
     * @param  s    string to check
     * @return     the sum of x and y
     */
    public static boolean isNumeric (String s)
    {
        for (int i=0;i<=s.length()-1;i++)
        {
            if (s.charAt(i) > 57 || s.charAt(i) < 48) return false;
        }
        return true;
    }

    
    /**
     * removes given char from a given string
     *
     * @param  s string to remove char from
     * @param c char to remove
     * @return     string without given char
     */
    public static String removeChar(String orig, char c)
    {
        String s = new String();
        for (int i=0;i<=orig.length()-1;i++)
        {
            if (orig.charAt(i) != c)
            {
                s += orig.charAt(i);
            } 
        }
        return s;
    }

    /**
     * creates one string from the array of strings given separated by given separator
     * if separator occures in given data, it is removed.
     *
     * @param  data   array of strings to compile
     * @param  separator    char for separation of strings
     * @return     compilation of strings
     */
    public static String implode(String[] data, char separator)
    {
        String s = new String();
        for (int i=0;i<=data.length-1;i++)
        {
            s += removeChar(data[i],separator)+separator;
        }
        return s;
    }

    /**
     * creates one string from the array of strings given separated by default separator
     *
     * @param  data   array of strings to compile
     * @return     compilation of strings
     */
    public static String implode(String[] data)
    {
        return implode (data, defaultSeparator);
    }
    
    
    /**
     * creates an array of strings from compilation of strings with given separator
     *
     * @param  orig original compilation of strings
     * @param  separator char for separation of strings
     * @return     array of strings
     */
    /** DOSSIER: SL mastery 8 - user-defined methods **/ 
    public static String[] explode(String orig, char separator)
    {
        if (orig == null) return null;
        int count = charOccurences(orig, separator);
        /** DOSSIER: SL mastery 1 - arrays **/
        String[] data = new String[count];
        String current = new String();
        int index = 0;
        /** DOSSIER: SL mastery 6 - loops **/ 
        for (int i=0;i<=orig.length()-1;i++)
        {
           /** DOSSIER: SL mastery 4 - simple selection **/ 
           if (orig.charAt(i) != separator) 
           {
              current += orig.charAt(i); 
           } else
           {
              data[index] = current;
              current = new String();
              index++;
           }
        }
        return data;
    }

    /**
     * creates an array of strings from compilation of strings with default separator
     *
     * @param  orig original compilation of strings
     * @return     array of strings
     */
    /** DOSSIER: SL mastery 9 - user-defined methods with parameters **/     
    public static String[] explode(String orig)
    {
        return explode (orig, defaultSeparator);
    }

    /**
     * Removes diacritics from a String given. 
     * 
     * Originally from 
     * http://www.rgagnon.com/javadetails/java-0456.html
     * 
     * @param  s   String with diacritics
     * @return     String without diacritics
     */
    public static String removeDiacritics(String s)
    {
        String temp = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]","");
    }    

    /**
     * Creates an array of strings from compilation of strings with default separator.
     *
     * @param  arr array of values
     * @param left left indx
     * @param right right index
     * @return     integer index
     */    
    private static int quickSortPartition(int arr[], int i, int j)
    {
        int tmp = 0;
        int pivot = arr[(i + j) / 2];
        
        /** DOSSIER: SL mastery 7 - nested loops **/ 
        while (i <= j) 
        {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;
            if (i <= j) 
            {
                  tmp = arr[i];
                  arr[i] = arr[j];
                  arr[j] = tmp;
                  i++;
                  j--;
            }
        }
        
        return i;
    }
 
    /**
     * Sorts given array within indexes given.
     *
     * @param  arr array of values
     * @param left left indx
     * @param right right index
     */            
    private static void quickSort(int arr[], int left, int right) 
    {
          /** DOSSIER: HL mastery 4 - recursion **/         
          int index = quickSortPartition(arr, left, right);
          if (left < index - 1) quickSort(arr, left, index - 1);
          if (index < right) quickSort(arr, index, right);
    }    

    /**
     * Sorts given array.
     *
     * @param  arr array of values
     */     
    public static void sort(int arr[])
    {
          if (arr != null && arr.length>0)quickSort(arr, 0, arr.length-1);
    }
    
    /**
     * Produces an array of ints - with numbers which are
     * included in all given arrays.
     *
     * @param  in  array of arrays of int
     * @return     int[] array
     */
    /** DOSSIER: HL mastery 19 - arrays of two or more dimensions **/             
    public static int[] conjunction(int[][] in)
    {
        if (in.length == 0) return new int[0];
        for (int a=0;a<=in.length-1;a++)
        {
            sort(in[a]);
        }
        
        /** DOSSIER: HL mastery 5 - merging sorted data structures **/ 
        
        Vector<Integer> v = new Vector<Integer>();
        
        int[] indices = new int[in.length];
        for (int i=0;i<=in.length-1;i++) indices[i] = 0;
        
        boolean fail;
        
        for (indices[0]=0;indices[0]<=in[0].length-1;indices[0]++)
        {
            fail = false;
            for (int b=1;b<=in.length-1;b++)
            {
                while (indices[b] < in[b].length 
                  && in[b][indices[b]] < in[0][indices[0]])
                  indices[b]++;
                if (indices[b] >= in[b].length || 
                  in[b][indices[b]] != in[0][indices[0]])  
                {
                    fail = true;
                    break;
                }
            }
            if (!fail) v.pushBack(in[0][indices[0]]);
        }

        return v.toIntArray();
    }

    /**
     * Generates random number in a given range.
     *
     * @param  from     the beginning of the range
     * @param  to      the end of the range
     * @return     integer number from the range
     */
    public static int random(int from, int to)
    {
        if (from > to) return -1;       

        return  new Random().nextInt(to-from+1) + from;            
    }

    /**
     * Parses given String into Date
     *
     * @param  s    string to parse
     * @return     Date object
     */
    public static Date stringToDate(String s)
    {
        try 
        {
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            return (Date)formatter.parse(s);        
        } catch (ParseException e)
        {
            return null;
        }
    }


    /**
     * Parses formats given Date into Stirng
     *
     * @param  d    Date to format
     * @return     String
     */
    /** DOSSIER: SL mastery 10 - user-defined methods with return values **/ 
    public static String dateToString(Date d)
    {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(d);        
    }
 
    /**
     * Deletes file by a given path
     *
     * @param  fileName fileName of the file to delete
     */
    public static void deleteFile (String fileName)
    {
        try
        {
            File f = new File(fileName);

            if (!f.exists()) 
            {
                Dialog.error("Error while deleting file '"+fileName+"':\n"
                             +"file does not exist.");
                return;
            }

            if (!f.delete())
                Dialog.error("Error while deleting file '"+fileName+"':\n"
                             +"deleting failed.");
        } catch (SecurityException e) 
        {
            Dialog.error("Error while deleting file '"+fileName+"':\n"
                             +"exception: "+e.getMessage());
        }
    }    


    /**
     * Converts given char Array to a single String
     *
     * @param  ca   char array
     * @return     String object
     */
    public static String charArrayToString(char[] ca)
    {
        String ret = "";
        for (int i=0;i<ca.length;i++) ret += ca[i];
        return ret;
    }

    /**
     * Validates the given email address.
     *
     * @param  s    String email address
     * @return     true if valid
     */
    public static boolean validateEmail(String s)
    {
        if (s == null || s.length() == 0)
          return false;
        if ((s.indexOf('@')+1 < s.lastIndexOf('.')) &&
        (s.indexOf('@')!=0) && (s.lastIndexOf('.')!=s.length()-1))
          return true;
          return false;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public static boolean validateLetters(String s)
    {
        for (int i=0;i<s.length();i++)
          if (s.charAt(i) < 57 && s.charAt(i) > 48) return false; //is number        
        return true;
    }

    
    /**
     * Validates given String according to given parameters.
     * If it is not valid, returns error message.
     *
     * @param  s    input string
     * @param  type 0 - numeric, 1 - letters and spaces, 2 - email  
     * @param  compulsory  if string can't be null
     * @param  name    to use in error message
     * @return     error message
     */
    public static String validateString(String s, int type, boolean compulsory, String name)
    {
        if (compulsory && (s == null || s.length() == 0))       
            return name+" is a compulsory field!";      
        if (type == 0 && !Static.isNumeric(s)) //numeric
            return name+" has to be numeric!";
        if (type == 1 && !Static.validateLetters(s)) //numeric
            return name+" can't contain numbers!";
        if (type == 2 && !Static.validateEmail(s) && (s != null && s.length() > 0)) //numeric
            return name+" has to be a valid email address!";
            
        return null;
    }

    /**
     * Returns a word representing Month given in number.
     *
     * @param  String   month in number
     * @return     String month in world
     */
    public static String monthByWord(String in)
    {
        if (Integer.parseInt(in) == 1) return "January";
        if (Integer.parseInt(in) == 2) return "February";
        if (Integer.parseInt(in) == 3) return "March";
        if (Integer.parseInt(in) == 4) return "April";
        if (Integer.parseInt(in) == 5) return "May";
        if (Integer.parseInt(in) == 6) return "June";
        if (Integer.parseInt(in) == 7) return "July";
        if (Integer.parseInt(in) == 8) return "August";
        if (Integer.parseInt(in) == 9) return "September";
        if (Integer.parseInt(in) == 10) return "October";
        if (Integer.parseInt(in) == 11) return "November";
        if (Integer.parseInt(in) == 12) return "December";        
        return "";
    }

    /**
     * Encrypts given String according to given key.
     *
     * @param  in   String to encrypt
     * @param  key  key of the encryption
     * @return      encrypted String
     */
    public static String encrypt(String in)
    {
        DesEncrypter encrypter = new DesEncrypter(Static.encryptionKey);
        return encrypter.encrypt(in);
    }

    /**
     * Decrypts given String according to given key.
     *
     * @param  in   String to decrypt
     * @return      encrypted String
     */
    public static String decrypt(String in)
    {
        DesEncrypter encrypter = new DesEncrypter(Static.encryptionKey);
        return encrypter.decrypt(in);
    }    
    
}
