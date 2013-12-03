package com.jurajmasar.ib.dossier.files;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.spec.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

import com.jurajmasar.ib.dossier.root.Dialog;
import org.apache.commons.codec.binary.Base64;
/**
 * Encrypting and decrypting interface for Strings.
 * All Strings remain in Base64!
 * 
 * Originally from:
 * http://www.exampledepot.com/egs/javax.crypto/PassKey.html
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class DesEncrypter 
{
    Cipher ecipher;
    Cipher dcipher;

    // 8-byte Salt
    byte[] salt = {
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
    };

    // Iteration count
    int iterationCount = 19;
    /**
     * Constructor - initializes the object for given 
     * passPhrase.
     * 
     * @param passPhrase passPhase for encryption
     */
    public DesEncrypter(String passPhrase)
    {
        try 
        {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(
                "PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        }
         catch (java.security.InvalidAlgorithmParameterException e) {}
         catch (java.security.spec.InvalidKeySpecException e) {}
         catch (javax.crypto.NoSuchPaddingException e) {}
         catch (java.security.NoSuchAlgorithmException e) {}
         catch (java.security.InvalidKeyException e) 
         {
               Dialog.error(e.getMessage());
         }
        
    }
    
    /**
     * Encrypts given String
     * 
     * @param str String to encrypt
     */
    public String encrypt(String str) 
    {
        if (str == null) return null;
        try 
        {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new String(Base64.encodeBase64(enc), "UTF8");
        } catch (javax.crypto.BadPaddingException e) {}
         catch (IllegalBlockSizeException e) {}
         catch (UnsupportedEncodingException e) {}

        return null;        
    }

    /**
     * Decrypts given String
     * 
     * @param str String to decrypt
     */
    public String decrypt(String str) 
    {
        if (str == null) return null;
        try 
        {
            // Decode base64 to get bytes
            byte[] dec = Base64.decodeBase64(str); 

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {}
         catch (IllegalBlockSizeException e) {}
         catch (UnsupportedEncodingException e) {}

        return null;
    }
}