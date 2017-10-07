import java.io.File;
import java.io.IOException;

import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;

public class DecryptionRead {

    private String fileText;
    private String password;

    public DecryptionRead(String fileText, String password){
        this.fileText = fileText;
        this.password = password;

        start();

    }

    private void startDecryption(SecretKeySpec key){

        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            try {

                cipher.init(Cipher.DECRYPT_MODE, key);

                try {

                    byte[] decryptedTextBytes = DatatypeConverter.parseBase64Binary(fileText);
                    decryptedTextBytes = cipher.doFinal(decryptedTextBytes);

                    String decryptedText = new String(decryptedTextBytes, "UTF-8");

                    System.out.println(decryptedText);

                } catch (IllegalBlockSizeException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException wpw){
                    System.out.println("Wrong password.");
                } catch ( ArrayIndexOutOfBoundsException ae ){
                    System.out.println("File is not encrypted.");
                }

            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    private void start(){

        SecretKeySpec key = getHash();
        if( key != null ){

            startDecryption(key);

        } else {
            System.out.println("Couldn't create password hash.");
        }

    }

    private SecretKeySpec getHash(){

        try {

            byte[] passwordBytes = password.getBytes("UTF-8");

            try {

                MessageDigest digestBytes = MessageDigest.getInstance("SHA-256");
                digestBytes.update(passwordBytes);
                byte[] digest = digestBytes.digest();

                byte[] prepareKey = Arrays.copyOf(digest, 16);

                SecretKeySpec key = new SecretKeySpec(prepareKey, "AES");

                return key;

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

}
