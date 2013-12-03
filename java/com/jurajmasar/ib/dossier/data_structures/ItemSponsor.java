package com.jurajmasar.ib.dossier.data_structures;

import com.jurajmasar.ib.dossier.root.Static;

import java.util.Date;

/**
 * Item object to be used in listRegNum data structure
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class ItemSponsor extends Item
{
    public String regNum; //registration number of this distributor
    public Date registrationDate; //registration date of this distributor
    public String name;
    public String surname;
    public ItemRegNum itemRegNum; //pointer to item of listRegNum
    /**
     * Constructor - initializes object variables to given values
     *
     * @param key key of item
     * @param position  position in file
     * @param next position to next Item
     * @param regNum    registration number
     * @param registrationDate  registrationDate
     * @param itemRegNum    pointer to ItemRegNum
     */
    public ItemSponsor(
        String key, 
        long pos,
        Item next,
        String regNum,
        String registrationDate,
        ItemRegNum itemRegNum,
        String name,
        String surname
        )
    {
        this.key = key;
        this.pos = pos;
        this.next = next;
        this.regNum = regNum;
        this.registrationDate =  Static.stringToDate(registrationDate);
        this.itemRegNum = itemRegNum;
        this.name = name;
        this.surname = surname;
    }    
}
