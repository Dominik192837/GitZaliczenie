package org.example;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;

public class SecurityApp {

    // AES Encryption/Decryption
    public static class AES {
        private static final String ALGORITHM = "AES";

        public static SecretKey generateKey() throws Exception {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128); // AES-128
            return keyGen.generateKey();
        }

        public static String encrypt(String data, SecretKey key) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        public static String decrypt(String encryptedData, SecretKey key) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedData));
        }
    }


    // SQL Injection Demonstration
    public static class SQLInjectionDemo {
        private static final String DB_URL = "jdbc:sqlite::memory:";
        private static Connection connection;

        public static void setupDatabase() throws SQLException {
            connection = DriverManager.getConnection(DB_URL);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT);");
                stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('admin', 'password123');");
                stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('user', 'userpass');");
            }
        }

        public static boolean loginVulnerable(String username, String password) throws SQLException {
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                return rs.next();
            }
        }

        public static boolean loginSecure(String username, String password) throws SQLException {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?;";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Part 1: AES Encryption/Decryption
            System.out.println("--- Demonstracja szyfrowania i deszyfrowania AES ---");
            SecretKey aesKey = AES.generateKey();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj wiadomość do zaszyfrowania: ");
            String message = scanner.nextLine();

            String encryptedMessage = AES.encrypt(message, aesKey);
            System.out.println("Zaszyfrowana wiadomość: " + encryptedMessage);

            String decryptedMessage = AES.decrypt(encryptedMessage, aesKey);
            System.out.println("Odszyfrowana wiadomość: " + decryptedMessage);

            // Part 2: SQL Injection Demonstration
            System.out.println("\n--- Demonstracja SQL Injection ---");
            SQLInjectionDemo.setupDatabase();

            System.out.print("Podaj nazwę użytkownika: ");
            String username = scanner.nextLine();
            System.out.print("Podaj hasło: ");
            String password = scanner.nextLine();

            System.out.println("Próba logowania podatna na SQL Injection...");
            if (SQLInjectionDemo.loginVulnerable(username, password)) {
                System.out.println("Logowanie zakończone sukcesem (podatne)!" );
            } else {
                System.out.println("Logowanie nieudane (podatne)." );
            }

            System.out.println("Próba logowania zabezpieczonego...");
            if (SQLInjectionDemo.loginSecure(username, password)) {
                System.out.println("Logowanie zakończone sukcesem (zabezpieczone)!" );
            } else {
                System.out.println("Logowanie nieudane (zabezpieczone)." );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
