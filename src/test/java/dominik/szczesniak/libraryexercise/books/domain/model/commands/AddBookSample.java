package dominik.szczesniak.libraryexercise.books.domain.model.commands;

import lombok.Builder;

import static dominik.szczesniak.libraryexercise.books.domain.model.AuthorSample.createAnyAuthor;
import static dominik.szczesniak.libraryexercise.books.domain.model.TitleSample.createAnyTitle;
import static dominik.szczesniak.libraryexercise.books.domain.model.YearSample.createAnyValidYearForBook;
import static java.util.Optional.ofNullable;

public class AddBookSample {

	@Builder
	private static AddBook build(final String title, final String author, final Integer year) {
		return new AddBook(
				ofNullable(title).orElse(createAnyTitle()),
				ofNullable(author).orElse(createAnyAuthor()),
				ofNullable(year).orElse(createAnyValidYearForBook())
		);
	}

}
