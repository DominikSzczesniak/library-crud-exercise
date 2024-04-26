package dominik.szczesniak.libraryexercise.books.domain.model.commands;

import lombok.Builder;

import static dominik.szczesniak.libraryexercise.books.domain.model.AuthorSample.createAnyAuthor;
import static dominik.szczesniak.libraryexercise.books.domain.model.BookIdSample.createAnyBookId;
import static dominik.szczesniak.libraryexercise.books.domain.model.TitleSample.createAnyTitle;
import static dominik.szczesniak.libraryexercise.books.domain.model.YearSample.createAnyValidYearForBook;
import static java.util.Optional.ofNullable;

public class UpdateBookSample {

	@Builder
	private static UpdateBook build(final Long bookId, final String title, final String author, final Integer year) {
		return new UpdateBook(
				ofNullable(bookId).orElse(createAnyBookId()),
				ofNullable(title).orElse(createAnyTitle()),
				ofNullable(author).orElse(createAnyAuthor()),
				ofNullable(year).orElse(createAnyValidYearForBook())
		);
	}

}
