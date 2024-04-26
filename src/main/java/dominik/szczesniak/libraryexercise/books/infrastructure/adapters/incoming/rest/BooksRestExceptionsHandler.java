package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookAlreadyExistsException;
import dominik.szczesniak.libraryexercise.books.domain.model.exceptions.BookDoesNotExistException;
import lombok.Value;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BooksRestExceptionsHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(), 400, "IllegalArgumentException");
		return ResponseEntity.status(400).body(response);
	}

	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<?> handleBookAlreadyExistsException(final BookAlreadyExistsException e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(), 400, "BookAlreadyExistsException");
		return ResponseEntity.status(400).body(response);
	}

	@ExceptionHandler(BookDoesNotExistException.class)
	public ResponseEntity<?> handleBookDoesNotExistException(final BookDoesNotExistException e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(), 404, "BookDoesNotExistException");
		return ResponseEntity.status(404).body(response);
	}

	@ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> handleJdbcSQLIntegrityConstraintViolationException(final JdbcSQLIntegrityConstraintViolationException e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(), 400, "JdbcSQLIntegrityConstraintViolationException");
		return ResponseEntity.status(400).body(response);
	}

	@Value
	private static class ErrorResponse {
		String message;
		int status;
		String error;
	}

}
