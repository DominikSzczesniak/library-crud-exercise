package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetBookRestInvoker {

	private static final String URL = "/api/books/{bookId}";

	private final TestRestTemplate restTemplate;

	public ResponseEntity<BookDto> getBook(final Long bookId) {
		return restTemplate.exchange(
				URL,
				HttpMethod.GET,
				new HttpEntity<>(null),
				BookDto.class,
				bookId
		);
	}

	@Value
	public static class BookDto {
		@NonNull Long id;
		@NonNull String title;
		@NonNull String author;
		@NonNull Integer year;
	}

}
