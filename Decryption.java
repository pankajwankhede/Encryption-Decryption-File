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

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Decryption {

    private File file;
    private String fileText;
    private String password;

    public Decryption(File file, String fileText, String password){
        this.file = file;
        this.fileText = fileText;
        this.password = password;

        start();

    }

    private void end(String decryptedText){

        try {

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(decryptedText);
            fileWriter.flush();
            fileWriter.close();

            System.out.println("File has been decrypted.");

        } catch (IOException e) {
            e.printStackTrace();
        }

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

                    end(decryptedText.trim());

                } catch (IllegalBlockSizeException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException wpw){
                    System.out.println("Wrong password.");
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
