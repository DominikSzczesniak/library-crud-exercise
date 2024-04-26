package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateBookRestInvoker {

	private static final String URL = "/api/books/{bookId}";

	private final TestRestTemplate restTemplate;

	public ResponseEntity<Void> updateBook(final UpdateBookDto dto, final Long bookId) {
		return restTemplate.exchange(
				URL,
				HttpMethod.PUT,
				new HttpEntity<>(dto),
				Void.class,
				bookId
		);
	}

	@Data
	@Builder
	public static class UpdateBookDto {
		private String title;
		private String author;
		private Integer year;
	}

}
