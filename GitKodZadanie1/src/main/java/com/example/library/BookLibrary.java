package com.example.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookLibrary {
    private List<Book> books;
    private Scanner scanner;

    public BookLibrary() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Dodaje książkę do biblioteki na podstawie danych podanych przez użytkownika.
     */
    public void addBookFromUserInput() {
        System.out.println("Dodawanie książki do biblioteki:");
        System.out.print("Podaj autora: ");
        String author = scanner.nextLine();
        System.out.print("Podaj tytuł: ");
        String title = scanner.nextLine();
        Book book = new Book(author, title);
        books.add(book);
        System.out.println("Książka została dodana do biblioteki.");
    }

    /**
     * Zwraca listę książek w bibliotece.
     *
     * @return lista książek
     */
    public List<Book> getBooks() {
        return books;
    }
}
