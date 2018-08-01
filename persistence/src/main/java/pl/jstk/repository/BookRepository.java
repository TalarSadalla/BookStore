package pl.jstk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.jstk.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

	@Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%')")
	List<BookEntity> findBookByTitle(@Param("title") String title);

	@Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%')")
	List<BookEntity> findBookByAuthor(@Param("author") String author);

	@Query("select book from BookEntity book where upper(book.id) like concat('%', upper(:id), '%')")
	BookEntity findBookById(@Param("id") Long id);

	@Query("select book from BookEntity book where upper(book.status) like concat('%', upper(:status), '%')")
	List<BookEntity> findBookByStatus(@Param("status") String status);

	@Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%') and upper(book.authors) like concat('%', upper(:author), '%')")
	List<BookEntity> findBookByTitleAndAuthor(@Param("title") String title, @Param("author") String author);

	@Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%') and upper(book.status) like concat('%', upper(:status), '%')")
	List<BookEntity> findBookByTitleAndStatus(@Param("title") String title, @Param("status") String status);

	@Query("select book from BookEntity book where upper(book.authors) like concat(upper(:author), '%') and upper(book.status) like concat('%', upper(:status), '%')")
	List<BookEntity> findBookByAuthorAndStatus(@Param("author") String author, @Param("status") String status);

	@Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%') and upper(book.authors) like concat('%', upper(:author), '%') and upper(book.status) like concat('%', upper(:status), '%')")
	List<BookEntity> findBookByTitleAndAuthorAndStatus(@Param("title") String title, @Param("author") String author,
			@Param("status") String status);
}
