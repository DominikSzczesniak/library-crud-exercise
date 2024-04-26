package dominik.szczesniak.libraryexercise.books.domain;

import dominik.szczesniak.libraryexercise.books.domain.model.commands.AddBook;
import dominik.szczesniak.libraryexercise.books.domain.model.commands.UpdateBook;
import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookDoesNotExistException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BooksService {

	private final BooksRepository repository;

	public Long add(final AddBook command) {
		return repository.create(new Book(command.getTitle(), command.getAuthor(), command.getYear()));
	}

	@Transactional
	public void update(final UpdateBook command) {
		final Book book = getBook(command.getBookId());
		book.update(command.getTitle(), command.getAuthor(), command.getYear());
		repository.update(book);
	}

	@Transactional
	public void remove(final Long bookId) {
		repository.removeBy(bookId);
	}

	public Book getBook(final Long bookId) {
		return repository.findBy(bookId).orElseThrow(() -> new BookDoesNotExistException("Book with id: " + bookId + " doesn't exist."));
	}

	public List<Book> findAll() {
		return repository.findAll();
	}

}
