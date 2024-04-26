package dominik.szczesniak.libraryexercise.books.domain;

import java.util.List;
import java.util.Optional;

public interface BooksRepository {

	Long create(Book book);

	void update(Book book);

	void removeBy(Long bookId);

	Optional<Book> findBy(Long bookId);

	List<Book> findAll();

}
