package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.outgoing.persistence;

import dominik.szczesniak.libraryexercise.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaBooksRepository extends JpaRepository<Book, Long> {

	void deleteById(final Long id);

	boolean existsByAuthorAndTitleAndYear(final String author, final String title, final int year);

}
