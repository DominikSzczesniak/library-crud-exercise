package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBookRestInvoker {

	private static final String URL = "/api/books/{bookId}";

	private final TestRestTemplate restTemplate;

	public ResponseEntity<Void> deleteBook(final Long bookId) {
		return restTemplate.exchange(
				URL,
				HttpMethod.DELETE,
				new HttpEntity<>(null),
				Void.class,
				bookId
		);
	}

}
