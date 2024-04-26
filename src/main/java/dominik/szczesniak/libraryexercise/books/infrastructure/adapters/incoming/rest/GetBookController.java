package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.Book;
import dominik.szczesniak.libraryexercise.books.domain.BooksService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GetBookController {

	private final BooksService booksService;

	@GetMapping("/api/books/{bookId}")
	public ResponseEntity<BookDto> getBook(@PathVariable final Long bookId) {
		final Book book = booksService.getBook(bookId);
		return ResponseEntity.status(200).body(toDto(book));
	}

	private static BookDto toDto(Book book) {
		return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
	}

	@Value
	private static class BookDto {
		@NonNull Long id;
		@NonNull String title;
		@NonNull String author;
		@NonNull Integer year;
	}

}
