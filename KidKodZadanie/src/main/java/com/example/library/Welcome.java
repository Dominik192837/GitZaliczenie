package com.example.library;

import java.util.Scanner;

public class Welcome {
    /**
     * Wita użytkownika i prosi o podanie imienia.
     * Następnie wyświetla personalizowane powitanie.
     */
    public void greetUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj swoje imię: ");
        String name = scanner.nextLine();

        System.out.println("Witaj, " + name + "!");
    }
}
