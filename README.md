<h2>Encryption and Decryption Console Java Program to a File</h2>
<p>Encrypt and Decrypt file with password.</p>
<h3>How it works</h3>
It is console-input program. Firstly, It's gonna ask if you want to do Encryption or Decryption. Then you need to set a file and password
that will be used for later decrypting. Program is using AES with ECB mode. Password is always hashed with SHA-256 before encryption.
<hr>
<h3>When do you want to use it and why it is useful</h3>
It is good to use it on files where you have stored sensitive datas. For example you can have many passwords that you are using and you do
not remember them all. So you can put them into the text file and encrypt that file so you only need to know one password (that is used for encryption/decryption).
<hr>
<h3>What do you need</h3>
Java 8+, jdk
<hr>
<h3>How to run it</h3>
javac Main.java
java Main
<hr>
<h3>Updates and Fixes</h3>
Catching exception when user starts to decrypt unencrypted file -> Printing: File is not encrypted.
Adding third option to read in console encrypted file without decrypting.
