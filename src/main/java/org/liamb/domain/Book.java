package org.liamb.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.liamb.Exceptions.InvalidIsbnException;
import org.liamb.util.Validation;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends Item{
    private String isbn;
    private String author;
    private String genre;

    public Book(String title, String isbn, String author, String genre) {
        super(title);
        if (!Validation.isValidISBN(isbn)) {
            throw new InvalidIsbnException(isbn);
        }
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }

    public Book(String id, ItemStatus status, String title, String isbn, String author, String genre) {
        super(id, status, title);
        if (!Validation.isValidISBN(isbn)) {
            throw new InvalidIsbnException(isbn);
        }
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }
}

