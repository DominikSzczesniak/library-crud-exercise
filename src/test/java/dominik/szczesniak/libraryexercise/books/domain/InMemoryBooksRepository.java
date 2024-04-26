package dominik.szczesniak.libraryexercise.books.domain;

import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookAlreadyExistsException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

class InMemoryBooksRepository implements BooksRepository {

	private final AtomicLong nextId = new AtomicLong(0);
	private final Map<Long, PersistedBook> books = new HashMap<>();

	@Override
	public Long create(final Book book) {
		checkBookWithExactDataExists(book);
		setId(book, nextId.incrementAndGet());
		books.put(book.getId(), PersistedBook.toPersisted(book));
		return book.getId();
	}

	@Override
	public void update(final Book book) {
		checkBookWithExactDataExists(book);
		books.replace(book.getId(), PersistedBook.toPersisted(book));
	}

	private void checkBookWithExactDataExists(final Book book) {
		boolean exactBookExists = books.values().stream().anyMatch(savedBook ->
				savedBook.getYear() == book.getYear()
						&& savedBook.getTitle().equals(book.getTitle())
						&& savedBook.getAuthor().equals(book.getAuthor())
		);
		if (exactBookExists) {
			throw new BookAlreadyExistsException("A book with the title '" + book.getTitle() + "', authored by "
					+ book.getAuthor() + ", and published in " + book.getYear() + " already exists.");
		}
	}

	private void setId(final Book book, final Long id) {
		final Class<Book> bookClass = Book.class;
		try {
			final Field bookId = bookClass.getDeclaredField("id");
			bookId.setAccessible(true);
			bookId.set(book, id);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeBy(final Long bookId) {
		books.remove(bookId);
	}

	@Override
	public Optional<Book> findBy(final Long bookId) {
		return ofNullable(books.get(bookId)).map(PersistedBook::fromPersisted);
	}

	@Override
	public List<Book> findAll() {
		return books.values().stream().map(PersistedBook::fromPersisted).toList();
	}

	@Value
	@EqualsAndHashCode(of = "id")
	private static class PersistedBook {

		Long id;
		String title;
		String author;
		int year;

		static PersistedBook toPersisted(final Book book) {
			return new PersistedBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
		}

		static Book fromPersisted(final PersistedBook persistedBook) {
			final Book book = new Book(persistedBook.getTitle(), persistedBook.getAuthor(), persistedBook.getYear());
			setBookId(persistedBook, book);
			return book;
		}

		private static void setBookId(final PersistedBook persistedBook, final Book book) {
			final Class<Book> bookClass = Book.class;
			try {
				final Field bookId = bookClass.getDeclaredField("id");
				bookId.setAccessible(true);
				bookId.set(book, persistedBook.getId());
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
