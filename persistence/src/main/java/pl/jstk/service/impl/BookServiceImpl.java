package pl.jstk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.jstk.entity.BookEntity;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookTo> findAllBooks() {
		return BookMapper.map2To(bookRepository.findAll());
	}

	@Override
	public List<BookTo> findBooksByTitle(String title) {
		return BookMapper.map2To(bookRepository.findBookByTitle(title));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return BookMapper.map2To(bookRepository.findBookByAuthor(author));
	}

	@Override
	@Transactional
	public BookTo saveBook(BookTo book) {
		BookEntity entity = BookMapper.map(book);
		entity = bookRepository.save(entity);
		return BookMapper.map(entity);
	}

	@Override
	@Transactional
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);

	}

	@Override
	public BookTo findBookById(long id) {
		BookEntity entity = bookRepository.findBookById(id);
		BookTo bookTo = BookMapper.map(entity);
		return bookTo;
	}

	@Override
	public List<BookTo> findByStatus(String status) {
		return BookMapper.map2To(bookRepository.findBookByStatus(status));
	}

	@Override
	public List<BookTo> findBookByTitleAndAuthor(String title, String author) {
		return BookMapper.map2To(bookRepository.findBookByTitleAndAuthor(title, author));
	}

	@Override
	public List<BookTo> findBookByTitleAndStatus(String title, String status) {
		return BookMapper.map2To(bookRepository.findBookByTitleAndStatus(title, status));
	}

	@Override
	public List<BookTo> findBookByAuthorAndStatus(String author, String status) {
		return BookMapper.map2To(bookRepository.findBookByAuthorAndStatus(author, status));
	}

	@Override
	public List<BookTo> findBookByTitleAndAuthorAndStatu(String title, String author, String status) {
		return BookMapper.map2To(bookRepository.findBookByTitleAndAuthorAndStatus(title, author, status));
	}

	@Override
	public List<BookTo> findByFilteredParameters(BookTo bookTo) {
		List<BookTo> bookToList = new ArrayList<>();
		String title = bookTo.getTitle();
		String author = bookTo.getAuthors();
		if (title != null && author != null) {
			bookToList = findBookByTitleAndAuthor(title, author);
		} else if (title != null && author == null) {
			bookToList = findBooksByTitle(title);
		} else if (title == null && author != null) {
			bookToList = findBooksByAuthor(author);
		}
		return bookToList;
	}
}
