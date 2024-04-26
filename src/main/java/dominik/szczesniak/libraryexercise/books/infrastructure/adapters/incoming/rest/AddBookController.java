package dominik.szczesniak.libraryexercise.books.infrastructure.adapters.incoming.rest;

import dominik.szczesniak.libraryexercise.books.domain.BooksService;
import dominik.szczesniak.libraryexercise.books.domain.model.commands.AddBook;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AddBookController {

	private final BooksService booksService;

	@PostMapping("/api/books")
	public ResponseEntity<Long> addBook(@RequestBody final AddBookDto dto) {
		final Long id = booksService.add(new AddBook(dto.getTitle(), dto.getAuthor(), dto.getYear()));
		return ResponseEntity.status(201).body(id);
	}

	@Data
	private static class AddBookDto {
		private String title;
		private String author;
		private int year;
	}

}
