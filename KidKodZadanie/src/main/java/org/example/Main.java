package org.example;

import com.example.library.Book;
import com.example.library.BookLibrary;
import com.example.library.Welcome;
import com.google.gson.Gson;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Welcome welcome = new Welcome();
        welcome.greetUser();

        BookLibrary library = new BookLibrary();

        // Dodawanie książek do biblioteki na podstawie danych podanych przez użytkownika
        library.addBookFromUserInput();
        library.addBookFromUserInput();

        // Wyświetlanie książek w formacie JSON
        Gson gson = new Gson();
        List<Book> books = library.getBooks();
        String booksJson = gson.toJson(books);
        System.out.println("Książki w formacie JSON:");
        System.out.println(booksJson);
    }
}
