package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest.books;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static dominik.szczesniak.libraryexercise.books.domain.model.AuthorSample.createAnyAuthor;
import static dominik.szczesniak.libraryexercise.books.domain.model.TitleSample.createAnyTitle;
import static dominik.szczesniak.libraryexercise.books.domain.model.YearSample.createAnyValidYearForBook;

@Component
@RequiredArgsConstructor
public class AddBookRestInvoker {

	private static final String URL = "/api/books";

	private final TestRestTemplate restTemplate;

	public ResponseEntity<Long> addBook(final AddBookDto dto) {
		return restTemplate.exchange(
				URL,
				HttpMethod.POST,
				new HttpEntity<>(dto),
				Long.class
		);
	}

	@Data
	@Builder
	public static class AddBookDto {

		@Builder.Default
		private String title = createAnyTitle();
		@Builder.Default
		private String author = createAnyAuthor();
		@Builder.Default
		private int year = createAnyValidYearForBook();

	}

}
