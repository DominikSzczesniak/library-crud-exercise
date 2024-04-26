package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.Book;
import dominik.szczesniak.libraryexercise.books.domain.BooksService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FindAllBooksController {

	private final BooksService booksService;

	@GetMapping("/api/books")
	public ResponseEntity<List<BookDto>> findAll() {
		final List<Book> books = booksService.findAll();
		return ResponseEntity.status(200).body(toDto(books));
	}

	private static List<BookDto> toDto(final List<Book> books) {
		return books.stream().map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getYear())).toList();
	}

	@Value
	private static class BookDto {
		@NonNull Long id;
		@NonNull String title;
		@NonNull String author;
		@NonNull Integer year;
	}

}
