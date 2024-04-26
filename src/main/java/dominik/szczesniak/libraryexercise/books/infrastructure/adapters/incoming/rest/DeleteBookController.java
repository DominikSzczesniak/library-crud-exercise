package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeleteBookController {

	private final BooksService booksService;

	@DeleteMapping("/api/books/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable final Long bookId) {
		booksService.remove(bookId);
		return ResponseEntity.status(204).build();
	}

}
