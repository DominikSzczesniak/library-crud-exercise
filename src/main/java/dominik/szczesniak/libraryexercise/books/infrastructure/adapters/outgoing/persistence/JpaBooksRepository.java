package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.outgoing.persistence;

import dominik.szczesniak.libraryexercise.books.domain.Book;
import dominik.szczesniak.libraryexercise.books.domain.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBooksRepository implements BooksRepository {

	private final SpringDataJpaBooksRepository springDataJpaBooksRepository;

	@Override
	public Long create(final Book book) {
		final Book savedBook = springDataJpaBooksRepository.save(book);
		return savedBook.getId();
	}

	@Override
	public void update(final Book book) {
		springDataJpaBooksRepository.save(book);
	}

	@Override
	public void removeBy(final Long bookId) {
		springDataJpaBooksRepository.deleteById(bookId);
	}

	@Override
	public Optional<Book> findBy(final Long bookId) {
		return springDataJpaBooksRepository.findById(bookId);
	}

	@Override
	public List<Book> findAll() {
		return springDataJpaBooksRepository.findAll();
	}

}
