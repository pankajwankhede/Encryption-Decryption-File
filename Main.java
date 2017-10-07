import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static File file;
    private static String fileText;

    public static void main(String[] args){

        System.out.println("What do you want to do ?");
        System.out.println("1. Encryption (Encrypt a file)");
        System.out.println("2. Decryption (Decrypt a file)");
        System.out.println("3. Read encrypted file (File will not be decrypted. It will just show it's content)");

        int answer = getAnswer();

        if( answer == 1 ) {

            System.out.println("You have selected Encryption.");

            getFile();

            if( file != null && !fileText.equals("") ){
                String password = getPassword(1);
                new Encryption(file, fileText, password);
            } else {
                System.out.println("Some error occurred.");
            }

        }else if( answer == 2 ) {

            System.out.println("You have selected Decryption.");

            getFile();

            if (file != null && !fileText.equals("")) {
                String password = getPassword(2);
                new Decryption(file, fileText, password);
            } else {
                System.out.println("Some error occurred.");
            }

        }else if( answer == 3 ){

            System.out.println("You have selected Read encrypted file.");

            getFile();

            if (file != null && !fileText.equals("")) {
                String password = getPassword(3);
                new DecryptionRead(fileText, password);
            } else {
                System.out.println("Some error occurred.");
            }

        }else {
            System.out.println("You have to choose between 1, 2 or 3.");
        }

    }

    private static String getPassword(int style){

        if( style == 1 ){
            System.out.println("Set password/key for encryption. You can also leave it empty.");
        } else if( style == 2 || style == 3 ) {
            System.out.println("Write password/key that was used for encryption.");
        }

        System.out.print("Password -> ");
        Scanner input = new Scanner(System.in);
        String password = input.nextLine();

        return password;

    }

    private static void getFile(){

        boolean bool = true;

        while( bool ) {

            fileText = "";

            Scanner fileInput = new Scanner(System.in);
            System.out.print("File (file path) -> ");
            String filePath = fileInput.next();

            file = new File(filePath);

            if (file.exists() && file.isFile()) {

                try {

                    Scanner fileRead = new Scanner(file, "ISO-8859-2");

                    while( fileRead.hasNextLine() ){

                        fileText += fileRead.nextLine() + "\n";

                    }

                    if( !fileText.equals("") ){

                        bool = false;

                    } else {
                        System.out.println("File is empty.");
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found Exception");
                }

            } else {
                System.out.println("File doesn't exist or it is not a file.");
            }

        }

    }

    private static int getAnswer(){

        boolean bool = true;
        int answer = 0;

        while( bool ) {

            try {

                System.out.print("(1/2/3) -> ");

                Scanner input = new Scanner(System.in);
                answer = input.nextInt();

                if(answer == 1 || answer == 2 || answer == 3){
                    bool = false;
                }else{
                    System.out.println("You have to choose between 1, 2 or 3.");
                }

            } catch (InputMismatchException e) {
                System.out.println("You have to choose between 1, 2 or 3.");
            }

        }

        return answer;

    }

}
