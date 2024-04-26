package dominik.szczesniak.libraryexercise.books.domain.model.exceptions;

public class BookDoesNotExistException extends RuntimeException {

	public BookDoesNotExistException(final String message) {
		super(message);
	}

}
