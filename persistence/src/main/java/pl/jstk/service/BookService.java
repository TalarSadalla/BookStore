package pl.jstk.service;

import java.util.List;

import pl.jstk.to.BookTo;

public interface BookService {

	List<BookTo> findAllBooks();

	BookTo findBookById(long id);

	List<BookTo> findBooksByTitle(String title);

	List<BookTo> findBooksByAuthor(String author);

	List<BookTo> findByStatus(String status);

	List<BookTo> findBookByTitleAndAuthor(String title, String author);

	List<BookTo> findBookByTitleAndStatus(String title, String status);

	List<BookTo> findBookByAuthorAndStatus(String author, String status);

	List<BookTo> findBookByTitleAndAuthorAndStatu(String title, String author, String status);

	List<BookTo> findByFilteredParameters(BookTo bookTo);

	BookTo saveBook(BookTo book);

	void deleteBook(Long id);
}
