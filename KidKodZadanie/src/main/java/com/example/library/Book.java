package com.example.library;

public class Book {
    private String author;
    private String title;

    /**
     * Tworzy obiekt reprezentujący książkę o podanym autorze i tytule.
     *
     * @param author autor książki
     * @param title  tytuł książki
     */
    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }

    /**
     * Zwraca autora książki.
     *
     * @return autor książki
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Zwraca tytuł książki.
     *
     * @return tytuł książki
     */
    public String getTitle() {
        return title;
    }
}
