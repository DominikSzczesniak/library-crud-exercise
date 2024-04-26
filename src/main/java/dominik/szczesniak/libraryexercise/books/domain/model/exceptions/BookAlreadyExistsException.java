package dominik.szczesniak.libraryexercise.books.domain.model.exceptions;

public class BookAlreadyExistsException extends RuntimeException {

	public BookAlreadyExistsException(final String message) {
		super(message);
	}

}
