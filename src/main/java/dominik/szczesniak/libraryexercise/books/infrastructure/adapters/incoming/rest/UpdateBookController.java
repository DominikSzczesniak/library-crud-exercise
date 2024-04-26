package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.BooksService;
import dominik.szczesniak.libraryexercise.books.domain.model.commands.UpdateBook;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UpdateBookController {

	private final BooksService booksService;

	@PutMapping("/api/books/{bookId}")
	public ResponseEntity<?> updateBook(@PathVariable final Long bookId, @RequestBody final UpdateBookDto dto) {
		booksService.update(new UpdateBook(bookId, dto.getTitle(), dto.getAuthor(), dto.getYear()));
		return ResponseEntity.status(200).build();
	}

	@Data
	private static class UpdateBookDto {
		private String title;
		private String author;
		private Integer year;
	}

}
