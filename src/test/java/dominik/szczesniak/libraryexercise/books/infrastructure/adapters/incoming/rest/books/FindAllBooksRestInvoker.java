package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllBooksRestInvoker {

	private static final String URL = "/api/books";

	private final TestRestTemplate restTemplate;

	public ResponseEntity<List<BookInListDto>> findAll() {
		return restTemplate.exchange(
				URL,
				HttpMethod.GET,
				new HttpEntity<>(null),
				new ParameterizedTypeReference<>() {
				}
		);
	}

	@Value
	public static class BookInListDto {
		@NonNull Long id;
		@NonNull String title;
		@NonNull String author;
		@NonNull Integer year;
	}

}
