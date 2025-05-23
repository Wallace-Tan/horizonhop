package org.example.aptry2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.util.Base64;

public class AES {
    private SecretKey key; // Secret key used for AES encryption and decryption
    private final int KEY_SIZE = 128; // Key size for AES encryption
    private final int T_LEN = 128; // Tag length for GCM mode
    private Cipher encryptionCipher; // Cipher instance for encryption

    private static final String KEY_FILE = "aes.key"; // File name for storing the secret key

    // Method to initialize the AES class and load/generate the secret key
    public void init() throws Exception {
        File keyFile = new File(KEY_FILE);
        if (keyFile.exists()) { // If the key file exists, read the key from the file
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile))) {
                key = (SecretKey) ois.readObject();
            }
        } else { // If the key file does not exist, generate a new key and save it to the file
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(KEY_SIZE);
            key = generator.generateKey();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(keyFile))) {
                oos.writeObject(key);
            }
        }
    }

    // Method to encrypt a message using AES/GCM/NoPadding
    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes(); // Convert the message to bytes
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key); // Initialize the cipher for encryption
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes); // Perform encryption
        byte[] iv = encryptionCipher.getIV(); // Get the initialization vector
        byte[] combined = new byte[iv.length + encryptedBytes.length]; // Combine IV and encrypted message
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(combined); // Encode the combined array to a Base64 string
    }

    // Method to decrypt an encrypted message using AES/GCM/NoPadding
    public String decrypt(String encryptedMessage) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedMessage); // Decode the Base64 string
        byte[] iv = new byte[12]; // Initialize an array for the IV
        byte[] encryptedBytes = new byte[combined.length - iv.length]; // Initialize an array for the encrypted message
        System.arraycopy(combined, 0, iv, 0, iv.length); // Extract the IV from the combined array
        System.arraycopy(combined, iv.length, encryptedBytes, 0, encryptedBytes.length); // Extract the encrypted message
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv); // Create GCMParameterSpec with the extracted IV
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec); // Initialize the cipher for decryption
        byte[] decryptedBytes = decryptionCipher.doFinal(encryptedBytes); // Perform decryption
        return new String(decryptedBytes); // Convert the decrypted bytes to a string
    }
}
